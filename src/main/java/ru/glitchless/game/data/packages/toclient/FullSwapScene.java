package ru.glitchless.game.data.packages.toclient;

import ru.glitchless.server.data.models.WebSocketMessage;

import java.util.HashMap;
import java.util.Map;

public class FullSwapScene extends WebSocketMessage {
    public Map<String, SnapObject> hashMap = new HashMap<>();

    public void put(String type, SnapObject object) {
        hashMap.put(type, object);
    }
}
