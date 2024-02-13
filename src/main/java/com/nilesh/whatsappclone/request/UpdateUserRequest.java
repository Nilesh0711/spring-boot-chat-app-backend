package com.nilesh.whatsappclone.request;

public class UpdateUserRequest {
    private String full_name;
    private String profile_picture;
    private String about;

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(String full_name, String profile_picture) {
        this.full_name = full_name;
        this.profile_picture = profile_picture;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    @Override
    public String toString() {
        return "UpdateUserRequest [full_name=" + full_name + ", profile_picture=" + profile_picture + "]";
    }

}
