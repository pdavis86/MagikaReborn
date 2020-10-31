package magikareborn.gui;

import magikareborn.base.BaseSpell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nonnull;

public class SpellAltarOutputSlot extends SlotCrafting {

    private final InventoryCrafting _craftMatrix;
    private final EntityPlayer _player;
    private int _amountCrafted;

    public SpellAltarOutputSlot(EntityPlayer player, InventoryCrafting craftingInventory, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
        super(player, craftingInventory, inventoryIn, slotIndex, xPosition, yPosition);
        _player = player;
        _craftMatrix = craftingInventory;
        _amountCrafted = 0;
    }

    @Override
    public void onSlotChange(ItemStack newItemStack, ItemStack oldItemStack) {
        super.onSlotChange(newItemStack, oldItemStack);
        _amountCrafted += oldItemStack.getCount() - newItemStack.getCount();
    }

    @Nonnull
    @Override
    public ItemStack onTake(EntityPlayer thePlayer, @Nonnull ItemStack stack) {

        if (stack.getItem() instanceof BaseSpell) {

            if (_amountCrafted > 0) {
                stack.onCrafting(_player.world, _player, stack.getCount());
                FMLCommonHandler.instance().firePlayerCraftingEvent(_player, stack, _craftMatrix);
            }
            _amountCrafted = 0;

            //Assuming only decreasing by one and not putting anything back into the crafting grid
            for (int i = 0; i < _craftMatrix.getSizeInventory(); ++i) {
                ItemStack craftMatrixStackInSlot = _craftMatrix.getStackInSlot(i);
                if (!craftMatrixStackInSlot.isEmpty()) {
                    _craftMatrix.decrStackSize(i, 1);
                }
            }

            return stack;

        } else {
            return super.onTake(thePlayer, stack);
        }
    }
}
