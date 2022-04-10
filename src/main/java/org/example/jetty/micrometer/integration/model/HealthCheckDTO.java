package org.example.jetty.micrometer.integration.model;

import lombok.Builder;

@Builder
public class HealthCheckDTO extends BaseDTO {
    private String status;
}
