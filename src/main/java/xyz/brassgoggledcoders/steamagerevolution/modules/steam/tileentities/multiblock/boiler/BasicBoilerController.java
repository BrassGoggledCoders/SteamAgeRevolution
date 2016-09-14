package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.boiler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.client.guis.IOpenableGUI;
import xyz.brassgoggledcoders.boilerplate.multiblock.IMultiblockPart;
import xyz.brassgoggledcoders.boilerplate.multiblock.MultiblockControllerBase;
import xyz.brassgoggledcoders.boilerplate.multiblock.rectangular.RectangularMultiblockControllerBase;
import xyz.brassgoggledcoders.boilerplate.multiblock.validation.IMultiblockValidator;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.ITickableMultiblockPart;

public class BasicBoilerController extends RectangularMultiblockControllerBase {

	// Values for parts to use
	public static int fluidTransferRate = 10; // mB per tick

	// Lists of connected parts
	private Set<TileEntityWaterInput> attachedInputs;
	private Set<TileEntityWaterTank> attachedWaterTanks;
	private Set<TileEntitySteamOutput> attachedOutputs;
	private Set<TileEntitySteamTank> attachedSteamTanks;
	private Set<TileEntitySolidFirebox> attachedFireboxes;
	private Set<ITickableMultiblockPart> attachedTickables;
	private ArrayList<TileEntityBasicBoilerPart> attachedGUIs;

	protected BasicBoilerController(World world) {
		super(world);
		attachedInputs = new HashSet<TileEntityWaterInput>();
		attachedWaterTanks = new HashSet<TileEntityWaterTank>();
		attachedOutputs = new HashSet<TileEntitySteamOutput>();
		attachedSteamTanks = new HashSet<TileEntitySteamTank>();
		attachedFireboxes = new HashSet<TileEntitySolidFirebox>();
		attachedTickables = new HashSet<ITickableMultiblockPart>();
		attachedGUIs = new ArrayList<TileEntityBasicBoilerPart>();
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		this.readFromDisk(data);
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		// FMLLog.warning("Part added " + newPart.toString());
		if(newPart instanceof TileEntityWaterInput) {
			attachedInputs.add((TileEntityWaterInput) newPart);
		}
		else if(newPart instanceof TileEntityWaterTank) {
			attachedWaterTanks.add((TileEntityWaterTank) newPart);
		}
		else if(newPart instanceof TileEntitySteamOutput) {
			attachedOutputs.add((TileEntitySteamOutput) newPart);
		}
		else if(newPart instanceof TileEntitySteamTank) {
			attachedSteamTanks.add((TileEntitySteamTank) newPart);
		}
		// TODO Not only solid
		else if(newPart instanceof TileEntitySolidFirebox) {
			attachedFireboxes.add((TileEntitySolidFirebox) newPart);
		}

		if(newPart instanceof ITickableMultiblockPart) {
			attachedTickables.add((ITickableMultiblockPart) newPart);
		}

		if(newPart instanceof IOpenableGUI) {
			attachedGUIs.add((TileEntityBasicBoilerPart) newPart);
		}
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		if(oldPart instanceof TileEntityWaterInput) {
			attachedInputs.remove(oldPart);
		}
		else if(oldPart instanceof TileEntityWaterTank) {
			attachedWaterTanks.remove(oldPart);
		}
		else if(oldPart instanceof TileEntitySteamOutput) {
			attachedOutputs.remove(oldPart);
		}
		else if(oldPart instanceof TileEntitySteamTank) {
			attachedSteamTanks.remove(oldPart);
		}
		else if(oldPart instanceof TileEntitySolidFirebox) {
			attachedFireboxes.remove(oldPart);
		}

		if(oldPart instanceof ITickableMultiblockPart) {
			attachedTickables.remove(oldPart);
		}

		if(oldPart instanceof IOpenableGUI) {
			attachedGUIs.remove(oldPart);
		}
	}

	@Override
	protected void onMachineAssembled() {}

	@Override
	protected void onMachineRestored() {}

	@Override
	protected void onMachinePaused() {}

	@Override
	protected void onMachineDisassembled() {}

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
	protected void onAssimilate(MultiblockControllerBase assimilated) {

	}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {
		this.attachedInputs.clear();
		this.attachedWaterTanks.clear();
		this.attachedFireboxes.clear();
		this.attachedOutputs.clear();
		this.attachedSteamTanks.clear();
		this.attachedTickables.clear();
		this.attachedGUIs.clear();
	}

	@Override
	protected boolean updateServer() {
		boolean flag = false;

		int i = 0;

		for(ITickableMultiblockPart tickable : attachedTickables) {
			if(tickable.tick(this))
				i++;
		}

		// If any tickable has changed, the whole multiblock has changed, thus flag for update.
		if(i > 0) {
			flag = true;
		}

		return flag;
	}

	@Override
	protected void updateClient() {}

	@Override
	protected boolean isBlockGoodForFrame(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean isBlockGoodForTop(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean isBlockGoodForBottom(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean isBlockGoodForSides(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void readFromDisk(NBTTagCompound data) {

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

	public ArrayList<TileEntityBasicBoilerPart> getAttachedGUIs() {
		return attachedGUIs;
	}

	public Set<TileEntityWaterTank> getAttachedWaterTanks() {
		return attachedWaterTanks;
	}

	public Set<TileEntitySteamTank> getAttachedSteamTanks() {
		return attachedSteamTanks;
	}

}