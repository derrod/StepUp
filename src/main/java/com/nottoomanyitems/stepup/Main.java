package com.nottoomanyitems.stepup;

import de.guntram.mcmod.fabrictools.ConfigurationProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class Main implements ClientModInitializer {
    
    public static final String MODNAME="StepUp";
    public static final String MODID="stepup";

    @Override
    public void onInitializeClient() {
        ConfigurationProvider.register(MODNAME, new ConfigHandler());
        ConfigHandler.load(ConfigurationProvider.getSuggestedFile(MODID));
        StepChanger stepChanger = new StepChanger();
        stepChanger.setKeyBindings();
        ClientTickEvents.END_CLIENT_TICK.register(stepChanger);
    }
}
