package com.apr.Api_Gateway.config;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class GatewayConfig {

    private final JwtGatewayFilter jwtGatewayFilter;

    public GatewayConfig(JwtGatewayFilter jwtGatewayFilter) {
        this.jwtGatewayFilter = jwtGatewayFilter;
    }

    @Bean
    public RouterFunction<ServerResponse> gatewayRoutes() {
        return route("auth_service_route")
                .route(req -> req.path().startsWith("/auth"), http())
                .before(uri(URI.create("http://localhost:8081")))
                .filter(jwtGatewayFilter)
                .build();
    }
}
