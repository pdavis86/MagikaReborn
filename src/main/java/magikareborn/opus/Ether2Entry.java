package magikareborn.opus;

import magikareborn.Capabilities.IOpusCapability;

public class Ether2Entry extends BaseEntry {

    private boolean _isUnlocked;
    private boolean _canBeUnlocked;
    float _progressToNext;

    public Ether2Entry(){
        super("Ether2");
    }

    @Override
    public void loadFromCapability(IOpusCapability opusCapability) {
        loadNextFromCapability(opusCapability);
        _isUnlocked = false; //todo: opusCapability.????
        if (!_isUnlocked) {
            _canBeUnlocked = true; //todo: opusCapability.????
        }
        _progressToNext = 0.2f; //todo: 1.0f - opusCapability.????
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
        //System.out.println("_progressToNext: " + _progressToNext);
        return _progressToNext;
    }
}
