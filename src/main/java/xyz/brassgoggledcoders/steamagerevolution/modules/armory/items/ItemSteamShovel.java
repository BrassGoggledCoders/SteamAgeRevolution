package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.IBaseMod;
import com.teamacronymcoders.base.IModAware;
import com.teamacronymcoders.base.client.models.IHasModel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.ModuleArmory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class ItemSteamShovel extends ItemSpade implements IHasModel, IModAware {

    public static final int steamUsePerBlock = 10;
    boolean creativeTabSet = false;
    int capacity;
    String name;
    private IBaseMod mod;

    public ItemSteamShovel(String name, int capacity) {
        super(ModuleArmory.STEAM);
        setTranslationKey(name);
        this.capacity = capacity;
        this.name = name;
    }

    @Override
    public List<String> getModelNames(List<String> modelNames) {
        modelNames.add(name);
        return modelNames;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new FluidHandlerItemStack(stack, capacity) {
            @Override
            public boolean canFillFluidType(FluidStack fluid) {
                return FluidRegistry.getFluidName(fluid).equals("steam");
            }
        };
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
                .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if (internal.getFluid() != null && internal.getFluid().amount >= steamUsePerBlock) {
            return super.getDestroySpeed(stack, state);
        } else {
            return 0.0F;
        }
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
                                    EntityLivingBase entityLiving) {
        FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
                .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if (internal.getFluid() != null && internal.getFluid().amount >= steamUsePerBlock) {
            internal.drain(steamUsePerBlock, true);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
                .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        return 1.0D - ((double) internal.getFluid().amount / capacity);

    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
                .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        return internal.getFluid() != null;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void getSubItems(@Nullable CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (tab != null && tab == getCreativeTab() || tab == CreativeTabs.SEARCH) {
            subItems.addAll(getAllSubItems(Lists.newArrayList()));
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
        if (!creativeTabSet) {
            super.setCreativeTab(tab);
            creativeTabSet = true;
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
