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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    private String username;
    private String password;
    private User loggedInUser;


    private Long id;
    private String firstname;
    private String lastname;
    private String dateOfBirth;
    private boolean deleted;
    private String role;
    private String email;
//    private String userRole;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
                this.username = loggedInUser.getUsername();
                this.password = loggedInUser.getPassword();
                this.id=loggedInUser.getId();
                this.dateOfBirth=loggedInUser.getDateOfBirth().toString();
                this.deleted=loggedInUser.isDeleted();
                this.firstname=loggedInUser.getFirstname();
                this.lastname=loggedInUser.getLastname();
                this.role=loggedInUser.getRole();
                this.email = loggedInUser.getEmail();

                System.out.println("AdminRole logged in user: " + loggedInUser.getRole());
                return "/pages/displayUsers.xhtml?faces-redirect=true"; // Redirect to admin home page
//                return "pages/newui/adminpages/dashboard.xhtml?faces-redirect=true"; // Redirect to admin home page
            } else {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedInUser", loggedInUser);
                System.out.println("Non-admin user: " + loggedInUser.getUsername());
                return "/pages/userpages/getUserSpecific.xhtml?faces-redirect=true&user=" + loggedInUser.getUsername(); // Redirect to getUser page
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



    public void updateUser() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dob = sdf.parse(dateOfBirth);

            loggedInUser.setFirstname(firstname);
            loggedInUser.setLastname(lastname);
            loggedInUser.setDateOfBirth(dob);
            loggedInUser.setRole(role);
            loggedInUser.setDeleted(deleted);
            loggedInUser.setId(id);
            loggedInUser.setEmail(email);
            loggedInUser.setUsername(username);

            if (!BCrypt.checkpw(password, loggedInUser.getPassword() )) {
                loggedInUser.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            }

            userService.validateName(loggedInUser.getFirstname(), "First name");
            userService.validateName(loggedInUser.getLastname(), "Last name");
            userService.validateDateOfBirth(loggedInUser.getDateOfBirth());

            boolean isUpdated = userService.updateUserOfUsername(loggedInUser);
            if (isUpdated) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User details have been successfully updated!", null));

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Something went wrong, please try again!", null));

            }
        } catch (ParseException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid date format! Please use yyyy-MM-dd.", null));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
        }
    }


}
