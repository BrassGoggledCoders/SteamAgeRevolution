package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.generators;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockMechanicalTEBase;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.generators.TileEntityWaterTurbine;

public class BlockWaterTurbine extends BlockMechanicalTEBase<TileEntityWaterTurbine> {
	public BlockWaterTurbine(Material mat, String name) {
		super(mat, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityWaterTurbine.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityWaterTurbine();
	}

}
