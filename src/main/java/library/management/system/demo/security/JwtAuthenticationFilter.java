package library.management.system.demo.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import library.management.system.demo.entity.User;
import library.management.system.demo.repository.UserRepository;
import library.management.system.demo.services.JwtService.JWTService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); //το Bearer ειναι 7 χαρακτηρες (6 συν κενο --> "Bearer ")
        Claims claims;

        try{
            claims = jwtService.parseToken(token); //validate το token με το Claims  !
            // To parseToken Κανει -->verify signature με secret key , --> ελέγχει αν έχει λήξει (expiration), --> αν όλα OK → σου γυρνάει τα Claims (payload) |  Ειδαλλως exception
        }catch(Exception ex){
            filterChain.doFilter(request, response);
            return;
        }

        UUID userId = UUID.fromString(claims.get("userId", String.class));

        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            filterChain.doFilter(request, response);
            return;
        }

        //“Χτίζει” Authentication object και το βάζει στο SecurityContext
        CustomUserDetails userDetails = new CustomUserDetails(user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response); //πό εδώ και πέρα, το Spring θεωρεί ότι ο χρήστης είναι authenticated για αυτό το request.

    }
}
