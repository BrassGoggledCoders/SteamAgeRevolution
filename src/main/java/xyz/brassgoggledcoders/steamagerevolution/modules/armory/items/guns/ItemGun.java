package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns.IGunPart.GunPartType;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns.IMechanism.ActionType;

public class ItemGun extends ItemBase {

	@ObjectHolder(value = SteamAgeRevolution.MODID + ":bullet")
	public static final Item bullet = null;

	public ItemGun() {
		super("gun");
	}

	@Override
	@SideOnly(Side.CLIENT)
	@ParametersAreNonnullByDefault
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(GunUtils.getOrCreateTagCompound(stack).getString("BARREL"));
		tooltip.add(GunUtils.getOrCreateTagCompound(stack).getString("MECHANISM"));
		tooltip.add(GunUtils.getOrCreateTagCompound(stack).getString("CHAMBER"));
		tooltip.add(GunUtils.getOrCreateTagCompound(stack).getString("STOCK"));
		if(GunUtils.getOrCreateTagCompound(stack).getBoolean("isLoaded")) {
			tooltip.add("Loaded");
		}
		else {
			tooltip.add("Unloaded");
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		GunUtils.getOrCreateTagCompound(stack);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);

		playerIn.setActiveHand(handIn);

		ItemStack ammo = GunUtils.findAmmo(playerIn, stack);
		if(!ammo.isEmpty()) {
			GunUtils.getOrCreateTagCompound(stack).setTag("loaded", ammo.writeToNBT(new NBTTagCompound()));
			ammo.shrink(1);
			GunUtils.getOrCreateTagCompound(stack).setBoolean("isLoaded", true);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase entityLiving, int count) {
		for(int i = 0; i < 4; i++) { // TODO Don't hardcode number of parts
			GunUtils.getPartFromGun(stack, GunPartType.values()[i]).onUsingTick(stack, entityLiving, count);
		}
		if(((IMechanism) GunPartRegistry.getPart(GunUtils.getOrCreateTagCompound(stack).getString("MECHANISM")))
				.getActionType() == ActionType.AUTO) {

		}
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		int i = this.getMaxItemUseDuration(stack) - timeLeft;
		if(i < 60) {
			return; // TODO
		}
		for(int i2 = 0; i2 < 4; i2++) { // TODO Don't hardcode number of parts
			GunUtils.getPartFromGun(stack, GunPartType.values()[i]).onPlayerStoppedUsing(stack, worldIn, entityLiving,
					timeLeft);
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		if(ActionType.AUTO.equals(
				((IMechanism) GunPartRegistry.getPart(GunUtils.getOrCreateTagCompound(stack).getString("MECHANISM")))
						.getActionType())) {
			return 72000;
		}
		return super.getMaxItemUseDuration(stack);
	}

}
