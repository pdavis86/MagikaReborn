package magikareborn.containers;

import magikareborn.base.PersistentInventoryCrafting;
import magikareborn.gui.SpellAltarOutputSlot;
import magikareborn.init.ModFluids;
import magikareborn.recipes.SpellAltarRecipe;
import magikareborn.tileentities.ManaTankTileEntity;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
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
        //todo: Replace - assert (_itemStackHandler != null);

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

        this.addSlotToContainer(new SpellAltarOutputSlot(player, _craftingMatrix, _craftingResult, _outputSlot, 100, 27));
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

            int totalMana = getAvailableMana();

            System.out.println("Total mana: " + totalMana);

            _te.getTileData().setInteger("manaAvailable", totalMana);
            //_te.getUpdateTag().setInteger("manaAvailable", totalMana);

            if (craftedItemStack != null) {
                _craftingResult.setInventorySlotContents(-1, craftedItemStack);
                entityPlayerMp.connection.sendPacket(new SPacketSetSlot(this.windowId, _outputSlot, craftedItemStack));
            }
        }
    }

    public int getAvailableMana() {
        BlockPos posCenter = _te.getPos();
        BlockPos posBotLeft = posCenter.add(-3, 1, -3);
        BlockPos posTopLeft = posCenter.add(-3, 1, +3);
        BlockPos posBotRight = posCenter.add(+3, 1, -3);
        BlockPos posTopRight = posCenter.add(+3, 1, +3);
        int totalMana = getAvailableManaAt(posBotLeft);
        totalMana += getAvailableManaAt(posTopLeft);
        totalMana += getAvailableManaAt(posBotRight);
        totalMana += getAvailableManaAt(posTopRight);
        return totalMana;
    }

    private int getAvailableManaAt(BlockPos blockPos) {

        TileEntity te = _world.getTileEntity(blockPos);

        //System.out.println("Block position: " + blockPos);
        //System.out.println("Tile entity: " + te);

        if (te == null || !te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
            return 0;
        }

        IFluidHandler fluidHandler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        if (fluidHandler == null) {
            return 0;
        }

        if (!(fluidHandler instanceof FluidTank)) {
            return 0;
        }

        FluidTank fluidTank = (FluidTank)fluidHandler;

        return fluidTank.getFluidAmount();

        //ManaTankTileEntity mtte = (ManaTankTileEntity) te;

        //int amount = mtte.getFluidTank().getFluidAmount();
        //System.out.println("Tile entity fluid amount: " + amount);
        //System.out.println("-");

        //return mtte.getFluidTank().getFluidAmount();
    }


}
