package com.sbrf.reboot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbrf.reboot.dto.Request;
import com.sbrf.reboot.dto.Response;

public class JSONUtils {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJSON(Request request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }

    public static String toJSON(Response response) throws JsonProcessingException {
        return objectMapper.writeValueAsString(response);
    }

    public static Request JSONtoRequest(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Request.class);
    }

    public static Response JSONtoResponse(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Response.class);
    }
}
