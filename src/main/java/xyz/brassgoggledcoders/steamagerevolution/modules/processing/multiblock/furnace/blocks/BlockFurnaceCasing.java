package xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.tileentities.TileEntityFurnaceCasing;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockFurnaceCasing extends BlockMultiblockBase<TileEntityFurnaceCasing> {

	public BlockFurnaceCasing(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityFurnaceCasing.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityFurnaceCasing();
	}

}