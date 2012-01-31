package grails.plugin.simplespringsocial.core.config

import grails.plugin.simplespringsocial.core.SessionConnectionRepository

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.social.connect.ConnectionRepository
import org.springframework.social.connect.support.ConnectionFactoryRegistry

/**
 * Spring configuration for the simple-spring-social-core plugin
 */
@Configuration
class CoreSpringConfig {
	@Bean
	ConnectionFactoryRegistry connectionFactoryRegistry() {
		new ConnectionFactoryRegistry()
	}

	@Bean
	@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
	ConnectionRepository connectionRepository() {
		new SessionConnectionRepository()
	}
}
