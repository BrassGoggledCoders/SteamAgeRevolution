package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.tileentities.TileEntityCrucibleCasing;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockCrucibleCasing extends BlockMultiblockBase<TileEntityCrucibleCasing> {

    public BlockCrucibleCasing(Material material, String name) {
        super(material, name);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityCrucibleCasing.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        return new TileEntityCrucibleCasing();
    }

}
