package org.pahappa.systems.registrationapp.views;

import org.mindrot.jbcrypt.BCrypt;
import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.UserService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ManagedBean
@SessionScoped
public class UpdateUser implements Serializable {

    private final UserService userService = new UserService();
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String dateOfBirth;
    private boolean deleted;
    private String role;
    private String password;
    private String email;

    private User user;

    public UpdateUser() {
    }

    @PostConstruct
    public void init() {
        user = new User();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
            user.setRole(role);
//            user.setPassword(password);
            user.setDeleted(deleted);
            user.setId(id);
            user.setEmail(email);
            user.setUsername(username);

            if (!BCrypt.checkpw(password, user.getPassword() )) {
                user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            }

            userService.validateName(user.getFirstname(), "First name");
            userService.validateName(user.getLastname(), "Last name");
            userService.validateDateOfBirth(user.getDateOfBirth());

            boolean isUpdated = userService.updateUserOfUsername(user);
            if (isUpdated) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User details have been successfully updated!", null));
                clearForm();
                return "/pages/displayUsers"; // Navigate to the index page
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

    public String selectUser(User selectedUser) {
        this.user = selectedUser;

        this.id = selectedUser.getId();
        this.username = selectedUser.getUsername();
        this.firstname = selectedUser.getFirstname();
        this.lastname = selectedUser.getLastname();
        this.email = selectedUser.getEmail();
        this.role = selectedUser.getRole();
        this.deleted = selectedUser.isDeleted();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.dateOfBirth = sdf.format(selectedUser.getDateOfBirth());

        return "updateUser"; // Should match the outcome in faces-config.xml
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
