let stompClient = null;
let isConnected = false;
let lastCount = 0;

function fetchCurrentUserCount() {
    console.log('Fetching current user count...');
    return fetch('/api/active-users/count')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP ${response.status}: ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            console.log('API Response:', data);
            updateUserCount(data.count, data.capacityExceeded);
            updateCapacityInfo(data.count);
            console.log('Successfully updated user count to:', data.count);
            return data.count;
        })
        .catch(error => {
            console.error('Failed to fetch current user count:', error);
            updateConnectionStatus(false, 'API Error');
            // Set count to 0 on error
            updateUserCount(0);
            updateCapacityInfo(0);
            throw error;
        });
}

function updateUserCount(count, capacityExceeded = false) {
    const countElement = document.getElementById('userCount');
    const formattedCount = formatUserCount(count);

    // Add animation if count changed
    if (count !== lastCount) {
        countElement.classList.add('update-animation');
        setTimeout(() => {
            countElement.classList.remove('update-animation');
        }, 600);
    }

    countElement.innerText = formattedCount;
    lastCount = count;

    // Update main counter background based on capacity
    const mainCounter = document.querySelector('.main-counter');
    if (capacityExceeded) {
        mainCounter.style.background = 'linear-gradient(135deg, #f5576c, #f093fb)';
    } else if (count > 800000) {
        mainCounter.style.background = 'linear-gradient(135deg, #f093fb, #f5576c)';
    } else {
        mainCounter.style.background = 'linear-gradient(135deg, #667eea, #764ba2)';
    }
}

function updateCapacityInfo(count) {
    const maxCapacity = 1000000;
    const percentage = Math.min((count / maxCapacity) * 100, 100);
    const capacityPercent = document.getElementById('capacityPercent');

    capacityPercent.innerText = percentage.toFixed(1) + '%';

    // Update color based on capacity
    if (percentage >= 100) {
        capacityPercent.style.color = '#f5576c';
    } else if (percentage >= 80) {
        capacityPercent.style.color = '#f093fb';
    } else {
        capacityPercent.style.color = '#718096';
    }
}

function updateConnectionStatus(connected, message = '') {
    const statusDot = document.getElementById('statusDot');
    const statusText = document.getElementById('statusText');

    if (connected) {
        statusDot.style.background = '#48bb78';
        statusText.style.color = '#48bb78';
        statusText.innerText = 'Connected';
        statusDot.style.animation = 'blink 2s infinite';
    } else {
        statusDot.style.background = '#f5576c';
        statusText.style.color = '#f5576c';
        statusText.innerText = message || 'Disconnected';
        statusDot.style.animation = 'none';
    }
}

function formatUserCount(count) {
    if (count >= 1000000) {
        return (count / 1000000).toFixed(1) + 'M';
    } else if (count >= 1000) {
        return (count / 1000).toFixed(1) + 'K';
    }
    return count.toLocaleString();
}

function connect() {
    if (isConnected) {
        console.log("Already connected, skipping connection attempt");
        return;
    }

    updateConnectionStatus(false, 'Connecting...');

    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        isConnected = true;
        updateConnectionStatus(true);
        console.log("WebSocket connected successfully");

        // Subscribe to user count updates
        stompClient.subscribe('/topic/activeUsers', function (message) {
            const count = parseInt(message.body);
            console.log("WebSocket received user count update:", count);
            updateUserCount(count);
            updateCapacityInfo(count);
        });

        // Request current count immediately after WebSocket connection to ensure sync
        setTimeout(() => {
            console.log("Fetching count after WebSocket connection...");
            fetchCurrentUserCount().catch(err => {
                console.error("Failed to fetch count after WebSocket connection:", err);
            });
        }, 100); // Small delay to ensure subscription is ready

    }, function (error) {
        isConnected = false;
        updateConnectionStatus(false, 'Connection Failed');
        console.error('WebSocket connection failed:', error);
        setTimeout(connect, 5000); // Retry every 5 seconds
    });
}

// Add some interactive effects
function addInteractiveEffects() {
    // Add hover effects to stat cards
    const statCards = document.querySelectorAll('.stat-card');
    statCards.forEach(card => {
        card.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-4px) scale(1.02)';
        });

        card.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0) scale(1)';
        });
    });

    // Add click effect to main counter
    const mainCounter = document.querySelector('.main-counter');
    mainCounter.addEventListener('click', function() {
        fetchCurrentUserCount();
        this.style.transform = 'scale(0.98)';
        setTimeout(() => {
            this.style.transform = 'scale(1)';
        }, 150);
    });

    // Add floating animation to tech badges
    const techBadges = document.querySelectorAll('.tech-badge');
    techBadges.forEach((badge, index) => {
        badge.style.animationDelay = `${index * 0.2}s`;
        badge.style.animation = 'float 3s ease-in-out infinite';
    });
}

// Initialize everything in proper sequence
async function initialize() {
    console.log('Initializing application...');

    try {
        // First, fetch the initial count
        await fetchCurrentUserCount();
        console.log('Initial count fetched successfully');

        // Then connect to WebSocket
        connect();
        console.log('WebSocket connection initiated');

    } catch (error) {
        console.error('Failed to initialize:', error);
        // Still try to connect to WebSocket even if API fails
        connect();
    }

    // Add interactive effects when DOM is ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', addInteractiveEffects);
    } else {
        addInteractiveEffects();
    }

    // Update timestamp periodically
    setInterval(() => {
        const now = new Date();
        console.log(`System active at ${now.toLocaleTimeString()}`);
    }, 30000); // Every 30 seconds
}

// Start initialization when DOM is ready
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initialize);
} else {
    initialize();
}
