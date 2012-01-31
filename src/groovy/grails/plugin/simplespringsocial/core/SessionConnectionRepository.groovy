package grails.plugin.simplespringsocial.core

import org.springframework.social.connect.ConnectionRepository
import org.springframework.social.connect.NoSuchConnectionException
import org.springframework.social.connect.NotConnectedException
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

/**
 * Created by IntelliJ IDEA.
 * User: odangreaux
 * Date: 17/01/12
 * Time: 22:10
 * To change this template use File | Settings | File Templates.
 */
class SessionConnectionRepository implements ConnectionRepository {
	def connectionMap = [:]


	@Override
	org.springframework.util.MultiValueMap<String, org.springframework.social.connect.Connection<?>> findAllConnections() {
		MultiValueMap multiValueMap = new LinkedMultiValueMap()
		connectionMap.each {
			multiValueMap.add(it.key, it.value)
		}

		return multiValueMap
	}

	@Override
	List<org.springframework.social.connect.Connection<?>> findConnections(String providerId) {
		def connection = connectionMap[providerId]
		def connectionList = []

		if(connection != null) {
			connectionList.add(connection)
		}
		return connectionList
	}

	@Override
	def <A> List<org.springframework.social.connect.Connection<A>> findConnections(Class<A> apiType) {
		def connection = findPrimaryConnection apiType
		def connectionList = []

		if(connection != null) {
			connectionList.add(connection)
		}
		return connectionList
	}

	@Override
	org.springframework.util.MultiValueMap<String, org.springframework.social.connect.Connection<?>> findConnectionsToUsers(org.springframework.util.MultiValueMap<String, String> providerUserIds) {
		MultiValueMap multiValueMap = new LinkedMultiValueMap()
		connectionMap.each {
			if(providerUserIds.containsKey(it.key)) {
				multiValueMap.add(it.key, it.value)
			}
		}

		return multiValueMap
	}

	@Override
	org.springframework.social.connect.Connection<?> getConnection(org.springframework.social.connect.ConnectionKey connectionKey) {
		def connection = null
		if (connectionKey != null){
			connection = connectionMap.get(connectionKey.providerId)
		}

		if(connection == null) {
			throw new NoSuchConnectionException(connectionKey)
		}

		return connection
	}

	@Override
	def <A> org.springframework.social.connect.Connection<A> getConnection(Class<A> apiType, String providerUserId) {
		def connection = findPrimaryConnection apiType

		if(connection == null) {
			throw new NoSuchConnectionException()
		}

		return connection
	}

	@Override
	def <A> org.springframework.social.connect.Connection<A> getPrimaryConnection(Class<A> apiType) {
		def connection = findPrimaryConnection apiType


		if(connection == null) {
			throw new NotConnectedException()
		}

		return connection
	}

	@Override
	def <A> org.springframework.social.connect.Connection<A> findPrimaryConnection(Class<A> apiType) {
		def connection = null

		if(apiType != null) {
			connection = connectionMap.find {
				apiType.isInstance(it.value.api)
			}.value
		}

		return connection
	}

	@Override
	void addConnection(org.springframework.social.connect.Connection<?> connection) {
		updateConnection connection
	}

	@Override
	void updateConnection(org.springframework.social.connect.Connection<?> connection) {
		if(connection != null) {
			connectionMap[connection.key.providerId] = connection
		}
	}

	@Override
	void removeConnections(String providerId) {
		connectionMap.remove(providerId)
	}

	@Override
	void removeConnection(org.springframework.social.connect.ConnectionKey connectionKey) {
		if(connectionKey != null) {
			connectionMap.remove(connectionKey.providerId)
		}
	}
}
