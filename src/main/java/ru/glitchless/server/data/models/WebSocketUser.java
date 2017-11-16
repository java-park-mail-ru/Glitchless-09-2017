package ru.glitchless.server.data.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.glitchless.server.utils.Constants;

import java.io.IOException;

public class WebSocketUser {
    private WebSocketSession session;
    private UserModel userModel;

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
        return session.getId().hashCode() * Constants.MAGIC_NUMBER + userModel.hashCode();
    }

    public void sendToUser(WebSocketMessage message, ObjectMapper objectMapper) throws IOException {
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
    }
}
