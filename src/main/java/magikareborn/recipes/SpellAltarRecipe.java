package magikareborn.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class SpellAltarRecipe {

    public final ItemStack Output;
    public final FluidStack ManaInput;
    public final List<ItemStack> ItemsInput;

    private SpellAltarRecipe(ItemStack output, FluidStack manaInput, List<ItemStack> itemsInput) {
        Output = output;
        ManaInput = manaInput;
        ItemsInput = itemsInput;
    }

    private boolean matches(InventoryCrafting craftingMatrix) {
        int inputCount = 0;
        for (int i = 0; i < craftingMatrix.getSizeInventory(); i++) {
            ItemStack stack = craftingMatrix.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                inputCount++;
                if (!isStackInInputs(stack)) {
                    return false;
                }
            }
        }
        return inputCount == ItemsInput.size();
    }

    private boolean isStackInInputs(ItemStack checkStack) {
        for (ItemStack inputStack : ItemsInput) {
            if (checkStack.getItem() == inputStack.getItem()) {
                return true;
            }
        }
        return false;
    }

    public ItemStack getOutputCopy() {
        return Output.copy();
    }


    public static ArrayList<SpellAltarRecipe> recipeList = new ArrayList<>();

    public static void addRecipe(ItemStack output, FluidStack manaInput, List<ItemStack> itemsInput) {
        recipeList.add(new SpellAltarRecipe(output, manaInput, itemsInput));
    }

    public static SpellAltarRecipe getMatch(InventoryCrafting craftingMatrix) {
        for (SpellAltarRecipe recipe : recipeList) {
            if (recipe.matches(craftingMatrix)) {
                return recipe;
            }
        }
        return null;
    }


}
