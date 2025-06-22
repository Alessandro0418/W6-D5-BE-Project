package it.epicode.W6_D5_BE_Project.security;

import it.epicode.W6_D5_BE_Project.exeption.UnAuthorizedExeption;
import it.epicode.W6_D5_BE_Project.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTool jwtTool;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /*
        1. Verificare se la richiesta ha il token.
        2. Se non ha il token, accezione.
        3. Se ha il token, viene verificato che il token sia valido. Se non è valido, eccezione.
        4. Se il token è valido, allora si farà accedere la richiesta ai filtri successivi.
        */

        String authorization = request.getHeader("Authorization");

        if (authorization==null || !authorization.startsWith("Bearer ")){
            throw new UnAuthorizedExeption("Token non presente, non sei autorizzato ad usare il serivizio richiesto");
        } else {
            //estraggo il token dalla stringa authorization che contiene anceh la parola "Bearer  " prima del token, per questo prendo solo la parte della stringa che comincia dal carattere 7
            String token = authorization.substring(7);

            //verifico che il token sia valido
            jwtTool.validazioneToken(token);

            try {
                User user = jwtTool.getUserFromToken(token);
                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (UsernameNotFoundException | ChangeSetPersister.NotFoundException e) {
                throw new UnAuthorizedExeption("Utente collegato al token non trovato");
            }

            filterChain.doFilter(request, response);
        }
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}