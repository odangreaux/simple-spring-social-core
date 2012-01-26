package org.o14x.simplespringsocial.core.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.social.connect.ConnectionRepository
import org.o14x.simplespringsocial.core.SessionConnectionRepository
import org.springframework.social.connect.support.ConnectionFactoryRegistry
import org.springframework.context.annotation.ScopedProxyMode

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
