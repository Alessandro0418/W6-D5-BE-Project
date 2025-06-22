package it.epicode.W6_D5_BE_Project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // questa annotazione abilita la classe a essere responsabile della sicurezza dei servizi
@EnableMethodSecurity //abilita l'utilizzo della preautorizzazione direttamente sui metodi di controllo
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //formLogin serve per creare in automatico una pagina di login. A noi non serve, perché usiamo pagine, perché siamo in un servizio REST
        httpSecurity.formLogin(http->http.disable());

        //csrf serve per evitare la possibilità di utilizzi di sessioni aperte, poiché però che i servizi REST non usano sessioni lo andiamo a disabilitare
        httpSecurity.csrf(http->http.disable());

        httpSecurity.sessionManagement(http->http.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //serve per bloccare richieste che provengono da domini (indirizzo ip e porta) esterni a quelli del servizio
        httpSecurity.cors(Customizer.withDefaults());


        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/auth/**").permitAll());
// httpSecurity.authorizeHttpRequests(http->http.requestMatchers(HttpMethod.GET,"/studenti/**").permitAll());

        httpSecurity.authorizeHttpRequests(http->http.requestMatchers(HttpMethod.GET, "/studenti/**").permitAll());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers(HttpMethod.POST).permitAll());

        httpSecurity.authorizeHttpRequests(http->http.anyRequest().denyAll());

        return httpSecurity.build();
    }

    //crea un oggetto di tipo BcryptEncoder che verrà usato per codificare la password
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}