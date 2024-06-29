package net.diamond.mod.event;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.server.network.ServerPlayerEntity;

public class Check {

    public static boolean grounded() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) {
            return false;
        }
        World world = client.player.getWorld();
        BlockPos posBelow = client.player.getBlockPos().down();
        BlockState blockBelow = world.getBlockState(posBelow);

        // Check if the block below is not air (or any other specific block check)
        return !blockBelow.isAir();
    }
}
