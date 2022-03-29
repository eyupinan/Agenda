package com.kartaca.challenge.config;

import com.kartaca.challenge.Service.AccountService;
import com.kartaca.challenge.config.handler.SuccessHandler;
import com.kartaca.challenge.dto.UserPrincipal;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {
    @Lazy
    @Autowired
    private AccountService userService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/success");
        web.ignoring().antMatchers("/err");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/sa").permitAll()
                .antMatchers("/user").permitAll()
                .antMatchers("/persistMessage").permitAll()
                .antMatchers("/destroy").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER")
                .antMatchers("/api/ticket*","/api/ticket/**").hasAnyRole("USER")
                .antMatchers("/api/event").permitAll()
                .antMatchers("/api/user/username/**","/api/user/update/**").hasAnyRole("USER")
                .antMatchers("/profile").hasAnyRole("USER","ADMIN")
                .antMatchers("/api/route*").permitAll()
                .antMatchers("/api/destination").permitAll()
                .antMatchers("/login*").permitAll()
                .antMatchers("/api/user/create").permitAll()
                .antMatchers("/register*").permitAll()
                .antMatchers("/route*").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/template/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/api/login")
                //.defaultSuccessUrl("/logged_in", true)
                .successHandler((request, response, authentication) -> {
                    System.out.println(((UserPrincipal)authentication.getPrincipal()).getUsername());
                    response.addHeader("Access-Control-Allow-Origin", "*");
                    response.setStatus(200);
                    
                })
                .failureHandler((request, response, authentication) -> {
                    response.addHeader("Access-Control-Allow-Origin", "*");
                    response.setStatus(401);
                    
                    
                })
                //.failureUrl("/login?err=true")
                .and()
                .logout()
                .logoutUrl("/api/perform_logout")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

    }

    @Bean
    public AuthenticationProvider authProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userService);

        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }
    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return new SuccessHandler();
    }
}
