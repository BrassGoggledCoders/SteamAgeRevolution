package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.smelting.multiblock.crucible.CrucibleRecipe;

public class CrucibleRecipeCategory implements IRecipeCategory<CrucibleRecipe> {

	private final IGuiHelper helper;

	public CrucibleRecipeCategory(IGuiHelper helper) {
		this.helper = helper;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		helper.getSlotDrawable().draw(minecraft, 100, 100);
		helper.getSlotDrawable().draw(minecraft, 160, 100);
	}

	@Override
	public String getUid() {
		return SteamAgeRevolution.MODID + ":crucible";
	}

	@Override
	public String getTitle() {
		return "Crucible";
	}

	@Override
	public String getModName() {
		return SteamAgeRevolution.MODNAME;
	}

	@Override
	public IDrawable getBackground() {
		return helper.createBlankDrawable(256, 256);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CrucibleRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getItemStacks().init(0, true, 100, 100);
		recipeLayout.getFluidStacks().init(1, false, 160, 100);

		recipeLayout.getItemStacks().set(0, ingredients.getInputs(ItemStack.class).get(0));
		recipeLayout.getFluidStacks().set(1, ingredients.getOutputs(FluidStack.class).get(0));
	}

}
