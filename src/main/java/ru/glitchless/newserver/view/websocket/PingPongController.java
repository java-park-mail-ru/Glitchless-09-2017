package ru.glitchless.newserver.view.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.PingMessage;
import ru.glitchless.newserver.data.stores.PlayerStore;
import ru.glitchless.newserver.utils.Constants;

import java.io.IOException;

@Controller
@EnableScheduling
public class PingPongController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PingPongController.class);
    private final PlayerStore playerStore;

    public PingPongController(PlayerStore playerStore) {
        this.playerStore = playerStore;
    }

    @Scheduled(fixedDelay = Constants.PING_TIMEOUT)
    void sendPing() {
        playerStore.getAllUser().forEach(item -> {
            if (!item.getSession().isOpen()) {
                return;
            }

            try {
                item.getSession().sendMessage(new PingMessage());
            } catch (IOException e) {
                LOGGER.error("Error while ping socket", e);
            }
        });
    }
}
