package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible;

import org.apache.commons.lang3.tuple.Pair;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.TileEntityCastingBench;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.PositionUtils;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerFiltered.ItemStackHandlerCrucible;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARRectangularMultiblockControllerBase;

public class ControllerCrucible extends SARRectangularMultiblockControllerBase implements ISmartTankCallback {

	BlockPos minimumInteriorPos;
	BlockPos maximumInteriorPos;
	public ItemStackHandler solid = new ItemStackHandlerCrucible();
	public FluidTankSmart tank = new FluidTankSmart(0, this);
	public FluidTankSingleSmart steamTank = new FluidTankSingleSmart(Fluid.BUCKET_VOLUME, "steam", this);

	public static int steamPerOperation = Fluid.BUCKET_VOLUME / 10;

	public ControllerCrucible(World world) {
		super(world);
	}

	@Override
	public ItemStackHandler getInventory(String toWrap) {
		return solid;
	}

	@Override
	protected FluidTank getTank(String toWrap) {
		if(toWrap.equals("output")) {
			return tank;
		}
		return steamTank;
	}

	@Override
	protected boolean updateServer() {
		if(steamTank.getFluidAmount() >= steamPerOperation) {
			if(TileEntityCastingBench.meltMetal(solid, tank)) {
				steamTank.drain(steamPerOperation, true);
				return true;
			}
		}
		return false;
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		solid.deserializeNBT(data.getCompoundTag("inventory"));
		tank.readFromNBT(data.getCompoundTag("tank"));
		steamTank.readFromNBT(data.getCompoundTag("steamTank"));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setTag("inventory", solid.serializeNBT());
		data.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		data.setTag("steamTank", steamTank.writeToNBT(new NBTTagCompound()));
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		// TODO Auto-generated method stub

	}

	// FIXME Caching
	@Override
	protected void onMachineAssembled() {
		Pair<BlockPos, BlockPos> interiorPositions =
				PositionUtils.shrinkPositionCubeBy(this.getMinimumCoord(), this.getMaximumCoord(), 1);
		this.minimumInteriorPos = interiorPositions.getLeft();
		this.maximumInteriorPos = interiorPositions.getRight();

		int blocksInside = 0;
		// TODO Expensive for loop just to increment an integer
		for(BlockPos pos : BlockPos.getAllInBox(minimumInteriorPos, maximumInteriorPos)) {
			blocksInside++;
		}
		// Size internal tank accordingly
		tank = new FluidTankSmart(tank.getFluid(), blocksInside * Fluid.BUCKET_VOLUME * 16, this);
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
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 17;
	}

	@Override
	public int getMaximumXSize() {
		return 5;
	}

	@Override
	public int getMaximumZSize() {
		return 5;
	}

	@Override
	public int getMaximumYSize() {
		return 5;
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
		return false;
	}

	@Override
	protected boolean isBlockGoodForTop(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
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
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return world.isAirBlock(new BlockPos(x, y, z));
	}

	@Override
	public void readFromDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTankContentsChanged(FluidTank tank) {
		SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
				new PacketFluidUpdate(this.getReferenceCoord(), tank.getFluid()), this.getReferenceCoord(),
				WORLD.provider.getDimension());
	}

	@Override
	public void updateFluid(PacketFluidUpdate message) {
		if(message.fluid.getFluid().equals(FluidRegistry.getFluid("steam")))
			steamTank.setFluid(message.fluid);
		else
			tank.setFluid(message.fluid);
	}

	@Override
	public String getName() {
		return "Crucible";
	}
}
