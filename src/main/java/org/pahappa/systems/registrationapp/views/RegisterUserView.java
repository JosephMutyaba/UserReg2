package org.pahappa.systems.registrationapp.views;

import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.UserService;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@ManagedBean
@ViewScoped
public class RegisterUserView implements Serializable {

    private final UserService userService = new UserService();
    private String username;
    private String firstname;
    private String lastname;
    private Date dateOfBirth;
    private String email;
    private String password;
    private String gender;
    // default in user model: user_role="USER", deleted=false;
    private User user;

    public RegisterUserView() {
    }

    @PostConstruct
    public void init() {
        user = new User();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getToday() {
        return new Date();
    }

    public String registerUser() {
        try {

//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Date dob = sdf.parse(dateOfBirth);

            // Set user properties from the managed bean fields
            user.setUsername(username);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setDateOfBirth(dateOfBirth);
            user.setEmail(email);
            user.setPassword(password);
            user.setGender(gender);

            // Perform validations
            userService.validateUsername(user.getUsername());
            userService.validateName(user.getFirstname(), "First name");
            userService.validateName(user.getLastname(), "Last name");
            userService.validateDateOfBirth(user.getDateOfBirth());

            // Register user
            boolean isRegistered = userService.registerUser(user);
            if (isRegistered) {

//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successfully registered!"));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User registered successfully"));
                PrimeFaces.current().ajax().update("registerForm:messages");
                user = new User(); // Reset form
                return "/pages/displayUsers"; // Navigate to the index page
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Something went wrong, please try again!"));
                PrimeFaces.current().ajax().update("registerForm:messages");
                return null;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            PrimeFaces.current().ajax().update("registerForm:messages");
            return null;
        }
    }


    public String registerNewUser() {
        try {

//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Date dob = sdf.parse(dateOfBirth);

            // Set user properties from the managed bean fields
            user.setUsername(username);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setDateOfBirth(dateOfBirth);
            user.setEmail(email);
            user.setPassword(password);
            user.setGender(gender);

            // Perform validations
            userService.validateUsername(user.getUsername());
            userService.validateName(user.getFirstname(), "First name");
            userService.validateName(user.getLastname(), "Last name");
            userService.validateDateOfBirth(user.getDateOfBirth());

            // Register user
            boolean isRegistered = userService.registerUser(user);
            if (isRegistered) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successfully registered! Now login into your account"));
                user = new User(); // Reset form
                return "/login.xhtml"; // Navigate to the login page
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Something went wrong, please try again!"));
                return null;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

}
