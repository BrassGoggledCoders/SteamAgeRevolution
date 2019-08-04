package xyz.brassgoggledcoders.steamagerevolution.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.SARCaps;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBuilder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryHeatable;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.TileEntityCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.pieces.InventoryPieceTemperatureGauge;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.MachineType;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.PacketSetBurnTime;

public class TileEntityHeater extends TileEntityCraftingMachine<InventoryHeatable> implements ITickable {

    public static final String uid = "heater";

    public TileEntityHeater() {
        setInventory(new InventoryBuilder<>(new InventoryHeatable(this, 1000)).addSteamTank(10, 30)
                .addPiece("temp", new InventoryPieceTemperatureGauge(10, 50)).build());
    }

    @Override
    public void update() {
        if(!getWorld().isRemote) {
            SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
                    new PacketSetBurnTime(getMachinePos(),
                            getInventory().getCapability(SARCaps.HEATABLE, null).getCurrentTemperature()),
                    getMachinePos(), getMachineWorld().provider.getDimension());
            if(getInventory().getFluidHandlers().get(0).getFluidAmount() > 0) {
                getInventory().internal.setCurrentTemperature(1000);
            }
            for(EnumFacing facing : EnumFacing.VALUES) {
                TileEntity te = getWorld().getTileEntity(getPos().offset(facing));
                if(te != null && te.hasCapability(SARCaps.HEATABLE, facing)) {
                    getInventory().internal.heat(-10);
                    te.getCapability(SARCaps.HEATABLE, facing).heat(10);
                }
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == SARCaps.HEATABLE || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
                || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == SARCaps.HEATABLE) {
            return getInventory().getCapability(capability, facing);
        }
        else if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
                    .cast(getInventory().getHandler("steamTank", FluidTankSync.class));
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public MachineType getMachineType() {
        if(!MachineType.machinesList.containsKey(uid)) {
            MachineType.machinesList.put(uid, new MachineType(uid));
        }
        return MachineType.machinesList.get(uid);
    }
}
