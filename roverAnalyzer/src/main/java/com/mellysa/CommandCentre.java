package com.mellysa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class CommandCentre {
    private static final Logger logger = LoggerFactory.getLogger(CommandCentre.class);

    ConcurrentHashMap<Pair<Integer, Integer>, String> occupiedCoordinates = new ConcurrentHashMap<>();

    @KafkaListener(
            topics = "mars-rover",
            groupId = "my-consumer-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void processRoverData(String roverCommands, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey) {
        if (roverCommands.isEmpty() || messageKey.isEmpty()) {
            return;
        }

        String[] roverInfo = roverCommands.split(":");
        String position = roverInfo[0];

        Position roverPos = parsePosition(position);
        char[] commands = roverInfo[1].replace(",", "").toCharArray();

        if (isOccupied(roverPos.getCoordinates(), roverPos.getDirection(), messageKey)) {
            logger.info(messageKey + " has collided with another rover");
            return;
        }

        for (int i = 0; i < commands.length; i++) {
            occupiedCoordinates.put(roverPos.getCoordinates(), messageKey);

            char command = commands[i];

            Position updatedPos = MovementRules.applyMovement(roverPos, command);

            if (isOccupied(updatedPos.getCoordinates(), updatedPos.getDirection(), messageKey)) {
                logger.info(messageKey + " has collided with another rover");
                return;
            }

            occupiedCoordinates.remove(roverPos.getCoordinates());
            roverPos = updatedPos;
        }

        printFinalPosition(roverPos.getCoordinates(), roverPos.getDirection());

    }

    private Position parsePosition(String pos) {
        String[] elements = pos.split(",");
        int x = Integer.parseInt(elements[0]);
        int y = Integer.parseInt(elements[1]);
        char direction = elements[2].charAt(0);
        return new Position(x, y, direction);
    }

    private boolean isOccupied(Pair<Integer, Integer> coordinates, char direction, String roverID) {
        if (occupiedCoordinates.containsKey(coordinates) && !occupiedCoordinates.get(coordinates).equals(roverID)) {
            printFinalPosition(coordinates, direction);
            return true;
        }
        return false;
    }

    private void printFinalPosition(Pair<Integer, Integer> coordinates, char direction) {
        logger.info("Final Coordinate: " + coordinates.toString());
        logger.info("Final Direction: " + direction);
    }

}
