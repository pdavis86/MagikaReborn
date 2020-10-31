package uk.co.davissoftware.magikareborn.common.capabilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.logging.log4j.Level;
import uk.co.davissoftware.magikareborn.ModRoot;
import uk.co.davissoftware.magikareborn.common.helpers.LevellingHelper;
import uk.co.davissoftware.magikareborn.common.helpers.SoundHelper;
import uk.co.davissoftware.magikareborn.common.network.OpusRequestPacket;
import uk.co.davissoftware.magikareborn.common.network.OpusUpdatePacket;
import uk.co.davissoftware.magikareborn.common.network.PacketHandler;

import java.util.Optional;

public class OpusCapability implements IOpusCapability {

    public OpusCapability() {
    }

    public OpusCapability(Entity entity) {
        init(entity);
    }

    //Flags
    //public static final int FLAG_UPDATE_MANA = 0x1;
    //public static final int FLAG_UPDATE_UTILITY = 0x2;

    /*public int addUpdateFlag(int flag) {
        _updateFlags = _updateFlags & flag;
        return _updateFlags;
    }

    public int removeUpdateFlags(int flag) {
        _updateFlags = _updateFlags ^ flag;
        return _updateFlags;
    }*/

    //Things passed in or calculated
    private Entity _entity;
    private boolean _isInitialised;
    private int _manaMax;
    private int _xpMax;
    private int _manaRegenCooldown;

    //Things to store
    private int _selectedTab;
    private int _magicLevel;
    private float _xp;
    private float _mana;

    public void init(Entity entity) {
        _entity = entity;
        _isInitialised = true;
    }

    public Entity getEntity() {
        return _entity;
    }

    public boolean isInitialised() {
        return _isInitialised;
    }

    public void fillFrom(IOpusCapability oldCapability, boolean updateSecureProps) {

        ModRoot.LOGGER.warn("Updating secure props: " + updateSecureProps);

        setSelectedTab(oldCapability.getSelectedTab());

        //Prevent cheating
        if (updateSecureProps) {
            setMana(oldCapability.getMana());
            setMagicLevel(oldCapability.getMagicLevel());
            setXp(oldCapability.getXp());
        }

        init(oldCapability.getEntity());
    }

    public void requestFromServer() {

        ModRoot.LOGGER.warn("Requesting OpusCapability from server");

        PacketHandler.getInstance().sendToServer(new OpusRequestPacket());
    }

    public void sendToServer() {

        ModRoot.LOGGER.warn("Sending OpusCapability to server");

        if (!(_entity instanceof PlayerEntity)) {
            ModRoot.LOGGER.warn("Sending OpusCapability to server: entity not player");
            return;
        }
        PacketHandler.getInstance().sendToServer(new OpusUpdatePacket(this));
    }

    public void sendToPlayer() {

        ModRoot.LOGGER.warn("Sending OpusCapability to player");

        if (!(_entity instanceof ServerPlayerEntity)) {
            ModRoot.LOGGER.warn("Sending OpusCapability to player: entity not player");
            return;
        }
        PacketHandler.getInstance().sendToPlayer(new OpusUpdatePacket(this), (ServerPlayerEntity) _entity);
    }


    //Selected Tab
    public int getSelectedTab() {
        return _selectedTab;
    }

    public void setSelectedTab(int tabIndex) {
        _selectedTab = tabIndex;
    }


    //Mana
    public void setMana(float value) {
        _mana = value;
    }

    public float getMana() {
        //System.out.println("getMana: " + _mana);
        return _mana;
    }

    public int getManaMax() {
        return _manaMax;
    }

    public void subtractMana(float cost) {
        if (_entity instanceof PlayerEntity && ((PlayerEntity) _entity).isCreative()) {
            return;
        }
        _mana -= cost;
        if (_mana < 0) {
            ModRoot.LOGGER.log(Level.WARN, "Entity " + _entity.getName() + " managed to spend more mana than they had!!!");
            _mana = 0;
        }
        sendToPlayer();
    }

    public void regenMana() {

        //todo: check for mana regen skill

        if (_mana == _manaMax) {
            return;
        }

        if (_manaRegenCooldown > 0) {
            _manaRegenCooldown -= 1;
        } else {
            //todo: is this a good default cooldown amount?
            //todo: allow things to increase this speed temporarily
            _manaRegenCooldown = 40;
            //todo: is this a good amount to regen?
            _mana += (float) _manaMax / 22;
            if (_mana > _manaMax) {
                _mana = _manaMax;
            }

            sendToPlayer();
        }
    }


    //Magic level
    public void setMagicLevel(int level) {
        _magicLevel = level;
        _xpMax = LevellingHelper.getXpToLevelUp(_magicLevel);
        _manaMax = LevellingHelper.getManaMax(_magicLevel);
    }

    public int getMagicLevel() {
        return _magicLevel;
    }


    //Magic XP
    public void setXp(float xp) {
        _xp = xp;
    }

    public float getXp() {
        return _xp;
    }

    public int getXpMax() {
        return _xpMax;
    }

    public void addXp(float xp) {
        _xp += xp;
        if (_xp > _xpMax) {
            _xp -= _xpMax;
            setMagicLevel(_magicLevel + 1);
            setMana(_manaMax);

            //todo: replace sound event
            SoundHelper.playSoundForPlayer(_entity, SoundEvents.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        }

        sendToPlayer();
    }

    public boolean isSpellUnlocked() {

        if (_entity instanceof PlayerEntity && ((PlayerEntity) _entity).isCreative()) {
            return true;
        }

        //todo: implement skill tree unlocking
        return true;
    }

    public static IOpusCapability getFromPlayer(PlayerEntity player){
        Optional<IOpusCapability> cap = player.getCapability(OpusCapabilityStorage.CAPABILITY, null).resolve();
        if (!cap.isPresent()) {
            ModRoot.LOGGER.warn("Could not find Capability for entity " + player.getName().toString());
            return null;
        }
        return cap.get();
    }

    //todo: get rid of this
    public static void logNullWarning(String name) {
        ModRoot.LOGGER.warn("Could not find Capability for entity " + name);
    }
}
