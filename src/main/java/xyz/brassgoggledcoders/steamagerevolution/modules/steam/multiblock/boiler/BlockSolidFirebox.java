package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSolidFirebox extends BlockTEBase<TileEntitySolidFirebox> {

	public BlockSolidFirebox(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySolidFirebox.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySolidFirebox();
	}

}
