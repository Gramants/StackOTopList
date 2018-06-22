package stacko.ste.mvpcleanarch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class TopUsersResponse {

    @SerializedName("items")
    private List<Item> items = null;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


}