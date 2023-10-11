package org.pokesplash.sts.api;

import com.cobblemon.mod.common.pokemon.Pokemon;
import org.pokesplash.sts.STS;
import org.pokesplash.sts.util.Utils;

/**
 * Methods for checking price.
 */
public abstract class Price {

	/**
	 * Method to calculate the price of a given Pokemon.
	 * @param pokemon The pokemon to calculate the price for.
	 * @return The price.
	 */
	public static double getPrice(Pokemon pokemon) {
		double base = STS.config.getBasePrice(); // Starts with the base price.

		// Checks for HA, and adds price.
		if (Utils.isHA(pokemon)) {
			base += STS.config.getHiddenAbilityBoost();
		}

		// Checks for legendary, and adds price.
		if (pokemon.isLegendary()) {
			base += STS.config.getLegendaryBoost();
		}

		// Checks for UB, and adds price.
		if (pokemon.isUltraBeast()) {
			base += STS.config.getUltraBeastBoost();
		}

		// Checks for Shiny and adds price.
		if (pokemon.getShiny()) {
			base += STS.config.getShinyBoost();
		}

		// Adds money for the amount of full IVs the Pokemon has.
		base += (Utils.amountOfFullIVS(pokemon) * STS.config.getMoneyPerIV());

		// Adds money for each level the Pokemon has.
		base += pokemon.getLevel() * STS.config.getMoneyPerLevel();

		// Checks for 6 full IVs, adds price.
		if (Utils.amountOfFullIVS(pokemon) == 6) {
			base += STS.config.getPerfectIVBoost();
		}

		return base;
	}
}
