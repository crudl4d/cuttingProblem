package com.cutting_problem;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.TreeMap;

public class BandDto {

    @NotNull
    private Integer bandLength;

    @NotEmpty
    private TreeMap<Integer, Integer> elements;

    //todo why lombok doesnt work??
    public TreeMap<Integer, Integer> getElements() {
        return elements;
    }

    public Integer getBandLength() {
        return bandLength;
    }

}
