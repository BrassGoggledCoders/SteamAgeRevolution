package xyz.brassgoggledcoders.steamagerevolution.modules.vanity;

import java.util.Arrays;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.boilerplate.items.ItemSubBase;

public class ItemTeacup extends ItemSubBase {

	public static List<String> teacup = Arrays.asList("empty", "quarter", "half", "threequarter", "full");

	public ItemTeacup() {
		super("tea", "teacup", teacup);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 20;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}

	/*
	 * @Override
	 * public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	 * {
	 * if(stack.getItemDamage() > 0 && !player.capabilities.isCreativeMode)
	 * {
	 * int damage = stack.getItemDamage();
	 * stack.setItemDamage(damage - 1);
	 * player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 200, 2));
	 * }
	 * //TODO: Proper Achievements
	 * player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
	 * return stack;
	 * }
	 * @Override
	 * public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	 * {
	 * if(stack.getItemDamage() != 0)
	 * {
	 * player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
	 * }
	 * return stack;
	 * }
	 */

	@Override
	public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, getNumberOfSubItems() - 1));
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean flag) {
		// list.add(StatCollector.translateToLocal("desc." + getUnlocalizedName() + "." + getMetaName(stack)));
		list.add(getSipsLeft(stack));
	}

	public String getSipsLeft(ItemStack stack) {
		if(stack.getItemDamage() < getNumberOfSubItems()) {
			// return StatCollector.translateToLocal("desc." + getUnlocalizedName() + "." + stack.getItemDamage() +
			// "sips");
		}

		return "";
	}
}