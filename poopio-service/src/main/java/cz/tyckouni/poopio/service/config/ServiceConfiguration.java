package cz.tyckouni.poopio.service.config;

import cz.tyckouni.poopio.PersistenceApplicationContext;
import cz.tyckouni.poopio.dto.MonsterDTO;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.PersistenceApplicationContext;
import cz.tyckouni.poopio.entity.Monster;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackages = "cz.tyckouni.poopio")
public class ServiceConfiguration {

	@Bean
	public Mapper dozer() {
		DozerBeanMapper dozer = new DozerBeanMapper();
		dozer.addMapping(new DozerCustomConfig());
		return dozer;
	}

	/**
	 * Custom config for Dozer
	 */
	public class DozerCustomConfig extends BeanMappingBuilder {
		@Override
		protected void configure() {
			mapping(Monster.class, MonsterDTO.class);
		}
	}
}
