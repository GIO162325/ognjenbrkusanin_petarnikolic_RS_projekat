package org.raflab.studsluzba.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // stateless API -> nema CSRF
                .csrf().disable() // REST bez session kolačića → ok.  :contentReference[oaicite:1]{index=1}
                // H2 konzola koristi <iframe>; dozvoli frame iz iste origin
                .headers().frameOptions().sameOrigin() // inače blank page.  :contentReference[oaicite:2]{index=2}
                .and()
                .authorizeRequests()
                // Swagger (springdoc v1 za Boot 2.x)
                .antMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll() // :contentReference[oaicite:3]{index=3}
                // H2 (ako ga koristiš u dev)
                .antMatchers("/h2/**", "/h2-console/**").permitAll() // :contentReference[oaicite:4]{index=4}
                // tvoji javni GET endpointi (po potrebi)
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                // sve ostalo (u dev) pusti
                .anyRequest().permitAll()
                .and()
                // bez form login / basic, čist REST
                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
