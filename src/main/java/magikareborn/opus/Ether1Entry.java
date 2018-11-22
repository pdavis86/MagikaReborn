package magikareborn.opus;

import magikareborn.Capabilities.IOpusCapability;

public class Ether1Entry extends BaseEntry {

    private boolean _isUnlocked;
    private boolean _canBeUnlocked;
    float _progressToNext;

    //todo: Ether + Impulse + Temper + Reward + Headway
    public Ether1Entry() {
        super("Ether1");
    }

    @Override
    public void loadFromCapability(IOpusCapability opusCapability) {
        loadNextFromCapability(opusCapability);
        _isUnlocked = true; //todo: opusCapability.????
        if (!_isUnlocked) {
            _canBeUnlocked = true; //todo: opusCapability.????
        }
        _progressToNext = 1f; //todo: 1.0f - opusCapability.????
    }

    @Override
    public String getCategory() {
        return BaseEntry.CATEGORY_SUPPORT;
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
        return _progressToNext;
    }
}
