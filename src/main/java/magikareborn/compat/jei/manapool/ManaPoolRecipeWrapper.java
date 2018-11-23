package magikareborn.compat.jei.manapool;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

public class ManaPoolRecipeWrapper implements IRecipeWrapper {

    private final ItemStack _output;
    private final ItemStack _input;

    ManaPoolRecipeWrapper(ItemStack output, ItemStack input) {
        _output = output;
        _input = input;
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {

        ingredients.setOutput(ItemStack.class, _output);

        /*if(fluid!=null&&ForgeModContainer.getInstance().universalBucket!=null)
            return Collections.singletonList(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, fluid.getFluid()));*/

        /*System.out.println("Stack item: " + _input.getItem().getUnlocalizedName());
        System.out.println("Stack meta: " + _input.getMetadata());
        System.out.println("Stack damage: " + _input.getItemDamage());*/

        if (_input.getMetadata() == OreDictionary.WILDCARD_VALUE && OreDictionary.getOreIDs(_input).length > 0) {
            int[] oreIds = OreDictionary.getOreIDs(_input);
            String oreName = OreDictionary.getOreName(oreIds[0]);
            ingredients.setInput(ItemStack.class, OreDictionary.getOres(oreName));

        } else {
            ingredients.setInput(ItemStack.class, _input);
        }
    }
}
