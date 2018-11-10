package magikareborn.Capabilities;

public interface IOpusCapability {

    public void onPlayerLoggedIn();

    int getSelectedTab();

    void setSelectedTab(int tabIndex);

    void cloneFrom(IOpusCapability oldCapability);
}
