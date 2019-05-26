package xyz.brassgoggledcoders.steamagerevolution.multiblocks.crucible.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.crucible.tileentities.TileEntityCrucibleSteamInput;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockCrucibleSteamInput extends BlockMultiblockBase<TileEntityCrucibleSteamInput> {

	public BlockCrucibleSteamInput(Material material, String name) {
		super(material, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityCrucibleSteamInput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityCrucibleSteamInput();
	}
}