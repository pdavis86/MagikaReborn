package magikareborn.Capabilities;

import magikareborn.ModRoot;
import magikareborn.network.OpusRequestPacket;
import magikareborn.network.OpusUpdatePacket;
import magikareborn.network.PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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

    @Override
    public void init(Entity entity) {
        _entity = entity;
        _isInitialised = true;
    }

    @Override
    public Entity getEntity() {
        return _entity;
    }

    @Override
    public boolean isInitialised() {
        return _isInitialised;
    }

    @Override
    public void cloneFrom(IOpusCapability oldCapability) {
        setSelectedTab(oldCapability.getSelectedTab());
        setMana(oldCapability.getMana());
        setMagicLevel(oldCapability.getMagicLevel());
        setMagicXp(oldCapability.getMagicXp());
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


    //Selected Tab
    @Override
    public int getSelectedTab() {
        return _selectedTab;
    }

    @Override
    public void setSelectedTab(int tabIndex) {
        _selectedTab = tabIndex;
    }


    //Mana
    @Override
    public void setMana(float value) {
        _mana = value;
    }

    @Override
    public float getMana() {
        return _mana;
    }

    @Override
    public float getManaMax() {
        return _manaMax;
    }

    @Override
    public void subtractMana(float cost) {
        if (_entity instanceof EntityPlayer && ((EntityPlayer) _entity).capabilities.isCreativeMode) {
            return;
        }
        _mana -= cost;
        if (_mana < 0) {
            ModRoot.logger.log(Level.WARN, "Entity " + _entity.getName() + " managed to spend more mana than they had!!!");
            _mana = 0;
        }
        sendToServer();
    }

    @Override
    public void regenMana() {

        //todo: check for mana regen skill

        if (_mana == _manaMax) {
            //System.out.println("Mana is full at " + _mana);
            return;
        } else if (_manaRegenCooldown > 0) {
            _manaRegenCooldown -= 1;
        } else {

            //todo: is this a good cooldown amount?
            _manaRegenCooldown = 200;

            //System.out.println("Mana was " + _mana);

            //todo: is this a good amount to regen?
            _mana += _manaMax / 10;
            if (_mana > _manaMax) {
                _mana = _manaMax;
            }

            //System.out.println("Mana is now " + _mana);

            sendToServer();
        }
    }


    //Magic level
    @Override
    public void setMagicLevel(int level) {

        //System.out.println("Setting magic level to " + level);

        _magicLevel = level;

        //todo: are these values OK?
        _manaMax = (float) Math.pow(_magicLevel, 1.2f);
        _magicXpMax = (float) Math.pow(_magicLevel, 1.2f);
    }

    @Override
    public int getMagicLevel() {
        return _magicLevel;
    }


    //Magic XP
    @Override
    public void setMagicXp(float xp) {
        _magicXp = xp;
    }

    @Override
    public float getMagicXp() {
        return _magicXp;
    }

    @Override
    public float getMagicXpMax() {
        return _magicXpMax;
    }

    @Override
    public void addMagicXp(float xp) {
        _magicXp += xp;
        if (_magicXp > _magicXpMax) {
            _magicXp -= _magicXpMax;
            setMagicLevel(_magicLevel + 1);
            setMana(_manaMax);

            //todo: check this works
            //todo: replace sound event
            _entity.getEntityWorld().playSound(_entity.posX, _entity.posY, _entity.posZ, SoundEvents.ENTITY_PLAYER_LEVELUP, null, 1, 1, false);
        }
        sendToServer();
    }


}
