package br.com.deep.envers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(
	repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class
)
public class DeepEnversApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeepEnversApplication.class, args);
	}

}
