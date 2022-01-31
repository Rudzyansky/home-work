package com.sbrf.reboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
    private final String statusCode;

    public Response(@JsonProperty("statusCode") String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
