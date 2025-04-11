package kodlamaio.hrms.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import kodlamaio.hrms.security.jwt.AuthEntryPointJwt;
import kodlamaio.hrms.security.jwt.AuthTokenFilter;
import kodlamaio.hrms.security.services.UserDetailsServiceImpl;


@Configuration
@EnableMethodSecurity
// (securedEnabled = true,
// jsr250Enabled = true,
// prePostEnabled = true) // by default
public class WebSecurityConfig { // extends WebSecurityConfigurerAdapter {
  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }
  
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
	private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v2/api-docs/**",
            "/v3/api-docs/**",  
            "/webjars/**",
            "/swagger-resources/**", 
            "/swagger-ui/**",
            "/swagger-ui.html/**",
            "/api/trial/**"
             };
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> 
          auth.requestMatchers("/api/auth/**").permitAll()
              .requestMatchers("/api/test/**").permitAll()
              .requestMatchers(AUTH_WHITELIST).permitAll()
              .requestMatchers("/api/jobAdvertisements/getall").permitAll()
              .requestMatchers("/api/jobAdvertisements/getbyid/{id}").permitAll()
              .requestMatchers("/api/jobAdvertisements/add/").permitAll()
              .requestMatchers("/api/jobAdvertisements/getallbyemployerid/{employerId}").permitAll()
              .requestMatchers("/api/jobAdvertisements/updatejobAdvertisementbyid/{id}").permitAll()
              .requestMatchers("/api/candidates/**").permitAll()
              .requestMatchers("/api/auth/signup/candidate").permitAll()
              .requestMatchers("/api/auth/signup/employer").permitAll()
              .requestMatchers("/api/verifications/verifyCandidateAccount").permitAll()
              .requestMatchers("/api/verifications/verifyEmployerAccount").permitAll()
              .requestMatchers("/api/verifications/employeeConfirmEmployer").permitAll()
              .requestMatchers("/api/employers/getemployerbyid/{employerId}").permitAll()
              .requestMatchers("/api/employers/updateemployerbyid/{employerId}").permitAll()
              .requestMatchers("/api/jobs/getall/").permitAll()
              .requestMatchers("/api/jobs/add/").permitAll()
              .requestMatchers("/api/jobs/getall/").permitAll()
              .requestMatchers("/api/cities/getall/").permitAll()
              .requestMatchers("/api/workingtimes/getall/").permitAll()
              .requestMatchers("/api/workingtypes/getall/").permitAll()
              .anyRequest().authenticated()
        );
    
    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
  }
}