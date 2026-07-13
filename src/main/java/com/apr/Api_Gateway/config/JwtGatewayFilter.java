package com.apr.Api_Gateway.config;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Component
public class JwtGatewayFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    private final JwtUtil jwtUtil;

    public JwtGatewayFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public ServerResponse filter(ServerRequest request, HandlerFunction<ServerResponse> next) throws Exception {
        // Rutas de auth (registro, login y validación) y Swagger/OpenAPI no requieren token
        String path = request.path();
        if (path.equals("/auth/login") || path.equals("/auth/validar") || 
            (path.startsWith("/auth") && request.method().name().equals("POST")) ||
            path.contains("/swagger-ui") || path.contains("/v3/api-docs") || 
            path.equals("/swagger-ui.html") || path.contains("/api-docs-proxy")) {
            return next.handle(request);
        }

        // Obtenemos el header de Authorization
        List<String> authHeaders = request.headers().header("Authorization");
        if (authHeaders.isEmpty()) {
            return ServerResponse.status(HttpStatus.UNAUTHORIZED).body("Token no proporcionado");
        }

        String authHeader = authHeaders.get(0);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ServerResponse.status(HttpStatus.UNAUTHORIZED).body("Formato de token inválido");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validarToken(token)) {
            return ServerResponse.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado");
        }

        String email = jwtUtil.extraerEmail(token);
        String rol = jwtUtil.extraerRol(token);
        Long id = jwtUtil.extraerId(token);

        // Propagamos los datos extraídos en headers personalizados para los servicios de negocio
        ServerRequest modifiedRequest = ServerRequest.from(request)
                .header("X-User-Email", email)
                .header("X-User-Role", rol)
                .header("X-User-Id", id != null ? id.toString() : "")
                .build();

        return next.handle(modifiedRequest);
    }
}
