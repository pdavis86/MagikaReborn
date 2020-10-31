package magikareborn.compat.jei.spellaltar;

import magikareborn.containers.SpellAltarContainer;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.inventory.Slot;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SpellAltarRecipeTranfer implements IRecipeTransferInfo<SpellAltarContainer> {

    @Nonnull
    @Override
    public Class<SpellAltarContainer> getContainerClass() {
        return SpellAltarContainer.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid() {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Override
    public boolean canHandle(@Nonnull SpellAltarContainer spellAltarContainer) {
        return true;
    }

    @Nonnull
    @Override
    public List<Slot> getRecipeSlots(@Nonnull SpellAltarContainer spellAltarContainer) {
        List<Slot> slots = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            slots.add(spellAltarContainer.getSlot(i));
        }
        return slots;
    }

    @Nonnull
    @Override
    public List<Slot> getInventorySlots(@Nonnull SpellAltarContainer spellAltarContainer) {
        List<Slot> slots = new ArrayList<>();
        for (int i = 10; i < spellAltarContainer.inventorySlots.size(); i++) {
            slots.add(spellAltarContainer.getSlot(i));
        }
        return slots;
    }
}
