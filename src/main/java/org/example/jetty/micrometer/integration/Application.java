package org.example.jetty.micrometer.integration;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.example.jetty.micrometer.integration.api.HealthCheckServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    public static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {

        int port = 8082;
        logger.info("Starting the jetty server on port " + port);

        var handler = new ServletHandler();
        handler.addServletWithMapping(HealthCheckServlet.class, "/status");
        var resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{ "index.html" });
        resourceHandler.setResourceBase(".");
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { resourceHandler, handler});

        var server = new Server(port);
        server.setHandler(handlers);

        server.start();
        server.join();

    }

}