package magikareborn.Capabilities;

public class ManaCapability implements IManaCapability {

    private float _mana = 250.0F;

    @Override
    public void consume(float points) {
        _mana -= points;
    }

    @Override
    public void fill(float points) {
        _mana += points;
    }

    @Override
    public void setMana(float points) {
        _mana = points;
    }

    @Override
    public float getMana() {
        return _mana;
    }
}
