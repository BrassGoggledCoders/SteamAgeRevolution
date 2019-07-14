package xyz.brassgoggledcoders.steamagerevolution.multiblocks.grinder.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.MultiblockInventoryTileEntity;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.grinder.ControllerGrinder;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;

public abstract class TileEntityGrinderPart extends MultiblockInventoryTileEntity<ControllerGrinder> {

	@Override
	public Class<ControllerGrinder> getMultiblockControllerType() {
		return ControllerGrinder.class;
	}

	@Override
	public ISARMultiblock getControllerInfo() {
		return new ControllerGrinder(null);
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerGrinder(getWorld());
	}
}