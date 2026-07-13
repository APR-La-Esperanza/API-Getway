package com.apr.Api_Gateway.config;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.stripPrefix;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.net.URI;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class GatewayConfig {

    private final JwtGatewayFilter jwtGatewayFilter;

    @Value("${service.auth.url:http://localhost:8081}")
    private String authServiceUrl;

    @Value("${service.socio.url:http://localhost:8082}")
    private String socioServiceUrl;

    @Value("${service.facturacion.url:http://localhost:8083}")
    private String facturacionServiceUrl;

    @Value("${service.pago.url:http://localhost:8084}")
    private String pagoServiceUrl;

    @Value("${service.morosidad.url:http://localhost:8085}")
    private String morosidadServiceUrl;

    @Value("${service.reporte.url:http://localhost:8086}")
    private String reporteServiceUrl;

    @Value("${service.operacion.url:http://localhost:8087}")
    private String operacionServiceUrl;

    @Value("${service.balance.url:http://localhost:8088}")
    private String balanceServiceUrl;

    @Value("${service.incidencia.url:http://localhost:8089}")
    private String incidenciaServiceUrl;

    @Value("${service.reparacion.url:http://localhost:8090}")
    private String reparacionServiceUrl;

    public GatewayConfig(JwtGatewayFilter jwtGatewayFilter) {
        this.jwtGatewayFilter = jwtGatewayFilter;
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    @Bean
    public RouterFunction<ServerResponse> authRoute() {
        return route("auth_service_route")
                .route(req -> req.path().startsWith("/auth"), http())
                .before(stripPrefix(1))
                .before(uri(URI.create(authServiceUrl)))
                .filter(jwtGatewayFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> socioRoute() {
        return route("socio_service_route")
                .route(req -> req.path().startsWith("/socio"), http())
                .before(stripPrefix(1))
                .before(uri(URI.create(socioServiceUrl)))
                .filter(jwtGatewayFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> facturacionRoute() {
        return route("facturacion_service_route")
                .route(req -> req.path().startsWith("/facturas"), http())
                .before(stripPrefix(1))
                .before(uri(URI.create(facturacionServiceUrl)))
                .filter(jwtGatewayFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> pagoRoute() {
        return route("pago_service_route")
                .route(req -> req.path().startsWith("/pagos"), http())
                .before(stripPrefix(1))
                .before(uri(URI.create(pagoServiceUrl)))
                .filter(jwtGatewayFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> morosidadRoute() {
        return route("morosidad_service_route")
                .route(req -> req.path().startsWith("/morosos"), http())
                .before(stripPrefix(1))
                .before(uri(URI.create(morosidadServiceUrl)))
                .filter(jwtGatewayFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> reporteRoute() {
        return route("reporte_service_route")
                .route(req -> req.path().startsWith("/reportes"), http())
                .before(stripPrefix(1))
                .before(uri(URI.create(reporteServiceUrl)))
                .filter(jwtGatewayFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> operacionRoute() {
        return route("operacion_service_route")
                .route(req -> req.path().startsWith("/operaciones"), http())
                .before(stripPrefix(1))
                .before(uri(URI.create(operacionServiceUrl)))
                .filter(jwtGatewayFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> balanceHidricoRoute() {
        return route("balance_hidrico_service_route")
                .route(req -> req.path().startsWith("/balance"), http())
                .before(stripPrefix(1))
                .before(uri(URI.create(balanceServiceUrl)))
                .filter(jwtGatewayFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> incidenciaRoute() {
        return route("incidencia_service_route")
                .route(req -> req.path().startsWith("/incidencias"), http())
                .before(stripPrefix(1))
                .before(uri(URI.create(incidenciaServiceUrl)))
                .filter(jwtGatewayFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> reparacionRoute() {
        return route("reparacion_service_route")
                .route(req -> req.path().startsWith("/reparaciones"), http())
                .before(stripPrefix(1))
                .before(uri(URI.create(reparacionServiceUrl)))
                .filter(jwtGatewayFilter)
                .build();
    }
}
