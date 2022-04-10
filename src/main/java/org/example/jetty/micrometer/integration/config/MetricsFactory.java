package org.example.jetty.micrometer.integration.config;

import io.github.mweirauch.micrometer.jvm.extras.ProcessMemoryMetrics;
import io.github.mweirauch.micrometer.jvm.extras.ProcessThreadMetrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

public class MetricsFactory {

    private final PrometheusMeterRegistry registry;

    private MetricsFactory() {
        this.registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
        var metricConfig = new CustomConfiguration();
        registry.config().meterFilter(metricConfig.enableHistogram());
        registry.config().meterFilter(metricConfig.configureAllRegistries());
        registry.config().meterFilter(metricConfig.configurePrometheusRegistries());
        /* Plain Java */
        new ProcessMemoryMetrics().bindTo(registry);
        new ProcessThreadMetrics().bindTo(registry);
    }

    public static MetricsFactory getInstance() {
        return MetricsFactoryHolder.metricsFactory;
    }

    public PrometheusMeterRegistry getRegistry() {
        return registry;
    }

    private static final class MetricsFactoryHolder {
        private static final MetricsFactory metricsFactory = new MetricsFactory();
    }


}
