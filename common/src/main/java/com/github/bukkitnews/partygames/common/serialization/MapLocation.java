package com.github.bukkitnews.partygames.common.serialization;

import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MapLocation {

    private @NotNull String world;
    private double x;
    private double y;
    private double z;

    public MapLocation(@NotNull Location location) {
        this.world = Optional.ofNullable(location.getWorld())
                .map(WorldInfo::getName)
                .orElseThrow(() -> new IllegalStateException("MapLocation: world of bukkit location is null"));
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public MapLocation(@NotNull Map<String, Object> map) {
        this.world = (String) map.getOrDefault("world", "world");
        this.x = (double) map.get("x");
        this.y = (double) map.get("y");
        this.z = (double) map.get("z");
    }

    public @NotNull Location toBukkitLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z);
    }

    public @NotNull Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("world", world);
        map.put("x", x);
        map.put("y", y);
        map.put("z", z);
        return map;
    }
}
