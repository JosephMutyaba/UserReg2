package org.pahappa.systems.registrationapp.services;

import org.pahappa.systems.registrationapp.dao.UserDAO;
import org.pahappa.systems.registrationapp.exception.InvalidDateFormatException;
import org.pahappa.systems.registrationapp.exception.InvalidNameException;
import org.pahappa.systems.registrationapp.exception.UsernameAlreadyExistsException;
import org.pahappa.systems.registrationapp.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class UserService {
    private final UserDAO usrDAO = new UserDAO();

    public boolean registerUser(User person) throws InvalidNameException, InvalidDateFormatException, UsernameAlreadyExistsException {
        validateUser(person, "register");
        return usrDAO.registerUser(person);
    }

    public List<User> displayAllUsers() {
        return usrDAO.displayAllUsers();
    }

    public User getUserOfUsername(String username) {
        return usrDAO.getUserOfUsername(username);
    }

    public boolean updateUserOfUsername(User user) throws InvalidNameException, InvalidDateFormatException, UsernameAlreadyExistsException {
        validateUser(user,"update");
        return usrDAO.updateUserOfUsername(user);
    }

    public boolean deleteUserOfUsername(String username) {

        return usrDAO.deleteUserOfUsername(username);
    }

    public boolean deleteAllUsers() {
        try {
            usrDAO.deleteAllUsers();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private boolean checkIfDateOfBirthIsFuture(Date dateOfBirth) {
        LocalDate birthDate = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();

        return birthDate.isAfter(currentDate);
    }


    // EXTRA METHODS FOR VALIDATION OF INPUT AT REGISTRATION AND UPDATE
    public boolean checkIfUserExists(String username) {
        // Implement method to check if user exists in the database
        return usrDAO.getUserOfUsername(username) != null;
    }

    private void validateUser(User user, String field) throws InvalidNameException, UsernameAlreadyExistsException, InvalidDateFormatException {
        if (field.equals("register")) {
            validateUsername(user.getUsername());
        }
        validateName(user.getFirstname(), "First name");
        validateName(user.getLastname(), "Last name");
        validateDateOfBirth(user.getDateOfBirth());
    }

    public void validateUsername(String username) throws InvalidNameException, UsernameAlreadyExistsException {
        if (username == null || username.trim().length() < 4 || !Pattern.matches("[@'a-z]['a-z0-9]+", username.trim())) {
            throw new InvalidNameException("Username cannot be null and should contain [@'a-z]['a-z0-9] and at least 4 characters.");
        }
        if (checkIfUserExists(username.trim())) {
            throw new UsernameAlreadyExistsException("That username is already in use. Try a different username.");
        }
    }

    public void validateName(String name, String fieldName) throws InvalidNameException {
        if (name == null || !Pattern.matches("[A-Z][a-z]+", name.trim())) {
            throw new InvalidNameException(fieldName + "cannot be null and should be at least 2 characters, should be capitalised.");
        }
    }

    public void validateDateOfBirth(Date dateOfBirth) throws InvalidDateFormatException {
        if (dateOfBirth == null) {
            throw new InvalidDateFormatException("Date of Birth cannot be null.");
        }
        if (checkIfDateOfBirthIsFuture(dateOfBirth)) {
            throw new InvalidDateFormatException("Date of Birth cannot be a future date.");
        }
    }

    public Date parseDateOfBirth(String dateOfBirth) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(dateOfBirth);
    }

    public void dateSyntax(String date) throws InvalidDateFormatException {
        if(!(Pattern.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}",date.trim()))){
            throw new InvalidDateFormatException("Strictly follow the date format shown");
        }
    }

}