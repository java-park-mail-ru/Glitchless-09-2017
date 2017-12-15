package ru.glitchless.game.data.packages.toclient;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import ru.glitchless.newserver.data.model.WebSocketMessage;

import java.util.HashMap;
import java.util.Map;

public class FullSwapScene extends WebSocketMessage {
    private final Map<String, SnapObject> hashMap = new HashMap<>();

    @JsonAnySetter
    public void put(String type, SnapObject object) {
        hashMap.put(type, object);
    }

    @JsonAnyGetter
    public Map<String, SnapObject> getMap() {
        return hashMap;
    }
}
