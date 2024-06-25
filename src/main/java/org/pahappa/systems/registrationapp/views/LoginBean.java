package org.pahappa.systems.registrationapp.views;

import org.mindrot.jbcrypt.BCrypt;
import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.UserService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    private String username;
    private String password;
    private User loggedInUser;

    private UserService userService;

    @PostConstruct
    public void init() {
        userService = new UserService();
        loggedInUser = new User();
    }

    // Getters and setters
    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

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
        System.out.println("Logged in user: " + loggedInUser);

        if (loggedInUser != null && BCrypt.checkpw(password, loggedInUser.getPassword())) {
            System.out.println("Password check passed for user: " + loggedInUser.getUsername());

            if ("ADMIN".equals(loggedInUser.getRole())) {
                System.out.println("AdminRole logged in user: " + loggedInUser.getRole());
                return "/pages/displayUsers.xhtml?faces-redirect=true"; // Redirect to admin home page
            } else {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedInUser", loggedInUser);
                System.out.println("Non-admin user: " + loggedInUser.getUsername());
                return "/pages/getUser.xhtml?faces-redirect=true&user=" + loggedInUser.getUsername(); // Redirect to getUser page

            }
        }

        System.out.println("Login failed for user: " + username);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", "Invalid username or password"));
        return "/login.xhtml?faces-redirect=true"; // Redirect to login page if failed
    }


    public String logout() {
        loggedInUser = null;
        return "/login.xhtml?faces-redirect=true";
    }
}
