package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import net.minecraft.util.text.TextComponentTranslation;

public enum RecipeError {
	NONE(-1, ""), OUTPUT_BLOCKED(0,
			"sar.recipeerror.outputspace")/* TODO Information on which output */, INSUFFICIENT_STEAM(1,
					"sar.recipeerror.insufficientsteam");

	String translationKey;
	Short networkID;

	RecipeError(int networkID, String translationKey) {
		this.networkID = Integer.valueOf(networkID).shortValue();
		this.translationKey = translationKey;
	}

	public Short getNetworkID() {
		return networkID;
	}

	public String getTranslationKey() {
		return translationKey;
	}

	public String getLocalized() {
		return new TextComponentTranslation(this.getTranslationKey()).getFormattedText();
	}

	// TODO
	public static RecipeError fromNetworkID(short errorID) {
		for(RecipeError error : RecipeError.values()) {
			if(error.networkID.equals(errorID)) {
				return error;
			}
		}
		return RecipeError.NONE;
	}

}