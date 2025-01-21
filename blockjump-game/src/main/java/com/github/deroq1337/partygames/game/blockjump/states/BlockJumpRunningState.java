package com.github.deroq1337.partygames.game.blockjump.states;

import com.github.deroq1337.partygames.api.countdown.Countdown;
import com.github.deroq1337.partygames.api.game.PartyGame;
import com.github.deroq1337.partygames.game.base.states.FinishablePartyGameRunningState;
import com.github.deroq1337.partygames.game.blockjump.config.BlockJumpConfig;
import com.github.deroq1337.partygames.game.blockjump.countdowns.BlockJumpRunningCountdown;
import com.github.deroq1337.partygames.game.blockjump.map.BlockJumpMap;
import com.github.deroq1337.partygames.game.blockjump.user.BlockJumpUser;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class BlockJumpRunningState extends FinishablePartyGameRunningState<BlockJumpUser> {

    private final @NotNull BlockJumpConfig config;
    private final @NotNull BlockJumpMap map;

    public BlockJumpRunningState(@NotNull PartyGame<BlockJumpMap, BlockJumpConfig, BlockJumpUser> partyGame, @NotNull BlockJumpMap map) {
        super(partyGame);
        this.config = partyGame.getGameConfig();
        this.map = map;
    }

    @Override
    public void enter() {
        super.enter();
        partyGame.getUsers().forEach(this::setBlock);
    }

    @Override
    public void leave() {
        super.leave();
        partyGame.getUsers().forEach(this::destroyBlocks);
    }

    @Override
    public @NotNull Countdown initCountdown() {
        return new BlockJumpRunningCountdown(partyGame, this);
    }

    @Override
    public void finish(@NotNull BlockJumpUser user) {
        super.finish(user);
        destroyBlocks(user);
    }

    private void destroyBlocks(@NotNull BlockJumpUser user) {
        user.getCurrentBlock().ifPresent(currentBlock -> {
            currentBlock.setType(Material.AIR);
            user.setCurrentBlock(Optional.empty());
        });

        user.getPreviousBlock().ifPresent(previousBlock -> {
            previousBlock.setType(Material.AIR);
            user.setPreviousBlock(Optional.empty());
        });
    }

    public void onJump(@NotNull BlockJumpUser user) {
        user.incrementValue();
        if (isGoalReached(user) && !user.isFinished()) {
            finish(user);
            return;
        }

        setBlock(user);
    }

    public void onFail(@NotNull BlockJumpUser user) {
        destroyBlocks(user);
        user.setValue(0);
    }

    public void setBlock(@NotNull BlockJumpUser user) {
        user.getPreviousBlock().ifPresent(previousBlock -> previousBlock.setType(Material.AIR));
        user.setPreviousBlock(user.getCurrentBlock());

        user.getBukkitPlayer().ifPresent(player -> {
            Block block = user.getPreviousBlock()
                    .map(previousBlock -> findJump(previousBlock.getLocation()))
                    .orElseGet(() -> findJump(player.getLocation().clone().subtract(0, 1, 0)));

            block.setType(Material.TERRACOTTA);
            user.setCurrentBlock(Optional.of(block));
            player.playSound(block.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
        });
    }

    private @NotNull Block findJump(@NotNull Location previousLocation) {
        List<List<Integer>> jumps = config.getJumps();

        for (int tries = 50; tries > 0; tries--) {
            List<Integer> jump = jumps.get(ThreadLocalRandom.current().nextInt(jumps.size()));
            if (jump.size() != 3) {
                System.err.println("Jump " + jump + " is not of size 3");
                continue;
            }

            double y = jump.get(1);
            if (y == 0) {
                y = fixHeight(y);
            }

            Location blockLocation = previousLocation.clone().add(jump.get(0), y, jump.get(2));
            if (blockLocation.getBlock().getType() == Material.AIR) {
                return blockLocation.getBlock();
            }
        }

        throw new IllegalStateException("No possible jump found");
    }

    private double fixHeight(double y) {
        double finalY = y;
        boolean isFirstBlock = Optional.ofNullable(map.getSpawnLocation())
                .map(spawnLocation -> finalY + 1 == spawnLocation.getY())
                .orElse(false);

        if (isFirstBlock) {
            y++;
        }

        return y;
    }

    @Override
    public boolean isGoalReached(@NotNull BlockJumpUser user) {
        return user.getValue() >= map.getGoalBlocks();
    }
}
