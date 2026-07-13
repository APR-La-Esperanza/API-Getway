package com.apr.Api_Gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SwaggerProxyController {

    @Value("${service.auth.url:http://localhost:8081}")
    private String authUrl;

    @Value("${service.socio.url:http://localhost:8082}")
    private String socioUrl;

    @Value("${service.facturacion.url:http://localhost:8083}")
    private String facturacionUrl;

    @Value("${service.pago.url:http://localhost:8084}")
    private String pagoUrl;

    @Value("${service.morosidad.url:http://localhost:8085}")
    private String morosidadUrl;

    @Value("${service.incidencia.url:http://localhost:8089}")
    private String incidenciaUrl;

    @Value("${service.reparacion.url:http://localhost:8090}")
    private String reparacionUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("unchecked")
    @GetMapping("/api-docs-proxy/{serviceName}")
    public ResponseEntity<Map<String, Object>> getOpenApiDocs(@PathVariable String serviceName) {
        String targetUrl = getServiceUrl(serviceName) + "/v3/api-docs";
        try {
            Map<String, Object> docs = restTemplate.getForObject(targetUrl, Map.class);
            if (docs != null) {
                // Modificar dinámicamente los servidores para que apunten al Gateway
                Map<String, Object> modifiedDocs = new HashMap<>(docs);
                
                // Mapeo del prefijo según el servicio
                String prefix = "/" + serviceName.toLowerCase().replace("-service", "");
                if (prefix.equals("/facturacion")) {
                    prefix = "/facturas";
                } else if (prefix.equals("/incidencia")) {
                    prefix = "/incidencias";
                } else if (prefix.equals("/reparacion")) {
                    prefix = "/reparaciones";
                } else if (prefix.equals("/morosidad")) {
                    prefix = "/morosos";
                } else if (prefix.equals("/pago")) {
                    prefix = "/pagos";
                }
                
                Map<String, Object> serverMap = new HashMap<>();
                serverMap.put("url", prefix);
                serverMap.put("description", "Enrutamiento vía API Gateway");
                
                modifiedDocs.put("servers", List.of(serverMap));
                return ResponseEntity.ok(modifiedDocs);
            }
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al obtener documentación de " + serviceName);
            error.put("details", e.getMessage());
            return ResponseEntity.status(502).body(error);
        }
        return ResponseEntity.notFound().build();
    }

    private String getServiceUrl(String serviceName) {
        switch (serviceName.toLowerCase()) {
            case "auth-service": return authUrl;
            case "socio-service": return socioUrl;
            case "facturacion-service": return facturacionUrl;
            case "pago-service": return pagoUrl;
            case "morosidad-service": return morosidadUrl;
            case "incidencia-service": return incidenciaUrl;
            case "reparacion-service": return reparacionUrl;
            default: return "http://localhost";
        }
    }
}
