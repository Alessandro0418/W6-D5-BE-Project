package it.epicode.W6_D5_BE_Project.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.epicode.W6_D5_BE_Project.model.User;
import it.epicode.W6_D5_BE_Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTool {
    //classe gestita direttamente da Spring per la gestione del token

    @Value("${jwt.duration}")
    private long durata;

    @Value("${jwt.secret}")
    private String chaiveSegreta;

    @Autowired
    private UserService userService;

    public String createToken(User user){
        //per generare il token avremo bisogno della data di generazione del token, della durata e dell'id dell'utente per il quale stiamo creando il token.
        //Avremo inoltre bisogno anche della chiave segreta, per poter crittografare il token.

        return Jwts.builder().issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+durata))
                .subject(user.getId()+"")
                .signWith(Keys.hmacShaKeyFor(chaiveSegreta.getBytes()))
                .compact();
    }

    //metodo per la verifica della validità del token
    public void validazioneToken(String token){
        Jwts.parser().verifyWith(Keys.hmacShaKeyFor(chaiveSegreta.getBytes()))
                .build().parse(token);
    }

    public User getUserFromToken(String token) throws ChangeSetPersister.NotFoundException {
        //recupero l'id dell'utente dal token
        int id = Integer.parseInt(Jwts.parser().verifyWith(Keys.hmacShaKeyFor(chaiveSegreta.getBytes())).
                build().parseSignedClaims(token).getPayload().getSubject());

        return userService.getUser((long) id);
    }
}