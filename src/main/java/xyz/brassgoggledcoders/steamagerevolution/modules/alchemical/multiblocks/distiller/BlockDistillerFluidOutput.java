package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockMultiblockBase;

public class BlockDistillerFluidOutput extends BlockMultiblockBase<TileEntityDistillerFluidOutput> {

	public BlockDistillerFluidOutput(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityDistillerFluidOutput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityDistillerFluidOutput();
	}
}