package org.pokesplash.sts.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.pokesplash.sts.STS;
import org.pokesplash.sts.util.CommandsRegistry;

public class STSFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        STS.init();
        CommandRegistrationCallback.EVENT.register(CommandsRegistry::registerCommands);
    }
}