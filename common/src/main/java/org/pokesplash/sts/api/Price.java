package org.pokesplash.sts.api;

import com.cobblemon.mod.common.pokemon.Pokemon;
import org.pokesplash.sts.STS;
import org.pokesplash.sts.util.Utils;

public abstract class Price {
	public static double getPrice(Pokemon pokemon) {
		double base = STS.config.getBasePrice();

		if (Utils.isHA(pokemon)) {
			base += STS.config.getHiddenAbilityBoost();
		}

		if (pokemon.isLegendary()) {
			base += STS.config.getLegendaryBoost();
		}

		if (pokemon.isUltraBeast()) {
			base += STS.config.getUltraBeastBoost();
		}

		if (pokemon.getShiny()) {
			base += STS.config.getShinyBoost();
		}

		base += (Utils.amountOfFullIVS(pokemon) * STS.config.getMoneyPerIV());

		base += pokemon.getLevel() * STS.config.getMoneyPerLevel();

		if (Utils.amountOfFullIVS(pokemon) == 6) {
			base += STS.config.getPerfectIVBoost();
		}

		return base;
	}
}
