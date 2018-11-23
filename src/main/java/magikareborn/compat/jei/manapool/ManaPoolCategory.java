package magikareborn.compat.jei.manapool;

import magikareborn.ModRoot;
import magikareborn.compat.jei.ICustomRecipeCategory;
import magikareborn.helpers.ResourceHelper;
import magikareborn.init.ModBlocks;
import magikareborn.recipes.ManaPoolRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class ManaPoolCategory implements ICustomRecipeCategory<ManaPoolRecipeWrapper, ManaPoolRecipe>, IRecipeWrapperFactory<ManaPoolRecipe> {

    public static String UUID = "magikareborn.manapool";

    private final IDrawable _background;

    public ManaPoolCategory(IGuiHelper guiHelper) {

        ResourceLocation location = ResourceHelper.getGuiResource("recipebg_manapool");

        //------------------------------------resource, u, v, width, height, paddingTop, paddingBottom, paddingLeft, paddingRight
        _background = guiHelper.createDrawable(location, 0, 0, 120, 120, 0, 2, 0, 0);
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper, @Nonnull IIngredients ingredients) {

        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        //int slotIndex, boolean input, int xPosition, int yPosition
        guiItemStacks.init(0, true, 29, 3);
        guiItemStacks.init(1, false, 29, 21);
        guiItemStacks.init(2, false, 29, 57);

        guiItemStacks.set(0, ingredients.getInputs(ItemStack.class).get(0));
        guiItemStacks.set(1, new ArrayList<ItemStack>() {{
            add(new ItemStack(ModBlocks.MANA_FLUID_BLOCK));
        }});
        guiItemStacks.set(2, ingredients.getOutputs(ItemStack.class).get(0));
    }

    @Nonnull
    @Override
    public String getUid() {
        return UUID;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return new TextComponentTranslation("jei.category.title.manapool").getUnformattedText();
    }

    @Nonnull
    @Override
    public String getModName() {
        return ModRoot.MODNAME;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return _background;
    }

    @Override
    public Class getRecipeClass() {
        return ManaPoolRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return UUID;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull ManaPoolRecipe recipe) {
        return new ManaPoolRecipeWrapper(recipe.Output, recipe.Input);
    }
}
