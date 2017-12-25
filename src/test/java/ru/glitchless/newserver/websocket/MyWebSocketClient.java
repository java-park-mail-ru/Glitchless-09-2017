package ru.glitchless.newserver.websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.BlockingQueue;

public class MyWebSocketClient extends WebSocketClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyWebSocketClient.class);
    private final Object lock = new Object();
    private final BlockingQueue<String> blockingQueue;
    private final int port;

    public MyWebSocketClient(BlockingQueue<String> blockingQueue, int port) throws URISyntaxException {
        super(new URI("ws://localhost:" + port + "/game"));
        this.port = port;
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        LOGGER.debug("onOpen");
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    @Override
    public void onMessage(String s) {
        LOGGER.debug(s);
        blockingQueue.offer(s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        LOGGER.debug("onClose");

    }

    @Override
    public void onError(Exception e) {
        LOGGER.error("onError", e);
    }

    public BlockingQueue<String> getBlockingQueue() {
        return blockingQueue;
    }

    public void waitOpen() throws InterruptedException {
        synchronized (lock) {
            while (!super.isOpen()) {
                lock.wait();
            }
        }
    }

    public MyWebSocketClient clone(BlockingQueue<String> blockingQueue) throws URISyntaxException {
        return new MyWebSocketClient(blockingQueue, port);
    }
}
