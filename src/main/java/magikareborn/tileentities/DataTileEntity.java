package magikareborn.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import javax.annotation.Nonnull;

public class DataTileEntity extends TileEntity {

    private static final String COUNTER_NAME = "counter";
    private int counter = 0;

    public int decrement() {
        counter--;
        markDirty();
        return counter;
    }

    public int increment() {
        counter++;
        markDirty();
        return counter;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        counter = compound.getInteger(COUNTER_NAME);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger(COUNTER_NAME, counter);
        return compound;
    }
}
