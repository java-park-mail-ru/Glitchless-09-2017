package ru.glitchless.newserver.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import ru.glitchless.game.data.ProcessingCommit;
import ru.glitchless.newserver.data.model.WebSocketMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SendWorker implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendMessageService.class);

    private final BlockingQueue<ProcessingCommit<WebSocketMessage>> waitSendingCommit
            = new LinkedBlockingQueue<>();

    private final ObjectMapper objectMapper;

    public SendWorker(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void addToQueue(ProcessingCommit<WebSocketMessage> commit) {
        waitSendingCommit.add(commit);
    }

    @Override
    @SuppressWarnings("OverlyBroadCatchBlock")
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ProcessingCommit<WebSocketMessage> commit;
                while ((commit = waitSendingCommit.take()) != null) {
                    sendMessageSync(commit);
                }
            } catch (Exception e) {
                LOGGER.error("Error in worker loop", e);
            }
            LOGGER.error("Terminated message worker...");
        }
    }

    public boolean sendMessageSync(ProcessingCommit<WebSocketMessage> commit) {
        if (!commit.getUser().getSession().isOpen()) {
            return false;
        }

        try {
            commit.getUser().getSession().sendMessage(
                    new TextMessage(objectMapper.writeValueAsString(commit.getMessage())));
        } catch (Exception e) {

            LOGGER.error("Error while sending message. Socket is open: "
                    + commit.getUser().getSession().isOpen(), e);
            return false;
        }

        return true;
    }
}
