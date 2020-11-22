/**
 * This content is released under the MIT License (MIT)
 *
 * Copyright (c) 2020, canchito-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author 		José Carlos Mendoza Prego
 * @copyright	Copyright (c) 2020, canchito-dev (http://www.canchito-dev.com)
 * @license		http://opensource.org/licenses/MIT	MIT License
 * @link		http://www.canchito-dev.com/public/blog/2020/11/15/spring-security-with-saml2-and-okta/
 * @link		https://github.com/canchito-dev/spring-security-with-saml2-and-okta
 **/
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
                .username("contact@canchito-dev.com")
                .password("gtOkta2020")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
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