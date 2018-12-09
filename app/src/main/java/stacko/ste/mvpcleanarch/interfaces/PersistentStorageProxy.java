package stacko.ste.mvpcleanarch.interfaces;


public interface PersistentStorageProxy {

    Boolean getLastNetworkStatus();

    void setNetworkStatus(Boolean networkStatus);

}
