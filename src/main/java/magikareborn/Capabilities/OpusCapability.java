package magikareborn.Capabilities;

public class OpusCapability implements IOpusCapability {

    private int _selectedTab = -1;

    @Override
    public void afterLogin() {

        //todo: data appears not to be saving :'(

        if (getSelectedTab() == -1) {
            System.out.println("Setting selected tab to 0");
            setSelectedTab(0);
        } else {
            System.out.println("No need to change selected tab");
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
