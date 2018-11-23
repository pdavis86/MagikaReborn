package magikareborn.opus;

import magikareborn.capabilities.IOpusCapability;
import magikareborn.helpers.ResourceHelper;

public class LightSpellEntry extends BaseEntry {

    private boolean _isUnlocked;
    private boolean _canBeUnlocked;
    //float _progressToNext;

    public LightSpellEntry() {
        super("Illumination");
        Resource = ResourceHelper.getItemResource("LightSpell");
    }

    @Override
    public void loadFromCapability(IOpusCapability opusCapability) {
        loadNextFromCapability(opusCapability);
        _isUnlocked = true; //todo: opusCapability.????
        if (!_isUnlocked) {
            _canBeUnlocked = true; //todo: opusCapability.????
        }
    }

    @Override
    public String getCategory() {
        return BaseEntry.CATEGORY_UTILITY;
    }

    @Override
    public boolean isUnlocked() {
        return _isUnlocked;
    }

    @Override
    public boolean canBeUnlocked() {
        return _canBeUnlocked;
    }

    @Override
    public float getProgressToNext() {
        return 0;
    }
}
