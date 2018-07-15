package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.tileentities.TileEntityBase;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketItemUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ISmartStackCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeMachineHelper;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

public abstract class SARMachineTileEntity extends TileEntityBase
		implements ITickable, ISmartStackCallback, IHasGui, IHasInventory {

	protected int currentTicks = 0;
	SARMachineRecipe currentRecipe;
	public InventoryMachine inventory;

	@Override
	public void setInventory(InventoryMachine inventory) {
		this.inventory = inventory;
	}

	public abstract String getName();

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("inventory", inventory.serializeNBT());
		return new SPacketUpdateTileEntity(pos, 3, nbt);
	}

	@Nonnull
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = super.writeToNBT(new NBTTagCompound());
		nbt.setTag("inventory", inventory.serializeNBT());
		return nbt;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		inventory.deserializeNBT(pkt.getNbtCompound().getCompoundTag("inventory"));
	}

	@Override
	protected void readFromDisk(NBTTagCompound data) {
		currentTicks = data.getInteger("progress");
		inventory.deserializeNBT(data.getCompoundTag("inventory"));
	}

	@Override
	protected NBTTagCompound writeToDisk(NBTTagCompound data) {
		data.setInteger("progress", currentTicks);
		data.setTag("inventory", inventory.serializeNBT());
		return data;
	}

	@Override
	public void update() {
		onTick();
		if(canRun()) {
			onActiveTick();
			currentTicks++;
			if(canFinish()) {
				onFinish();
			}
		}
	}

	protected void onTick() {
		// NO-OP
	}

	protected void onActiveTick() {
		// NO-OP
	}

	protected void onFinish() {
		RecipeMachineHelper.onFinish(currentRecipe, inventory);
		currentTicks = 0;
		currentRecipe = null; // TODO Only null when inputs hit zero
	}

	protected boolean canFinish() {
		return RecipeMachineHelper.canFinish(currentTicks, currentRecipe, inventory);
	}

	protected boolean canRun() {
		return RecipeMachineHelper.canRun(world, pos, this, getName().toLowerCase()/* .replace(' ', '_')TODO */,
				currentRecipe, inventory);
	}

	@Override
	public void updateStack(PacketItemUpdate message) {
		// TODO
	}

	@Override
	public void onContentsChanged(int slot) {
		// TODO
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiInventory(entityPlayer, this);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new ContainerInventory(entityPlayer, this);
	}

	@Override
	public void setCurrentRecipe(SARMachineRecipe recipe) {
		this.currentRecipe = recipe;
	}

	@Override
	public SARMachineRecipe getCurrentRecipe() {
		return currentRecipe;
	}

	@Override
	public int getCurrentProgress() {
		return currentTicks;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getCurrentMaxTicks() {
		return 0; // TODO
	}

	@Override
	public void setCurrentTicks(int ticks) {
		this.currentTicks = ticks;
	}

	@Override
	public BlockPos getPos() {
		return this.getPos();
	}

	@Override
	public World getWorld() {
		return this.getWorld();
	}
}
