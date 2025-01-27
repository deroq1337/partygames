package com.github.deroq1337.partygames.core.data.game.board.models;

import com.github.bukkitnews.partygames.common.serialization.MapDirectedLocation;
import lombok.*;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_21_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class PartyGamesBoardField {

    private @NotNull MapDirectedLocation location;
    private boolean eventField;

    public void glow(@NotNull Player player) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                Location blockLocation = location.toBukkitLocation().clone().add(x, 0, z);

                CraftPlayer craftPlayer = ((CraftPlayer) player);
                CraftWorld world = (CraftWorld) player.getWorld();
                ServerPlayer serverPlayer = craftPlayer.getHandle();

                ArmorStand armorStand = new ArmorStand(EntityType.ARMOR_STAND, world.getHandle());
                armorStand.setPos(blockLocation.getX(), location.getY(), location.getZ());
                armorStand.setInvisible(true);
                armorStand.setNoGravity(true);
                armorStand.setMarker(true);
                armorStand.setCustomNameVisible(false);
                armorStand.setGlowingTag(true);

                ServerEntity serverEntity = new ServerEntity(world.getHandle(), armorStand, 0, false, packet -> {}, Set.of());
                craftPlayer.getHandle().connection.send(craftPlayer.getHandle().getAddEntityPacket(serverEntity));
            }
        }
    }
}
