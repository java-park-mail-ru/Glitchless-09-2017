package ru.glitchless.newserver.view.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.glitchless.game.data.packages.toclient.AuthMessage;
import ru.glitchless.game.data.packages.toclient.WebSocketMessageError;
import ru.glitchless.newserver.data.model.UserModel;
import ru.glitchless.newserver.data.model.WebSocketMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.data.throwables.HandleException;
import ru.glitchless.newserver.interactor.auth.UserInteractor;
import ru.glitchless.newserver.utils.Constants;
import ru.glitchless.newserver.utils.ResourceFactory;
import ru.glitchless.newserver.utils.SendMessageService;

import java.io.IOException;

import static org.springframework.web.socket.CloseStatus.SERVER_ERROR;

public class SocketHandler extends TextWebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);
    private static final CloseStatus ACCESS_DENIED = new CloseStatus(4500, "Not logged in. Access denied");
    private final SocketMessageHandlerManager handlerManager;
    private final ObjectMapper objectMapper;
    private final SendMessageService sendMessageService;
    private final UserInteractor userInteractor;

    public SocketHandler(@NotNull SocketMessageHandlerManager manager,
                         @NotNull ResourceFactory resourceFactory,
                         @NotNull UserInteractor service,
                         @NotNull SendMessageService sendMessageService,
                         ObjectMapper objectMapper) {
        this.handlerManager = manager;
        this.objectMapper = objectMapper;
        this.userInteractor = service;
        sendMessageService.init();
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        UserModel user = (UserModel) session.getAttributes().get(Constants.SESSION_EXTRA_USER);
        if (user == null) {
            user = userInteractor.getAnonUser();
            sendMessageService.sendMessage(new AuthMessage(user.getLogin()), new WebSocketUser(session, user));
            session.getAttributes().put(Constants.SESSION_EXTRA_USER, user);
        }
    }

    @Override
    @SuppressWarnings("OverlyBroadCatchBlock")
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (!session.isOpen()) {
            return;
        }
        UserModel user = (UserModel) session.getAttributes().get(Constants.SESSION_EXTRA_USER);
        if (user == null) {
            user = userInteractor.getAnonUser();
            sendMessageService.sendMessage(new AuthMessage(user.getLogin()), new WebSocketUser(session, user));
            session.getAttributes().put(Constants.SESSION_EXTRA_USER, user);
        }
        WebSocketUser wsUser = (WebSocketUser) session.getAttributes().get(Constants.SESSION_EXTRA_USERWS);
        if (wsUser == null) {
            wsUser = new WebSocketUser(session, user);
            session.getAttributes().put(Constants.SESSION_EXTRA_USERWS, wsUser);
        }
        try {
            handleMessage(wsUser, message);
        } catch (Throwable e) {
            LOGGER.error("Error handle message", e);
            session.sendMessage(new TextMessage(exceptionToJson(e)));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {
        LOGGER.warn("Websocket transport problem", throwable);
    }

    @SuppressWarnings("OverlyBroadCatchBlock")
    private void handleMessage(WebSocketUser userProfile, TextMessage text) {
        final WebSocketMessage message;
        try {
            message = objectMapper.readValue(text.getPayload(), WebSocketMessage.class);
        } catch (IOException ex) {
            LOGGER.error("wrong json format at game response", ex);
            LOGGER.error(text.getPayload());
            return;
        }
        try {
            handlerManager.handle(message, userProfile);
        } catch (HandleException e) {
            LOGGER.error("Can't handle message of type " + message.getClass().getName() + " with content: " + text, e);
            throw e;
        }
    }

    @SuppressWarnings("OverlyBroadCatchBlock")
    private void closeSessionSilently(@NotNull WebSocketSession session) {
        CloseStatus status = SocketHandler.ACCESS_DENIED;
        if (status == null) {
            status = SERVER_ERROR;
        }

        try {
            session.close(status);
        } catch (Exception ignore) {
            LOGGER.debug("Ignore", ignore);
        }

    }

    private String exceptionToJson(Throwable ex) {
        try {
            final ResponseStatus responseStatus = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
            if (responseStatus != null) {
                String reason = ex.getMessage();
                if (reason == null) {
                    reason = responseStatus.reason();
                }
                return objectMapper.writeValueAsString(new WebSocketMessageError().setReason(reason));
            }
            return objectMapper.writeValueAsString(new WebSocketMessageError().setReason(ex.getMessage()));
        } catch (JsonProcessingException e) {
            return "Error in processing Exception";
        }
    }
}
