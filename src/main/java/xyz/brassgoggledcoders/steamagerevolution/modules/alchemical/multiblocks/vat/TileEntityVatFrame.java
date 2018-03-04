package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityVatFrame extends TileEntityVatPart {

	@SideOnly(Side.CLIENT)
	@Override
	public net.minecraft.util.math.AxisAlignedBB getRenderBoundingBox() {
		if(this.isConnected()) {
			return new AxisAlignedBB(this.getMultiblockController().getMinimumCoord(),
					this.getMultiblockController().getMaximumCoord());
		}
		else
			return super.getRenderBoundingBox();
	}

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
}