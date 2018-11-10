package magikareborn.Capabilities;

public class OpusCapability implements IOpusCapability {

    private int _selectedTab = -1;

    @Override
    public void onPlayerLoggedIn() {
        //Server has 0, client has -1. Client knows it needs to request the data.
        if (getSelectedTab() == -1) {
            setSelectedTab(0);
        }
    }

    @Override
    public int getSelectedTab() {
        return _selectedTab;
    }

    @Override
    public void setSelectedTab(int tabIndex) {
        _selectedTab = tabIndex;
    }

    @Override
    public void cloneFrom(IOpusCapability oldCapability) {
        setSelectedTab(oldCapability.getSelectedTab());
    }

}
