package com.example.claims.security;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.Priorities;
import jakarta.annotation.Priority;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtFilter implements ContainerRequestFilter {

    private final JwtService jwtService = new JwtService();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String path = requestContext.getUriInfo().getPath();

        // On laisse le login et le hello accessibles sans token
        if (path.equals("auth/login") || path.equals("hello")) {
            return;
        }

        // On laisse les requêtes OPTIONS (préflight) accessibles sans token
        if ("OPTIONS".equalsIgnoreCase(requestContext.getMethod())) {
            return;
        }

        String authHeader = requestContext.getHeaderString("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Token JWT manquant")
                            .build());
            return;
        }

        String token = authHeader.substring("Bearer ".length());

        try {
            jwtService.verifyToken(token);
        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Token JWT invalide ou expiré")
                            .build());
        }
    }
}