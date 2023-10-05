package com.cmellysa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoverData {
    private String roverId;
    private String position;
    private String commands;

}
