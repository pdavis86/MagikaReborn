package uk.co.davissoftware.magikareborn.common.tileentities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import uk.co.davissoftware.magikareborn.common.containers.SpellAltarContainer;
import uk.co.davissoftware.magikareborn.init.ModTileEntities;

import javax.annotation.Nullable;

public class SpellAltarTileEntity extends TileEntity implements INamedContainerProvider {

    public SpellAltarTileEntity() {
        super(ModTileEntities.SPELLALTAR_TILEENTITY.get());
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("todo: what goes here?");
    }

    @Override
    public Container createMenu(int menuId, PlayerInventory playerInventory, @Nullable PlayerEntity playerEntity) {
        return new SpellAltarContainer(menuId, playerInventory, playerEntity, this);
    }

    public boolean canInteractWith(PlayerEntity playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return playerIn.getDistanceSq(Vector3d.func_237489_a_(pos)) <= 64D;
    }

}
