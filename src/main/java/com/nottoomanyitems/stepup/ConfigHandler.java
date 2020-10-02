package com.nottoomanyitems.stepup;

import de.guntram.mcmod.fabrictools.ConfigChangedEvent;
import de.guntram.mcmod.fabrictools.Configuration;
import de.guntram.mcmod.fabrictools.ModConfigurationHandler;
import java.io.File;
import java.util.List;

public class ConfigHandler implements ModConfigurationHandler {
    public static Configuration config;
    public static final String[] stepupKeys = {
        "mod.stepup.config.dostepup",
        "mod.stepup.config.nostepup",
        "mod.stepup.config.vanillajump"
    };

    public static void load(File file) {
        config = new Configuration(file);
        
        // make sure a default entry exists
        getDefaultConfigValue();
        // Port old config format, which used ints, to the new selection list format
        boolean needToSave = false;
        List<String> presentServers = config.getKeys();
        for (String serverName: presentServers) {
            if (!config.isSelectList(serverName)) {
                int oldValue = (int) (double) config.getValue(serverName);
                config.getSelection(serverName, Configuration.CATEGORY_CLIENT, oldValue, stepupKeys, "mod.stepup.config.tt.onthisserver");
                needToSave = true;
            }
        }
        if (needToSave) {
            config.save();
        }
    }

    public static void reloadConfig() {
        if (config.hasChanged()) {
            config.save();
        }
    }

    public static void loadConfigForServer(String serverName) {
        int defaultState;
        defaultState = getDefaultConfigValue();
    	StepChanger.autoJumpState = config.getSelection(serverName,
                Configuration.CATEGORY_CLIENT, defaultState, stepupKeys, "mod.stepup.config.tt.onthisserver");
        System.out.println("setting state on "+serverName+" to "+StepChanger.autoJumpState);
        ConfigHandler.reloadConfig();
    }
    
    public static int getDefaultConfigValue() {
        return config.getSelection("mod.stepup.config.default", Configuration.CATEGORY_CLIENT, 0, stepupKeys, "");
    }

    public static void changeConfig() {
        config.setValue(StepChanger.serverName, StepChanger.autoJumpState);
        ConfigHandler.reloadConfig();
    }

    @Override
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals("stepup")) {
            ConfigHandler.reloadConfig();
        }
    }

    @Override
    public Configuration getConfig() {
        return config;
    }
}

