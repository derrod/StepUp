package com.nottoomanyitems.stepup.mixins;

import com.nottoomanyitems.stepup.ConfigHandler;
import com.nottoomanyitems.stepup.StepChanger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class NetHandler {
    
    @Inject(method="onGameJoin", at=@At("RETURN"))
    private void onConnectedToServerEvent(GameJoinS2CPacket packet, CallbackInfo cbi) {
        MinecraftClient mc=MinecraftClient.getInstance();
        ServerInfo serverData = mc.getCurrentServerEntry();
        StepChanger.serverName = serverData != null ? serverData.name : "localserver";
        System.out.println("connected to "+StepChanger.serverName);
        StepChanger.firstRun = true;
        ConfigHandler.loadConfigForServer(StepChanger.serverName);
    }
}
