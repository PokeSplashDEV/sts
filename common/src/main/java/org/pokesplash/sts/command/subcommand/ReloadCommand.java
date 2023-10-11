package org.pokesplash.sts.command.subcommand;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.pokesplash.sts.STS;
import org.pokesplash.sts.util.Permissions;
import org.pokesplash.sts.util.Subcommand;
import org.pokesplash.sts.util.Utils;

public class ReloadCommand extends Subcommand {

	public ReloadCommand() {
		super("This is the subcommand usage.");
	}

	/**
	 * Method used to add to the base command for this subcommand.
	 * @return source to complete the command.
	 */
	@Override
	public LiteralCommandNode<CommandSourceStack> build() {
		return Commands.literal("reload")
				.requires(e -> {
					if (e.isPlayer()) {
						return STS.permissions.hasPermission(e.getPlayer(), STS.permissions.getPermission(
								"ReloadPermission"));
					} else {
						return true;
					}
				})
				.executes(this::run)
				.build();
	}

	/**
	 * Method to perform the logic when the command is executed.
	 * @param context the source of the command.
	 * @return integer to complete command.
	 */
	@Override
	public int run(CommandContext<CommandSourceStack> context) {

		STS.init();

		context.getSource().sendSystemMessage(Component.literal(Utils.formatMessage("Reloaded STS.",
				context.getSource().isPlayer())));

		return 1;
	}
}
