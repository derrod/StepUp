package com.nottoomanyitems.stepup;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.EndTick;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;

import java.rmi.registry.Registry;

public final class StepChanger implements EndTick {
    public KeyBinding myKey;
    public static int autoJumpState = -1; //0 StepUp, 1 None, 2 Minecraft
    public static boolean firstRun = true;
    public static String serverName;

    private static Identifier stepHeightIdent = Identifier.of("minecraft", "generic.step_height");
    private static RegistryEntry<EntityAttribute> stepHeightAttr = Registries.ATTRIBUTE.getEntry(stepHeightIdent).get();
    
    private MinecraftClient mc;

    public void setKeyBindings() {
        final String category="key.categories.stepup";
        KeyBindingHelper.registerKeyBinding(myKey = new KeyBinding("key.stepup.toggle", InputUtil.Type.KEYSYM, GLFW_KEY_J, category));
    }

    @Override
    public void onEndTick(MinecraftClient client) {
        ClientPlayerEntity player;
        mc=client;      // can't do this in onInit because mixins need to be applied first
        player = client.player;
        if (player==null)
            return;
        processKeyBinds();

        EntityAttributeInstance attr = player.getAttributeInstance(stepHeightAttr);

        if(player.isSneaking()) {
        	attr.setBaseValue(.6f);
        } else if(autoJumpState == 0 && player.getStepHeight() < 1.0f){
        	attr.setBaseValue(1.25f);
        } else if(autoJumpState == 1 && player.getStepHeight() >= 1.0f){
        	attr.setBaseValue(.6f);
        } else if(autoJumpState == 2 && player.getStepHeight() >= 1.0f){
        	attr.setBaseValue(.6f);
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
        SimpleOption<Boolean> option = mc.options.getAutoJump();
    	boolean b = option.getValue();
    	if (autoJumpState < 2 && b == true) {
    		option.setValue(false);
    	} else if (autoJumpState == 2 && b == false) {
    		option.setValue(true);
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
        mc.player.sendMessage(Text.literal(m), false);
    }
}