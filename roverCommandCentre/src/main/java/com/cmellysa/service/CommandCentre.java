package com.cmellysa.service;

import com.cmellysa.dto.RoverData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommandCentre {

    private static final Logger logger = LoggerFactory.getLogger(CommandCentre.class);
    private List<RoverData> roverDataList = new ArrayList<>();
    private final RoverProducerService roverProducerService;

    @Autowired
    public CommandCentre(RoverProducerService roverProducerService) {
        this.roverProducerService = roverProducerService;
    }

    public void processCommandLineArgs(String[] args) {
        if (args.length % 2 != 0) {
            logger.error("Invalid input!");
            return;
        }

        int num_rovers = args.length / 2;

        for (int i = 0; i < num_rovers; i++) {
            RoverData roverData = new RoverData();
            roverData.setRoverId("Rover" + (i+1));

            int roverPosIndex = 2 * i;
            if (roverPosIndex < args.length && isPositionValid(args[roverPosIndex])) {
                roverData.setPosition(args[roverPosIndex]);
            } else {
                logger.warn("Rover {} starting position is incorrect.", i+1);
                continue;
            }

            int roverCmdIndex = roverPosIndex + 1;
            if (roverCmdIndex < args.length && isCommandsValid(args[roverCmdIndex])) {
                roverData.setCommands(args[roverCmdIndex]);
            } else {
                logger.warn("Rover {} has invalid commands. ", i+1);
                continue;
            }

            roverDataList.add(roverData);
        }

        roverProducerService.sendRoverCommands(roverDataList);
    }

    public boolean isPositionValid(String pos) {
        String[] position = pos.split(",");
        if (position.length == 3) {
            char firstChar = position[0].charAt(0);
            char secondChar = position[1].charAt(0);
            char thirdChar = Character.toUpperCase(position[2].charAt(0));

            boolean areFirstTwoDigits = Character.isDigit(firstChar) && Character.isDigit(secondChar);
            boolean isThirdCharValid = (thirdChar == 'N' || thirdChar == 'S' || thirdChar == 'E' || thirdChar == 'W');

            return areFirstTwoDigits && isThirdCharValid;
        }

        return false;
    }

    public boolean isCommandsValid(String command) {
        Set<Character> validCommands = new HashSet<>(Arrays.asList('r', 'b', 'l', 'f'));
        char[] commands = command.replaceAll(",", "").toLowerCase().toCharArray();

        for (char c : commands) {
            if (!validCommands.contains(c)) {
                return false;
            }
        }

        return true;
    }
}
