package stacko.ste.mvpcleanarch.data.persistent;


public interface PersistentStorageProxy {

    Boolean getLastNetworkStatus();

    void setNetworkStatus(Boolean networkStatus);

}
