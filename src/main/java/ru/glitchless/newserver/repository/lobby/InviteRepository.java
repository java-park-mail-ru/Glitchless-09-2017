package ru.glitchless.newserver.repository.lobby;

import org.springframework.stereotype.Component;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.data.stores.InviteStore;

@Component
public class InviteRepository {
    private final InviteStore inviteStore;

    public InviteRepository(InviteStore inviteStore) {
        this.inviteStore = inviteStore;
    }

    public String generateInvite(WebSocketUser webSocketUser) {
        return inviteStore.generateInvite(webSocketUser);
    }

    public void replaceUser(String inviteLink, WebSocketUser webSocketUser) {
        inviteStore.replaceUser(inviteLink, webSocketUser);
    }

    public WebSocketUser getUserAndRemoveByInvite(String reflink) {
        return inviteStore.getUserAndRemoveByInvite(reflink);
    }

}
