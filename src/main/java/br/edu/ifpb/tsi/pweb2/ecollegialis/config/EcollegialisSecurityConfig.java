package br.edu.ifpb.tsi.pweb2.ecollegialis.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class EcollegialisSecurityConfig {
    private DataSource dataSource;

    public EcollegialisSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authentication -> authentication
                .requestMatchers("/css/**", "/js/**", "/img/**", "/auth/**").permitAll()
                .requestMatchers("/aluno/**").hasRole("ALUNO")
                .requestMatchers("/professor/**").hasRole("PROFESSOR")
                .requestMatchers("/coordenador/**").hasRole("COORDENADOR")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated())

            .formLogin(form -> form
                .loginPage("/auth/login")
                .defaultSuccessUrl("/home", true)
                .permitAll())

            .logout(logout -> logout.logoutUrl("/auth/logout"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("ADMIN")
            .build();

        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

        if (!users.userExists(admin.getUsername())) {
            users.createUser(admin);
        }

        return users;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }
}