package com.minhub.homebanking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                .antMatchers("/webb/pages2/**").permitAll()

                .antMatchers("/h2-console").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients/current/accounts").hasAnyAuthority("ADMIN","CLIENT")
                .antMatchers(HttpMethod.GET, "/api/clients/current/accounts").hasAnyAuthority("ADMIN","CLIENT")
                .antMatchers(HttpMethod.PATCH, "/api/clients/current/accounts").hasAnyAuthority("ADMIN","CLIENT")
                .antMatchers(HttpMethod.POST,"/api/transactions").hasAnyAuthority("ADMIN","CLIENT")
                .antMatchers(HttpMethod.POST,"/api/transactions/payment").hasAnyAuthority("ADMIN","CLIENT")
                .antMatchers(HttpMethod.POST,"/api/transactions/downloads").hasAnyAuthority("ADMIN","CLIENT")
                .antMatchers(HttpMethod.POST,"/api/loans").hasAnyAuthority("ADMIN","CLIENT")
                .antMatchers(HttpMethod.GET, "/api/loans").hasAnyAuthority("ADMIN","CLIENT")
                .antMatchers("/webb/index.html", "/web/login.html").permitAll()
                .antMatchers("/web/style.css", "/web/index.js").permitAll()
                .antMatchers("/webb/pages/index.html","/webb/assets/styles/**",
                                        "/webb/assets/js/**","/webb/assets/img/**").permitAll()
                .antMatchers("/webb/assets/js/main.js").permitAll()


                .antMatchers("/manager.html", "/manager.js", "/style.css", "/web/img/**", "/web/cards.html",
                        "/web/accounts.html", "/web/dashboard/dashboard.html", "/web/cards.css",
                        "/web/accounts.js", "/web/transaction/**", "/webb/assets/js/login.js" ).hasAnyAuthority("ADMIN", "CLIENT");

        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
    }

    private void clearAuthenticationAttributes(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
