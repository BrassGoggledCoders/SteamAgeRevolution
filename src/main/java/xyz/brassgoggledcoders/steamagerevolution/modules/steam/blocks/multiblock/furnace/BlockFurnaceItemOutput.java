package xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.furnace;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.furnace.TileEntityFurnaceItemOutput;

public class BlockFurnaceItemOutput extends BlockTEBase<TileEntityFurnaceItemOutput> {

	public BlockFurnaceItemOutput(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityFurnaceItemOutput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityFurnaceItemOutput();
	}

}