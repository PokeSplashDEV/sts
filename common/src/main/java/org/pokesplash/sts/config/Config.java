package org.pokesplash.sts.config;

import com.google.gson.Gson;
import org.pokesplash.sts.STS;
import org.pokesplash.sts.util.Utils;

import java.util.concurrent.CompletableFuture;

public class Config {
	private double basePrice;
	private double hiddenAbilityBoost;
	private double legendaryBoost;
	private double ultraBeastBoost;
	private double shinyBoost;
	private double moneyPerIV;
	private double moneyPerLevel;
	private double perfectIVBoost;

	public Config() {
		basePrice = 100;
		hiddenAbilityBoost = 50;
		legendaryBoost = 50;
		ultraBeastBoost = 50;
		shinyBoost = 50;
		moneyPerIV = 10;
		moneyPerLevel = 1;
		perfectIVBoost = 50;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public double getHiddenAbilityBoost() {
		return hiddenAbilityBoost;
	}

	public double getLegendaryBoost() {
		return legendaryBoost;
	}

	public double getUltraBeastBoost() {
		return ultraBeastBoost;
	}

	public double getShinyBoost() {
		return shinyBoost;
	}

	public double getMoneyPerIV() {
		return moneyPerIV;
	}

	public double getMoneyPerLevel() {
		return moneyPerLevel;
	}

	public double getPerfectIVBoost() {
		return perfectIVBoost;
	}

	public void init() {
		CompletableFuture<Boolean> futureRead = Utils.readFileAsync("/config/sts/", "config.json", el -> {
			Gson gson = Utils.newGson();
			Config cfg = gson.fromJson(el, Config.class);
			basePrice = cfg.getBasePrice();
			hiddenAbilityBoost = cfg.getHiddenAbilityBoost();
			legendaryBoost = cfg.getLegendaryBoost();
			ultraBeastBoost = cfg.getUltraBeastBoost();
			shinyBoost = cfg.getShinyBoost();
			moneyPerIV = cfg.getMoneyPerIV();
			moneyPerLevel = cfg.getMoneyPerLevel();
			perfectIVBoost = cfg.getPerfectIVBoost();
		});

		if (!futureRead.join()) {
			STS.LOGGER.info("Could not find Config file for STS, attempting to generate one.");
			Gson gson = Utils.newGson();
			CompletableFuture<Boolean> futureWrite = Utils.writeFileAsync("/config/sts/", "config.json",
					gson.toJson(this));

			if (!futureWrite.join()) {
				STS.LOGGER.fatal("Could not write STS Config file.");
				return;
			}
		}
		STS.LOGGER.info("STS Config file read successfully.");
	}
}
