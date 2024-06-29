package net.diamond.mod;

import net.diamond.mod.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;

public class DiamondModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        KeyInputHandler.register();
    }
}
