package com.github.deroq1337.partygames.core.data.game.provider;

import lombok.Getter;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

@Getter
public class PartyGameManifest {

    private final @NotNull String name;
    private final @NotNull String description;
    private final @NotNull String main;
    private final Optional<String> version;
    private final Optional<String> author;
    private final Optional<Material> displayItem;

    public PartyGameManifest(@NotNull Map<String, String> map) {
        this.name = String.valueOf(map.get("name"));
        this.description = String.valueOf(map.get("description"));
        this.version = Optional.ofNullable(String.valueOf(map.get("version")));
        this.author = Optional.ofNullable(String.valueOf(map.get("author")));
        this.main = String.valueOf(map.get("main"));
        this.displayItem = Optional.ofNullable(String.valueOf(map.get("displayItem")))
                .map(Material::valueOf);
    }
}
