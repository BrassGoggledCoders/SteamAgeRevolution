package xyz.brassgoggledcoders.steamagerevolution.modules.storage.multiblock.tank.tileentities;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityTankValve extends TileEntityTankPart {

    public TileEntityTankValve() {

    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    @Nonnull
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new TankWrapper(this));
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean isGoodForSides(IMultiblockValidator validatorCallback) {

        return true;
    }

    @Override
    public boolean isGoodForTop(IMultiblockValidator validatorCallback) {

        return true;
    }

    @Override
    public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {

        return true;
    }

}
