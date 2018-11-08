package magikareborn.Capabilities;

public interface IOpusCapability {

    public void afterLogin();

    int getSelectedTab();
    void setSelectedTab(int tabIndex);

    void cloneFrom(IOpusCapability oldCapability);
}
