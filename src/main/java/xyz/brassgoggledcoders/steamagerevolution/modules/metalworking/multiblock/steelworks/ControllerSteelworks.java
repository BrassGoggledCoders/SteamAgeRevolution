package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.TileEntityCastingBench;
import xyz.brassgoggledcoders.steamagerevolution.utils.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.ISmartTankCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.SARRectangularMultiblockControllerBase;

public class ControllerSteelworks extends SARRectangularMultiblockControllerBase implements ISmartTankCallback {

	public FluidTank steamTank = new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "steam", this);
	public FluidTank ironTank = new FluidTankSingleSmart(TileEntityCastingBench.VALUE_BLOCK * 16, "iron", this);
	public ItemStackHandler inputSolid = new ItemStackHandler();
	public FluidTank outputTank = new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "steel", this);

	public static final int workingPoolLevel = TileEntityCastingBench.VALUE_BLOCK * 9;
	public static final int conversionPerOperation = TileEntityCastingBench.VALUE_NUGGET;
	public static final int steamUsePerOperation = Fluid.BUCKET_VOLUME / 10;
	public static final int carbonPerOperation = 1;

	public ControllerSteelworks(World world) {
		super(world);
	}

	@Override
	protected boolean updateServer() {
		boolean flag = false;

		boolean hasIron = ironTank.getFluid() != null;
		boolean hasItems = !inputSolid.getStackInSlot(0).isEmpty();

		// Can't do anything without a base for the alloy
		if(hasIron && hasItems) {
			if(ironTank.getFluidAmount() >= workingPoolLevel) {
				if(inputSolid.getStackInSlot(0).getCount() >= carbonPerOperation
						&& steamTank.getFluidAmount() >= steamUsePerOperation) {
					if((outputTank.getCapacity() - outputTank.getFluidAmount()) >= conversionPerOperation) {
						ironTank.drain(conversionPerOperation, true);
						steamTank.drain(steamUsePerOperation, true);
						inputSolid.extractItem(0, carbonPerOperation, false);
						outputTank.fill(new FluidStack(FluidRegistry.getFluid("steel"), conversionPerOperation), true);
						flag = true;
					}
				}
			}

		}
		// else do nothing, can't make a recipe with one fluid
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
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setTag("solidInput", inputSolid.serializeNBT());
		data.setTag("fluidInput1", ironTank.writeToNBT(new NBTTagCompound()));
		data.setTag("output", outputTank.writeToNBT(new NBTTagCompound()));
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
	public void onTankContentsChanged(FluidTank tank) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFromDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFluid(FluidStack fluid) {
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
}