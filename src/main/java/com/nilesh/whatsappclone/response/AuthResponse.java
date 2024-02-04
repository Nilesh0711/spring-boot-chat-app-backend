package com.nilesh.whatsappclone.response;

public class AuthResponse {
    private String jwt;
    private boolean isAuth;

    public AuthResponse() {
    }

    public AuthResponse(String jwt, boolean isAuth) {
        this.jwt = jwt;
        this.isAuth = isAuth;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public boolean getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(boolean isAuth) {
        this.isAuth = isAuth;
    }

    @Override
    public String toString() {
        return "AuthResponse [jwt=" + jwt + ", isAuth=" + isAuth + "]";
    }

}
