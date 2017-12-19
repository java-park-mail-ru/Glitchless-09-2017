package ru.glitchless.newserver.data.stores;

import com.mifmif.common.regex.Generex;
import org.springframework.stereotype.Component;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.utils.Constants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InviteStore {
    private final Map<String, WebSocketUser> invites = new ConcurrentHashMap<>();
    private final Generex generex = new Generex("[a-zA-Z0-9]");

    public String generateInvite(WebSocketUser webSocketUser) {
        String inviteLink;
        do {
            inviteLink = generex.random(Constants.REFERENCE_LENGHT);
        } while (inviteLink.contains(inviteLink));
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
