package org.example.jetty.micrometer.integration.api;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MetricsAPIServlet extends HttpServlet {

    private final PrometheusMeterRegistry prometheusRegistry;

    public MetricsAPIServlet(PrometheusMeterRegistry prometheusRegistry) {
        this.prometheusRegistry = prometheusRegistry;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        String metricResponse = prometheusRegistry.scrape();

        response.getWriter().println(metricResponse);
    }
}
