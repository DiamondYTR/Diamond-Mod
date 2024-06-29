package net.diamond.mod;
import net.diamond.mod.event.Check;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static net.diamond.mod.DiamondMod.MOD_ID;

public class Ghost {
    public static boolean toggle;
    public static double savedPosX, savedPosY, savedPosZ;
    public static float savedYaw, savedPitch;
    public static BlockPos posBelow; // Correct type
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void initialize() {
        // Register a tick event listener
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.world != null) {
                // World is loaded, perform your logic here
            }
        });
    }

    public static void ghost(ClientPlayerEntity player) {
        // When key pressed
        toggle = !toggle;
        if(toggle) {
            if(Check.grounded()) {
                player.sendMessage(Text.literal("Ghost enabled.").formatted(Formatting.GREEN), true);
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.player != null) {
                    World world = client.player.getWorld();
                    if (world != null) {
                        if (client.player.getBlockPos() != null) {
                            posBelow = client.player.getBlockPos().down();
                            LOGGER.info("SAVED POSITIONS");
                            savedPosX = player.getX();
                            savedPosY = player.getY();
                            savedPosZ = player.getZ();
                            savedYaw = player.getYaw();
                            savedPitch = player.getPitch();
                        }
                    }
                }
            } else {
                player.sendMessage(Text.literal("Player is not grounded.").formatted(Formatting.RED), true);
                toggle = !toggle;
            }

        } else {
            player.sendMessage(Text.literal("Ghost disabled.").formatted(Formatting.GREEN), true);
            player.setPos(savedPosX, savedPosY, savedPosZ);
            player.setYaw(savedYaw);
            player.setPitch(savedPitch);
        }
    }

    public static boolean status() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            World world = client.player.getWorld();
            if (world != null) {
                if(posBelow != null) {
                    BlockState blockBelow = world.getBlockState(posBelow);
                    //LOGGER.info("{}: {}", toggle, !blockBelow.isAir());
                    if(toggle && blockBelow.isAir()){
                        client.player.sendMessage(Text.literal("Player moved.").formatted(Formatting.RED), true);
                        client.player.setPos(savedPosX, savedPosY, savedPosZ);
                        LOGGER.info("SENT BACK TO POSITIONS");
                        client.player.setYaw(savedYaw);
                        client.player.setPitch(savedPitch);
                        toggle = !toggle;



                        // Use CompletableFuture to delay and return false after 1 second
                        CompletableFuture<Boolean> future = new CompletableFuture<>();
                        CompletableFuture.delayedExecutor(10, TimeUnit.SECONDS).execute(() -> {
                            future.complete(false);
                        });

                        // Block and wait for the future to complete
                        try {
                            return future.get(); // This will block until the future completes
                        } catch (InterruptedException | ExecutionException e) {

                        }

                    }
                    return toggle && !blockBelow.isAir();
                }
            }
        }
        return false;
    }

}

