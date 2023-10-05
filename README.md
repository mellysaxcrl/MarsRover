# MarsRover

## Overview

This project simulates the movement of Mars rovers using Kafka for communication between the roverCommandCentre and roverAnalyzer services.

## How to Run

### Prequisite
Before running Kafka clients, you need to download Kafka onto your machine and start both ZooKeeper and the Kafka broker.

You can customize the Kafka bootstrap server configuration by modifying the `application.properties` file. By default, it is set to `localhost:9092`, but you can change it to suit your specific Kafka setup.

### Running the Producer (roverCommandCentre)

To run the producer with a single rover, use the following command:

```sh
mvn spring-boot:run -Dspring-boot.run.arguments="3,4,N f,f,r,f,f" 
```

This command initializes one rover with the given initial position and commands.

To run the producer with multiple rovers, provide their positions and commands delimited by spaces, as shown below:

```shell
mvn spring-boot:run -Dspring-boot.run.arguments="3,4,N f,f,r,f,f 3,5,E r,r,b 5,6,N r,l"
```

Debugging the Producer
To debug the producer, you can use the following command:

```shell
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005" -Dspring-boot.run.arguments="3,4,N f,f,r,f,f"
```

### Running the Consumer (roverAnalyzer)
To run the consumer using Maven, execute the following command:

```shell
mvn spring-boot:run
```

To run the consumer using the JAR file, follow these steps:

1. Navigate to the target directory:
`cd target`
2. Run the JAR file: `java -jar roverAnalyzer-1.0-SNAPSHOT.jar`

## Technology Used
Kafka: Used for communication between roverCommandCentre and roverAnalyzer services.

## Project Structure

The project is organized into the following components:

- **MarsRover**
    - **roverCommandCentre**: The producer component responsible for sending rover commands.
        - ...
    - **roverAnalyzer**: The consumer component responsible for analyzing rover movements.
        - ...
- **target**: Contains the JAR files generated by Maven for running the producer and consumer.
    - `roverCommandCentre-1.0-SNAPSHOT.jar`
    - `roverAnalyzer-1.0-SNAPSHOT.jar`
    - ...
- **README.md**: This documentation file.
