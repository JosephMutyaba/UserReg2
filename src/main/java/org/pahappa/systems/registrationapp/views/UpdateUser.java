package org.pahappa.systems.registrationapp.views;

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

@ManagedBean
@SessionScoped
public class UpdateUser implements Serializable {

    private final UserService userService = new UserService();
    private String username;
    private String firstname;
    private String lastname;
    private String dateOfBirth;

    private User user;

    @PostConstruct
    public void init() {
        user = new User();
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void getUserOfUsername() {
        try {
            user = userService.getUserOfUsername(username);
            if (user == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "User not found!", null));
                clearForm();
            } else {
                firstname = user.getFirstname();
                lastname = user.getLastname();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                dateOfBirth = sdf.format(user.getDateOfBirth());
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error fetching user details!", e.getMessage()));
        }
    }

    public String updateUser() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dob = sdf.parse(dateOfBirth);

            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setDateOfBirth(dob);

            userService.validateName(user.getFirstname(), "First name");
            userService.validateName(user.getLastname(), "Last name");
            userService.validateDateOfBirth(user.getDateOfBirth());

            boolean isUpdated = userService.updateUserOfUsername(user);
            if (isUpdated) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User details have been successfully updated!", null));
                clearForm();
                return "index"; // Navigate to the index page
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Something went wrong, please try again!", null));
                return null;
            }
        } catch (ParseException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid date format! Please use yyyy-MM-dd.", null));
            return null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            return null;
        }
    }

    private void clearForm() {
        firstname = null;
        lastname = null;
        dateOfBirth = null;
    }
}








//
//package org.pahappa.systems.registrationapp.views;
//
//import org.pahappa.systems.registrationapp.models.User;
//import org.pahappa.systems.registrationapp.services.UserService;
//
//import javax.annotation.PostConstruct;
//import javax.faces.application.FacesMessage;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
//import javax.faces.context.FacesContext;
//import java.io.Serializable;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@ManagedBean
//@SessionScoped
//public class UpdateUser implements Serializable {
//
//    private final UserService userService = new UserService();
//    private String username;
//    private String firstname;
//    private String lastname;
//    private String dateOfBirth;
//
//    private User user;
//
//    @PostConstruct
//    public void init() {
//        user = new User();
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
//    public String getFirstname() {
//        return firstname;
//    }
//
//    public void setFirstname(String firstname) {
//        this.firstname = firstname;
//    }
//
//    public String getLastname() {
//        return lastname;
//    }
//
//    public void setLastname(String lastname) {
//        this.lastname = lastname;
//    }
//
//    public String getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public void setDateOfBirth(String dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public void getUserOfUsername() {
//        try {
//            user = userService.getUserOfUsername(username);
//            if (user == null) {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "User not found!", null));
//                clearForm();
//            } else {
//                firstname = user.getFirstname();
//                lastname = user.getLastname();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                dateOfBirth = sdf.format(user.getDateOfBirth());
//            }
//        } catch (Exception e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error fetching user details!", e.getMessage()));
//        }
//    }
//
//    public String updateUser() {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Date dob = sdf.parse(dateOfBirth);
//
//            user.setFirstname(firstname);
//            user.setLastname(lastname);
//            user.setDateOfBirth(dob);
//
//            userService.validateName(user.getFirstname(), "First name");
//            userService.validateName(user.getLastname(), "Last name");
//            userService.validateDateOfBirth(user.getDateOfBirth());
//
//            boolean isUpdated = userService.updateUserOfUsername(user);
//            if (isUpdated) {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User details have been successfully updated!", null));
//                clearForm();
//                return "index"; // Navigate to the index page
//            } else {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Something went wrong, please try again!", null));
//                return null;
//            }
//        } catch (Exception e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
//            return null;
//        }
//    }
//
//    private void clearForm() {
//        firstname = null;
//        lastname = null;
//        dateOfBirth = null;
//    }
//}
//
