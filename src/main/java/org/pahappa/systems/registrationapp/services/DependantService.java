package org.pahappa.systems.registrationapp.services;

import org.pahappa.systems.registrationapp.dao.DependantDAO;
import org.pahappa.systems.registrationapp.exception.InvalidDateFormatException;
import org.pahappa.systems.registrationapp.exception.InvalidNameException;
import org.pahappa.systems.registrationapp.exception.UsernameAlreadyExistsException;
import org.pahappa.systems.registrationapp.models.Dependant;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class DependantService {
    private DependantDAO dependantDAO = new DependantDAO();

    public boolean addDependant(Dependant dependant) throws InvalidNameException, InvalidDateFormatException, UsernameAlreadyExistsException {
        validateDependant(dependant, "register");
        return dependantDAO.addDependant(dependant);
    }

    public List<Dependant> getAllDependantsByUserId(Long userId) {
        return dependantDAO.getAllDependantsByUserId(userId);
    }

    public List<Dependant> searchDependantsByGenderAndUserId(String gender, Long userId) {
        return dependantDAO.searchDependantsByGenderAndUserId(gender, userId);
    }

    public List<Dependant> searchDependantsByUsernameAndUserId(String username, Long userId) {
        return dependantDAO.searchDependantsByUsernameAndUserId(username, userId);
    }

    public List<Dependant> searchDependantsByFirstnameAndUserId(String firstname, Long userId) {
        return dependantDAO.searchDependantsByFirstnameAndUserId(firstname, userId);
    }

    public List<Dependant> searchDependantsByLastnameAndUserId(String lastname, Long userId) {
        return dependantDAO.searchDependantsByLastnameAndUserId(lastname, userId);
    }

    public boolean deleteDependantsByUserId(Long userId) {
        return dependantDAO.deleteDependantsByUserId(userId);
    }

    public void validateDependant(Dependant dependant, String field) throws InvalidNameException, UsernameAlreadyExistsException, InvalidDateFormatException {
        if (field.equals("register")) {
            validateUsername(dependant.getUsername());
        }
        validateName(dependant.getFirstname(), "First name");
        validateName(dependant.getLastname(), "Last name");
        validateDateOfBirth(dependant.getDateOfBirth());
    }

    public void validateUsername(String username) throws InvalidNameException, UsernameAlreadyExistsException {
        if (username == null || username.trim().length() < 4 || !Pattern.matches("[@'a-z]['a-z0-9]+", username.trim())) {
            throw new InvalidNameException("Username cannot be null and should contain [@'a-z]['a-z0-9] and at least 4 characters.");
        }

    }

    public void validateName(String name, String fieldName) throws InvalidNameException {
        if (name == null || !Pattern.matches("[A-Z][a-z]+", name.trim())) {
            throw new InvalidNameException(fieldName + " cannot be null and should be at least 2 characters, should be capitalized.");
        }
    }

    public void validateDateOfBirth(Date dateOfBirth) throws InvalidDateFormatException {
        if (dateOfBirth == null) {
            throw new InvalidDateFormatException("Date of Birth cannot be null.");
        }
        // Additional validation logic if needed
    }
    public void deleteDependantById(Long id) {
        dependantDAO.deleteDependantById(id);
    }

    public boolean updateDependant(Dependant dependant) {
        return dependantDAO.updateDependant(dependant);
    }

    public Dependant getDependantById(Long id) {
        return dependantDAO.getDependantById(id);
    }
}






//package org.pahappa.systems.registrationapp.services;
//
//import org.pahappa.systems.registrationapp.dao.DependantDAO;
//import org.pahappa.systems.registrationapp.exception.InvalidDateFormatException;
//import org.pahappa.systems.registrationapp.exception.InvalidNameException;
//import org.pahappa.systems.registrationapp.exception.UsernameAlreadyExistsException;
//import org.pahappa.systems.registrationapp.models.Dependant;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.Date;
//import java.util.List;
//import java.util.regex.Pattern;
//
//public class DependantService {
//    private final DependantDAO dependantDAO = new DependantDAO();
//
//    public boolean addDependant(Dependant dependant) throws InvalidNameException, InvalidDateFormatException, UsernameAlreadyExistsException {
//        validateDependant(dependant, "register");
//        return dependantDAO.addDependant(dependant);
//    }
//
//    public List<Dependant> displayAllDependants() {
//        return dependantDAO.getAllDependants();
//    }
//
//    public List<Dependant> searchDependantsByGender(String gender) {
//        return dependantDAO.searchDependantsByGender(gender);
//    }
//
//    public List<Dependant> searchDependantsByName(String name) {
//        return dependantDAO.searchDependantsByName(name);
//    }
//
//    public boolean deleteDependant(Long id) {
//        return dependantDAO.deleteDependant(id);
//    }
//
//    public boolean deleteAllDependants() {
//        try {
//            dependantDAO.deleteAllDependants();
//            return true;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return false;
//    }
//
//    private boolean checkIfDateOfBirthIsFuture(Date dateOfBirth) {
//        LocalDate birthDate = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate currentDate = LocalDate.now();
//        return birthDate.isAfter(currentDate);
//    }
//
//    // EXTRA METHODS FOR VALIDATION OF INPUT AT REGISTRATION AND UPDATE
//    public boolean checkIfDependantExists(String username) {
//        // Implement method to check if dependant exists in the database
//        return dependantDAO.searchDependantsByName(username) != null;
//    }
//
//    private void validateDependant(Dependant dependant, String field) throws InvalidNameException, UsernameAlreadyExistsException, InvalidDateFormatException {
//        if (field.equals("register")) {
//            validateUsername(dependant.getUsername());
//        }
//        validateName(dependant.getFirstname(), "First name");
//        validateName(dependant.getLastname(), "Last name");
//        validateDateOfBirth(dependant.getDateOfBirth());
//    }
//
//    public void validateUsername(String username) throws InvalidNameException, UsernameAlreadyExistsException {
//        if (username == null || username.trim().length() < 4 || !Pattern.matches("[@'a-z]['a-z0-9]+", username.trim())) {
//            throw new InvalidNameException("Username cannot be null and should contain [@'a-z]['a-z0-9] and at least 4 characters.");
//        }
//        if (checkIfDependantExists(username.trim())) {
//            throw new UsernameAlreadyExistsException("That username is already in use. Try a different username.");
//        }
//    }
//
//    public void validateName(String name, String fieldName) throws InvalidNameException {
//        if (name == null || !Pattern.matches("[A-Z][a-z]+", name.trim())) {
//            throw new InvalidNameException(fieldName + " cannot be null and should be at least 2 characters, should be capitalised.");
//        }
//    }
//
//    public void validateDateOfBirth(Date dateOfBirth) throws InvalidDateFormatException {
//        if (dateOfBirth == null) {
//            throw new InvalidDateFormatException("Date of Birth cannot be null.");
//        }
//        if (checkIfDateOfBirthIsFuture(dateOfBirth)) {
//            throw new InvalidDateFormatException("Date of Birth cannot be a future date.");
//        }
//    }
//
//    public Date parseDateOfBirth(String dateOfBirth) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        return sdf.parse(dateOfBirth);
//    }
//
//    public void dateSyntax(String date) throws InvalidDateFormatException {
//        if (!(Pattern.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}", date.trim()))) {
//            throw new InvalidDateFormatException("Strictly follow the date format shown");
//        }
//    }
//
//
//    public List<Dependant> searchDependantsByGenderAndUserId(String gender, Long userId) {
//        return dependantDAO.searchDependantsByGenderAndUserId(gender, userId);
//    }
//    public List<Dependant> searchDependantsByUsernameAndUserId(String username, Long userId) {
//        return dependantDAO.searchDependantsByUsernameAndUserId(username, userId);
//    }
//
//    public List<Dependant> searchDependantsByFirstnameAndUserId(String firstname, Long userId) {
//        return dependantDAO.searchDependantsByFirstnameAndUserId(firstname, userId);
//    }
//
//    public List<Dependant> searchDependantsByLastnameAndUserId(String lastname, Long userId) {
//        return dependantDAO.searchDependantsByLastnameAndUserId(lastname, userId);
//    }
//
//
//}
