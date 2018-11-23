package magikareborn.capabilities;

import net.minecraft.entity.Entity;

public interface IOpusCapability {

    void init(Entity entity);

    Entity getEntity();

    boolean isInitialised();

    void cloneFrom(IOpusCapability oldCapability, boolean updateSecureProps);

    void requestFromServer();

    void sendToServer();

    void sendToPlayer();


    //Selected Tab
    int getSelectedTab();

    void setSelectedTab(int tabIndex);


    //Mana
    void setMana(float value);

    float getMana();

    float getManaMax();

    void subtractMana(float cost);

    void regenMana();


    //Magic level
    void setMagicLevel(int level);

    int getMagicLevel();


    //Magic XP
    void setMagicXp(float xp);

    float getMagicXp();

    float getMagicXpMax();

    void addMagicXp(float xp);


    boolean isSpellUnlocked();

}