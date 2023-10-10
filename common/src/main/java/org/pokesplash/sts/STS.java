package org.pokesplash.sts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pokesplash.sts.command.basecommand.ExampleCommand;
import org.pokesplash.sts.util.CommandsRegistry;
import org.pokesplash.sts.util.Permissions;

public class STS
{
	public static final String MOD_ID = "sts";
	public static final Permissions permissions = new Permissions();
	public static final Logger LOGGER = LogManager.getLogger();

	public static void init() {
		// Adds command to registry
		CommandsRegistry.addCommand(new ExampleCommand());
	}
}
