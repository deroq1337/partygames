package com.github.deroq1337.partygames.core.data.game.loader;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

@Getter
public class PartyGameManifest {

    private final @NotNull String name;
    private final Optional<String> version;
    private final Optional<String> author;
    private final @NotNull String main;

    public PartyGameManifest(@NotNull Map<String, String> map) {
        this.name = String.valueOf(map.get("name"));
        this.version = Optional.ofNullable(String.valueOf(map.get("version")));
        this.author = Optional.ofNullable(String.valueOf(map.get("author")));
        this.main = String.valueOf(map.get("main"));
    }
}
