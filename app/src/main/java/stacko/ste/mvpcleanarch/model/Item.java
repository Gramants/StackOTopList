package stacko.ste.mvpcleanarch.model;

import com.google.gson.annotations.SerializedName;


public class Item {


    @SerializedName("account_id")
    private Long accountId;

    @SerializedName("reputation")
    private Integer reputation;

    @SerializedName("creation_date")
    private Long creationDate;

    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("profile_image")
    private String profileImage;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("location")
    private String location;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }


    public Integer getReputation() {
        return reputation;
    }

    public void setReputation(Integer reputation) {
        this.reputation = reputation;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
