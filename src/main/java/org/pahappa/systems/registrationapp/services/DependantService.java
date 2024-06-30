package org.pahappa.systems.registrationapp.services;

import org.pahappa.systems.registrationapp.dao.DependantDAO;
import org.pahappa.systems.registrationapp.exception.InvalidDateFormatException;
import org.pahappa.systems.registrationapp.exception.InvalidNameException;
import org.pahappa.systems.registrationapp.exception.UsernameAlreadyExistsException;
import org.pahappa.systems.registrationapp.models.Dependant;

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

    public List<Dependant> getAllDependants() {
        return dependantDAO.getAllDependants();
    }



    public boolean deleteAllDependantsFromDb(){
        return dependantDAO.deleteAllDependantsFromDb();
    }

    public List<Dependant> findDependantsByUserAndGender(Long selectedUserId, String selectedGender) {
        return dependantDAO.findDependantsByUserAndGender(selectedUserId, selectedGender);
    }

    public List<Dependant> findDependantsByUser(Long selectedUserId) {
        return dependantDAO.findDependantsByUser(selectedUserId);
    }

    public List<Dependant> findDependantsByGender(String selectedGender) {
        return dependantDAO.findDependantsByGender(selectedGender);
    }

    public List<Dependant> getUsersRegisteredOnADay(Date date) {
        return dependantDAO.getUsersRegisteredOnADay(date);
    }

    public List<Dependant> findDependantsByGenderAndUserId(Long userId, String selectedGender) {
        return dependantDAO.findDependantsByGenderAndUserId(userId,selectedGender);
    }

    public List<Dependant> getDependantsRegisteredOnADayAndOfUserId(Date dateOfReg, Long userId) {
        return dependantDAO.getDependantsRegisteredOnADayAndOfUserId(dateOfReg, userId);
    }
}
