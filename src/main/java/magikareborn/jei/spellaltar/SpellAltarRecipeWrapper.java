package magikareborn.jei.spellaltar;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpellAltarRecipeWrapper implements IRecipeWrapper {

    private final List<ItemStack> _spellOutput;
    private final List<FluidStack> _manaInput;
    private final List<ItemStack> _ingredients;

    public SpellAltarRecipeWrapper(Item spellOutput, FluidStack manaInput, List<Item> ingredients) {
        _spellOutput = Collections.singletonList(new ItemStack(spellOutput));
        _manaInput = Collections.singletonList(manaInput); //Collections.singletonList(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, fluid.getFluid()));
        _ingredients = new ArrayList<ItemStack>();
        for (Item ing : ingredients) {
            _ingredients.add(new ItemStack(ing));
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutput(ItemStack.class, _spellOutput);
        ingredients.setInputs(FluidStack.class, _manaInput);
        ingredients.setInputs(ItemStack.class, _ingredients);
    }
}
