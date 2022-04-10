package org.example.jetty.micrometer.integration.api;

import com.google.gson.Gson;
import io.micrometer.core.instrument.Timer;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.jetty.micrometer.integration.model.GreetingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GreetingServlet extends HttpServlet {

    public static final Logger logger = LoggerFactory.getLogger(GreetingServlet.class);
    private final PrometheusMeterRegistry prometheusRegistry;

    private final Gson gson;

    public GreetingServlet(PrometheusMeterRegistry prometheusRegistry) {
        this.prometheusRegistry = prometheusRegistry;
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        long startTime = System.currentTimeMillis();
        Timer timer = prometheusRegistry.timer("jettyservice.greetings", "tag1Key", "tag1Value");
        var responseJson = performAction();
        timer.record(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);
        response.getWriter().println(gson.toJson(responseJson));
    }

    private GreetingDTO performAction() {
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            logger.error("Error in thread sleep " + e.getMessage(), e);
        }
        return GreetingDTO.builder()
                .currentTime(String.valueOf(System.currentTimeMillis()))
                .message("Hello")
                .user("Random user")
                .build();
    }
}
