package org.example.jetty.micrometer.integration;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.jetty.micrometer.integration.api.GreetingServlet;
import org.example.jetty.micrometer.integration.api.HealthCheckServlet;
import org.example.jetty.micrometer.integration.api.MetricsAPIServlet;
import org.example.jetty.micrometer.integration.config.MetricsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    public static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {

        int port = 8082;
        logger.info("Starting the jetty server on port " + port);
        var registry = MetricsFactory.getInstance().getRegistry();

        var handler = new ServletHandler();
        handler.addServletWithMapping(HealthCheckServlet.class, "/status");
        handler.addServletWithMapping(new ServletHolder(new MetricsAPIServlet(registry)), "/metrics");
        handler.addServletWithMapping(new ServletHolder(new GreetingServlet(registry)), "/greeting");

        var resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        resourceHandler.setResourceBase(".");

        var handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, handler});

        var server = new Server(port);
        server.setHandler(handlers);

        server.start();
        server.join();

    }

}