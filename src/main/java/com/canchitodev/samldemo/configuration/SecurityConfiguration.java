package com.canchitodev.samldemo.configuration;

import org.opensaml.saml.saml2.core.Assertion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.saml2.provider.service.authentication.OpenSamlAuthenticationProvider;
import org.springframework.security.saml2.provider.service.authentication.Saml2Authentication;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService inMemoryUserDetailsManager() {
        // The builder will ensure the passwords are encoded before saving in memory
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails user = users
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        UserDetails admin = users
                .username("admin")
                .password("password")
                .roles("USER", "ADMIN")
                .build();
        UserDetails jose = users
                .username("josecarlos.mendoza@mimacom.com")
                .password("HUT8utf7$")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin, jose);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        OpenSamlAuthenticationProvider authenticationProvider = new OpenSamlAuthenticationProvider();
        authenticationProvider.setResponseAuthenticationConverter(responseToken -> {
            Saml2Authentication authentication = OpenSamlAuthenticationProvider
                    .createDefaultResponseAuthenticationConverter()
                    .convert(responseToken);
            Assertion assertion = responseToken.getResponse().getAssertions().get(0);
            String username = assertion.getSubject().getNameID().getValue();
            UserDetails userDetails = inMemoryUserDetailsManager().loadUserByUsername(username);
            authentication.setDetails(userDetails);
            return authentication;
        });

        http
                .authorizeRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .saml2Login(saml2 -> saml2
                        .authenticationManager(new ProviderManager(authenticationProvider))
                );
    }
}