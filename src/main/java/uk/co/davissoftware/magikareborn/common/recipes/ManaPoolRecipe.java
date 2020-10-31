package magikareborn.recipes;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ManaPoolRecipe {

    public final ItemStack Output;
    public final ItemStack Input;

    private ManaPoolRecipe(ItemStack itemStackOutput, ItemStack itemStackInput) {
        Output = itemStackOutput;
        Input = itemStackInput;
    }

    /*public ItemStack getOutputCopy() {
        return Output.copy();
    }*/


    public static ArrayList<ManaPoolRecipe> recipeList = new ArrayList<>();

    public static void addRecipe(ItemStack itemStackOutput, ItemStack itemStackInput) {
        recipeList.add(new ManaPoolRecipe(itemStackOutput, itemStackInput));
    }
}
