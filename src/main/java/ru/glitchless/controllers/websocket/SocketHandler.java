package ru.glitchless.controllers.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.glitchless.data.models.UserModel;
import ru.glitchless.data.models.WebSocketMessage;
import ru.glitchless.data.throwables.HandleException;
import ru.glitchless.utils.Constants;

import java.io.IOException;

import static org.springframework.web.socket.CloseStatus.SERVER_ERROR;

public class SocketHandler extends TextWebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);
    private static final CloseStatus ACCESS_DENIED = new CloseStatus(4500, "Not logged in. Access denied");
    private SocketMessageHandlerManager handlerManager;
    private ObjectMapper objectMapper;

    public SocketHandler(@NotNull SocketMessageHandlerManager manager,
                         ObjectMapper objectMapper) {
        this.handlerManager = manager;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        final UserModel user = (UserModel) session.getAttributes().get(Constants.SESSION_EXTRA_USER);
        if (user == null) {
            LOGGER.warn("User requested websocket is not registred or not logged in. Openning websocket session is denied.");
            closeSessionSilently(session, ACCESS_DENIED);
            return;
        }

        //TODO open websocket
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (!session.isOpen()) {
            return;
        }
        final UserModel user = (UserModel) session.getAttributes().get(Constants.SESSION_EXTRA_USER);
        if (user == null) {
            LOGGER.warn("User requested websocket is not registred or not logged in. Openning websocket session is denied.");
            closeSessionSilently(session, ACCESS_DENIED);
            return;
        }
        handleMessage(user, message);
    }


    @SuppressWarnings("OverlyBroadCatchBlock")
    private void handleMessage(UserModel userProfile, TextMessage text) {
        final WebSocketMessage message;
        try {
            message = objectMapper.readValue(text.getPayload(), WebSocketMessage.class);
        } catch (IOException ex) {
            LOGGER.error("wrong json format at game response", ex);
            return;
        }
        try {
            handlerManager.handle(message, userProfile);
        } catch (HandleException e) {
            LOGGER.error("Can't handle message of type " + message.getClass().getName() + " with content: " + text, e);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {
        LOGGER.warn("Websocket transport problem", throwable);
    }

    private void closeSessionSilently(@NotNull WebSocketSession session, @Nullable CloseStatus closeStatus) {
        final CloseStatus status = closeStatus;
        if (status == null) {
            closeStatus = SERVER_ERROR;
        }
        //noinspection OverlyBroadCatchBlock
        try {
            session.close(status);
        } catch (Exception ignore) {
            LOGGER.debug("Ignore", ignore);
        }

    }
}
