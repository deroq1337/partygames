package com.github.deroq1337.partygames.game.blockjump.listeners;

import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.game.base.PartyGameBase;
import com.github.deroq1337.partygames.game.base.config.PartyGameConfig;
import com.github.deroq1337.partygames.game.blockjump.map.BlockJumpMap;
import com.github.deroq1337.partygames.game.blockjump.states.BlockJumpRunningState;
import com.github.deroq1337.partygames.game.blockjump.user.BlockJumpUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlayerMoveListener implements Listener {

    private final @NotNull PartyGame<BlockJumpMap, ? extends PartyGameConfig, BlockJumpUser> partyGame;

    public PlayerMoveListener(@NotNull PartyGame<BlockJumpMap, ? extends PartyGameConfig, BlockJumpUser> partyGame) {
        this.partyGame = partyGame;
        PartyGameBase.registerListener(this);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!(partyGame.getCurrentState() instanceof BlockJumpRunningState state)) {
            return;
        }

        Player player = event.getPlayer();
        partyGame.getUser(player.getUniqueId()).ifPresent(user -> {
            if (user.isFinished()) {
                return;
            }

            if (user.getCurrentBlock().isEmpty()) {
                Optional.ofNullable(partyGame.getMap().getSpawnLocation()).ifPresent(spawnLocation -> {
                    if (player.getLocation().getY() == partyGame.getMap().getSpawnLocation().getY()) {
                        state.setBlock(user);
                    }
                });
                return;
            }

            user.getCurrentBlock().ifPresent(currentBlock -> {
                Optional.ofNullable(event.getTo()).ifPresent(to -> {
                    if (didUserFallDown(user, to.getY())) {
                        state.onFail(user);
                        return;
                    }

                    if (to.clone().subtract(0, 1, 0).getBlock().equals(currentBlock)) {
                        state.onJump(user);
                    }
                });
            });
        });
    }

    private boolean didUserFallDown(@NotNull BlockJumpUser user, double y) {
        return user.getPreviousBlock()
                .map(previousBlock -> y < previousBlock.getLocation().getY())
                .orElse(false);
    }
}
