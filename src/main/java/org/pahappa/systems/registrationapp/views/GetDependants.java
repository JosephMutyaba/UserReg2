package org.pahappa.systems.registrationapp.views;

import org.pahappa.systems.registrationapp.models.Dependant;
import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.DependantService;
import org.pahappa.systems.registrationapp.services.UserService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ManagedBean
@RequestScoped
public class GetDependants implements Serializable {
    private UserService userService = new UserService();
    private DependantService dependantService = new DependantService();
    private List<User> users;
    private List<Dependant> dependants;
    private User user = new User();
    private String username;
    private String searchCriteria;
    private String searchValue;

    @PostConstruct
    public void init() {
        dependants = dependantService.getAllDependantsByUserId(user.getId());
    }


    public List<User> getUsers() {
        return userService.displayAllUsers();
    }

    public List<Dependant> getDependants() {
        // Retrieve the user identifier from the cookie
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, Object> cookies = facesContext.getExternalContext().getRequestCookieMap();
        Cookie cookie = (Cookie) cookies.get("selectedUser");

        if (cookie != null) {
            String username = cookie.getValue();
            user = userService.getUserOfUsername(username);
        }

        dependants = dependantService.getAllDependantsByUserId(user.getId());
        System.out.println("Dependants: " + dependants);
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

}
