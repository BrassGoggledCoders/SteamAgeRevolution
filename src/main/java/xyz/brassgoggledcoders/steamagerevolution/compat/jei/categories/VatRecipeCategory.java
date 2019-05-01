package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.SARJEIPlugin;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat.ControllerVat;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

public class VatRecipeCategory extends SARRecipeCategory<SARMachineRecipe> {

	public static final String uid = "vat";

	public VatRecipeCategory() {
		super(uid, "Vat");
	}

	@Override
	public String getUid() {
		return SteamAgeRevolution.MODID + ":vat";
	}

	@Override
	public String getTitle() {
		return "Vat";
	}

	@Override
	public String getModName() {
		return SteamAgeRevolution.MODNAME;
	}

	@Override
	public IDrawable getBackground() {
		// 144, 122
		return helper.createDrawable(new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/jei/vat.png"), 0, 0,
				146, 125);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SARMachineRecipe recipeWrapper, IIngredients ingredients) {
		for (int i = 0; i < recipeWrapper.getFluidInputs().length; i++) {
			FluidStack stack = recipeWrapper.getFluidInputs()[i].getFluid();
			if (stack != null) {
				recipeLayout.getFluidStacks().init(i, true, 1 + (i * 39), 1, 20, 60,
						ControllerVat.inputCapacity / recipeWrapper.getFluidInputs().length, true, null);
				recipeLayout.getFluidStacks().set(i, recipeWrapper.getFluidInputs()[i].getFluid());
			}
		}
		if (recipeWrapper.getItemInputs() != null) {
			for (int i2 = 0; i2 < recipeWrapper.getItemInputs().length; i2++) {
				ItemStack stack = recipeWrapper.getItemInputs()[i2].getMatchingStacks()[0];
				if (!stack.isEmpty()) {
					recipeLayout.getItemStacks().init(i2 + 3, true, 2 + (i2 * 40) - i2, 105);
					recipeLayout.getItemStacks().set(i2 + 3, recipeWrapper.getItemInputs()[i2].getMatchingStacks()[0]);// TODO
				}
			}
		}
		recipeLayout.getFluidStacks().init(6, false, 124, 50, 20, 60, ControllerVat.outputCapacity, true, null);
		recipeLayout.getFluidStacks().set(6, ingredients.getOutputs(VanillaTypes.FLUID).get(0));

		recipeLayout.getFluidStacks().addTooltipCallback(SARJEIPlugin.fluidTooltipCallback);
	}

}
