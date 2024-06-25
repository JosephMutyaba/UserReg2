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
import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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
        users = userService.displayAllUsers();
//        String usernameFromParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("user");
//        if (usernameFromParam != null) {
//            User foundUser = userService.getUserOfUsername(usernameFromParam);
//            if (foundUser != null) {
//                this.user = foundUser;
//                System.out.println("Found user: " + foundUser);
//                dependants = dependantService.getAllDependantsByUserId(user.getId());
//            }else {
//                System.out.println("User not found-----------------");
//            }
//
//        }
        dependants = dependantService.getAllDependantsByUserId(user.getId());
    }

    // Getters and setters

    public List<User> getUsers() {
        return userService.displayAllUsers();
    }

    public List<Dependant> getDependants() {
        return dependantService.getAllDependantsByUserId(user.getId());
    }

    public User getUser() {
        return user;
    }

//    public void setUser(User user) {
//        this.user = user;
//        dependants = dependantService.getAllDependantsByUserId(user.getId());
//    }
    public void setUser(User user) {
        this.user = user;
        this.username = user.getUsername();
        this.dependants = dependantService.getAllDependantsByUserId(user.getId());
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
        dependants = dependantService.getAllDependantsByUserId(user.getId());
        return "/pages/getUser"; // Navigate to getUser.xhtml
    }

    public String deleteUser() {
        boolean userDeleted = userService.deleteUserOfUsername(username);
        if (userDeleted) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User deleted successfully"));
            users = userService.displayAllUsers();
            return "/pages/displayUsers"; // Navigate to the index page
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User not found"));
            return null;
        }
    }

    public String deleteAllUsers() {
        boolean allUsersDeleted = userService.deleteAllUsers();
        if (allUsersDeleted) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("All users have been successfully deleted!"));
            users = userService.displayAllUsers();
            return "/pages/displayUsers"; // Navigate to the index page
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Deletion unsuccessful, please try again!"));
            return null;
        }
    }

//    public void populateUser(String username) {
//        User foundUser = userService.getUserOfUsername(username);
//        if (foundUser != null) {
//            this.user = foundUser;
//            dependants = dependantService.getAllDependantsByUserId(user.getId());
//            System.out.println("Found user: " + foundUser);
//        } else {
//            System.out.println("User not found-----------------");
//        }
//    }
}














//package org.pahappa.systems.registrationapp.views;
//
//import org.pahappa.systems.registrationapp.models.Dependant;
//import org.pahappa.systems.registrationapp.models.User;
//import org.pahappa.systems.registrationapp.services.DependantService;
//import org.pahappa.systems.registrationapp.services.UserService;
//
//import javax.annotation.PostConstruct;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
//import javax.faces.application.FacesMessage;
//import javax.faces.bean.ViewScoped;
//import javax.faces.context.FacesContext;
//import javax.servlet.http.Cookie;
//import java.io.Serializable;
//import java.text.SimpleDateFormat;
//import java.util.List;
//import java.util.Map;
//
//@ManagedBean
//@SessionScoped
//public class UserView implements Serializable {
//
//    private UserService userService = new UserService();
//    private DependantService dependantService = new DependantService();
//    private List<User> users;
//    private List<Dependant> dependants;
//    private User user = new User();
//    private String username;
//    private String searchCriteria;
//    private String searchValue;
//    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//    @PostConstruct
//    public void init() {
//        dependants = dependantService.getAllDependantsByUserId(user.getId());
//    }
//
//
//    public List<User> getUsers() {
//        return userService.displayAllUsers();
//    }
//
//    public List<Dependant> getDependants() {
//        // Retrieve the user identifier from the cookie
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        Map<String, Object> cookies = facesContext.getExternalContext().getRequestCookieMap();
//        Cookie cookie = (Cookie) cookies.get("selectedUser");
//
//        if (cookie != null) {
//            String username = cookie.getValue();
//            user = userService.getUserOfUsername(username);
//        }
//
//        dependants = dependantService.getAllDependantsByUserId(user.getId());
//
//        return dependants;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//        dependants = dependantService.getAllDependantsByUserId(user.getId());
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getSearchCriteria() {
//        return searchCriteria;
//    }
//
//    public void setSearchCriteria(String searchCriteria) {
//        this.searchCriteria = searchCriteria;
//    }
//
//    public String getSearchValue() {
//        return searchValue;
//    }
//
//    public void setSearchValue(String searchValue) {
//        this.searchValue = searchValue;
//    }
//
//    public void getUserOfUsername() {
//        User foundUser = userService.getUserOfUsername(username);
//        if (foundUser != null) {
//            user = foundUser;
//            dependants = dependantService.getAllDependantsByUserId(user.getId());
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User not found"));
//        }
//    }
//
//    public void deleteAllDependants() {
//        boolean allDeleted = dependantService.deleteDependantsByUserId(user.getId());
//        if (allDeleted) {
//            dependants.clear();
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("All dependants deleted successfully"));
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Failed to delete dependants"));
//        }
//    }
//
//    public void searchDependants() {
//        switch (searchCriteria) {
//            case "gender":
//                dependants = dependantService.searchDependantsByGenderAndUserId(searchValue, user.getId());
//                break;
//            case "username":
//                dependants = dependantService.searchDependantsByUsernameAndUserId(searchValue, user.getId());
//                break;
//            case "firstname":
//                dependants = dependantService.searchDependantsByFirstnameAndUserId(searchValue, user.getId());
//                break;
//            case "lastname":
//                dependants = dependantService.searchDependantsByLastnameAndUserId(searchValue, user.getId());
//                break;
//            default:
//                dependants = dependantService.getAllDependantsByUserId(user.getId());
//        }
//    }
//
//    public String selectUser(User selectedUser) {
//        this.user = selectedUser;
//        this.username = selectedUser.getUsername();
//        return "/pages/getUser"; // Navigate to getUser.xhtml
//    }
//
//    public String deleteUser() {
//        boolean userDeleted = userService.deleteUserOfUsername(username);
//        if (userDeleted) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User deleted successfully"));
//            users=userService.displayAllUsers();
//            return "/pages/displayUsers"; // Navigate to the index page
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User not found"));
//            return null;
//        }
//    }
//
//
//    public String deleteAllUsers() {
//        boolean allUsersDeleted = userService.deleteAllUsers();
//        if (allUsersDeleted) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("All users have been successfully deleted!"));
//            users=userService.displayAllUsers();
//            return "/pages/displayUsers"; // Navigate to the index page
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Deletion unsuccessful, please try again!"));
//            return null;
//        }
//    }
//}
