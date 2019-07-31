package xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.BlockMultiblockBase;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.tileentities.TileEntityBoilerLiquidFirebox;

public class BlockBoilerLiquidFirebox extends BlockMultiblockBase<TileEntityBoilerLiquidFirebox> {

	public BlockBoilerLiquidFirebox(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityBoilerLiquidFirebox.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityBoilerLiquidFirebox();
	}

}
