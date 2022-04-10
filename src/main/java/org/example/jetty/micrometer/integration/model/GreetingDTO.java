package org.example.jetty.micrometer.integration.model;

import lombok.Builder;

@Builder
public class GreetingDTO extends BaseDTO {
    private final String message;
    private final String user;
    private final String currentTime;
}
