package com.nilesh.whatsappclone.service;
import java.util.List;

import com.nilesh.whatsappclone.expection.UserException;
import com.nilesh.whatsappclone.model.User;
import com.nilesh.whatsappclone.request.UpdateUserRequest;

public interface UserService {
    public User findUserById(Integer id) throws UserException;
    public User findUserByProfile(String jwt) throws UserException;
    public User updateUser(Integer id, UpdateUserRequest req) throws UserException; 
    public List<User> searchUser(String query);

}
