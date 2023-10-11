package org.pokesplash.sts.command.basecommand;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.FlagType;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.helpers.InventoryHelper;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.item.PokemonItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.pokesplash.sts.STS;
import org.pokesplash.sts.api.Price;
import org.pokesplash.sts.command.subcommand.ReloadCommand;
import org.pokesplash.sts.ui.MainScreen;
import org.pokesplash.sts.util.BaseCommand;
import org.pokesplash.sts.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Creates the mods base command.
 */
public class STSCommand extends BaseCommand {

	/**
	 * Constructor to create a new comand.
	 */
	public STSCommand() {
		// Super needs the command, a list of aliases, permission and array of subcommands.
		super("sts", Arrays.asList(),
				STS.permissions.getPermission("BasePermission"), Arrays.asList(new ReloadCommand()));
	}

	// Runs when the base command is run with no subcommands.
	@Override
	public int run(CommandContext<CommandSourceStack> context) {

		// Only players can run the command.
		if (!context.getSource().isPlayer()) {
			context.getSource().sendSystemMessage(Component.literal("This command must be ran by a player!"));
		}

		ServerPlayer sender = context.getSource().getPlayer();

		PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(sender);

		UIManager.openUIForcefully(sender, new MainScreen().getPage(party)); // Open UI.

		return 1;
	}
}
