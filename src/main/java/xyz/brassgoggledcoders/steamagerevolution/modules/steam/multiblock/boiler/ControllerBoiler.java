package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler;

import java.util.HashSet;
import java.util.Set;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.FluidTankSingleType;

// TODO NBT
public class ControllerBoiler extends RectangularMultiblockControllerBase {

	public static final int fuelDivisor = 3;
	public static final int fluidConversionPerTick = 10;

	// Lists of connected parts
	private Set<TileEntityWaterInput> attachedInputs;
	private Set<TileEntitySteamOutput> attachedOutputs;
	private Set<TileEntitySolidFirebox> attachedFireboxes;

	public ItemStackHandler solidFuelInventory;
	public FluidTankSingleType waterTank;
	public FluidTankSingleType steamTank;

	int temperature = 0;
	int currentBurnTime = 0;

	protected ControllerBoiler(World world) {
		super(world);
		attachedInputs = new HashSet<TileEntityWaterInput>();
		attachedOutputs = new HashSet<TileEntitySteamOutput>();
		attachedFireboxes = new HashSet<TileEntitySolidFirebox>();
		solidFuelInventory = new ItemStackHandler(3);
	}

	@Override
	protected boolean updateServer() {
		if(steamTank.getFluidAmount() == (steamTank.getCapacity() - fluidConversionPerTick)
				|| waterTank.getFluidAmount() < fluidConversionPerTick)
			return false;

		if(temperature < 100 && currentBurnTime == 0) {
			for(int i = 0; i < solidFuelInventory.getSlots(); i++) {
				ItemStack fuel = solidFuelInventory.getStackInSlot(i);
				if(ItemStackUtils.isItemNonNull(fuel) && TileEntityFurnace.getItemBurnTime(fuel) != 0) {
					currentBurnTime = (TileEntityFurnace.getItemBurnTime(fuel) / fuelDivisor);
					fuel.stackSize--;
					solidFuelInventory.setStackInSlot(i, fuel);
				}
			}
		}
		else {
			steamTank.fill(new FluidStack(FluidRegistry.getFluid("steam"), fluidConversionPerTick), true);
			waterTank.drain(fluidConversionPerTick, true);
			currentBurnTime--;
		}
		return false;
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		// FMLLog.warning("Part added " + newPart.toString());
		if(newPart instanceof TileEntityWaterInput) {
			attachedInputs.add((TileEntityWaterInput) newPart);
			waterTank = new FluidTankSingleType(waterTank.getFluid(),
					(Fluid.BUCKET_VOLUME * 16) * attachedInputs.size(), "water");
		}
		else if(newPart instanceof TileEntitySteamOutput) {
			attachedOutputs.add((TileEntitySteamOutput) newPart);
			steamTank = new FluidTankSingleType(steamTank.getFluid(),
					(Fluid.BUCKET_VOLUME * 16) * attachedOutputs.size(), "steam");
		}
		// TODO Not only solid
		else if(newPart instanceof TileEntitySolidFirebox) {
			attachedFireboxes.add((TileEntitySolidFirebox) newPart);
		}
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		if(oldPart instanceof TileEntityWaterInput) {
			attachedInputs.remove(oldPart);
			waterTank = new FluidTankSingleType(waterTank.getFluid(),
					(Fluid.BUCKET_VOLUME * 16) * attachedInputs.size(), "water");
		}
		else if(oldPart instanceof TileEntitySteamOutput) {
			attachedOutputs.remove(oldPart);
			steamTank = new FluidTankSingleType(steamTank.getFluid(),
					(Fluid.BUCKET_VOLUME * 16) * attachedOutputs.size(), "steam");
		}
		else if(oldPart instanceof TileEntitySolidFirebox) {
			attachedFireboxes.remove(oldPart);
		}
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 26;
	}

	@Override
	protected int getMaximumXSize() {
		return 3;
	}

	@Override
	protected int getMaximumZSize() {
		return 3;
	}

	@Override
	protected int getMaximumYSize() {
		return 3;
	}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {
		this.attachedInputs.clear();
		this.attachedFireboxes.clear();
		this.attachedOutputs.clear();
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		if(world.isAirBlock(new BlockPos(x, y, z)))
			return true;
		else
			return false;
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMachineAssembled() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMachineRestored() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMachinePaused() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMachineDisassembled() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onAssimilate(MultiblockControllerBase assimilated) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateClient() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isBlockGoodForTop(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isBlockGoodForBottom(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void readFromDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFromUpdatePacket(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeToUpdatePacket(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isBlockGoodForFrame(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isBlockGoodForSides(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

}
