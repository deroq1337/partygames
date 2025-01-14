package com.github.deroq1337.partygames.core.data.game.provider;

import lombok.Getter;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.io.File;
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
        this.main = String.valueOf(map.get("main"));
        this.version = Optional.ofNullable(String.valueOf(map.get("version")));
        this.author = Optional.ofNullable(String.valueOf(map.get("author")));
        this.displayItem = Optional.ofNullable(map.get("displayItem"))
                .map(Material::valueOf);
    }

    public @NotNull File getDirectory(@NotNull File gamesDirectory) {
        File gameDirectory = new File(gamesDirectory, name + "/");
        if (!gameDirectory.exists()) {
            gameDirectory.mkdirs();
        }

        return gameDirectory;
    }
}
