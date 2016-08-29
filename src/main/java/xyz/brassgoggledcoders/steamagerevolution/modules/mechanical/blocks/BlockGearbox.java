package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockTEBase;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.TileEntityGearbox;

public class BlockGearbox extends BlockTEBase<TileEntityGearbox> {
	public BlockGearbox(Material mat, String name) {
		super(mat, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityGearbox.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityGearbox();
	}

}
