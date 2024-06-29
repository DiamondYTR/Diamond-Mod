package net.diamond.mod.mixin;

import net.diamond.mod.Ghost;
import net.diamond.mod.event.Check;
import net.diamond.mod.Ghost.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.diamond.mod.DiamondMod.MOD_ID;


@Mixin(ClientConnection.class)
public class ExampleMixin {

    @Inject(method = "send(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        if (Ghost.status() && ((packet instanceof PlayerMoveC2SPacket)||(packet instanceof UpdateSelectedSlotC2SPacket)||(packet instanceof ButtonClickC2SPacket)||(packet instanceof ClickSlotC2SPacket)||(packet instanceof CraftRequestC2SPacket)||(packet instanceof CreativeInventoryActionC2SPacket)||(packet instanceof PickFromInventoryC2SPacket)||(packet instanceof PlayerInteractBlockC2SPacket)||(packet instanceof PlayerInputC2SPacket)||(packet instanceof PlayerActionC2SPacket)||(packet instanceof HandSwingC2SPacket)||(packet instanceof PlayerInteractEntityC2SPacket)||(packet instanceof PlayerInteractItemC2SPacket))) {
            ci.cancel();
        }
    }
}
