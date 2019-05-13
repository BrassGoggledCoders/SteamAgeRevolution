package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.blocks;

import com.teamacronymcoders.base.blocks.BlockGUIBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.tileentities.TileEntityFumeCollector;

public class BlockFumeCollector extends BlockGUIBase<TileEntityFumeCollector> {
	public BlockFumeCollector(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityFumeCollector.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityFumeCollector();
	}
}
