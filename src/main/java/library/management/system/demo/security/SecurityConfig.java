package library.management.system.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //αυτη η κλαση δηλωνει Beans
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){ //Αυτό θα χρησιμοποιηθεί: 1) στο register για να αποθηκεύεις encoded password | 2) στο authentication για να γίνει match
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(csrf-> csrf.disable())  // CSRF είναι επίθεση που αφορά κυρίως: 1) browser-based sessions 2) cookies που στέλνονται αυτόματα,  --- Stateless APIs + JWT → συνήθως csrf.disable()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // STATELESS = Μην αποθηκεύεις authentication σε session στο server
                .authorizeHttpRequests(auth -> auth //ποιος μπαίνει πού
                        .requestMatchers("/api/users/register","/swagger-ui/**", "/v3/api-docs/**","/api/auth/login").permitAll() //public endpoints - NO LOGIN
                        .requestMatchers("/api/books/**").authenticated().anyRequest().authenticated()
                )
                //.httpBasic(Customizer.withDefaults()); //httpBasic(Customizer.withDefaults() = Χρησιμοποίησε HTTP Basic authentication , Δηλαδή για protected endpoints, το client στέλνει
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                return httpSecurity.build();
    }
}
