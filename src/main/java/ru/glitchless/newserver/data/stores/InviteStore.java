package ru.glitchless.newserver.data.stores;

import org.springframework.stereotype.Component;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.utils.RandomString;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InviteStore {
    private final Map<String, WebSocketUser> invites = new ConcurrentHashMap<>();
    private final RandomString randomString = new RandomString();

    public String generateInvite(WebSocketUser webSocketUser) {
        String inviteLink;
        do {
            inviteLink = randomString.nextString();
        } while (invites.get(inviteLink) != null);
        invites.put(inviteLink, webSocketUser);
        return inviteLink;
    }

    public void replaceUser(String inviteLink, WebSocketUser webSocketUser) {
        invites.put(inviteLink, webSocketUser);
    }

    public WebSocketUser getUserAndRemoveByInvite(String reflink) {
        return invites.remove(reflink);
    }
}
