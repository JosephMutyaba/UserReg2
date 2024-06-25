package org.pahappa.systems.registrationapp.views;

import org.mindrot.jbcrypt.BCrypt;
import org.pahappa.systems.registrationapp.models.Dependant;
import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.DependantService;
import org.pahappa.systems.registrationapp.services.UserService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    private String username;
    private String password;
    private User loggedInUser;

    private List<Dependant> dependants;
    DependantService dependantService = new DependantService();

    private String searchCriteria;
    private String searchValue;

    private UserService userService;

    @PostConstruct
    public void init() {
        userService = new UserService();
        loggedInUser = new User();
//        userView = new UserView(); // Initialize userView here
    }

    // Getters and setters


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

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

//    public UserView getUserView() {
//        return userView;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public boolean isAdmin() {
        return loggedInUser != null && "ADMIN".equals(loggedInUser.getRole());
    }

    public String login() {
        loggedInUser = userService.getUserOfUsername(username);
        if (loggedInUser != null && BCrypt.checkpw(password, loggedInUser.getPassword())) {
            System.out.println("Logged in user: " + loggedInUser);
            if ("ADMIN".equals(loggedInUser.getRole())) {
                System.out.println("AdminRole logged in user: " + loggedInUser.getRole());
                return "/pages/displayUsers.xhtml?faces-redirect=true"; // Redirect to admin home page
            } else {
                return "/pages/getUser.xhtml"; // Redirect to getUser page
            }
        }
        return "/login.xhtml?faces-redirect=true"; // Redirect to login page if failed
    }

    public String logout() {
        loggedInUser = null;
        return "/login.xhtml?faces-redirect=true";
    }



    public void getUserAndDependants() {
        dependants = dependantService.getAllDependantsByUserId(loggedInUser.getId());
    }

    public void searchDependants() {
        switch (searchCriteria) {
            case "gender":
                dependants = dependantService.searchDependantsByGenderAndUserId(searchValue, loggedInUser.getId());
                break;
            case "username":
                dependants = dependantService.searchDependantsByUsernameAndUserId(searchValue, loggedInUser.getId());
                break;
            case "firstname":
                dependants = dependantService.searchDependantsByFirstnameAndUserId(searchValue, loggedInUser.getId());
                break;
            case "lastname":
                dependants = dependantService.searchDependantsByLastnameAndUserId(searchValue, loggedInUser.getId());
                break;
            default:
                dependants = dependantService.getAllDependantsByUserId(loggedInUser.getId());
                break;
        }
    }
}
