package org.example.jetty.micrometer.integration.config;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;

import java.util.List;

public class CustomConfiguration {

    String deploymentEnv = "local";

    /**
     * Define common tags that apply only to a Prometheus Registry
     */
    public MeterFilter configurePrometheusRegistries() {
        return MeterFilter.commonTags(List.of(
                Tag.of("registry", "prometheus")));
    }

    public MeterFilter configureAllRegistries() {
        return MeterFilter.commonTags(List.of(
                Tag.of("env", deploymentEnv)));
    }

    public MeterFilter enableHistogram() {
        return new MeterFilter() {
            @Override
            public DistributionStatisticConfig configure(Meter.Id id, DistributionStatisticConfig config) {
                if (id.getName().startsWith("jettyservice")) {
                    return DistributionStatisticConfig.builder()
                            .percentiles(0.5, 0.95, 0.99)     // median, 95th and 99th percentile
                            .build()
                            .merge(config);
                }
                return config;
            }
        };
    }
}