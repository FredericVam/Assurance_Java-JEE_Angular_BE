package com.example.claims.resource;

import com.example.claims.entity.User;
import com.example.claims.repository.UserRepository;
import com.example.claims.security.JwtService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    private final UserRepository userRepository = new UserRepository();
    private final JwtService jwtService = new JwtService();

    @POST
    @Path("/login")
    public Response login(Map<String, String> credentials) {

        String username = credentials.get("username");
        String password = credentials.get("password");

        User user = userRepository.findByUsername(username);

        if (user == null || !user.getPassword().equals(password) || !user.isEnabled()) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Identifiants invalides")
                    .build();
        }

        String token = jwtService.generateToken(user.getUsername(), user.getRole());

        return Response.ok(Map.of("token", token)).build();
    }
}