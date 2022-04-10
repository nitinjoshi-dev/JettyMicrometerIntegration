package org.example.jetty.micrometer.integration.api;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.jetty.micrometer.integration.model.HealthCheckDTO;

import java.io.IOException;

public class HealthCheckServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        var status = HealthCheckDTO.builder()
                .status("OK")
                .build();
        response.getWriter().println(gson.toJson(status));
    }

}