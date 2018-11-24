package magikareborn.containers;

import magikareborn.base.MySlot;
import magikareborn.base.PersistentInventoryCrafting;
import magikareborn.recipes.SpellAltarRecipe;
import magikareborn.tileentities.SpellAltarTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
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

    private static final int _outputSlot = 9;

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

        Slot slot = this.inventorySlots.get(index);
        if (slot == null || !slot.getHasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack slotItemStack = slot.getStack();
        ItemStack copyItemStack = slotItemStack.copy();
        ItemStack copyForTakeItemStack = slotItemStack.copy();

        //todo: set these as instance variables with values set from adding of the slots
        int firstCraftingAreaSlotIndex = 0;
        int lastCraftingAreaSlotIndex = 8;
        int firstInventorySlotIndex = _outputSlot + 1;
        int lastInventorySlotIndex = this.inventorySlots.size() - 1;

        if (index == _outputSlot) {

            //todo: does the stat counter work?

            //ItemStack slotTakenItemStack = slot.onTake(playerIn, slotItemStack);
            //slotItemStack.getItem().onCreated(slotItemStack, _world, playerIn);
            if (!this.mergeItemStack(slotItemStack, firstInventorySlotIndex, lastInventorySlotIndex + 1, false)) {
                return ItemStack.EMPTY;
            }
            slot.onSlotChange(slotItemStack, copyItemStack);

        } else if (index <= lastCraftingAreaSlotIndex) {
            if (!this.mergeItemStack(slotItemStack, firstInventorySlotIndex, lastInventorySlotIndex + 1, false)) {
                return ItemStack.EMPTY;
            }

        } else if (!this.mergeItemStack(slotItemStack, firstCraftingAreaSlotIndex, lastCraftingAreaSlotIndex + 1, false)) {
            return ItemStack.EMPTY;
        }

        if (slotItemStack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        } else {
            slot.onSlotChanged();
        }

        slot.onTake(playerIn, copyForTakeItemStack);

        return copyItemStack;
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        checkForValidRecipe();
    }

    private void addOwnSlots(EntityPlayer player) {

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                //System.out.println("Adding slot at index " + (j + i * 3));
                this.addSlotToContainer(new Slot(_craftingMatrix, j + i * 3, 28 + j * 18, 9 + i * 18));
            }
        }

        this.addSlotToContainer(new MySlot(player, _craftingMatrix, _craftingResult, _outputSlot, 100, 27));
    }

    private void addPlayerSlots(IInventory playerInventory) {
        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 10 + col * 18;
                int y = row * 18 + 70;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = 10 + row * 18;
            int y = 58 + 70;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    private void checkForValidRecipe() {
        if (!_world.isRemote) {

            EntityPlayerMP entityPlayerMp = (EntityPlayerMP) _player;
            ItemStack craftedItemStack = ItemStack.EMPTY;

            //Check for crafting table recipe first
            IRecipe recipeMatch = CraftingManager.findMatchingRecipe(_craftingMatrix, _world);
            if (recipeMatch != null && (recipeMatch.isDynamic() || !_world.getGameRules().getBoolean("doLimitedCrafting") || entityPlayerMp.getRecipeBook().isUnlocked(recipeMatch))) {
                _craftingResult.setRecipeUsed(recipeMatch);
                craftedItemStack = recipeMatch.getCraftingResult(_craftingMatrix);

            } else {
                SpellAltarRecipe spellRecipeMatch = SpellAltarRecipe.getMatch(_craftingMatrix);
                if (spellRecipeMatch != null) {
                    craftedItemStack = spellRecipeMatch.getOutputCopy();
                }
            }

            _craftingResult.setInventorySlotContents(-1, craftedItemStack);
            entityPlayerMp.connection.sendPacket(new SPacketSetSlot(this.windowId, _outputSlot, craftedItemStack));
        }
    }


}
