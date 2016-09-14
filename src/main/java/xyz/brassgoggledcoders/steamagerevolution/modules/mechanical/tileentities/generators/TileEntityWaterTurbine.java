package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.generators;

import net.minecraft.block.material.Material;

public class TileEntityWaterTurbine extends TileEntitySpinGenerator {

	private static int blocksToCheck = 25;

	public TileEntityWaterTurbine() {
		super();
	}

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		int numberOfBlocks = 0;

		for(int i = 1; i < blocksToCheck - 1; i++) {
			if(getWorld().getBlockState(getPos().up(i)).getMaterial() == Material.WATER)
				numberOfBlocks++;
			else
				break;
		}

		if(numberOfBlocks != 0) {
			this.handler.setSpeed(3 * numberOfBlocks);
		}
		else {
			// Spin down
			if(this.handler.getSpeed() > 0) {
				this.handler.decrSpeed();
			}
		}

		this.sendBlockUpdate();

		super.updateTile();
	}
}