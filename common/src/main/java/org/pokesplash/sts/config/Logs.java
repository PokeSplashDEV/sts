package org.pokesplash.sts.config;

import com.google.gson.Gson;
import org.pokesplash.sts.STS;
import org.pokesplash.sts.util.Utils;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Config class.
 */
public class Logs {
	private HashMap<UUID, Double> data; // Player sell data.

	public Logs() {
		data = new HashMap<>();
	}

	/**
	 * Getters
	 */
	public HashMap<UUID, Double> getData() {
		return data;
	}

	/**
	 * Method to read or write the config file.
	 */
	public void init() {
		CompletableFuture<Boolean> futureRead = Utils.readFileAsync("/config/sts/", "logs.json", el -> {
			Gson gson = Utils.newGson();
			Logs cfg = gson.fromJson(el, Logs.class);
			data = cfg.getData();
		});

		if (!futureRead.join()) {
			STS.LOGGER.info("Could not find logs file for STS, attempting to generate one.");
			Gson gson = Utils.newGson();
			CompletableFuture<Boolean> futureWrite = Utils.writeFileAsync("/config/sts/", "logs.json",
					gson.toJson(this));

			if (!futureWrite.join()) {
				STS.LOGGER.fatal("Could not write STS logs file.");
				return;
			}
		}
		STS.LOGGER.info("STS logs file read successfully.");
	}

	public void addBalance(UUID player, double amount) {
		if (amount <= 0) {
			return;
		}

		if (!data.containsKey(player)) {
			data.put(player, 0.0);
		}

		double current = data.get(player);
		data.put(player, current + amount);

		Gson gson = Utils.newGson();
		CompletableFuture<Boolean> futureWrite = Utils.writeFileAsync("/config/sts/", "logs.json",
				gson.toJson(this));

		if (!futureWrite.join()) {
			STS.LOGGER.fatal("Could not write STS logs file.");
		}
	}
}
