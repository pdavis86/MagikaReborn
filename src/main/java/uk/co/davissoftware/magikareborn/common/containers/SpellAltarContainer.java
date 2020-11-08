package uk.co.davissoftware.magikareborn.common.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import uk.co.davissoftware.magikareborn.ModRoot;
import uk.co.davissoftware.magikareborn.common.tileentities.SpellAltarTileEntity;
import uk.co.davissoftware.magikareborn.common.util.RegistryHandler;

import java.util.Objects;

public class SpellAltarContainer extends Container {

    private final PlayerInventory _playerInv;
    private final PlayerEntity _player;
    private final SpellAltarTileEntity _te;
    private final IWorldPosCallable _canInteractWithCallable;

    @SuppressWarnings("ConstantConditions")
    public SpellAltarContainer(int menuId, PlayerInventory playerInventory, PlayerEntity playerEntity, SpellAltarTileEntity tileEntity) {
        super(RegistryHandler.SPELLALTAR_CONTAINER.get(), menuId);

        _playerInv = playerInventory;
        _player = playerEntity;
        _te = tileEntity;
        _canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
    }

    public SpellAltarContainer(int windowId, PlayerInventory playerInventory, PacketBuffer data) {
        this(windowId, playerInventory, playerInventory.player, getTileEntity(playerInventory, data));
    }

    //todo: move and generalise this
    private static SpellAltarTileEntity getTileEntity(PlayerInventory playerInventory, PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof SpellAltarTileEntity) {
            return (SpellAltarTileEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(_canInteractWithCallable, playerIn, RegistryHandler.SPELLALTAR_BLOCK.get());
    }

//    @Override
//    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
//        return super.transferStackInSlot(playerIn, index);
//    }
}
