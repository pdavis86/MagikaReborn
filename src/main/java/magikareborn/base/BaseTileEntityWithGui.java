package magikareborn.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BaseTileEntityWithGui extends TileEntity {

    public abstract Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z);

    public abstract Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z);

    /*private final IItemHandler inventory = new ItemStackHandler(...) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            markDirty();
        }
    }*/
}
