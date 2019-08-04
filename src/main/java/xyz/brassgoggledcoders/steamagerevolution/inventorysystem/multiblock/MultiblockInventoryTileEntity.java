package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock;

import javax.annotation.Nonnull;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.MultiblockMachineType;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.SARMultiblockTileBase;

public abstract class MultiblockInventoryTileEntity<MB extends MultiblockCraftingMachine<? extends InventoryCraftingMachine>>
        extends SARMultiblockTileBase<MB> implements IHasInventory<InventoryCraftingMachine> {
    // Handles sync on world load
    @Nonnull
    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbt = this.writeToNBT(super.getUpdateTag());
        if(this.isConnected() && this.isMultiblockSaveDelegate()) {
            this.getMultiblockController().writeToDisk(nbt);
        }
        return nbt;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleUpdateTag(NBTTagCompound tag) {
        if(this.isConnected() && this.isMultiblockSaveDelegate()) {
            this.getMultiblockController().onAttachedPartWithMultiblockData(this, tag);
        }
        this.readFromNBT(tag);
    }

    // Delegate IHasInventory/IMachine methods to those of our controller

    @Override
    public InventoryCraftingMachine getInventory() {
        // FIX This causes (harmless-ish) NPEs on world load
        return this.getMultiblockController().getInventory();
    }

    @Override
    public void setInventory(InventoryCraftingMachine inventory) {
        throw new UnsupportedOperationException("Cannot use setInventory on a read-only delegate");
    }

    @Override
    public void markMachineDirty() {
        this.getMultiblockController().markMachineDirty();
    }

    @Override
    public World getMachineWorld() {
        return super.getWorld();
    }

    @Override
    public BlockPos getMachinePos() {
        return super.getPos();
    }

    @Override
    public MultiblockMachineType getMachineType() {
        return this.getMultiblockController().getMachineType();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return this.getMultiblockController().getInventory().hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return this.getMultiblockController().getInventory().getCapability(capability, facing);
    }
}
