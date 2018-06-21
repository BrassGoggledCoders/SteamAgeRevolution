package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.ModuleMetalworking;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerFiltered.ItemStackHandlerFuel;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockBase;

public class ControllerSteelworks extends SARMultiblockBase implements ISmartTankCallback {

	public FluidTank steamTank = new FluidTankSmart(Fluid.BUCKET_VOLUME * 16, this);
	public FluidTank ironTank = new FluidTankSmart(Fluid.BUCKET_VOLUME * 16, this);
	public ItemStackHandler inputSolid = new ItemStackHandlerFuel(1);
	public FluidTank outputTank = new FluidTankSmart(Fluid.BUCKET_VOLUME * 16, this);

	// public static final int workingPoolLevel = ModuleMetalworking.VALUE_BLOCK * 9;
	public static final int conversionPerOperation = ModuleMetalworking.VALUE_NUGGET;
	public static final int steamUsePerOperation = Fluid.BUCKET_VOLUME / 10;
	public static final int carbonPerOperation = TileEntityFurnace.getItemBurnTime(new ItemStack(Items.COAL)) / 2;

	int carbonLevel = 0;

	public ControllerSteelworks(World world) {
		super(world);
	}

	@Override
	protected FluidTank getTank(String toWrap) {
		if(toWrap.equals("iron")) {
			return ironTank;
		}
		else if(toWrap.equals("steel")) {
			return outputTank;
		}
		return steamTank;
	}

	@Override
	public ItemStackHandler getInventory(String toWrap) {
		return inputSolid;
	}

	@Override
	protected boolean updateServer() {
		boolean flag = false;

		boolean hasIron = ironTank.getFluid() != null
				&& ironTank.getFluid().isFluidEqual(FluidRegistry.getFluidStack("iron", conversionPerOperation));
		boolean hasItems = !inputSolid.getStackInSlot(0).isEmpty();

		if(hasItems) {
			carbonLevel += TileEntityFurnace.getItemBurnTime(inputSolid.getStackInSlot(0));
			inputSolid.extractItem(0, 1, false);
			flag = true;
		}

		if(hasIron && carbonLevel >= carbonPerOperation) {
			if(ironTank.getFluidAmount() >= conversionPerOperation
					&& steamTank.getFluidAmount() >= steamUsePerOperation) {
				FluidStack steel = FluidRegistry.getFluidStack("steel", conversionPerOperation);
				if(steamTank.getFluid().isFluidEqual(FluidRegistry.getFluidStack("steam", conversionPerOperation))
						&& outputTank.fill(steel, false) == conversionPerOperation) {
					ironTank.drain(conversionPerOperation, true);
					steamTank.drain(steamUsePerOperation, true);
					carbonLevel -= carbonPerOperation;
					outputTank.fill(steel, true);
					flag = true;
				}
			}

		}
		return flag;
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		// TODO
		return 1;
	}

	@Override
	public int getMinimumXSize() {
		return 5;
	}

	@Override
	public int getMinimumZSize() {
		return 5;
	}

	@Override
	public int getMinimumYSize() {
		return 9;
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
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
	protected void onAssimilated(MultiblockControllerBase assimilator) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateClient() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isBlockGoodForFrame(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		inputSolid.deserializeNBT(data.getCompoundTag("solidInput"));
		ironTank.readFromNBT(data.getCompoundTag("fluidInput1"));
		outputTank.readFromNBT(data.getCompoundTag("output"));
		steamTank.readFromNBT(data.getCompoundTag("steam"));
		carbonLevel = data.getInteger("carbon");
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setTag("solidInput", inputSolid.serializeNBT());
		data.setTag("fluidInput1", ironTank.writeToNBT(new NBTTagCompound()));
		data.setTag("output", outputTank.writeToNBT(new NBTTagCompound()));
		data.setTag("steam", steamTank.writeToNBT(new NBTTagCompound()));
		data.setInteger("carbon", carbonLevel);
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
	protected boolean isBlockGoodForSides(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void readFromDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFluid(PacketFluidUpdate fluid) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Steelworks";
	}

	@Override
	public int getMaximumXSize() {
		return this.getMinimumXSize();
	}

	@Override
	public int getMaximumZSize() {
		return this.getMinimumZSize();
	}

	@Override
	public int getMaximumYSize() {
		return this.getMinimumYSize();
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return world.isAirBlock(new BlockPos(x, y, z));
	}

	@Override
	public ItemStackHandlerExtractSpecific getItemInput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiFluidTank getFluidInputs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStackHandlerExtractSpecific getItemOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiFluidTank getFluidOutputs() {
		// TODO Auto-generated method stub
		return null;
	}
}
