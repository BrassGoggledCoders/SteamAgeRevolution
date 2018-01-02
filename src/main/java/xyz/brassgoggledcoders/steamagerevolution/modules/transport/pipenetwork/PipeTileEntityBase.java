package xyz.brassgoggledcoders.steamagerevolution.modules.transport.pipenetwork;

import java.util.*;

import com.teamacronymcoders.base.Base;
import com.teamacronymcoders.base.tileentities.TileEntityBase;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import xyz.brassgoggledcoders.steamagerevolution.modules.transport.ModuleTransport;

/**
 * Base logic class for Multiblock-connected tile entities. Most multiblock machines
 * should derive from this and implement their game logic in certain abstract methods.
 */
public abstract class PipeTileEntityBase<T extends PipeNetworkBase> extends TileEntityBase implements IPipe {
	private PipeNetworkBase controller;
	private boolean visited;

	private boolean saveMultiblockData;
	private NBTTagCompound cachedMultiblockData;
	// private boolean paused;

	public PipeTileEntityBase() {
		super();
		controller = null;
		visited = false;
		saveMultiblockData = false;
		// paused = false;
		cachedMultiblockData = null;
	}

	// Multiblock Connection Base Logic
	@Override
	public Set<PipeNetworkBase> attachToNeighbors() {
		Set<PipeNetworkBase> controllers = null;
		PipeNetworkBase bestController = null;

		// Look for a compatible controller in our neighboring parts.
		IPipe[] partsToCheck = getNeighboringParts();
		for(IPipe neighborPart : partsToCheck) {
			if(neighborPart.isConnected()) {
				PipeNetworkBase candidate = neighborPart.getMultiblockController();
				if(!candidate.getClass().equals(this.getMultiblockControllerType())) {
					// Skip multiblocks with incompatible types
					continue;
				}

				if(controllers == null) {
					controllers = new HashSet<PipeNetworkBase>();
					bestController = candidate;
				}
				else if(!controllers.contains(candidate) && candidate.shouldConsume(bestController)) {
					bestController = candidate;
				}

				controllers.add(candidate);
			}
		}

		// If we've located a valid neighboring controller, attach to it.
		if(bestController != null) {
			// attachBlock will call onAttached, which will set the controller.
			this.controller = bestController;
			bestController.attachBlock(this);
		}

		return controllers;
	}

	@Override
	public void assertDetached() {
		if(this.controller != null) {
			BlockPos coord = this.getWorldPosition();

			Base.instance.getLogger().info(String.format(
					"[assert] Part @ (%d, %d, %d) should be detached already, but detected that it was not. This is not a fatal error, and will be repaired, but is unusual.",
					coord.getX(), coord.getY(), coord.getZ()));
			this.controller = null;
		}
	}

	@Override
	protected void readFromDisk(NBTTagCompound data) {
		// We can't directly initialize a multiblock controller yet, so we cache the data here until
		// we receive a validate() call, which creates the controller and hands off the cached data.
		if(data.hasKey("multiblockData")) {
			this.cachedMultiblockData = data.getCompoundTag("multiblockData");
		}
	}

	@Override
	protected NBTTagCompound writeToDisk(NBTTagCompound data) {
		if(isMultiblockSaveDelegate() && isConnected()) {
			NBTTagCompound multiblockData = new NBTTagCompound();
			this.getMultiblockController().writeToDisk(multiblockData);
			data.setTag("multiblockData", multiblockData);
		}
		return data;
	}

	/**
	 * Called when a block is removed by game actions, such as a player breaking the block
	 * or the block being changed into another block.
	 *
	 * @see net.minecraft.tileentity.TileEntity#invalidate()
	 */
	@Override
	public void invalidate() {
		super.invalidate();
		detachSelf(false);
	}

	/**
	 * Called from Minecraft's tile entity loop, after all tile entities have been ticked,
	 * as the chunk in which this tile entity is contained is unloading.
	 * Happens before the Forge TickEnd event.
	 *
	 * @see net.minecraft.tileentity.TileEntity#onChunkUnload()
	 */
	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		detachSelf(true);
	}

	/**
	 * This is called when a block is being marked as valid by the chunk, but has not yet fully
	 * been placed into the world's TileEntity cache. this.WORLD, xCoord, yCoord and zCoord have
	 * been initialized, but any attempts to read data about the world can cause infinite loops -
	 * if you call getTileEntity on this TileEntity's coordinate from within validate(), you will
	 * blow your call stack.
	 * TL;DR: Here there be dragons.
	 *
	 * @see net.minecraft.tileentity.TileEntity#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		REGISTRY.onPartAdded(this.getWorld(), this);
	}

	@Override
	public boolean hasMultiblockSaveData() {
		return this.cachedMultiblockData != null;
	}

	@Override
	public NBTTagCompound getMultiblockSaveData() {
		return this.cachedMultiblockData;
	}

	@Override
	public void onMultiblockDataAssimilated() {
		this.cachedMultiblockData = null;
	}

	@Override
	public abstract void onMachineAssembled(PipeNetworkBase multiblockControllerBase);

	@Override
	public abstract void onMachineBroken();

	@Override
	public abstract void onMachineActivated();

	@Override
	public abstract void onMachineDeactivated();

	@Override
	public boolean isConnected() {
		return (controller != null);
	}

	@Override
	public T getMultiblockController() {
		return (T) controller;
	}

	@Override
	public void becomeMultiblockSaveDelegate() {
		this.saveMultiblockData = true;
	}

	@Override
	public void forfeitMultiblockSaveDelegate() {
		this.saveMultiblockData = false;
	}

	@Override
	public boolean isMultiblockSaveDelegate() {
		return this.saveMultiblockData;
	}

	@Override
	public void setUnvisited() {
		this.visited = false;
	}

	@Override
	public void setVisited() {
		this.visited = true;
	}

	@Override
	public boolean isVisited() {
		return this.visited;
	}

	@Override
	public void onAssimilated(PipeNetworkBase newController) {
		assert (this.controller != newController);
		this.controller = newController;
	}

	@Override
	public void onAttached(PipeNetworkBase newController) {
		this.controller = newController;
	}

	@Override
	public void onDetached(PipeNetworkBase oldController) {
		this.controller = null;
	}

	@Override
	public abstract PipeNetworkBase createNewMultiblock();

	@Override
	public IPipe[] getNeighboringParts() {

		TileEntity te;
		List<IPipe> neighborParts = new ArrayList<IPipe>();
		BlockPos neighborPosition, partPosition = this.getWorldPosition();

		for(EnumFacing facing : EnumFacing.VALUES) {

			neighborPosition = partPosition.offset(facing);
			te = this.getWorld().getTileEntity(neighborPosition);

			if(te instanceof IPipe)
				neighborParts.add((IPipe) te);
		}

		return neighborParts.toArray(new IPipe[neighborParts.size()]);
	}

	@Override
	public void onOrphaned(PipeNetworkBase controller, int oldSize, int newSize) {
		this.markDirty();
		getWorld().markChunkDirty(this.getWorldPosition(), this);
	}

	@Override
	public BlockPos getWorldPosition() {
		return this.pos;
	}

	@Override
	public boolean isPartInvalid() {
		return this.isInvalid();
	}

	//// Helper functions for notifying neighboring blocks
	protected void notifyNeighborsOfBlockChange() {
		getWorld().notifyNeighborsOfStateChange(this.getWorldPosition(), this.getBlockType(), true);
	}

	///// Private/Protected Logic Helpers
	/*
	 * Detaches this block from its controller. Calls detachBlock() and clears the controller member.
	 */
	protected void detachSelf(boolean chunkUnloading) {
		if(this.controller != null) {
			// Clean part out of controller
			this.controller.detachBlock(this, chunkUnloading);

			// The above should call onDetached, but, just in case...
			this.controller = null;
		}

		// Clean part out of lists in the registry
		REGISTRY.onPartRemovedFromWorld(getWorld(), this);
	}

	/**
	 * IF the part is connected to a multiblock controller, marks the whole multiblock for a render update on the
	 * client.
	 * On the server, this does nothing
	 */
	protected void markMultiblockForRenderUpdate() {

		PipeNetworkBase controller = this.getMultiblockController();

		if(null != controller)
			controller.markMultiblockForRenderUpdate();
	}

	private static final IPipeNetworkRegistry REGISTRY;

	static {
		REGISTRY = ModuleTransport.initNetworkRegistry();
	}
}
