package magikareborn.compat.jei;

import magikareborn.compat.jei.manapool.ManaPoolCategory;
import magikareborn.compat.jei.spellaltar.SpellAltarCategory;
import magikareborn.compat.jei.spellaltar.SpellAltarRecipeTranfer;
import magikareborn.gui.SpellAltarGuiContainer;
import magikareborn.init.ModBlocks;
import magikareborn.recipes.ManaPoolRecipe;
import magikareborn.recipes.SpellAltarRecipe;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
@JEIPlugin
public class JeiPlugin implements IModPlugin {

    private ArrayList<ICustomRecipeCategory> categories = new ArrayList<>();

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {

        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        categories.add(new SpellAltarCategory(guiHelper));
        categories.add(new ManaPoolCategory(guiHelper));

        registry.addRecipeCategories(categories.toArray(new IRecipeCategory[0]));
    }

    @Override
    public void register(IModRegistry registry) {

        //Remove from JEI
        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
        blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.LIGHT_SPELL_BLOCK));

        //Add info recipes
        /*registry.addIngredientInfo(new ItemStack(ModBlocks.MAGICAL_LOG_BLOCK), ItemStack.class, new TextComponentTranslation("jei.magical_log").getUnformattedText());
        registry.addIngredientInfo(new ItemStack(ModBlocks.MAGICAL_PLANKS_BLOCK), ItemStack.class, new TextComponentTranslation("jei.magical_planks").getUnformattedText());
        registry.addIngredientInfo(new ItemStack(ModItems.MAGIKA_OPUS_ITEM), ItemStack.class, "jei.magika_opus");
        registry.addIngredientInfo(new ItemStack(ModItems.LIGHT_SPELL_ITEM), ItemStack.class, new TextComponentTranslation("jei.light_spell").getUnformattedText());*/

        //Add recipe handlers
        for (ICustomRecipeCategory cat : categories) {
            registry.handleRecipes(cat.getRecipeClass(), cat, cat.getRecipeCategoryUid());
        }

        registry.addRecipeCatalyst(new ItemStack(ModBlocks.SPELL_ALTAR_BLOCK), SpellAltarCategory.UUID);
        registry.addRecipes(new ArrayList<>(SpellAltarRecipe.recipeList), SpellAltarCategory.UUID);
        Rectangle r = SpellAltarGuiContainer.RecipesArea;
        registry.addRecipeClickArea(SpellAltarGuiContainer.class, r.x, r.y, r.width, r.height, SpellAltarCategory.UUID);
        registry.getRecipeTransferRegistry().addRecipeTransferHandler(new SpellAltarRecipeTranfer());

        registry.addRecipeCatalyst(new ItemStack(ModBlocks.MANA_FLUID_BLOCK), ManaPoolCategory.UUID);
        registry.addRecipes(new ArrayList<>(ManaPoolRecipe.recipeList), ManaPoolCategory.UUID);
    }

}
