package org.pahappa.systems.registrationapp.views;

import org.pahappa.systems.registrationapp.models.Dependant;
import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.DependantService;
import org.pahappa.systems.registrationapp.services.UserService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

@ManagedBean
@SessionScoped
public class UserView implements Serializable {

    private UserService userService = new UserService();
    private DependantService dependantService = new DependantService();
    private List<User> users;
    private List<Dependant> dependants;
    private User user = new User();
    private String username;
    private String searchCriteria;
    private String searchValue;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @PostConstruct
    public void init() {
        dependants = dependantService.getAllDependantsByUserId(user.getId());
    }

    public List<User> getUsers() {
        return userService.displayAllUsers();
    }

    public List<Dependant> getDependants() {
        return dependants;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        dependants = dependantService.getAllDependantsByUserId(user.getId());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void getUserOfUsername() {
        User foundUser = userService.getUserOfUsername(username);
        if (foundUser != null) {
            user = foundUser;
            dependants = dependantService.getAllDependantsByUserId(user.getId());
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User not found"));
        }
    }

    public void deleteAllDependants() {
        boolean allDeleted = dependantService.deleteDependantsByUserId(user.getId());
        if (allDeleted) {
            dependants.clear();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("All dependants deleted successfully"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Failed to delete dependants"));
        }
    }

    public void searchDependants() {
        switch (searchCriteria) {
            case "gender":
                dependants = dependantService.searchDependantsByGenderAndUserId(searchValue, user.getId());
                break;
            case "username":
                dependants = dependantService.searchDependantsByUsernameAndUserId(searchValue, user.getId());
                break;
            case "firstname":
                dependants = dependantService.searchDependantsByFirstnameAndUserId(searchValue, user.getId());
                break;
            case "lastname":
                dependants = dependantService.searchDependantsByLastnameAndUserId(searchValue, user.getId());
                break;
            default:
                dependants = dependantService.getAllDependantsByUserId(user.getId());
        }
    }

    public String selectUser(User selectedUser) {
        this.user = selectedUser;
        this.username = selectedUser.getUsername();
        return "getUser?faces-redirect=true"; // Navigate to getUser.xhtml
    }

    public String deleteUser() {
        boolean userDeleted = userService.deleteUserOfUsername(username);
        if (userDeleted) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User deleted successfully"));
            users=userService.displayAllUsers();
            return "index"; // Navigate to the index page
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User not found"));
            return null;
        }
    }


    public String deleteAllUsers() {
        boolean allUsersDeleted = userService.deleteAllUsers();
        if (allUsersDeleted) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("All users have been successfully deleted!"));
            users=userService.displayAllUsers();
            return "index"; // Navigate to the index page
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Deletion unsuccessful, please try again!"));
            return null;
        }
    }
}
