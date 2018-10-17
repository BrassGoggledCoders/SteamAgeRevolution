package xyz.brassgoggledcoders.steamagerevolution.modules.armory.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.tileenties.TileEntityGunsmithingBench;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.BlockGUIBase;

public class BlockGunsmithingBench extends BlockGUIBase<TileEntityGunsmithingBench> {
	public BlockGunsmithingBench() {
		super(Material.WOOD, "gunsmithing_bench");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityGunsmithingBench.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityGunsmithingBench();
	}
}