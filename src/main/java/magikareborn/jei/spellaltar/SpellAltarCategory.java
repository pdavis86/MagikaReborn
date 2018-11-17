package magikareborn.jei.spellaltar;

import magikareborn.ModRoot;
import magikareborn.helpers.ResourceHelper;
import magikareborn.jei.ICustomRecipeCategory;
import magikareborn.recipes.SpellAltarRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;

public class SpellAltarCategory implements ICustomRecipeCategory<SpellAltarRecipeWrapper, SpellAltarRecipe>, IRecipeWrapperFactory<SpellAltarRecipe> {

    public static String UUID = "magikareborn.spellaltar";

    //private final int _workingAreaHeight = 262;
    //private final int _workingAreaWidth = 378;
    private final IDrawable _background;
    /*private final IDrawable slotDrawable;
    private final String localizedName;
    private final IDrawableAnimated arrow;
    private final IDrawableAnimated bubbles;
    private final IDrawableStatic blazeHeat;*/

    public SpellAltarCategory(IGuiHelper guiHelper) {
        ResourceLocation location = ResourceHelper.getGuiResourceLocation("recipebg_spellaltar");

        //------------------------------------resource, u, v, width, height, paddingTop, paddingBottom, paddingLeft, paddingRight
        _background = guiHelper.createDrawable(location, 0, 0, 120, 120, 0, 2, 0, 0);

        /*IDrawableStatic brewArrowDrawable = guiHelper.createDrawable(location, 64, 0, 9, 28);
        arrow = guiHelper.createAnimatedDrawable(brewArrowDrawable, 400, IDrawableAnimated.StartDirection.TOP, false);

        IDrawableStatic brewBubblesDrawable = guiHelper.createDrawable(location, 73, 0, 12, 29);
        ITickTimer bubblesTickTimer = new BrewingBubblesTickTimer(guiHelper);
        bubbles = new DrawableAnimated(brewBubblesDrawable, bubblesTickTimer, IDrawableAnimated.StartDirection.BOTTOM);

        blazeHeat = guiHelper.createDrawable(location, 64, 29, 18, 4);

        slotDrawable = guiHelper.getSlotDrawable();*/
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {

        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

        //int slotIndex, boolean input, int xPosition, int yPosition
        guiItemStacks.init(0, true, 29, 3);
        guiItemStacks.init(1, true, 29, 21);
        guiFluidStacks.init(0, true, 29, 39);
        guiItemStacks.init(2, false, 29, 57);

        guiItemStacks.set(0, ingredients.getInputs(ItemStack.class).get(0));
        guiItemStacks.set(1, ingredients.getInputs(ItemStack.class).get(1));
        guiFluidStacks.set(0, ingredients.getInputs(FluidStack.class).get(0));
        guiItemStacks.set(2, ingredients.getOutputs(ItemStack.class).get(0));
    }

    @Override
    public String getUid() {
        return UUID;
    }

    @Override
    public String getTitle() {
        return new TextComponentTranslation("jei.category.title.spellaltar").getUnformattedText();
    }

    @Override
    public String getModName() {
        return ModRoot.MODNAME;
    }

    @Override
    public IDrawable getBackground() {
        return _background;
    }

    @Override
    public Class getRecipeClass() {
        return SpellAltarRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return UUID;
    }

    /*@Override
    public IDrawable getIcon() {
        return null;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {

    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return null;
    }*/

    @Override
    public IRecipeWrapper getRecipeWrapper(SpellAltarRecipe recipe) {
        return new SpellAltarRecipeWrapper(recipe.Spell, recipe.Mana, recipe.Ingredients);
    }
}
