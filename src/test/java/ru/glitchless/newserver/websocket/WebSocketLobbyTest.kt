package ru.glitchless.newserver.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.simp.stomp.StompFrameHandler
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import org.springframework.web.socket.sockjs.client.SockJsClient
import org.springframework.web.socket.sockjs.client.Transport
import org.springframework.web.socket.sockjs.client.WebSocketTransport
import ru.glitchless.game.data.packages.fromclient.WantPlayMessage
import java.lang.reflect.Type
import java.util.Arrays.asList
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.TimeUnit


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner::class)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
class WebSocketLobbyTest {
    @LocalServerPort
    public var randomServerPort = 0;

    private val objectMapper = ObjectMapper();
    private lateinit var blockingQueue: BlockingQueue<String>
    private lateinit var myClient: MyWebSocketClient

    @Before
    fun setup() {
        blockingQueue = LinkedBlockingDeque()
        myClient = MyWebSocketClient(blockingQueue, randomServerPort)
    }

    @Test
    @Throws(Exception::class)
    fun shouldReceiveAMessageFromTheServer() {
        myClient.connect();
        myClient.waitOpen();

        myClient.close();
    }
}
