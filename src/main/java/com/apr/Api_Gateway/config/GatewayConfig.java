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
    public RouterFunction<ServerResponse> authRoute() {
        return route("auth_service_route")
                .route(req -> req.path().startsWith("/auth"), http())
                .before(uri(URI.create("http://localhost:8081")))
                .filter(jwtGatewayFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> socioRoute() {
        return route("socio_service_route")
                .route(req -> req.path().startsWith("/socios"), http())
                .before(uri(URI.create("http://localhost:8082")))
                .filter(jwtGatewayFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> facturacionRoute() {
        return route("facturacion_service_route")
                .route(req -> req.path().startsWith("/facturas"), http())
                .before(uri(URI.create("http://localhost:8083")))
                .filter(jwtGatewayFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> pagoRoute() {
        return route("pago_service_route")
                .route(req -> req.path().startsWith("/pagos"), http())
                .before(uri(URI.create("http://localhost:8084")))
                .filter(jwtGatewayFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> morosidadRoute() {
        return route("morosidad_service_route")
                .route(req -> req.path().startsWith("/morosos"), http())
                .before(uri(URI.create("http://localhost:8085")))
                .filter(jwtGatewayFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> reporteRoute() {
        return route("reporte_service_route")
                .route(req -> req.path().startsWith("/reportes"), http())
                .before(uri(URI.create("http://localhost:8086")))
                .filter(jwtGatewayFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> operacionRoute() {
        return route("operacion_service_route")
                .route(req -> req.path().startsWith("/operaciones"), http())
                .before(uri(URI.create("http://localhost:8087")))
                .filter(jwtGatewayFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> balanceHidricoRoute() {
        return route("balance_hidrico_service_route")
                .route(req -> req.path().startsWith("/balance"), http())
                .before(uri(URI.create("http://localhost:8088")))
                .filter(jwtGatewayFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> incidenciaRoute() {
        return route("incidencia_service_route")
                .route(req -> req.path().startsWith("/incidencias"), http())
                .before(uri(URI.create("http://localhost:8089")))
                .filter(jwtGatewayFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> reparacionRoute() {
        return route("reparacion_service_route")
                .route(req -> req.path().startsWith("/reparaciones"), http())
                .before(uri(URI.create("http://localhost:8090")))
                .filter(jwtGatewayFilter)
                .build();
    }
}
