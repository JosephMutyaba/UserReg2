package org.pahappa.systems.registrationapp.views;

import org.pahappa.systems.registrationapp.exception.InvalidDateFormatException;
import org.pahappa.systems.registrationapp.exception.InvalidNameException;
import org.pahappa.systems.registrationapp.exception.UsernameAlreadyExistsException;
import org.pahappa.systems.registrationapp.models.Dependant;
import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.DependantService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ManagedBean
@RequestScoped
public class DependantView implements Serializable {
    private final DependantService dependantService = new DependantService();
    private String username;
    private String firstname;
    private String lastname;
    private String dateOfBirth;
    private String gender;
    private User user;
    private Long user_id;

    private Dependant dependant;
    private List<Dependant> dependants;
    private String searchCriteria;
    private String searchValue;

    @PostConstruct
    public void init() {
        dependant = new Dependant();
//        User user = new User();
//        dependants = dependantService.getAllDependantsByUserId();
    }

    public String getUsername() {
        return username;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Dependant getDependant() {
        return dependant;
    }

    public void setDependant(Dependant dependant) {
        this.dependant = dependant;
    }

    public List<Dependant> getDependants() {
        return dependants;
    }

    public void setDependants(List<Dependant> dependants) {
        this.dependants = dependants;
    }

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public void addDependant() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dob = sdf.parse(dateOfBirth);

            dependant.setUsername(username);
            dependant.setFirstname(firstname);
            dependant.setLastname(lastname);
            dependant.setDateOfBirth(dob);
            dependant.setGender(gender);

            // Perform validations
            dependantService.validateUsername(dependant.getUsername());
            dependantService.validateName(dependant.getFirstname(), "First name");
            dependantService.validateName(dependant.getLastname(), "Last name");
            dependantService.validateDateOfBirth(dependant.getDateOfBirth());

            dependantService.addDependant(dependant);
//            dependants = dependantService.getAllDependantsByUserId();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dependant added successfully"));
        } catch (InvalidNameException | InvalidDateFormatException | UsernameAlreadyExistsException | ParseException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
        }
    }

//    public void searchDependants() {
//        switch (searchCriteria) {
//            case "gender":
//                dependants = dependantService.searchDependantsByGender(searchValue);
//                break;
//            case "name":
//                dependants = dependantService.searchDependantsByName(searchValue);
//                break;
//            default:
//                dependants = dependantService.displayAllDependants();
//        }
//    }

    public void deleteDependant(Long id) {
        dependantService.deleteDependantsByUserId(id);
        dependants = dependantService.getAllDependantsByUserId(id);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dependant deleted successfully"));
    }

    public void deleteAllDependants() {
//        dependantService.deleteAllDependants();
//        dependants = dependantService.displayAllDependants();
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("All dependants deleted successfully"));
    }
}
