package magikareborn.opus;

import magikareborn.capabilities.IOpusCapability;
import magikareborn.helpers.ResourceHelper;
import net.minecraft.util.ResourceLocation;

public abstract class BaseEntry {

    public static final String CATEGORY_UTILITY = "utility";
    public static final String CATEGORY_SUPPORT = "support";
    public static final String CATEGORY_OFFENSIVE = "offensive";
    public static final String CATEGORY_DEFENSIVE = "defensive";

    ResourceLocation Resource;

    //private final static String _namePrefix = ModRoot.MODID + ".opus.";

    private final String _name;

    private BaseEntry _next;

    BaseEntry(String name) {
        _name = name.toLowerCase();
    }

    public String getName() {
        return _name;
    }

    BaseEntry setNext(BaseEntry next) {
        _next = next;
        return this;
    }

    public BaseEntry getNext() {
        return _next;
    }

    public ResourceLocation getResource() {
        if (Resource == null) {
            Resource = ResourceHelper.getOpusGuiResource(getName());
        }
        return Resource;
    }

    void loadNextFromCapability(IOpusCapability opusCapability) {
        BaseEntry next = getNext();
        if (next != null) {
            next.loadFromCapability(opusCapability);
        }
    }

    public abstract void loadFromCapability(IOpusCapability opusCapability);

    public abstract String getCategory();

    public abstract boolean isUnlocked();

    public abstract boolean canBeUnlocked();

    public abstract float getProgressToNext();


    /*public String getNameTk() {
        return _namePrefix + _name + ".name";
    }

    public String getTooltipTk() {
        return _namePrefix + _name + ".tooltip";
    }*/

}



