package magikareborn.jei;

import magikareborn.init.ModBlocks;
import magikareborn.init.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

@JEIPlugin
public class JeiPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {

        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
        blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.LIGHT_SPELL_BLOCK));

        registry.addIngredientInfo(new ItemStack(ModBlocks.MAGICAL_LOG_BLOCK), ItemStack.class, new TextComponentTranslation("jei.magical_log_block").getUnformattedText());
        registry.addIngredientInfo(new ItemStack(ModBlocks.MAGICAL_PLANKS_BLOCK), ItemStack.class, new TextComponentTranslation("jei.magical_planks_block").getUnformattedText());
        registry.addIngredientInfo(new ItemStack(ModItems.MAGIKA_OPUS_ITEM), ItemStack.class, "jei.magika_opus");
        registry.addIngredientInfo(new ItemStack(ModItems.LIGHT_SPELL_ITEM), ItemStack.class, new TextComponentTranslation("jei.light_spell_item").getUnformattedText());
    }

}
