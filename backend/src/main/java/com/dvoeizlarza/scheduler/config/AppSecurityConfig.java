package com.dvoeizlarza.scheduler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = passwordEncoder();

        auth.inMemoryAuthentication().withUser("Smartkin").password(passwordEncoder().encode("user")).roles("USER");
        auth.inMemoryAuthentication().withUser("a_sey").password(passwordEncoder().encode("user")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/lk/**").access("hasRole('ROLE_USER')")
//                .antMatchers("/tetenka/**").access("hasRole('ROLE_TETENKA')")
//                .antMatchers("/protected/**").access("hasRole('ROLE_MODER')")
//                .antMatchers("/confidential/**").access("hasRole('ROLE_ADMIN')")
//                .and().formLogin().defaultSuccessUrl("/", false);
        http.csrf().disable();
//        http.formLogin().loginPage("/login").permitAll().and().logout().permitAll();
        http.authorizeRequests().antMatchers("/**").permitAll();
    }
}
