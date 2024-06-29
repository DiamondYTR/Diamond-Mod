package net.diamond.mod.event;

import net.diamond.mod.Ghost;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


public class KeyInputHandler {
    public static final String KEY_CATEGORY = "key.category.mod.ghostcategory";
    public static final String KEY_GHOST = "key.mod.ghost";

    public static KeyBinding ghostKey;
    private static final Logger LOGGER = LoggerFactory.getLogger("diamondmod");


    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (ghostKey.wasPressed()) {
                Ghost.ghost(client.player);
                LOGGER.info("They pressed the hotkey.");
            }
        });
    }

    public static void register() {
        ghostKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_GHOST,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                KEY_CATEGORY
        ));

        registerKeyInputs();
    }
}
