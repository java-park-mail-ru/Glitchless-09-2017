package ru.glitchless.newserver.data.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RoomUsers {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomUsers.class);

    private WebSocketUser firstUser;
    private WebSocketUser secondUser;

    public RoomUsers(WebSocketUser firstUser, @Nullable WebSocketUser secondUser) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
    }

    public WebSocketUser getFirstUser() {
        return firstUser;
    }

    public WebSocketUser getSecondUser() {
        return secondUser;
    }

    public boolean isAllUserActive() {
        return firstUser.getSession().isOpen() && secondUser.getSession().isOpen();
    }

    public WebSocketUser getComanion(WebSocketUser currentUser) {
        if (firstUser.equals(currentUser)) {
            return secondUser;
        }
        return firstUser;
    }

    public void sendToUsers(WebSocketMessage message, ObjectMapper objectMapper) {
        try {
            firstUser.sendToUser(message, objectMapper);
        } catch (IOException e) {
            LOGGER.error("Error while sending to first user", e);
        }

        try {
            secondUser.sendToUser(message, objectMapper);
        } catch (IOException e) {
            LOGGER.error("Error while sending to second user", e);
        }
    }
}
