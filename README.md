API-Gateway (puerto 8080, sin base de datos)
Responsabilidad: Punto de entrada único del ecosistema (Puerta de enlace). Realiza el ruteo de peticiones a los servicios correspondientes, valida la firma del token JWT en cada solicitud, y propaga headers de identidad (X-User-Id, X-User-Role, X-User-Email) hacia los servicios downstream.
Configuración:


GatewayConfig.java
 — mapea los paths (ej: /auth/** -> lb://auth-service, /socios/** -> lb://socio-service).
Filtros:
JwtGatewayFilter — intercepta las peticiones, extrae el token del header Authorization, valida su firma, extrae los claims del usuario y los añade a las cabeceras HTTP redirigidas.
Dependencias externas:
Se registra en Eureka-Server.
Auth-Service (para validar tokens y verificar credenciales si es necesario).
Tabla BD: Ninguna.
