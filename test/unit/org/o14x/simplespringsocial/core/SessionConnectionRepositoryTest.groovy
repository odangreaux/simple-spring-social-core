package org.o14x.simplespringsocial.core

import org.gmock.GMockTestCase
import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionKey
import org.springframework.social.connect.NoSuchConnectionException
import org.springframework.util.MultiValueMap

/**
 * Tests for SessionConnectionRepository.
 *
 * @author odangreaux
 */
class SessionConnectionRepositoryTest extends GMockTestCase {
	/**
	 * Creates a new SessionConnectionRepository
	 *
	 * @return a new SessionConnectionRepository
	 */
    SessionConnectionRepository createSessionConnectionRepository() {
		new SessionConnectionRepository()
	}

	/**
	 * Test for addConnection(key)
	 */
	void testAddConnection() {
		// the SessionConnectionRepository under test
		def sessionConnectionRepository = createSessionConnectionRepository()

		// create a new ConnectionKey
		def key = new ConnectionKey("myprovider", "myprovideruser1")

		// the mock Connection
		def connection = mock(Connection)
		connection.key.returns key

		play {
			// adds the connection
			sessionConnectionRepository.addConnection(connection)

			// tests if the added connection can be retrieved
			assert connection == sessionConnectionRepository.getConnection(key)
		}
	}

	/**
	 * Test for removeConnection(key)
	 */
	void testRemoveConnection() {
		// the SessionConnectionRepository under test
		def sessionConnectionRepository = createSessionConnectionRepository()

		// create a new ConnectionKey
		def key = new ConnectionKey("myprovider", "myprovideruser1")

		// the mock Connection
		def connection = mock(Connection)
		connection.key.returns key

		play {
			// adds the connection
			sessionConnectionRepository.addConnection(connection)

			// tries to remove it
			sessionConnectionRepository.removeConnection(key)

			// should throw a NoSuchConnectionException
			shouldFail(NoSuchConnectionException){sessionConnectionRepository.getConnection(key)}
		}
	}

	/**
	 * Test for removeConnections(providerId)
	 */
	void testRemoveConnections() {
		// the SessionConnectionRepository under test
		def sessionConnectionRepository = createSessionConnectionRepository()

		// create a new ConnectionKey
		def key = new ConnectionKey("myprovider", "myprovideruser1")

		// the mock Connection
		def connection = mock(Connection)
		connection.key.returns key

		play {
			// adds the connection
			sessionConnectionRepository.addConnection(connection)

			// tries to remove it
			sessionConnectionRepository.removeConnections("myprovider")

			// should throw a NoSuchConnectionException
			shouldFail(NoSuchConnectionException){sessionConnectionRepository.getConnection(key)}
		}
	}

	/**
	 * Test for findAllConnections()
	 */
	void testFindAllConnections() {
		// the SessionConnectionRepository under test
		def sessionConnectionRepository = createSessionConnectionRepository()

		// create ConnectionKeys
		def key1 = new ConnectionKey("myprovider1", "myprovideruser1")
		def key2 = new ConnectionKey("myprovider2", "myprovideruser2")

		// the mock Connections
		def connection1 = mock(Connection)
		connection1.key.returns key1
		def connection2 = mock(Connection)
		connection2.key.returns key2

		play {
			// adds the connection
			sessionConnectionRepository.addConnection(connection1)
			sessionConnectionRepository.addConnection(connection2)

			// calls findAllConnections
			MultiValueMap allConnections = sessionConnectionRepository.findAllConnections()

			// assertions
			assert allConnections.get("myprovider1").contains(connection1)
			assert !allConnections.get("myprovider1").contains(connection2)
			assert allConnections.get("myprovider2").contains(connection2)
			assert !allConnections.get("myprovider2").contains(connection1)
		}
	}

	/**
	 * Test for findConnections(String providerId)
	 */
	void testFindConnections() {
		// the SessionConnectionRepository under test
		def sessionConnectionRepository = createSessionConnectionRepository()

		// create ConnectionKeys
		def key1 = new ConnectionKey("myprovider1", "myprovideruser1")

		// the mock Connections
		def connection1 = mock(Connection)
		connection1.key.returns key1

		play {
			// adds the connection
			sessionConnectionRepository.addConnection(connection1)

			// calls findConnections
			def connections = sessionConnectionRepository.findConnections("myprovider1")

			// assertions
			assert connections.contains(connection1)
		}
	}

	/**
	 * Test for findConnections(Class<A> apiType)
	 */
	void testFindConnections2() {
		// the SessionConnectionRepository under test
		def sessionConnectionRepository = createSessionConnectionRepository()

		// create ConnectionKeys
		def key1 = new ConnectionKey("myprovider1", "myprovideruser1")

		// the mock Connections
		def connection1 = mock(Connection)
		connection1.getKey().returns key1
		connection1.getApi().returns(new MockAPI())

		play {
			// adds the connection
			sessionConnectionRepository.addConnection(connection1)

			// calls findConnections
			def connections = sessionConnectionRepository.findConnections(MockAPI)

			// assertions
			assert connections.contains(connection1)
		}
	}
}

class MockAPI {

}
