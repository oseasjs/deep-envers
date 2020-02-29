package br.com.deep.envers.config;

import br.com.deep.envers.listener.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> auditorAwareImpl(){
        return new AuditorAwareImpl();
    }

}
