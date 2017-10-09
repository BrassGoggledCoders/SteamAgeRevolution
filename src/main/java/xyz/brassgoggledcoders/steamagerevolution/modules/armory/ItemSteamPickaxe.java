package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.IBaseMod;
import com.teamacronymcoders.base.IModAware;
import com.teamacronymcoders.base.client.models.IHasModel;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

public class ItemSteamPickaxe extends ItemPickaxe implements IHasModel, IModAware {

	boolean creativeTabSet = false;
	private IBaseMod mod;
	int capacity;

	public static final int steamUsePerBlock = 10;

	protected ItemSteamPickaxe(String name, int capacity) {
		super(ModuleArmory.STEAM);
		this.setUnlocalizedName(name);
		this.capacity = capacity;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new FluidHandlerItemStack(stack, capacity);
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		FluidHandlerItemStack internal =
				(FluidHandlerItemStack) stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		if(internal.getFluid() != null && internal.getFluid().amount >= steamUsePerBlock)
			return super.getDestroySpeed(stack, state);
		else {
			return 0.0F;
		}
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		FluidHandlerItemStack internal =
				(FluidHandlerItemStack) stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		if(internal.getFluid() != null && internal.getFluid().amount >= steamUsePerBlock) {
			internal.drain(steamUsePerBlock, true);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		FluidHandlerItemStack internal =
				(FluidHandlerItemStack) stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		return 1.0D - ((double) internal.getFluid().amount / capacity);

	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		FluidHandlerItemStack internal =
				(FluidHandlerItemStack) stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		return internal.getFluid() != null;
	}

	@Override
	@ParametersAreNonnullByDefault
	public void getSubItems(@Nullable CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if(tab != null && tab == this.getCreativeTab() || tab == CreativeTabs.SEARCH) {
			subItems.addAll(this.getAllSubItems(Lists.newArrayList()));
		}
	}

	@Override
	public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
		itemStacks.add(new ItemStack(this, 1));
		return itemStacks;
	}

	@Override
	@Nonnull
	public Item setCreativeTab(@Nonnull CreativeTabs tab) {
		if(!creativeTabSet) {
			super.setCreativeTab(tab);
			this.creativeTabSet = true;
		}
		return this;
	}

	@Override
	public IBaseMod getMod() {
		return mod;
	}

	@Override
	public void setMod(IBaseMod mod) {
		this.mod = mod;
	}

	@Override
	public Item getItem() {
		return this;
	}

}