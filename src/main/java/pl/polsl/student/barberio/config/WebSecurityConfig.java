package pl.polsl.student.barberio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Bean
    public PasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.formLogin().loginPage("/login").permitAll();
        http.authorizeRequests().antMatchers("/res/fomanticui/**", "/login", "/", "/signup").permitAll().antMatchers("/client/**").hasAnyRole("CLIENT", "ADMIN").antMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated();
        return http.build();
    }

    /*    @Profile({"devel"})
        @Bean
        public SecurityFilterChain disableSecurityRestrictions(HttpSecurity http) throws Exception {
            return http.authorizeRequests().antMatchers("/h2-console/**").permitAll()
                    .and().csrf().ignoringAntMatchers("/h2-console/**")
                    .and().headers().frameOptions().sameOrigin().and().build();
        }*/
    @Profile({"devel"})
    @Bean
    public WebSecurityCustomizer ignoreForH2Console() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**");
    }

}
