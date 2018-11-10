package magikareborn.Capabilities;

import magikareborn.ModRoot;
import magikareborn.network.OpusUpdatePacket;
import magikareborn.network.PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import org.apache.logging.log4j.Level;

public class OpusCapability implements IOpusCapability {

    //Things passed in or calculated
    private Entity _entity;
    private boolean _isInitialised;
    private float _manaMax;
    private float _magicXpToNextLevel;
    private int _manaRegenCooldown;

    //Things to store
    private int _selectedTab;
    private int _magicLevel;
    private float _magicXp;
    private float _mana;

    @Override
    public void init(Entity entity) {
        _entity = entity;

        //todo: set values based on level
        _manaMax = 250.0F;

        _magicXpToNextLevel = (float) Math.pow(_magicLevel, 1.2f);

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

    private void sync() {
        PacketHandler.INSTANCE.sendToServer(new OpusUpdatePacket(this));
    }


    //Selected Tab
    @Override
    public int getSelectedTab() {
        return _selectedTab;
    }

    @Override
    public void setSelectedTab(int tabIndex) {
        _selectedTab = tabIndex;
        sync();
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
    public void subtractMana(float cost) {
        if (_entity instanceof EntityPlayer && ((EntityPlayer) _entity).capabilities.isCreativeMode) {
            return;
        }
        _mana -= cost;
        if (_mana < 0) {
            ModRoot.logger.log(Level.WARN, "Entity " + _entity.getName() + " managed to spend more mana than they had!!!");
            _mana = 0;
        }
        sync();
    }

    @Override
    public void regenMana() {
        if (_mana == _manaMax) {
            return;
        } else if (_manaRegenCooldown > 0) {
            _manaRegenCooldown -= 1;
        } else {
            //todo: is this a good cooldown amount?
            _manaRegenCooldown = 20;

            //todo: is this a good amount to regen?
            _mana += _manaMax / 10;

            if (_mana > _manaMax) {
                _mana = _manaMax;
            }

            sync();
        }
    }


    //Magic level
    @Override
    public void setMagicLevel(int level) {
        _magicLevel = level;
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
    public void addMagicXp(float xp) {
        _magicXp += xp;
        if (_magicXp > _magicXpToNextLevel) {
            _magicXp -= _magicXpToNextLevel;
            setMagicLevel(_magicLevel + 1);
            init(_entity);
            setMana(_manaMax);
            //todo: check this works
            //todo: replace sound event
            _entity.getEntityWorld().playSound(_entity.posX, _entity.posY, _entity.posZ, SoundEvents.ENTITY_PLAYER_LEVELUP, null, 1, 1, false);
        }
        sync();
    }


}
