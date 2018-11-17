package magikareborn.recipes;

import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class SpellAltarRecipe {

    public static ArrayList<SpellAltarRecipe> recipeList = new ArrayList<SpellAltarRecipe>();

    public final Item Spell;
    public final FluidStack Mana;
    public final List<Item> Ingredients;

    public SpellAltarRecipe(Item spellOutput, FluidStack manaInput, List<Item> ingredients) {
        Spell = spellOutput;
        Mana = manaInput;
        Ingredients = ingredients;
    }

    public static void addRecipe(Item spellOutput, FluidStack manaInput, List<Item> ingredients) {
        recipeList.add(new SpellAltarRecipe(spellOutput, manaInput, ingredients));
    }

    /*public static CokeOvenRecipe findRecipe(ItemStack input)
    {
        for(CokeOvenRecipe recipe : recipeList)
            if(ApiUtils.stackMatchesObject(input, recipe.input))
                return recipe;
        return null;
    }

    public static List<CokeOvenRecipe> removeRecipes(ItemStack stack)
    {
        List<CokeOvenRecipe> list = new ArrayList();
        Iterator<CokeOvenRecipe> it = recipeList.iterator();
        while(it.hasNext())
        {
            CokeOvenRecipe ir = it.next();
            if(OreDictionary.itemMatches(ir.output, stack, true))
            {
                list.add(ir);
                it.remove();
            }
        }
        return list;
    }*/
}
