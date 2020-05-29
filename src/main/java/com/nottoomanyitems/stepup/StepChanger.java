package com.nottoomanyitems.stepup;

import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;

public final class StepChanger implements ClientTickCallback {
    public FabricKeyBinding myKey;
    public static int autoJumpState = -1; //0 StepUp, 1 None, 2 Minecraft
    public static boolean firstRun = true;
    public static String serverIP;
    
    private MinecraftClient mc;

    public void setKeyBindings() {
        final String category="key.categories.stepup";
        KeyBindingRegistry.INSTANCE.addCategory(category);
        KeyBindingRegistry.INSTANCE.register(
            myKey=FabricKeyBinding.Builder
                .create(new Identifier("stepup:toggle"), InputUtil.Type.KEYSYM, GLFW_KEY_J, category)
                .build());        
    }

    @Override
    public void tick(MinecraftClient client) {
        ClientPlayerEntity player;
        mc=client;      // can't do this in onInit because mixins need to be applied first
        player = client.player;
        if (player==null)
            return;
        processKeyBinds();
        if(player.isSneaking()) {
        	player.stepHeight = .6f;
        }else if(autoJumpState == 0 && player.stepHeight < 1.0f){
        	player.stepHeight = 1.25f;
        }else if(autoJumpState == 1 && player.stepHeight >= 1.0f){
        	player.stepHeight = .6f;
        }else if(autoJumpState == 2 && player.stepHeight >= 1.0f){
        	player.stepHeight = .6f;
        }
        autoJump();

        if (firstRun && autoJumpState != -1) {
        	message();
            firstRun = false;
        }
    }

    public void processKeyBinds() {
        if (myKey.wasPressed()) {
            autoJumpState = ( autoJumpState + 1 ) % 3;
            message();
            ConfigHandler.changeConfig();
        }
    }
    
    private void autoJump() {
    	boolean b = mc.options.autoJump;
    	if(autoJumpState < 2 && b == true) {
    		mc.options.autoJump=false;
    	}else if(autoJumpState == 2 && b == false) {
    		mc.options.autoJump=true;
    	}
    }
    
    private void message() {
    	String m = Formatting.DARK_AQUA + "[" + Formatting.YELLOW + "StepUp" + Formatting.DARK_AQUA + "]" + " ";
    	if(autoJumpState == 0) {
    		m = m + Formatting.GREEN + I18n.translate("mod.stepup.enabled");
    	}else if(autoJumpState == 1) {
    		m = m + Formatting.RED + I18n.translate("mod.stepup.disabled");
    	}else if(autoJumpState == 2) {
    		m = m + Formatting.GREEN + I18n.translate("mod.stepup.minecraft") + " " + I18n.translate("mod.stepup.autojump") + " " + I18n.translate("mod.stepup.enabled");
    	}
        mc.player.sendMessage(new LiteralText(m), false);
    }
}