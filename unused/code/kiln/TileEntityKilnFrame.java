package xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.kiln;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntityKilnFrame extends TileEntityKilnPart {

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		return true;
	}
}
