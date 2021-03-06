
package com.twilio.video.app.SingelUSerByIDResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("fcm_token")
    @Expose
    private String fcmToken;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("skill")
    @Expose
    private String skill;
    @SerializedName("interests")
    @Expose
    private String interests;
    @SerializedName("class_status")
    @Expose
    private Object classStatus;
    @SerializedName("user_type")
    @Expose
    private Integer userType;
    @SerializedName("last_class_joined")
    @Expose
    private Object lastClassJoined;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("is_admin")
    @Expose
    private Integer isAdmin;
    @SerializedName("can_create_team")
    @Expose
    private Integer canCreateTeam;
    @SerializedName("can_create_skill")
    @Expose
    private Integer canCreateSkill;
    @SerializedName("can_create_class")
    @Expose
    private Integer canCreateClass;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @SerializedName("pro_requested_at")
    @Expose
    private String proRequestedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("private")
    @Expose
    private Integer _private;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("sex")
    @Expose
    private Integer sex;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("cover_path")
    @Expose
    private String coverPath;
    @SerializedName("is_phone_verified")
    @Expose
    private Integer isPhoneVerified;
    @SerializedName("otp_purpose")
    @Expose
    private Integer otpPurpose;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("following")
    @Expose
    private List<Following> following = null;
    @SerializedName("follower")
    @Expose
    private List<Follower> follower = null;

    @SerializedName("rating")
    @Expose
    private String rating;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public Object getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(Object classStatus) {
        this.classStatus = classStatus;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Object getLastClassJoined() {
        return lastClassJoined;
    }

    public void setLastClassJoined(Object lastClassJoined) {
        this.lastClassJoined = lastClassJoined;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getCanCreateTeam() {
        return canCreateTeam;
    }

    public void setCanCreateTeam(Integer canCreateTeam) {
        this.canCreateTeam = canCreateTeam;
    }

    public Integer getCanCreateSkill() {
        return canCreateSkill;
    }

    public void setCanCreateSkill(Integer canCreateSkill) {
        this.canCreateSkill = canCreateSkill;
    }

    public Integer getCanCreateClass() {
        return canCreateClass;
    }

    public void setCanCreateClass(Integer canCreateClass) {
        this.canCreateClass = canCreateClass;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getProRequestedAt() {
        return proRequestedAt;
    }

    public void setProRequestedAt(String proRequestedAt) {
        this.proRequestedAt = proRequestedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getPrivate() {
        return _private;
    }

    public void setPrivate(Integer _private) {
        this._private = _private;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public Integer getIsPhoneVerified() {
        return isPhoneVerified;
    }

    public void setIsPhoneVerified(Integer isPhoneVerified) {
        this.isPhoneVerified = isPhoneVerified;
    }

    public Integer getOtpPurpose() {
        return otpPurpose;
    }

    public void setOtpPurpose(Integer otpPurpose) {
        this.otpPurpose = otpPurpose;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public List<Following> getFollowing() {
        return following;
    }

    public void setFollowing(List<Following> following) {
        this.following = following;
    }

    public List<Follower> getFollower() {
        return follower;
    }

    public void setFollower(List<Follower> follower) {
        this.follower = follower;
    }

}
