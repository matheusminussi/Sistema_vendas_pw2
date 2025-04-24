package br.edu.ifto.aula09.security;

import br.edu.ifto.aula09.model.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UsuarioRepository usuarioRepository;

    public SecurityConfiguration(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .csrf(csrf -> csrf.ignoringRequestMatchers("/logout", "/saveRedirectAfterLogin"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/produto/catalogo", "/venda/carrinho", "/venda/checkout",
                                "/venda/adicionaCarrinho/*", "/venda/removerProdutoCarrinho/*",
                                "/venda/alterarQuantidade/*", "/pessoa/form",
                                "/pessoa/cadastro", "/venda/alterarQuantidade/**", "/saveRedirectAfterLogin").permitAll()
                        .requestMatchers("/venda/finalizar", "/departamento/form",
                                "/departamento/save", "/departamento/edit/",
                                "/departamento/update").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
//                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/produto/catalogo", true)
                        .successHandler(savedRequestAwareAuthenticationSuccessHandler())
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/produto/catalogo?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
        );

        return http.build();
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler() {
            @Override
            protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
                    if (redirectUrl != null) {
                        session.removeAttribute("redirectAfterLogin");
                        return redirectUrl;
                    }
                }
                return super.determineTargetUrl(request, response);
            }
        };
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        return new UsuarioDetailsManager(usuarioRepository, passwordEncoder());
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
