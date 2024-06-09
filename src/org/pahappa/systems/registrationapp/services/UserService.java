package org.pahappa.systems.registrationapp.services;

import org.pahappa.systems.registrationapp.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserService {
    private final List<User> users = new ArrayList<>();

    public boolean registerUser(User user) {
        try {
            users.add(user);
            return true;
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<User> displayAllUsers() {
        return users;
    }

    public User getUserOfUsername(String username) {
        for (User user : users) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public boolean deleteAllUsers() {
        try {
            users.clear();
            return true;
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteUserOfUsername(String username) {
        for (User user : users) {
            if(user.getUsername().equals(username)){
                users.remove(user);
                return true;
            }
        }
        return false;
    }

    public boolean updateUserOfUsername(String userName, String firstName, String lastName, Date dateOfBirth ) {
        for (User user : users) {
            if(user.getUsername().equals(userName)){
                user.setFirstname(firstName);
                user.setLastname(lastName);
                user.setDateOfBirth(dateOfBirth);
                return true;
            }
        }
        return false;
    }

    // Validate User
    public boolean checkIfUserExists(String username) {
        for (User user : users) {
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
}
