package ru.glitchless.server.repositories.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import ru.glitchless.game.data.ProcessingCommit;
import ru.glitchless.server.data.models.WebSocketMessage;
import ru.glitchless.server.data.models.WebSocketUser;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class SendMessageService implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendMessageService.class);

    private final Object lockObject = new Object();
    private BlockingQueue<ProcessingCommit<WebSocketMessage>> waitSendingCommit
            = new LinkedBlockingQueue<>();

    private Executor executor = Executors.newSingleThreadExecutor();
    private ObjectMapper objectMapper;

    public SendMessageService(ObjectMapper objectMapper) {
        executor.execute(this); // Add more workers for perfomance
        this.objectMapper = objectMapper;
    }

    public boolean sendMessage(ProcessingCommit<WebSocketMessage> commit) {
        if (!commit.getUser().getSession().isOpen()) {
            return false;
        }
        waitSendingCommit.add(commit);
        return true;
    }

    public boolean sendMessage(WebSocketMessage message, WebSocketUser user) {
        return sendMessage(new ProcessingCommit<>(message, user));
    }

    @Override
    @SuppressWarnings("OverlyBroadCatchBlock")
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ProcessingCommit<WebSocketMessage> commit;
                while ((commit = waitSendingCommit.take()) != null) {
                    final WebSocketMessage message = commit.getMessage();
                    try {
                        commit.getUser().getSession().sendMessage(
                                new TextMessage(objectMapper.writeValueAsString(message)));
                    } catch (IOException e) {
                        LOGGER.error("Error while sending message. Socket is open: "
                                + commit.getUser().getSession().isOpen(), e);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Error in worker loop", e);
            }
            LOGGER.error("Terminated message worker...");
        }
    }
}
