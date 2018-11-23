package magikareborn.compat.jei.spellaltar;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SpellAltarRecipeWrapper implements IRecipeWrapper {

    private final ItemStack _output;
    private final FluidStack _manaInput;
    private final List<ItemStack> _itemsInput;

    SpellAltarRecipeWrapper(ItemStack output, FluidStack manaInput, List<ItemStack> itemsInput) {
        _output = output;
        _manaInput = manaInput;
        _itemsInput = itemsInput;
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {

        ingredients.setOutput(ItemStack.class, _output);

        ingredients.setInput(FluidStack.class, _manaInput);

        List<List<ItemStack>> generalisedItemsInput = new ArrayList<>();
        for (ItemStack stack : _itemsInput) {
            if (stack.getMetadata() == OreDictionary.WILDCARD_VALUE && OreDictionary.getOreIDs(stack).length > 0) {
                int[] oreIds = OreDictionary.getOreIDs(stack);
                String oreName = OreDictionary.getOreName(oreIds[0]);
                generalisedItemsInput.add(OreDictionary.getOres(oreName));

            } else {
                generalisedItemsInput.add(new ArrayList<ItemStack>() {{
                    add(stack);
                }});
            }
        }
        ingredients.setInputLists(ItemStack.class, generalisedItemsInput);
    }
}
