# IPrivado
This dating application is a robust platform designed to connect two primary types of users:

Couples Seeking Connections: Users looking to find and interact with potential matches.

Content Sellers: Users selling content, which can overlap with the first type.

The application's primary feature is a real-time chat system that facilitates communication between users who express mutual interest.

Key Features

1. User Preferences

Users can set personalized filters, such as:

Age Range: ageFrom and ageTo values to specify the desired age range.

Sex Preference: Users can specify their gender preference for potential matches.

2. Real-Time Chat

The chat system is the application's core, allowing matched users to communicate instantly.

Built with Apache Kafka and WebSockets for scalable and efficient messaging.

3. City Autocomplete

Integrated autocomplete feature for selecting cities in the filter form.

Backend support enables city search by name and country.

4. Match Engine

Facilitates matches between couples and/or content sellers based on mutual interests.

5. Modular Frontend

The frontend is built with Angular 17+ and Angular Material for a seamless user experience.

Organized into modules such as:

Home Layout (layout-home-page.component), containing sections like:

filter-content-page

meeting-room-content-page

public-feed-content-page

navbar-content-page

post-free-content-page

suggestion-content-page

Technology Stack

Backend

Spring Boot: Provides the framework for building REST APIs and microservices.

MySQL: Stores structured data such as user profiles, preferences, and matches.

MongoDB: Handles large datasets of cities globally within the ms-api-ext microservice.

Apache Kafka: Ensures real-time messaging capabilities in the chat system.

Docker: Used for containerizing services, including MongoDB and other microservices.

Frontend

Angular 17+: Offers a modern and dynamic user interface.

Angular Material: Enhances the visual appeal and functionality of UI components.

Cloud Infrastructure

Initially developed on local infrastructure, with plans to migrate to AWS for scalability and reliability.

Microservices Architecture

1. ms-users

Manages user profiles, including preferences and matches.

Provides APIs for:

Retrieving user data by ID.

Receiving messages without requiring a frontend implementation.

2. ms-free-area

Handles public user areas.

Integrated with the User entity from ms-users.

Contains a list of public content.

3. ms-producer

Sends chat messages through the chat-topic in Kafka.

Provides an endpoint at:

POST http://localhost:8082/api/v1/web/sendMessage

4. ms-consumer

Listens to messages on the chat-topic.

Retrieves user data via the UserClient interface from ms-users.

Forwards messages to recipients using the WebSocket service.

5. ms-api-ext

Stores a JSON dataset of global cities in MongoDB.

Provides APIs to filter cities by name and country.

Integrated with the city autocomplete feature in Angular.

How It Works

User Registration:

Users create profiles and set preferences.

Finding Matches:

The system uses the match engine to connect users based on preferences.

City Search:

Users filter potential matches using the autocomplete-enabled city input.

Real-Time Chat:

Matched users can exchange messages instantly using Kafka and WebSockets.

Content Selling:

Sellers can interact with potential buyers while also being eligible for matches.

Deployment and Future Plans

Local Development

The application is currently being developed and tested on local infrastructure.

Cloud Migration

Planned migration to AWS to leverage cloud-based scalability and resilience.

Docker Integration

The ms-api-ext microservice uses MongoDB, which can be containerized for deployment using Docker.

Conclusion

This dating application combines modern technologies and a microservices architecture to deliver a scalable and user-friendly platform. By focusing on modular design and leveraging tools like Kafka, MongoDB, and Angular, the system ensures an efficient and enjoyable user experience for all.

