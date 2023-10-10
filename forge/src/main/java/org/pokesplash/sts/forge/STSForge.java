package org.pokesplash.sts.forge;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.pokesplash.sts.STS;
import org.pokesplash.sts.util.CommandsRegistry;

@Mod(STS.MOD_ID)
public class STSForge {
    public STSForge() {
        STS.init();
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent e) {
        CommandsRegistry.registerCommands(e.getDispatcher());
    }
}