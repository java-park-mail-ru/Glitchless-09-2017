package ru.glitchless.newserver.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import ru.glitchless.game.data.packages.fromclient.WantPlayMessage
import ru.glitchless.game.data.packages.toclient.AuthMessage
import ru.glitchless.game.data.packages.toclient.GameInitState
import ru.glitchless.newserver.data.model.ClientState
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals


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
        myClient.connect();
        myClient.waitOpen();
    }

    private fun authAsAnonim(client: MyWebSocketClient) {
        val msg = WantPlayMessage();
        msg.state = ClientState.WAITING_USER.id;
        client.send(objectMapper.writeValueAsString(msg))
        val answer = objectMapper.readValue<AuthMessage>(client.blockingQueue.poll(1, TimeUnit.SECONDS),
                AuthMessage::class.java);

        assert(answer.login.isNotEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun testAnonimLogin() {
        authAsAnonim(myClient)
    }

    @Test
    @Throws(Exception::class)
    fun testFirstStage() {
        authAsAnonim(myClient)

        val answer = objectMapper.readValue<GameInitState>(blockingQueue.poll(1, TimeUnit.SECONDS),
                GameInitState::class.java);
        assertEquals(answer.state, ClientState.WAITING_USER.id)
    }

    @Test
    @Throws(Exception::class)
    fun testFindingUser() {
        testFirstStage()

        val queue = LinkedBlockingDeque<String>();
        val secondClient = myClient.clone(queue);
        secondClient.connect()
        secondClient.waitOpen()

        authAsAnonim(secondClient)

        var answerState = objectMapper.readValue<GameInitState>(queue.poll(1, TimeUnit.SECONDS),
                GameInitState::class.java);
        assertEquals(answerState.state, ClientState.WAITING_USER.id)

        answerState = objectMapper.readValue<GameInitState>(queue.poll(1, TimeUnit.SECONDS),
                GameInitState::class.java);
        assertEquals(answerState.state, ClientState.PREPARING_RESOURCE.id)

        secondClient.close()
    }

    @After
    fun after() {
        myClient.close()
    }
}
