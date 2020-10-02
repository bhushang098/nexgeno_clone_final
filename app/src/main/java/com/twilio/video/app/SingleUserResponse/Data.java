
package com.twilio.video.app.SingleUserResponse;

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
    @SerializedName("location")
    @Expose
    private Object location;
    @SerializedName("skill")
    @Expose
    private String skill;
    @SerializedName("interests")
    @Expose
    private String interests;
    @SerializedName("class_status")
    @Expose
    private String classStatus;
    @SerializedName("user_type")
    @Expose
    private Integer userType;
    @SerializedName("last_class_joined")
    @Expose
    private Integer lastClassJoined;
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
    private Object birthday;
    @SerializedName("sex")
    @Expose
    private Integer sex;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("bio")
    @Expose
    private Object bio;
    @SerializedName("profile_path")
    @Expose
    private Object profilePath;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("cover_path")
    @Expose
    private Object coverPath;
    @SerializedName("is_phone_verified")
    @Expose
    private Integer isPhoneVerified;
    @SerializedName("otp_purpose")
    @Expose
    private Integer otpPurpose;
    @SerializedName("gender")
    @Expose
    private Integer gender;

    @SerializedName("is_hr")
    @Expose
    private Integer is_hr;

    public Integer get_private() {
        return _private;
    }

    public void set_private(Integer _private) {
        this._private = _private;
    }

    public Integer getIs_hr() {
        return is_hr;
    }

    public void setIs_hr(Integer is_hr) {
        this.is_hr = is_hr;
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

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
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

    public String getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(String classStatus) {
        this.classStatus = classStatus;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getLastClassJoined() {
        return lastClassJoined;
    }

    public void setLastClassJoined(Integer lastClassJoined) {
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

    public Object getBirthday() {
        return birthday;
    }

    public void setBirthday(Object birthday) {
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

    public Object getBio() {
        return bio;
    }

    public void setBio(Object bio) {
        this.bio = bio;
    }

    public Object getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(Object profilePath) {
        this.profilePath = profilePath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(Object coverPath) {
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

}
