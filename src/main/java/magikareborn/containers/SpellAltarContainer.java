package magikareborn.containers;

import magikareborn.base.PersistentInventoryCrafting;
import magikareborn.tileentities.SpellAltarTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber
public class SpellAltarContainer extends Container {

    private static final int _ouputSlot = 9;

    private final EntityPlayer _player;
    private final SpellAltarTileEntity _te;
    private final World _world;
    private final PersistentInventoryCrafting _craftingMatrix;
    private final InventoryCraftResult _craftingResult;

    public SpellAltarContainer(EntityPlayer player, SpellAltarTileEntity te) {

        _player = player;
        _te = te;
        _world = player.getEntityWorld();

        ItemStackHandler _itemStackHandler = (ItemStackHandler) te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        assert (_itemStackHandler != null);

        _craftingMatrix = new PersistentInventoryCrafting(this, 3, 3, _itemStackHandler);
        _craftingResult = new InventoryCraftResult();

        addOwnSlots(player);
        addPlayerSlots(player.inventory);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onCraftingStationGuiOpened(PlayerContainerEvent.Open event) {
        // Check for a recipe
        //if (event.getContainer() instanceof SpellAltarContainer) {
        ((SpellAltarContainer) event.getContainer()).checkForValidRecipe();
        //}
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
        return _te.canInteractWith(playerIn);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        /*ItemStack returnItemStack = ItemStack.EMPTY;

        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack slotItemStack = slot.getStack();
            returnItemStack = slotItemStack.copy();

            if (index < SpellAltarTileEntity.SIZE) {
                if (!this.mergeItemStack(slotItemStack, SpellAltarTileEntity.SIZE, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(slotItemStack, 0, SpellAltarTileEntity.SIZE, false)) {
                return ItemStack.EMPTY;
            }

            if (slotItemStack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return returnItemStack;*/

        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 0) {
                itemstack1.getItem().onCreated(itemstack1, _world, playerIn);

                if (!this.mergeItemStack(itemstack1, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index >= 10 && index < 37) {
                if (!this.mergeItemStack(itemstack1, 37, 46, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 37 && index < 46) {
                if (!this.mergeItemStack(itemstack1, 10, 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);

            if (index == 0) {
                playerIn.dropItem(itemstack2, false);
            }
        }

        return itemstack;
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        checkForValidRecipe();
    }

    /*@Override
    public void onContainerClosed(EntityPlayer playerIn) {
        //super.onContainerClosed(playerIn);

        if (!_world.isRemote) {
            for (int i = 0; i < 9; ++i) {
                ItemStack itemstack = _craftingMatrix.getStackInSlot(i);
                if (itemstack != null) {
                    playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
                }
            }
        }

        //playerIn.inventory.markDirty();
    }*/

    private void addOwnSlots(EntityPlayer player) {

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                //System.out.println("Adding slot at index " + (j + i * 3));
                this.addSlotToContainer(new Slot(_craftingMatrix, j + i * 3, 28 + j * 18, 9 + i * 18));
            }
        }

        this.addSlotToContainer(new SlotCrafting(player, _craftingMatrix, _craftingResult, _ouputSlot, 100, 27));


        /*IItemHandler itemHandler = _te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        final int slotSize = 16;
        final int space = 2;
        int leftStart = 64;  //(SpellAltarGuiContainer.WIDTH - (3 * slotSize) - (2 * space)) /2;
        int x = leftStart;
        int y = space * 3;

        int slotIndex = 0;
        int c = 0;
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, x, y));
            slotIndex++;
            if (c < 2) {
                c++;
                x += slotSize + space;
            } else {
                c = 0;
                x = leftStart;
                y += slotSize + space;
            }
        }*/
    }

    private void addPlayerSlots(IInventory playerInventory) {
        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 10 + col * 18;
                int y = row * 18 + 70;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 10, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = 10 + row * 18;
            int y = 58 + 70;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }

        //onCraftMatrixChanged(_craftingMatrix);
    }

    private void checkForValidRecipe() {
            /*System.out.println("Stack 0 name: " + _craftingMatrix.getStackInSlot(0).getDisplayName());

        IRecipe recipeMatch = CraftingManager.findMatchingRecipe(_craftingMatrix, _world);

        if (!_world.isRemote) {
            if (recipeMatch != null) {
                ItemStack matchItemStack = recipeMatch.getRecipeOutput();
                System.out.println("Found recipe for: " + recipeMatch.getRecipeOutput().getItem().getUnlocalizedName());
                System.out.println("recipeMatch type SimpleName is: " + recipeMatch.getRegistryType().getSimpleName());
                _craftingResult.setInventorySlotContents(0, matchItemStack);

            } else {
                System.out.println("No recipeMatch found");
                _craftingResult.setInventorySlotContents(0, ItemStack.EMPTY);
            }
        }*/


        if (!_world.isRemote) {
            IRecipe recipeMatch = CraftingManager.findMatchingRecipe(_craftingMatrix, _world);

            EntityPlayerMP entityPlayerMp = (EntityPlayerMP) _player;
            ItemStack craftedItemStack = ItemStack.EMPTY;

            if (recipeMatch != null && (recipeMatch.isDynamic() || !_world.getGameRules().getBoolean("doLimitedCrafting") || entityPlayerMp.getRecipeBook().isUnlocked(recipeMatch))) {
                //System.out.println("Found recipe for: " + recipeMatch.getRecipeOutput().getItem().getUnlocalizedName());
                //System.out.println("recipeMatch type SimpleName is: " + recipeMatch.getRegistryType().getSimpleName());

                _craftingResult.setRecipeUsed(recipeMatch);
                craftedItemStack = recipeMatch.getCraftingResult(_craftingMatrix);
            }

            _craftingResult.setInventorySlotContents(-1, craftedItemStack);
            entityPlayerMp.connection.sendPacket(new SPacketSetSlot(this.windowId, _ouputSlot, craftedItemStack));
        }
    }


}
