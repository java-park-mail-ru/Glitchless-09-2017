package ru.glitchless.repositories.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import org.springframework.stereotype.Service;
import ru.glitchless.data.stores.Resource;
import ru.glitchless.data.throwables.ResourceException;

import java.io.IOException;

@Service
public class ResourceFactory {
    private final ObjectMapper objectMapper;

    public ResourceFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Get resource.
     *
     * @param path - path to resource
     * @return Resource
     * @throws ResourceException - if cant find or parse the resource
     */
    public Resource get(String path) {
        return get(path, Resource.class);
    }

    @SuppressWarnings("OverlyBroadCatchBlock")
    public <T extends Resource> T get(String path, Class<T> clazz) {
        final T resource;
        try {
            resource = objectMapper.readValue(Resources.getResource("config/" + path), clazz);
        } catch (IOException e) {
            throw new ResourceException("Failed constructing resource object " + path + " of type " + clazz.getName(), e);
        }

        return resource;
    }
}
