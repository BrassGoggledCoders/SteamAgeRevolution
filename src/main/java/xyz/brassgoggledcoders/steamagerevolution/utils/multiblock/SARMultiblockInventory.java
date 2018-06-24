package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.*;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketMultiFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.*;

public abstract class SARMultiblockInventory extends SARMultiblockBase
		implements ISmartTankCallback, ISARMultiblockInventory {

	protected int currentTicks = 0;
	SARMachineRecipe currentRecipe = null;
	public FluidTankSingleSmart steamTank;

	protected SARMultiblockInventory(World world) {
		super(world);
		steamTank = new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "steam", this);
	}

	@Override
	protected boolean updateServer() {
		onTick();
		if(canRun()) {
			onActiveTick();
			currentTicks++;
			if(canFinish()) {
				onFinish();
			}
			return true; // TODO
		}
		return false;
	}

	protected void onTick() {
		// NO-OP
	}

	protected void onActiveTick() {
		// NO-OP
	}

	protected void onFinish() {
		if(ArrayUtils.isNotEmpty(currentRecipe.getItemOutputs())) {
			for(ItemStack output : currentRecipe.getItemOutputs()) {
				ItemHandlerHelper.insertItem(getItemOutput(), output, false);
			}
		}
		if(ArrayUtils.isNotEmpty(currentRecipe.getFluidOutputs())) {
			for(FluidStack output : currentRecipe.getFluidOutputs()) {
				getFluidOutputs().fill(output, true);
			}
		}
		if(ArrayUtils.isNotEmpty(currentRecipe.getItemInputs())) {
			for(Ingredient input : currentRecipe.getItemInputs()) {
				// TODO Inefficient for oredict
				for(ItemStack stack : input.getMatchingStacks()) {
					getItemInput().extractStack(stack);
				}
			}
		}
		if(ArrayUtils.isNotEmpty(currentRecipe.getFluidInputs())) {
			for(IngredientFluidStack input : currentRecipe.getFluidInputs()) {
				getFluidInputs().drain(input.getFluid(), true);
			}
		}
		steamTank.drain(currentRecipe.getSteamUsePerCraft(), true);
		currentTicks = 0;
		currentRecipe = null; // TODO Only null when inputs hit zero
	}

	protected boolean canFinish() {
		if(currentTicks >= currentRecipe.getTicks()) {
			boolean roomForItems = true;
			boolean roomForFluids = true;
			if(ArrayUtils.isNotEmpty(currentRecipe.getItemOutputs())) {
				roomForItems = Arrays.asList(currentRecipe.getItemOutputs()).parallelStream()
						.allMatch(output -> ItemHandlerHelper.insertItem(getItemOutput(), output, true).isEmpty());
			}
			if(ArrayUtils.isNotEmpty(currentRecipe.getFluidOutputs())) {
				roomForFluids = Arrays.asList(currentRecipe.getFluidOutputs()).parallelStream()
						.allMatch(output -> getFluidOutputs().fill(output, false) == output.amount);
			}
			return roomForItems && roomForFluids;
		}
		return false;
	}

	protected boolean canRun() {
		Optional<SARMachineRecipe> recipe = RecipeRegistry.getRecipesForMachine(getName().toLowerCase())
				.parallelStream().filter(this::hasRequiredFluids).filter(this::hasRequiredItems).findFirst();
		if(recipe.isPresent()) {
			currentRecipe = recipe.get();
			return steamTank.getFluidAmount() >= currentRecipe.getSteamUsePerCraft();
		}
		return currentRecipe != null && steamTank.getFluidAmount() >= currentRecipe.getSteamUsePerCraft();
	}

	private boolean hasRequiredFluids(SARMachineRecipe recipe) {
		if(ArrayUtils.isNotEmpty(recipe.getFluidInputs())) {
			// Stream the fluid stacks
			return Arrays.stream(recipe.getFluidInputs())
					// Apply tanksHaveFluid to each element and output result to stream
					.map(this::tanksHaveFluid)
					// Reduce list of booleans into one - so will only evaluate true if every
					// boolean is true
					.reduce((a, b) -> a && b).orElse(false);
		}
		return true;
	}

	private boolean tanksHaveFluid(IngredientFluidStack stack) {
		return Arrays
				.asList(getFluidInputs()).stream().filter(Objects::nonNull).filter(tank -> tank.fluids.stream()
						.filter(Objects::nonNull).anyMatch(fluid -> fluid.containsFluid(stack.getFluid())))
				.findAny().isPresent();
	}

	private boolean hasRequiredItems(SARMachineRecipe recipe) {
		if(ArrayUtils.isNotEmpty(recipe.getItemInputs())) {
			return Arrays.stream(recipe.getItemInputs()).map(this::handlerHasItems).reduce((a, b) -> a && b)
					.orElse(false);
		}
		return true;
	}

	private boolean handlerHasItems(Ingredient ingredient) {
		return IntStream.range(0, getItemInput().getSlots()).mapToObj(slotNum -> getItemInput().getStackInSlot(slotNum))
				.filter(inputStack -> Arrays.asList(ingredient.getMatchingStacks()).stream()
						.anyMatch(stack -> ItemStackUtils.containsItemStack(stack, inputStack)))
				.findAny().isPresent();
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		currentTicks = data.getInteger("progress");
		steamTank.readFromNBT(data.getCompoundTag("steam"));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setInteger("progress", currentTicks);
		data.setTag("steam", steamTank.writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void onTankContentsChanged(FluidTankSmart tank) {
		if(tank instanceof MultiFluidTank) {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketMultiFluidUpdate(getReferenceCoord(), ((MultiFluidTank) tank), tank.getId()),
					getReferenceCoord(), WORLD.provider.getDimension());
		}
		else {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketFluidUpdate(getReferenceCoord(), tank.getFluid(), tank.getId()), getReferenceCoord(),
					WORLD.provider.getDimension());
		}
	}

	@Override
	public void updateFluid(PacketFluidUpdate message) {
		steamTank.setFluid(message.fluid);
	}

	@Override
	public FluidTankSingleSmart getSteamTank() {
		return steamTank;
	}
}