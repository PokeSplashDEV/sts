package org.pokesplash.sts.ui;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.FlagType;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.item.PokemonItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.pokesplash.sts.STS;
import org.pokesplash.sts.api.Price;
import org.pokesplash.sts.api.event.StsEvents;
import org.pokesplash.sts.api.event.events.SellEvent;
import org.pokesplash.sts.util.ImpactorService;
import org.pokesplash.sts.util.Utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Sell screen.
 */
public class SellScreen {
	/**
	 * Method to get the page for the sell screen.
	 * @param pokemon The Pokemon to create the page for.
	 * @return The page.
	 */
	public Page getPage(Pokemon pokemon) {
		// Adds price to the lore.
		Collection<String> pokemonLore = new ArrayList<>();
		pokemonLore.add("§bPrice: §e" + Price.getPrice(pokemon));

		// Creates the Pokemon button.
		Button pokemonButton = GooeyButton.builder()
				.display(PokemonItem.from(pokemon, 1))
				.title(pokemon.getShiny() ? "§e" + pokemon.getDisplayName().getString() :
						"§b" + pokemon.getDisplayName().getString())
				.lore(pokemonLore)
				.build();

		// Creates the purchase button.
		Button purchase = GooeyButton.builder()
				.display(Utils.parseItemId(STS.lang.getPurchaseMaterial()))
				.title("§2Confirm Sale")
				.onClick(e -> {
					// Gets price
					double price = Price.getPrice(pokemon);

					// Attempts the transaction.
					boolean impactorSuccess = ImpactorService.add(ImpactorService.getAccount(e.getPlayer().getUUID()),
							price);

					// If successful, try to remove the Pokemon.
					if (impactorSuccess) {
						ServerPlayer sender = e.getPlayer();
						PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(sender);
						boolean removeSuccess = party.remove(pokemon);

						// If Pokemon was removed successful, close UI and send success message.
						if (removeSuccess) {
							UIManager.closeUI(sender);
							e.getPlayer().sendSystemMessage(Component.literal(
									Utils.formatPlaceholders(STS.lang.getSuccessMessage(), price, pokemon)));
							StsEvents.SELL.trigger(new SellEvent(sender.getUUID(), pokemon));
							STS.logs.addBalance(sender.getUUID(), price);
						} else {
							// Otherwise, revert transaction and send the party removal failed message.
							ImpactorService.remove(ImpactorService.getAccount(e.getPlayer().getUUID()),
									price);
							e.getPlayer().sendSystemMessage(Component.literal(
									Utils.formatPlaceholders(STS.lang.getPartyRemovalFailMessage(), price, pokemon)));
						}
					} else {
						// Otherwise send the transaction failed message.
						e.getPlayer().sendSystemMessage(Component.literal(
								Utils.formatPlaceholders(STS.lang.getTransactionFailMessage(), price, pokemon)));
					}
				})
				.build();

		// Cancel button, returns to main screen.
		Button cancel = GooeyButton.builder()
				.display(Utils.parseItemId(STS.lang.getCancelMaterial()))
				.title("§cCancel")
				.onClick(e -> {
					ServerPlayer player = e.getPlayer();
					PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);

					UIManager.openUIForcefully(player, new MainScreen().getPage(party));
				})
				.build();

		// Filler item for the border again.
		Button filler = GooeyButton.builder()
				.display(Utils.parseItemId(STS.lang.getFillerMaterial()))
				.hideFlags(FlagType.All)
				.lore(new ArrayList<>())
				.title("")
				.build();

		// Template
		ChestTemplate template = ChestTemplate.builder(3)
				.fill(filler)
				.set(11, purchase)
				.set(13, pokemonButton)
				.set(15, cancel)
				.build();

		// Creates the page.
		return GooeyPage.builder()
				.template(template)
				.title(pokemon.getShiny() ? "§e" + pokemon.getDisplayName().getString() :
						"§b" + pokemon.getDisplayName().getString())
				.build();
	}
}
