package magikareborn.base;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class PersistentInventoryCrafting extends InventoryCrafting {

    private final ItemStackHandler _itemStackHandler;
    private final Container _container;

    public PersistentInventoryCrafting(Container container, int width, int height, ItemStackHandler itemStackHandler) {
        super(container, width, height);

        assert (itemStackHandler.getSlots() == width * height);
        _itemStackHandler = itemStackHandler;
        _container = container;
    }

    private void updateContainer() {
        _container.onCraftMatrixChanged(this);
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < _itemStackHandler.getSlots(); i++) {
            if (!_itemStackHandler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int index) {
        return _itemStackHandler.getStackInSlot(index);
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int index, int count) {

        ItemStack stackInSlot = getStackInSlot(index);

        if (stackInSlot.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack itemstack;
        if (stackInSlot.getCount() <= count) {
            itemstack = stackInSlot;
            setInventorySlotContents(index, ItemStack.EMPTY);
            return itemstack;

        } else {
            itemstack = stackInSlot.splitStack(count);
            /*if (stackInSlot.getCount() == 0) {
                setInventorySlotContents(index, ItemStack.EMPTY);
            }*/
            updateContainer();
            return itemstack;
        }
    }

    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
        _itemStackHandler.setStackInSlot(index, stack);
        updateContainer();
    }

    /*@Override
    public void clear() {
        System.out.println("Clear() was called on PersistentInventoryCrafting");
        // Don't clear!
    }*/
}
