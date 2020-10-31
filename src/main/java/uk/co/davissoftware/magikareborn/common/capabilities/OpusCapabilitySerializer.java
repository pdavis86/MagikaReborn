package uk.co.davissoftware.magikareborn.common.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class OpusCapabilitySerializer implements ICapabilitySerializable<CompoundNBT> {

    private static final String TAGNAME_SELECTED_TAB = "selectedtab";
    private static final String TAGNAME_MAGIC_LEVEL = "magiclevel";
    private static final String TAGNAME_MAGIC_XP = "magicxp";
    private static final String TAGNAME_MANA = "mana";

    private final IOpusCapability _opusCapability;

    public OpusCapabilitySerializer() {
        _opusCapability = new OpusCapability();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
        if (capability == OpusCapabilityStorage.CAPABILITY) {
            return LazyOptional.of(() -> _opusCapability).cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tagCompound = new CompoundNBT();
        tagCompound.putInt(TAGNAME_SELECTED_TAB, _opusCapability.getSelectedTab());
        tagCompound.putInt(TAGNAME_MAGIC_LEVEL, _opusCapability.getMagicLevel());
        tagCompound.putFloat(TAGNAME_MAGIC_XP, _opusCapability.getXp());
        tagCompound.putFloat(TAGNAME_MANA, _opusCapability.getMana());
        return tagCompound;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        //todo: ? - if (this.player.getEntityWorld() instanceof ServerWorld) {
        _opusCapability.setSelectedTab(nbt.getInt(TAGNAME_SELECTED_TAB));
        _opusCapability.setMagicLevel(nbt.getInt(TAGNAME_MAGIC_LEVEL));
        _opusCapability.setXp(nbt.getFloat(TAGNAME_MAGIC_XP));
        _opusCapability.setMana(nbt.getFloat(TAGNAME_MANA));
        //Don't call init(). That's done on the login event.
    }

}
