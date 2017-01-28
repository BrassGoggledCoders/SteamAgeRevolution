package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntityFurnaceCasing extends TileEntityFurnacePart {
	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return true;
	}
}