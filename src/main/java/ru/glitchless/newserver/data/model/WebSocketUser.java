package ru.glitchless.newserver.data.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.glitchless.newserver.utils.Constants;

import java.io.IOException;

public class WebSocketUser {
    private final UserModel userModel;
    private WebSocketSession session;

    public WebSocketUser(WebSocketSession session, UserModel userModel) {
        this.session = session;
        this.userModel = userModel;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof WebSocketUser
                && ((WebSocketUser) obj).userModel.equals(userModel);
    }

    @Override
    public int hashCode() {
        return Constants.MAGIC_NUMBER * userModel.hashCode();
    }

    public void sendToUser(WebSocketMessage message, ObjectMapper objectMapper) throws IOException {
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
    }

    public void replaceSession(WebSocketSession webSocketSession) {
        this.session = webSocketSession;
    }
}
