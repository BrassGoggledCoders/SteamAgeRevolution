package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller.tileentities.TileEntityDistillerHotplate;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockDistillerHotplate extends BlockMultiblockBase<TileEntityDistillerHotplate> {

	public BlockDistillerHotplate(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityDistillerHotplate.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityDistillerHotplate();
	}
}