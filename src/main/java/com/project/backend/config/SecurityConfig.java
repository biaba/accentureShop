package com.project.backend.config;

import com.project.backend.services.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthorFailHandler authorFailHandler;
    @Autowired
    AuthentFailHandler authentFailHandler;
    @Autowired
    LoggingFilter loggingFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource()).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/**").permitAll()
                .antMatchers("/test").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/customer/**").hasAnyRole("CUSTOMER", "LYCUSTOMER", "ADMIN")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(authorFailHandler)
                .and()
                .addFilter(authentFilter())
                .formLogin().loginPage("/login")
                .permitAll().and()
                .logout()
                .logoutSuccessUrl("/home")
                .permitAll();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        CorsConfiguration configuration2 = new CorsConfiguration();
        configuration2.setAllowedOrigins(Arrays.asList("*"));
        configuration2.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration2.setAllowedHeaders(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/test/**", configuration);
        source.registerCorsConfiguration("/rest/**", configuration2);
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceImpl();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Filter customization through @Bean (@Component wouldn't offer all options)
    @Bean
    public FilterRegistrationBean loggingFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(loggingFilter);
        registration.addUrlPatterns("/login");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("loggingFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public BeforeAuthenticationFilter authentFilter() throws Exception {
        BeforeAuthenticationFilter filter = new BeforeAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationFailureHandler(authentFailHandler);
        return filter;
    }

/*    // Another Servlet for study purposes only. To handle Errors on by different Servlet.
    // No real advantage on managing this way
    @Bean
    public ServletRegistrationBean errorServlet() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        System.out.println("Second dispatcher ");

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(ResourceConfig2.class);
        dispatcherServlet.setApplicationContext(applicationContext);

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet, "/second/*");
        servletRegistrationBean.setName("secondServlet");
        return servletRegistrationBean;
    }*/

}
