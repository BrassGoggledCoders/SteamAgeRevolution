package xyz.brassgoggledcoders.steamagerevolution.utils;

import com.google.gson.JsonObject;
import com.teamacronymcoders.base.util.OreDictUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.event.ForgeEventFactory;
import xyz.brassgoggledcoders.steamagerevolution.modules.materials.ModuleMaterials;

/**
 * Original @author Choonster
 */
public class ShapelessHammerRecipe extends ShapelessOreResultRecipe {

	public ShapelessHammerRecipe(ResourceLocation group, NonNullList<Ingredient> input, ItemStack result) {
		super(group, input, result);
	}

	private ItemStack damageItem(final ItemStack stack) {
		final EntityPlayer craftingPlayer = ForgeHooks.getCraftingPlayer();
		if(stack.attemptDamageItem(1, craftingPlayer.getRNG(),
				craftingPlayer instanceof EntityPlayerMP ? (EntityPlayerMP) craftingPlayer : null)) {
			ForgeEventFactory.onPlayerDestroyItem(craftingPlayer, stack, null);
			return ItemStack.EMPTY;
		}

		return stack;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(final InventoryCrafting inventoryCrafting) {
		final NonNullList<ItemStack> remainingItems =
				NonNullList.withSize(inventoryCrafting.getSizeInventory(), ItemStack.EMPTY);

		for(int i = 0; i < remainingItems.size(); ++i) {
			final ItemStack itemstack = inventoryCrafting.getStackInSlot(i);

			if(!itemstack.isEmpty() && itemstack.getItem() == ModuleMaterials.hammer) {
				remainingItems.set(i, damageItem(itemstack.copy()));
			}
			else {
				remainingItems.set(i, ForgeHooks.getContainerItem(itemstack));
			}
		}

		return remainingItems;
	}

	@Override
	public String getGroup() {
		return group == null ? "" : group.toString();
	}

	public static class Factory implements IRecipeFactory {

		@Override
		public IRecipe parse(final JsonContext context, final JsonObject json) {
			final String group = JsonUtils.getString(json, "group", "");
			final NonNullList<Ingredient> ingredients = RecipeUtil.parseShapeless(context, json);
			final ItemStack result = OreDictUtils
					.getPreferredItemStack(JsonUtils.getString(JsonUtils.getJsonObject(json, "result"), "ore"));

			return new ShapelessHammerRecipe(group.isEmpty() ? null : new ResourceLocation(group), ingredients, result);
		}
	}

}
