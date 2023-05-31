# Pizza Toppings Ranking Application

This is a Spring Boot application written in Kotlin that uses Redis to store and rank pizza toppings based on user requests. The application stores these choices in a Redis data store (configured to persist data on disk) using the ZSET data structure, which automatically ranks items based on a score. When users submit their pizza toppings preferences, the application updates the score of each topping accordingly, and retrieves the toppings sorted by their scores.

## Technologies Used

- Spring Boot
- Kotlin
- Redis
- Docker

## Features

- Users can submit their preferred pizza toppings.
- The application stores these toppings and their occurrences in Redis.
- The application can return a ranking of pizza toppings.

## Demo

[![IMAGE ALT TEXT](http://img.youtube.com/vi/2dRLwXHLdIU/2.jpg)](http://www.youtube.com/watch?v=2dRLwXHLdIU "pizza toppings ranking - Kotlin, Spring boot, Redis ZSet, Docker compose")

https://www.youtube.com/watch?v=2dRLwXHLdIU

## TODO List

- [ ] :mag: Add speed parameter
- [ ] :mag: Coins balance per user
- [ ] :mag: Add leaderboard for coin accumulation across users
- [ ] :mag: Add blockchain based elements distribution

## How to Run the Application

### Prerequisites

Make sure you have the following installed:

- Java 17
- Gradle
- Docker

### Steps

1. Clone the repository:

```bash
git clone https://github.com/maxb2718/pizza-toppings-ranking.git
```
2. Navigate to the project directory:

```bash
cd pizza-toppings-ranking
```

3. Build the app

```bash
./build.sh
```

4. Run the app

```bash
./run.sh
```
Now the application should be running at http://localhost:8080 and the toppings dashboard is accessible at http://localhost:8888.

## API Endpoints
* `POST /toppings`: Submit a new pizza topping request.
* `GET /toppings`: Get a ranking of pizza toppings.

### Example of Usage
```bash
curl -X POST -H "Content-Type: application/json" -d '{"email": "test@example.com", "toppingsContent": "Pepperoni, Onions, Mushrooms"}' http://localhost:8080/toppings
```
You can then view the rankings:
```bash
curl http://localhost:8080/toppings
```

## Testing
You can send dozens test requests and populate the DB by using following command.
```bash
./add_toppings.sh
```