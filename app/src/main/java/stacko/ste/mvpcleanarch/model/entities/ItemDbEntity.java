package stacko.ste.mvpcleanarch.model.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import stacko.ste.mvpcleanarch.util.Config;

@Entity(tableName = Config.TABLE_USERS)
public class ItemDbEntity {

    @PrimaryKey
    @ColumnInfo(name = "account_id")
    private Long id;
    @ColumnInfo(name = "reputation")
    private Integer reputation;
    @ColumnInfo(name = "creation_date")
    private Long creationDate;
    @ColumnInfo(name = "user_id")
    private Integer userId;
    @ColumnInfo(name = "profile_image")
    private String profileImage;
    @ColumnInfo(name = "display_name")
    private String displayName;
    @ColumnInfo(name = "location")
    private String location;
    @ColumnInfo(name = "following")
    private Boolean following;
    @ColumnInfo(name = "blocked")
    private Boolean blocked;


    public ItemDbEntity(Long id, Integer reputation, Long creationDate, Integer userId, String profileImage, String displayName, String location, Boolean following, Boolean blocked) {
        this.id = id;
        this.reputation = reputation;
        this.creationDate = creationDate;
        this.userId = userId;
        this.profileImage = profileImage;
        this.displayName = displayName;
        this.location = location;
        this.following = following;
        this.blocked = blocked;

    }

    public Long getId() {

        return id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getFollowing() {
        return following;
    }

    public void setFollowing(Boolean following) {
        this.following = following;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }
}