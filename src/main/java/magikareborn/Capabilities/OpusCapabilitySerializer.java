package magikareborn.Capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OpusCapabilitySerializer implements ICapabilitySerializable<NBTTagCompound> {

    private static final String TAGNAME_SELECTED_TAB = "selectedtab";
    private static final String TAGNAME_MAGIC_LEVEL = "magiclevel";
    private static final String TAGNAME_MAGIC_XP = "magicxp";
    private static final String TAGNAME_MANA = "mana";

    private final IOpusCapability _opusCapability;

    public OpusCapabilitySerializer() {
        _opusCapability = new OpusCapability();
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == OpusCapabilityStorage.CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == OpusCapabilityStorage.CAPABILITY) {
            return (T) _opusCapability;
        }
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger(TAGNAME_SELECTED_TAB, _opusCapability.getSelectedTab());
        tagCompound.setInteger(TAGNAME_MAGIC_LEVEL, _opusCapability.getMagicLevel());
        tagCompound.setFloat(TAGNAME_MAGIC_XP, _opusCapability.getMagicXp());
        tagCompound.setFloat(TAGNAME_MANA, _opusCapability.getMana());
        return tagCompound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        _opusCapability.setSelectedTab(nbt.getInteger(TAGNAME_SELECTED_TAB));
        _opusCapability.setMagicLevel(nbt.getInteger(TAGNAME_MAGIC_LEVEL));
        _opusCapability.setMagicXp(nbt.getInteger(TAGNAME_MAGIC_XP));
        _opusCapability.setMana(nbt.getInteger(TAGNAME_MANA));
        //Don't call init(). That's done on the login event.
    }

}
