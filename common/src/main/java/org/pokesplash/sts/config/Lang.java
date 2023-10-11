package org.pokesplash.sts.config;

import com.google.gson.Gson;
import org.pokesplash.sts.STS;
import org.pokesplash.sts.util.Utils;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * Class for custom language.
 */
public class Lang {
	private String title; // Title of the screen.
	private String fillerMaterial; // Material used as a border.
	private String infoMaterial; // Material of the info button in the middle.
	private ArrayList<String> infoDetails; // List of details to add to the info button.
	private String purchaseMaterial; // Material for the purchase button.
	private String cancelMaterial; // Material for the cancel button.
	private String successMessage; // Success message.
	private String transactionFailMessage; // Message sent when the transaction failed.
	private String partyRemovalFailMessage; // Message sent when the Pokemon couldn't be removed.

	public Lang() {
		title = "§3STS";
		fillerMaterial = "minecraft:white_stained_glass_pane";
		infoMaterial = "minecraft:end_crystal";
		infoDetails = new ArrayList<>();
		infoDetails.add("§9Sell your Pokemon to the server!");
		purchaseMaterial = "minecraft:green_stained_glass_pane";
		cancelMaterial = "minecraft:red_stained_glass_pane";
		successMessage = "§aSuccessfully sold {pokemon} to STS for {price}!";
		transactionFailMessage = "§cUnable to perform transaction for {pokemon} sale.";
		partyRemovalFailMessage = "§cUnable to remove {pokemon} from your party.";
	}

	/**
	 * Getters
	 */

	public String getTitle() {
		return title;
	}

	public String getFillerMaterial() {
		return fillerMaterial;
	}

	public String getInfoMaterial() {
		return infoMaterial;
	}

	public ArrayList<String> getInfoDetails() {
		return infoDetails;
	}

	public String getPurchaseMaterial() {
		return purchaseMaterial;
	}

	public String getCancelMaterial() {
		return cancelMaterial;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public String getTransactionFailMessage() {
		return transactionFailMessage;
	}

	public String getPartyRemovalFailMessage() {
		return partyRemovalFailMessage;
	}

	/**
	 * Method to read or write the file.
	 */
	public void init() {
		CompletableFuture<Boolean> futureRead = Utils.readFileAsync("/config/sts/", "lang.json", el -> {
			Gson gson = Utils.newGson();
			Lang lang = gson.fromJson(el, Lang.class);
			title = lang.getTitle();
			fillerMaterial = lang.getFillerMaterial();
			infoMaterial = lang.getInfoMaterial();
			infoDetails = lang.getInfoDetails();
			purchaseMaterial = lang.getPurchaseMaterial();
			cancelMaterial = lang.getCancelMaterial();
			successMessage = lang.getSuccessMessage();
			transactionFailMessage = lang.getTransactionFailMessage();
			partyRemovalFailMessage = lang.getPartyRemovalFailMessage();
		});

		if (!futureRead.join()) {
			STS.LOGGER.info("Could not find language file for STS, attempting to write a new one.");
			Gson gson = Utils.newGson();
			CompletableFuture<Boolean> futureWrite = Utils.writeFileAsync("/config/sts/", "lang.json", gson.toJson(this));

			if (!futureWrite.join()) {
				STS.LOGGER.fatal("Unable to write STS language file.");
				return;
			}
		}
		STS.LOGGER.info("Successfully ready language file for STS.");
	}
}
