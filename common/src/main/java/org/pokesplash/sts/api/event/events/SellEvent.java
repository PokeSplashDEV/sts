package org.pokesplash.sts.api.event.events;

import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerPlayer;

/**
 * Class that is used to trigger a sell event.
 */
public class SellEvent {
	private ServerPlayer seller; // The player who sold the Pokemon.
	private Pokemon pokemon; // The Pokemon that was sold.

	/**
	 * Constructor to create the event.
	 * @param seller Seller
	 * @param pokemon Pokemon sold.
	 */
	public SellEvent(ServerPlayer seller, Pokemon pokemon) {
		this.seller = seller;
		this.pokemon = pokemon;
	}

	/**
	 * Getters
	 */

	public ServerPlayer getSeller() {
		return seller;
	}

	public Pokemon getPokemon() {
		return pokemon;
	}
}
