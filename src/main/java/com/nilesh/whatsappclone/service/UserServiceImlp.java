package com.nilesh.whatsappclone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.nilesh.whatsappclone.config.TokenProvider;
import com.nilesh.whatsappclone.expection.UserException;
import com.nilesh.whatsappclone.model.User;
import com.nilesh.whatsappclone.repository.UserRepository;
import com.nilesh.whatsappclone.request.UpdateUserRequest;

@Service
public class UserServiceImlp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public User findUserById(Integer id) throws UserException {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent())
            return optional.get();
        throw new UserException("User not found with id " + id);
    }

    @Override
    public User findUserByProfile(String jwt) throws UserException {
        String email = tokenProvider.getEmailFromToken(jwt);
        if (email == null)
            throw new BadCredentialsException("Recieved token invalid...");
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new UserException("user not found with email " + email);
        return user;

    }

    @Override
    public User updateUser(Integer id, UpdateUserRequest req) throws UserException {
        User user = findUserById(id);
        if (req.getFull_name() != null)
            user.setFull_name(req.getFull_name());
        if (req.getProfile_picture() != null)
            user.setProfile_picture(req.getProfile_picture());
        userRepository.save(user);
        return null;

    }

    @Override
    public List<User> searchUser(String query) {
        List<User> searchUsers = userRepository.searchUsers(query);
        return searchUsers;
    }

}
