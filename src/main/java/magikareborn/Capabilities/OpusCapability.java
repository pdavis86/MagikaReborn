package magikareborn.Capabilities;

import magikareborn.ModRoot;
import magikareborn.helpers.SoundHelper;
import magikareborn.network.OpusRequestPacket;
import magikareborn.network.OpusUpdatePacket;
import magikareborn.network.PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import org.apache.logging.log4j.Level;

public class OpusCapability implements IOpusCapability {

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
    private float _manaMax;
    private float _magicXpMax;
    private int _manaRegenCooldown;

    //Things to store
    private int _selectedTab;
    private int _magicLevel;
    private float _magicXp;
    private float _mana;

    public void init(Entity entity) {
        _entity = entity;

        //todo: set magic level to 1 at some event and show achievement
        if (getMagicLevel() == 0)
            setMagicLevel(1);

        _isInitialised = true;
    }

    public Entity getEntity() {
        return _entity;
    }

    public boolean isInitialised() {
        return _isInitialised;
    }

    public void cloneFrom(IOpusCapability oldCapability, boolean updateSecureProps) {

        setSelectedTab(oldCapability.getSelectedTab());

        //Prevent cheating
        if (updateSecureProps) {
            setMana(oldCapability.getMana());
            setMagicLevel(oldCapability.getMagicLevel());
            setMagicXp(oldCapability.getMagicXp());
        }

        init(oldCapability.getEntity());
    }

    public void requestFromServer() {
        //System.out.println("Requesting Opus data from server");
        PacketHandler.INSTANCE.sendToServer(new OpusRequestPacket());
    }

    public void sendToServer() {
        //Note: do not call on any getter or setter as they are used on both sides

        //if (_entity.getEntityWorld().isRemote) {
        //System.out.println("Sending Opus data to server");
        PacketHandler.INSTANCE.sendToServer(new OpusUpdatePacket(this));
        //}
    }

    public void sendToPlayer() {
        //Note: do not call on any getter or setter as they are used on both sides

        if (!(_entity instanceof EntityPlayerMP)) {
            return;
        }

        //if (!_entity.getEntityWorld().isRemote) {
        //System.out.println("Sending Opus data to player");
        PacketHandler.INSTANCE.sendTo(new OpusUpdatePacket(this), (EntityPlayerMP) _entity);
        //}
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

    public float getManaMax() {
        return _manaMax;
    }

    public void subtractMana(float cost) {
        if (_entity instanceof EntityPlayer && ((EntityPlayer) _entity).capabilities.isCreativeMode) {
            return;
        }
        _mana -= cost;
        if (_mana < 0) {
            ModRoot.logger.log(Level.WARN, "Entity " + _entity.getName() + " managed to spend more mana than they had!!!");
            _mana = 0;
        }
        sendToPlayer();
    }

    public void regenMana() {

        //todo: check for mana regen skill

        if (_mana == _manaMax) {
            return;
        } else if (_manaRegenCooldown > 0) {
            _manaRegenCooldown -= 1;
        } else {
            //todo: is this a good default cooldown amount?
            //todo: allow things to increase this speed temporarily
            _manaRegenCooldown = 40;
            //todo: is this a good amount to regen?
            _mana += _manaMax / 20;
            if (_mana > _manaMax) {
                _mana = _manaMax;
            }

            sendToPlayer();
        }
    }


    //Magic level
    public void setMagicLevel(int level) {

        //System.out.println("Setting magic level to " + level);

        _magicLevel = level;

        //todo: are these values OK?
        _manaMax = (float) Math.pow(_magicLevel, 1.2f);
        _magicXpMax = (float) Math.pow(_magicLevel, 1.2f);
    }

    public int getMagicLevel() {
        return _magicLevel;
    }


    //Magic XP
    public void setMagicXp(float xp) {
        _magicXp = xp;
    }

    public float getMagicXp() {
        return _magicXp;
    }

    public float getMagicXpMax() {
        return _magicXpMax;
    }

    public void addMagicXp(float xp) {
        _magicXp += xp;
        if (_magicXp > _magicXpMax) {
            _magicXp -= _magicXpMax;
            setMagicLevel(_magicLevel + 1);
            setMana(_manaMax);

            //todo: replace sound event
            SoundHelper.playSoundForPlayer(_entity, SoundEvents.ENTITY_PLAYER_LEVELUP, 1f, 1f);

        } else {
            //todo: replace sound event
            SoundHelper.playSoundForPlayer(_entity, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
        }

        sendToPlayer();
    }

    public boolean isSpellUnlocked() {
        //todo implement skill tree unlocking
        return true;
    }


}
