package org.pokesplash.sts.ui;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.FlagType;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.item.CobblemonItem;
import com.cobblemon.mod.common.item.PokemonItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.pokesplash.sts.STS;
import org.pokesplash.sts.api.Price;
import org.pokesplash.sts.util.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The main UI screen of STS.
 */
public class MainScreen {
	/**
	 * Method to fetch the page.
	 * @param party Players party to display.
	 * @return Built Page.
	 */
	public Page getPage(PlayerPartyStore party) {
		List<Button> pokemon = new ArrayList<>(); // Creates a list for the Pokemon buttons.
		for (int x=0; x < 6; x++) { // Checks each slot.
			Pokemon mon = party.get(x);

			// If no Pokemon, add a Pokeball as a placeholder.
			if (mon == null) {
				Button ball = GooeyButton.builder()
						.display(new ItemStack(CobblemonItems.POKE_BALL, 1))
						.title("No Pokemon in Slot")
						.build();

				pokemon.add(ball);
				continue;
			}

			// Adds price to lore.
			Collection<String> lore = new ArrayList<>();
			lore.add("§bPrice: §e" + Price.getPrice(mon));

			// Generates title, based on if the Pokemon is shiny or not.
			String title = mon.getShiny() ? "§e" + mon.getDisplayName().getString() :
					"§b" + mon.getDisplayName().getString();

			// Creates the button for the Pokemon.
			GooeyButton button = GooeyButton.builder()
					.display(PokemonItem.from(mon, 1))
					.title(title)
					.lore(lore)
					.onClick((e) -> {
						// Opens the sell page.
						ServerPlayer player = e.getPlayer();
						UIManager.openUIForcefully(player, new SellScreen().getPage(mon));
					})
					.build();

			// Add the button to the list.
			pokemon.add(button);
		}

		// Info button.
		Button info = GooeyButton.builder()
				.display(Utils.parseItemId(STS.lang.getInfoMaterial()))
				.title(STS.lang.getTitle())
				.lore(STS.lang.getInfoDetails())
				.build();

		// Adds the info button halfway through.
		pokemon.add(3, info);

		// Filler material for the border.
		Button filler = GooeyButton.builder()
				.display(Utils.parseItemId(STS.lang.getFillerMaterial()))
				.hideFlags(FlagType.All)
				.lore(new ArrayList<>())
				.title("")
				.build();

		PlaceholderButton placeholder = new PlaceholderButton();

		// Creates the template.
		ChestTemplate template = ChestTemplate.builder(3)
				.rectangle(1, 1, 1, 7, placeholder)
				.fill(filler)
				.build();

		LinkedPage page = PaginationHelper.createPagesFromPlaceholders(template, pokemon, null);
		page.setTitle(STS.lang.getTitle());

		return page;
	}
}
