package beingolea.org.desafio4.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .csrf().disable()
                .authorizeHttpRequests((authorize) -> authorize
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/swagger-ui/**")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/v3/api-docs/**")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api-docs")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api-docs/**")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/h2-console/**")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/h2-console/**")).permitAll()
                //                .requestMatchers(toH2Console()).permitAll()
                //        .requestMatchers("//swagger-ui/**","/h2-console/**").permitAll()
                        //.requestMatchers("/bar/*", "/baz/*").hasRole("ADMIN")
                        //.requestMatchers("/api/logs/**").hasAuthority(AuthoritiesConstants.ADMIN)
                        //.requestMatchers(HttpMethod.POST,"/empresa","/pessoa").permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/empresa")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/pessoa")).permitAll()
                        //.requestMatchers(HttpMethod.POST,"/empresa/*","/pessoa/*").authenticated()
                        //.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/empresa/*")).authenticated()
                        //.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/pessoa/*")).authenticated()
                        //.requestMatchers(HttpMethod.GET,"/empresa","/empresa/*","/pessoa","/pessoa/*",
                        //        "/fila","/fila/**").authenticated()
                        //.requestMatchers(HttpMethod.PUT,"/empresa/*","/pessoa/*").authenticated()
                        //.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"/empresa/*")).authenticated()
                        //.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"/pessoa/*")).authenticated()
                        //.requestMatchers(HttpMethod.DELETE,"/empresa/*","/pessoa/*").authenticated()
                        //.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE,"/empresa/*")).authenticated()
                        //.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE,"/pessoa/*")).authenticated()
                        //.requestMatchers(AntPathRequestMatcher("//swagger-ui/**","/h2-console/**")).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .formLogin(withDefaults());
        // @formatter:on
        return http.build();
    }

    // @formatter:off
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("cielo")
                .password("CIELO")
                .roles("USUARIO")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
    // @formatter:on
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console"),
                new AntPathRequestMatcher("/swagger-ui/**"),
                new AntPathRequestMatcher("/v3/api-docs/**"));
    }
}