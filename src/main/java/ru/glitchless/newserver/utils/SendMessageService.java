package ru.glitchless.newserver.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.glitchless.game.data.ProcessingCommit;
import ru.glitchless.newserver.data.model.RoomUsers;
import ru.glitchless.newserver.data.model.WebSocketMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SendMessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendMessageService.class);

    private final Executor executor = Executors.newFixedThreadPool(Constants.MESSAGE_THREAD_COUNT);

    private SendWorker[] workers = new SendWorker[Constants.MESSAGE_THREAD_COUNT];
    private Map<WebSocketUser, SendWorker> webSocketUserSendWorkerMap = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public SendMessageService(ObjectMapper objectMapper) {
        for (int i = 0; i < Constants.MESSAGE_THREAD_COUNT; i++) {
            workers[i] = new SendWorker(objectMapper);
        }
    }

    public void init() {
        for (int i = 0; i < Constants.MESSAGE_THREAD_COUNT; i++) {
            executor.execute(workers[i]);
        }
    }

    public boolean sendMessage(ProcessingCommit<WebSocketMessage> commit) {
        if (!commit.getUser().getSession().isOpen()) {
            return false;
        }
        SendWorker sendWorker = webSocketUserSendWorkerMap.get(commit.getUser());

        if (sendWorker == null) {
            sendWorker = workers[counter.getAndIncrement()];
            webSocketUserSendWorkerMap.put(commit.getUser(), sendWorker);
        }

        sendWorker.addToQueue(commit);
        return true;
    }

    public boolean sendMessage(WebSocketMessage message, RoomUsers users) {
        return sendMessage(message, users.getFirstUser())
                && sendMessage(message, users.getSecondUser());
    }

    public boolean sendMessage(WebSocketMessage message, WebSocketUser user) {
        return sendMessage(new ProcessingCommit<>(message, user));
    }
}
