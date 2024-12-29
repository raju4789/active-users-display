# Real-Time Active User Counter System with Spring Boot & Redis

This project implements a **real-time active user counter system** using **Spring Boot**, **Redis**, and **WebSockets**. It demonstrates how to manage and display the number of active users on a website in real time. The system is designed to scale and efficiently handle millions of concurrent users.

## Overview

This repository contains the source code for building a system that tracks and broadcasts the number of active users on a website. By utilizing **WebSockets** for real-time communication and **Redis** for storing active user sessions, this system can efficiently manage a large volume of users while providing real-time updates.

## Key Features

- **Real-Time Updates**: The active user count is updated in real time without requiring page refreshes.
- **Scalable**: The system leverages **Redis** for high-speed data storage and **Spring Boot** for handling concurrent WebSocket connections.
- **Cross-Platform Compatibility**: The system works on web browsers (PCs, tablets, and mobile devices).
- **Production-Ready**: The implementation is scalable, and I discuss how to adjust it for handling millions of active users.

## Design and Architecture

In my **[first Medium post](https://medium.com/@narasimha4789/from-zero-to-millions-designing-a-real-time-active-user-counter-system-a5d9545bd35e)**, I dive into the **design** and **architecture** of the system, explaining how to:

- Define "active users"
- Choose the right technologies (Spring Boot, Redis, WebSockets, etc.)
- Achieve real-time communication
- Scale the system to handle millions of users
- Handle WebSocket connections, Redis integration, and horizontal scaling

## Implementation

In the **[second Medium post](https://medium.com/@narasimha4789/implementation-building-real-time-active-user-counter-system-390940e0dda6)**, I walk through the step-by-step implementation of the system, including:

- Setting up Spring Boot with Redis integration
- Configuring WebSocket connections for real-time updates
- Implementing frontend using **Thymeleaf** templates to display active user count
- Testing the system locally and ensuring real-time functionality
- Steps to scale the application for production environments

## Getting Started

Follow these instructions to set up and run the project locally.

### Prerequisites

- Java 17 or later
- Maven 3.6 or later
- Redis (locally or via Docker)

### Clone the Repository

```bash
git clone https://github.com/your-username/active-user-counter.git
cd active-user-counter
```

### Set Up Redis

If you don't have Redis installed, you can run it using Docker:

```bash
docker run --name redis -p 6379:6379 -d redis
```

### Run the Spring Boot Application

```bash
mvn spring-boot:run
```

The application will be running at [http://localhost:8080](http://localhost:8080).

### Open Multiple Browser Tabs

Open multiple tabs in your browser to simulate multiple users. The active user count will update in real time across all tabs.

### Testing Results

During testing, the active user count updated seamlessly in real-time as we connected and disconnected browser tabs.

---

## Next Steps for Production

In the **production environment**, you would need to:

- **Scale horizontally**: Use Docker and Kubernetes to ensure the system can scale with the number of users.
- **Implement Redis clustering**: Ensure high availability of the Redis instance.
- **Monitor WebSocket connections**: Handle WebSocket reconnects and use load balancing (e.g., NGINX) for traffic distribution.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

### Links

- [Design Post on Medium]([https://medium.com/your-post-link](https://medium.com/@narasimha4789/from-zero-to-millions-designing-a-real-time-active-user-counter-system-a5d9545bd35e))
- [Implementation Post on Medium]([https://medium.com/your-post-link](https://medium.com/@narasimha4789/implementation-building-real-time-active-user-counter-system-390940e0dda6))

Feel free to **star** the repository and raise issues or contribute with pull requests!
