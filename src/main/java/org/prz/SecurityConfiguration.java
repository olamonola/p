/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz;

/**
 *
 * @author Ola
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests().antMatchers("/").permitAll();

        httpSecurity
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/Konto/rejestracja",
                        "/Konto/weryfikacja/**",
                        "/Konto/zapomnianeHaslo",
                        "/Konto/zmianaZapomnianegoHasla/**").access("isAnonymous()")
                .antMatchers("/System/**",
                        "/Fakultet/**",
                        "/Specjalizacja/**",
                        "/TypPraktyk/**",
                        "/Zarzadzanie/**",
                        "/Studenci/**",
                        "/Konto/zmianaMaila").
                access("hasRole('ROLE_ADMIN') or hasRole('ROLE_OPIEKUN_PRAKTYK')")
                .antMatchers("/Firma/**").
                access("hasRole('ROLE_ADMIN') or hasRole('ROLE_OPIEKUN_PRAKTYK') "
                        + "or hasRole('ROLE_STUDENT')")
                .antMatchers("/Konto/edycjaProfilu").
                access("hasRole('ROLE_STUDENT') or hasRole('ROLE_EXPIRED')"
                        + " or hasRole('ROLE_NONE')")
                .antMatchers("/Konto/profil",
                        "/Konto/zmianaHasla").authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**", "/webjars/**"); 
    }

}
