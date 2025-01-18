package com.github.deroq1337.partygames.core.data.game.board;

import com.github.deroq1337.partygames.api.config.YamlConfig;
import com.github.deroq1337.partygames.core.data.game.board.models.PartyGamesBoardField;
import com.github.bukkitnews.partygames.common.serialization.MapDirectedLocation;
import lombok.*;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class PartyGamesBoard extends YamlConfig {

    private @NotNull String name;
    private @Nullable MapDirectedLocation startLocation;
    private @Nullable Map<Integer, PartyGamesBoardField> fields;

    public PartyGamesBoard(@NotNull File file) {
        super(file);
    }

    public PartyGamesBoard(@NotNull String name) {
        this(new File("plugins/partygames/boards/" + name + ".yml"));
        this.name = name;
    }

    public int addField(@NotNull Location location, boolean event) {
        if (fields == null) {
            this.fields = new HashMap<>();
        }

        int id = fields.size() + 1;
        fields.put(id, new PartyGamesBoardField(new MapDirectedLocation(location), event));
        return id;
    }

    public boolean removeField(int id) {
        return Optional.ofNullable(fields)
                .map(fields -> removeAndUpdateMap(fields, id))
                .orElse(false);
    }

    public int getNumberOfFields() {
        return Optional.ofNullable(fields)
                .map(Map::size)
                .orElse(0);
    }

    public Optional<PartyGamesBoardField> getField(int id) {
        return Optional.ofNullable(fields).map(fields -> fields.get(id));
    }

   /* public void setStartLocation(@NotNull Location location) {
        this.startLocation = new MapDirectedLocation(location);
    } */

    private <T> boolean removeAndUpdateMap(@Nullable Map<Integer, T> map, int id) {
        return Optional.ofNullable(map).map(idMap -> {
            boolean removed = idMap.remove(id) != null;
            if (!removed) {
                return false;
            }

            List<Integer> keysToUpdate = map.keySet().stream()
                    .filter(i -> i > id)
                    .toList();
            keysToUpdate.forEach(i -> map.put(i - 1, map.remove(i)));
            return true;
        }).orElse(false);
    }
}
