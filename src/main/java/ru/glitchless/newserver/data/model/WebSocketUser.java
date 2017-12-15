package ru.glitchless.newserver.data.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.glitchless.newserver.utils.Constants;

import java.io.IOException;
import java.util.Objects;

public class WebSocketUser {
    private final WebSocketSession session;
    private final UserModel userModel;

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
                && ((WebSocketUser) obj).userModel.equals(userModel)
                && Objects.equals(session.getId(), ((WebSocketUser) obj).session.getId());
    }

    @Override
    public int hashCode() {
        return session.getId().hashCode() * Constants.MAGIC_NUMBER + userModel.hashCode();
    }

    public void sendToUser(WebSocketMessage message, ObjectMapper objectMapper) throws IOException {
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
    }
}
