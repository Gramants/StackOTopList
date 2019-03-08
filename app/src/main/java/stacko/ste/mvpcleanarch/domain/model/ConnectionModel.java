package stacko.ste.mvpcleanarch.domain.model;


public class ConnectionModel {
    public int mType;
    public boolean  mActive;
    public ConnectionModel(int type, boolean active) {
        mType=type;
        mActive=active;
    }
}
