package uk.co.davissoftware.magikareborn.common.helpers;

public class LevellingHelper {

    //todo: are these values OK?

    public static int getXpToLevelUp(int magicLevel) {
        return (int) Math.pow((float) magicLevel, 1.2f);
    }

    public static int getManaMax(int magicLevel) {
        return magicLevel * 20;
    }

    public static int getManaForCrafting(int magicLevel) {
        return getXpToLevelUp(magicLevel) * getManaMax(magicLevel);
    }

    public static int getManaForCasting(int magicLevel) {
        return (int) Math.pow((float) getManaForCrafting(magicLevel), 0.5f);
    }
}
