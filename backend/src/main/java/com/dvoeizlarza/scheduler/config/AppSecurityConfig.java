package com.dvoeizlarza.scheduler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = passwordEncoder();

        auth.inMemoryAuthentication().withUser("Smartkin").password(passwordEncoder().encode("user")).roles("USER");
        auth.inMemoryAuthentication().withUser("a_sey").password(passwordEncoder().encode("user")).roles("USER");
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select login, password, activity from USR where login=?")
                .authoritiesByUsernameQuery("select u.login, ur.roles from USR u, USER_ROLE ur where u.id=ur.usr_id AND u.login=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/lk/**").access("hasRole('ROLE_USER')")
                .and().formLogin().defaultSuccessUrl("/", false);
        http.csrf().disable();
//        http.formLogin().loginPage("/login").permitAll().and().logout().permitAll();
//        http.authorizeRequests().antMatchers("/**").permitAll();
    }
}
