package com.sbrf.reboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {
    private final String atmNumber;

    public Request(@JsonProperty("atmNumber") String atmNumber) {
        this.atmNumber = atmNumber;
    }

    public String getAtmNumber() {
        return atmNumber;
    }
}
