package com.github.bukkitnews.partygames.common.serialization;

import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MapDirectedLocation extends MapLocation {

    private float yaw;
    private float pitch;

    public MapDirectedLocation(@NotNull Location location) {
        super(location);
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }

    public MapDirectedLocation(@NotNull Map<String, Object> map) {
      super(map);
      this.yaw = Double.valueOf((double) map.get("yaw")).floatValue();
      this.pitch = Double.valueOf((double) map.get("pitch")).floatValue();
    }

    public @NotNull Location toBukkitLocation() {
        return new Location(Bukkit.getWorld(getWorld()), getX(), getY(), getZ(), yaw, pitch);
    }

    @Override
    public @NotNull Map<String, Object> toMap() {
        Map<String, Object> map = super.toMap();
        map.put("yaw", yaw);
        map.put("pitch", pitch);
        return map;
    }
}
