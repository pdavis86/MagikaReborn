package magikareborn.jei;

import magikareborn.gui.SpellAltarGuiContainer;
import magikareborn.init.ModBlocks;
import magikareborn.init.ModItems;
import magikareborn.jei.spellaltar.SpellAltarCategory;
import magikareborn.jei.spellaltar.SpellAltarRecipeTranfer;
import magikareborn.recipes.SpellAltarRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
@JEIPlugin
public class JeiPlugin implements IModPlugin {

    private ArrayList<ICustomRecipeCategory> categories = new ArrayList<>();

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {

        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();

        categories.add(new SpellAltarCategory(guiHelper));

        registry.addRecipeCategories(categories.toArray(new IRecipeCategory[0]));
    }

    @Override
    public void register(IModRegistry registry) {

        //Remove from JEI
        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
        blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.LIGHT_SPELL_BLOCK));

        //Add info recipies
        registry.addIngredientInfo(new ItemStack(ModBlocks.MAGICAL_LOG_BLOCK), ItemStack.class, new TextComponentTranslation("jei.magical_log_block").getUnformattedText());
        registry.addIngredientInfo(new ItemStack(ModBlocks.MAGICAL_PLANKS_BLOCK), ItemStack.class, new TextComponentTranslation("jei.magical_planks_block").getUnformattedText());
        registry.addIngredientInfo(new ItemStack(ModItems.MAGIKA_OPUS_ITEM), ItemStack.class, "jei.magika_opus");
        registry.addIngredientInfo(new ItemStack(ModItems.LIGHT_SPELL_ITEM), ItemStack.class, new TextComponentTranslation("jei.light_spell_item").getUnformattedText());

        //Add recipe handlers
        for (ICustomRecipeCategory cat : categories) {
            registry.handleRecipes(cat.getRecipeClass(), cat, cat.getRecipeCategoryUid());
        }

        //Add custom recipes
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.SPELL_ALTAR_BLOCK), SpellAltarCategory.UUID);
        registry.addRecipes(new ArrayList<>(SpellAltarRecipe.recipeList), SpellAltarCategory.UUID);

        //Allow 'Show Recipes'
        Rectangle r = SpellAltarGuiContainer.RecipesArea;
        registry.addRecipeClickArea(SpellAltarGuiContainer.class, r.x, r.y, r.width, r.height, SpellAltarCategory.UUID);

        //Allow recipie transfer
        registry.getRecipeTransferRegistry().addRecipeTransferHandler(new SpellAltarRecipeTranfer());
    }

}
