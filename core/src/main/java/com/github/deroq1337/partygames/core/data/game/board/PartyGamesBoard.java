package com.github.deroq1337.partygames.core.data.game.board;

import com.github.deroq1337.partygames.core.data.game.board.converters.EnumConverter;
import com.github.deroq1337.partygames.core.data.game.board.converters.MapDirectedLocationConverter;
import com.github.deroq1337.partygames.core.data.game.board.converters.MapLocationConverter;
import com.github.deroq1337.partygames.core.data.game.board.models.PartyGamesBoardField;
import com.github.deroq1337.partygames.core.data.game.board.serialization.MapDirectedLocation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.cubespace.Yamler.Config.InvalidConverterException;
import net.cubespace.Yamler.Config.YamlConfig;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class PartyGamesBoard extends YamlConfig {

    private @NotNull String name;
    private @Nullable Map<Integer, PartyGamesBoardField> fields;

    public PartyGamesBoard(@NotNull File file) {
        this.CONFIG_FILE = file;

        try {
            addConverter(MapLocationConverter.class);
            addConverter(MapDirectedLocationConverter.class);
            addConverter(EnumConverter.class);
        } catch (InvalidConverterException e) {
            throw new RuntimeException(e);
        }
    }

    public PartyGamesBoard(@NotNull String name) {
        this(new File("plugins/partygames/boards/" + name + ".yml"));
        this.name = name;
    }

    public boolean delete() {
        return CONFIG_FILE.delete();
    }

    public boolean exists() {
        return CONFIG_FILE.exists();
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
