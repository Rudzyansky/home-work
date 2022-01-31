package com.sbrf.reboot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sbrf.reboot.dto.Request;
import com.sbrf.reboot.dto.Response;

public class XMLUtils {
    private final static XmlMapper xmlMapper = new XmlMapper();

    public static String toXML(Request request) throws JsonProcessingException {
        return xmlMapper.writeValueAsString(request);
    }

    public static String toXML(Response response) throws JsonProcessingException {
        return xmlMapper.writeValueAsString(response);
    }

    public static Request XMLtoRequest(String xml) throws JsonProcessingException {
        return xmlMapper.readValue(xml, Request.class);
    }

    public static Response XMLtoResponse(String xml) throws JsonProcessingException {
        return xmlMapper.readValue(xml, Response.class);
    }
}
