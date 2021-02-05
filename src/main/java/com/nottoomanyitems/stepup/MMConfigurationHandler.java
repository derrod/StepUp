package com.nottoomanyitems.stepup;

import de.guntram.mcmod.fabrictools.ConfigurationProvider;
import de.guntram.mcmod.fabrictools.GuiModOptions;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.client.gui.screen.Screen;

public class MMConfigurationHandler implements ModMenuApi
{
    @Override
    public String getModId() {
        return "stepup";
    }

    @Override
    public ConfigScreenFactory getModConfigScreenFactory() {
        return screen -> new GuiModOptions(screen, Main.MODNAME, ConfigurationProvider.getHandler(Main.MODNAME));
    }
}
