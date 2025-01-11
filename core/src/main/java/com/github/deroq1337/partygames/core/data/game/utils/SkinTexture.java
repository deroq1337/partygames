package com.github.deroq1337.partygames.core.data.game.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

public class SkinTexture {

    public static Optional<URL> getUrlFromTexture(@NotNull String texture) {
        if (!texture.startsWith("ey")) {
            return Optional.empty();
        }

        byte[] decodedBytes = Base64.getDecoder().decode(texture);
        String decodedTexture = new String(decodedBytes, StandardCharsets.UTF_8);
        if (decodedTexture.isEmpty()) {
            return Optional.empty();
        }

        JsonObject textureObject = JsonParser.parseString(decodedTexture).getAsJsonObject();
        return Optional.ofNullable(textureObject.getAsJsonObject("textures")).flatMap(texturesObject -> {
            return Optional.ofNullable(texturesObject.getAsJsonObject("SKIN")).flatMap(skinObject -> {
                return Optional.ofNullable(skinObject.get("url")).flatMap(url -> {
                    return createUrl(url.getAsString());
                });
            });
        });
    }

    private static Optional<URL> createUrl(String s) {
        try {
            return Optional.of(URI.create(s).toURL());
        } catch (MalformedURLException e) {
            System.err.println("invalid url: " + s);
            return Optional.empty();
        }
    }
}
