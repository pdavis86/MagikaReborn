package uk.co.davissoftware.magikareborn.common.capabilities;

import net.minecraft.entity.Entity;

public interface IOpusCapability {

    void init(Entity entity);

    Entity getEntity();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isInitialised();

    void fillFrom(IOpusCapability oldCapability, boolean updateSecureProps);

    void requestFromServer();

    void sendToServer();

    void sendToPlayer();


    //Selected Tab
    int getSelectedTab();

    void setSelectedTab(int tabIndex);


    //Mana
    void setMana(float value);

    float getMana();

    int getManaMax();

    void subtractMana(float cost);

    void regenMana();


    //Magic level
    void setMagicLevel(int level);

    int getMagicLevel();


    //Magic XP
    void setXp(float xp);

    float getXp();

    int getXpMax();

    void addXp(float xp);


    boolean isSpellUnlocked();

}
