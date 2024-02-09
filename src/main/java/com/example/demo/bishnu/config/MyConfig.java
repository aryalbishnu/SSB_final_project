package com.example.demo.bishnu.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cloudinary.Cloudinary;

@Configuration
@EnableWebSecurity
public class MyConfig extends WebSecurityConfigurerAdapter{
  
  @Autowired
  private Environment env; 
  
 /* 
  @Value("${server.servlet.session.timeout}") 
  private int sessionTimeoutInSeconds;
 
  @Bean
  public SessionRegistry sessionRegistry() {
      return new SessionRegistryImpl();
  }
  
  @Bean
  public SessionAuthenticationStrategy sessionAuthenticationStrategy(SessionRegistry sessionRegistry) {
      return new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry);
  }
  
  @Bean
  public SessionInformationExpiredStrategy sessionExpiredStrategy() {
      return new SimpleRedirectSessionInformationExpiredStrategy("/bishnu/loginForm?error");
  }
  
  
  @Bean
  public ConcurrentSessionFilter concurrentSessionFilter(SessionRegistry sessionRegistry) {
      return new ConcurrentSessionFilter(sessionRegistry, event -> {
          if (event.getSessionInformation().isExpired()) {
              handleSessionTimeout(event);
          } else {
              // Session is not expired, handle accordingly
          }
      });
  }

  private void handleSessionTimeout(SessionInformationExpiredEvent event) {
      // Handle session timeout or expired sessions here
      String username = event.getSessionInformation().getPrincipal().toString();
      // Log out the user or perform other actions...
  }
  
*/
  
  
  @Bean
  public UserDetailsService getUserDetailsService() {    
    return new UserDetailsServiceImpl();
  }
  
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  
  
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
  }
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
   auth.authenticationProvider(authenticationProvider());
  }
  /*
  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
          AuthenticationFailureHandler loginFailureHandler,
          AuthenticationSuccessHandler loginSuccessHandler,
          SessionAuthenticationStrategy sessionAuthenticationStrategy,
          SessionRegistry sessionRegistry) throws Exception {
      http
          .authorizeRequests(authorizeRequests ->
              authorizeRequests
                  .antMatchers("/bishnu/user/admin/**").hasRole("ADMIN")
                  .antMatchers("/bishnu/user/**").access("hasRole('ROLE_NORMAL') or hasRole('ROLE_ADMIN')")
                  .antMatchers("/**").permitAll()
          )
          .formLogin(withDefaults())
              .loginPage("/bishnu/loginForm")
              .loginProcessingUrl("/dologin")
              .failureHandler(loginFailureHandler)
              .successHandler(loginSuccessHandler)
              .usernameParameter("username")
          .and()
          .sessionManagement(sessionManagement ->
              sessionManagement
                  .sessionAuthenticationStrategy(sessionAuthenticationStrategy)
                  .maximumSessions(1) // Maximum allowed sessions for a user
                  .sessionRegistry(sessionRegistry)
                  .expiredUrl("/sessionExpired") // Redirect URL for expired sessions
          )
          .and()
          .csrf().disable();

      return http.build();
  }
  */

  @Override
  protected void configure(HttpSecurity http) throws Exception {
  http.authorizeRequests().antMatchers("/bishnu/user/admin/**").hasRole("ADMIN")
  .antMatchers("/bishnu/user/**").access("hasRole('ROLE_NORMAL') or hasRole('ROLE_ADMIN')")
//.antMatchers("/bishnu/user/**").hasAnyRole("ADMIN", "NORMAL")
  //.antMatchers("/bishnu/user/**").hasRole("NORMAL")
  //.antMatchers("/bishnu/user/**").hasRole("ADMIN")
 .antMatchers("/**").permitAll().and().formLogin()

  .loginPage("/bishnu/loginForm")
  .loginProcessingUrl("/dologin") 
  //.defaultSuccessUrl("/bishnu/user/dologin")
  .failureHandler(loginFailureHandler)
  .successHandler(loginSuccessHandler)  
  .usernameParameter("username")
  .and()
  .sessionManagement(sessionManagement ->
  sessionManagement.maximumSessions(1)
  .expiredUrl("/bishnu/loginForm?error")
  .maxSessionsPreventsLogin(true)
)

.csrf().disable();

  }

  
@Autowired
private CustomLoginFailureHandler loginFailureHandler;

@Autowired
private CustomLoginSuccessHandler loginSuccessHandler;

// cloudinary bean creat
@Bean
public Cloudinary cloudinary() {
    Map<String, Object> map = new HashMap<>();
    map.put("cloud_name", env.getProperty("cloudinary.cloud_name"));
    map.put("api_key", env.getProperty("cloudinary.api_key"));
    map.put("api_secret", env.getProperty("cloudinary.api_secret"));
    map.put("secure", true);
    return new Cloudinary(map);
}

  

}
