package org.pahappa.systems.registrationapp.views;

import org.pahappa.systems.registrationapp.exception.InvalidDateFormatException;
import org.pahappa.systems.registrationapp.exception.InvalidNameException;
import org.pahappa.systems.registrationapp.exception.UsernameAlreadyExistsException;
import org.pahappa.systems.registrationapp.models.Dependant;
import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.DependantService;
import org.pahappa.systems.registrationapp.services.UserService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;
import javax.inject.Inject;
import javax.inject.Qualifier;
import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@ManagedBean(name = "addDependantView")
@SessionScoped
public class AddDependantView implements Serializable {
    private User user;
    private String username;
    private String firstname;
    private String user_username;

    private String lastname;
    private Date dateOfBirth;
    private String gender;
    private Long user_id;

    DependantService dependantService = new DependantService();
    UserService userService = new UserService();

    public AddDependantView() {}


    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Date getToday() {
        return new Date();
    }

    @PostConstruct
    public void init() {

    }

    public String addDependant(User selectedUser) {
        try {
            System.out.println("addDependant called"); // Debug statement
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Date dob = sdf.parse(dateOfBirth);

            Dependant dependant = new Dependant();
            dependant.setUsername(username);
            dependant.setFirstname(firstname);
            dependant.setLastname(lastname);
            dependant.setDateOfBirth(dateOfBirth);
            dependant.setGender(gender);
            dependant.setUser(selectedUser); // Set the user retrieved from the cookie

            // Perform validations
            dependantService.validateUsername(dependant.getUsername());
            dependantService.validateName(dependant.getFirstname(), "First name");
            dependantService.validateName(dependant.getLastname(), "Last name");
            dependantService.validateDateOfBirth(dependant.getDateOfBirth());

            dependantService.addDependant(dependant);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dependant added successfully"));

//            System.out.println("Dependant added successfully: " + user.getUsername()); // Debug statement

            return "/pages/getUser";


        } catch (InvalidNameException | InvalidDateFormatException | UsernameAlreadyExistsException e) {
            System.out.println("Exception: " + e.getMessage()); // Debug statement
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));

            return null;
        }
    }


    public void addDependantUser(User passedInUser) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            System.out.println("addDependant called"); // Debug statement

            Dependant dependant = new Dependant();
            dependant.setUsername(username);
            dependant.setFirstname(firstname);
            dependant.setLastname(lastname);
            dependant.setDateOfBirth(dateOfBirth);
            dependant.setGender(gender);
            dependant.setUser(passedInUser); // Set the user retrieved from the cookie

            // Perform validations
            dependantService.validateUsername(dependant.getUsername());
            dependantService.validateName(dependant.getFirstname(), "First name");
            dependantService.validateName(dependant.getLastname(), "Last name");
            dependantService.validateDateOfBirth(dependant.getDateOfBirth());

            dependantService.addDependant(dependant);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dependant added successfully"));


//            return "/pages/userpages/dependants";


        } catch (InvalidNameException | InvalidDateFormatException | UsernameAlreadyExistsException e) {
            System.out.println("Exception: " + e.getMessage()); // Debug statement
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            context.validationFailed(); // Mark validation as failed

//            return null;
        }finally {
            this.username=null;
            this.firstname=null;
            this.lastname=null;
            this.dateOfBirth=null;
            this.gender=null;
        }
    }
    public void addDependantAdmin() {
        try {
            Dependant dependant = new Dependant();
            dependant.setUsername(username);
            dependant.setFirstname(firstname);
            dependant.setLastname(lastname);
            dependant.setDateOfBirth(dateOfBirth);
            dependant.setGender(gender);

            User associated_user = userService.getUserOfUsername(user_username);

            dependant.setUser(associated_user); // Set the user retrieved from the cookie

            // Perform validations
            dependantService.validateUsername(dependant.getUsername());
            dependantService.validateName(dependant.getFirstname(), "First name");
            dependantService.validateName(dependant.getLastname(), "Last name");
            dependantService.validateDateOfBirth(dependant.getDateOfBirth());

            dependantService.addDependant(dependant);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dependant added successfully"));

//            System.out.println("Dependant added successfully: " + user.getUsername()); // Debug statement

//            return "/pages/dependants.xhtml";


        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage()); // Debug statement
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));

//            return null;
        }
    }


    public String selectUser(User selectedUser) {
        try {
            this.user = userService.getUserOfUsername(selectedUser.getUsername());
            this.user_id = this.user.getId(); // Assuming user.getId() exists
            System.out.println("user role: " + this.user.getRole());

            return "/pages/addDependant";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error selecting user", null));
            return null; // Handle error case appropriately
        }
    }


    public String selectUser_User(User selectedUser) {
        try {
            this.user = userService.getUserOfUsername(selectedUser.getUsername());
            this.user_id = this.user.getId(); // Assuming user.getId() exists
            this.user_username=selectedUser.getUsername();
            System.out.println("user role: " + this.user.getRole());

            return "/pages/userpages/addDependantUser";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error selecting user", null));
            return null; // Handle error case appropriately
        }
    }

    public void clearData() {
        this.username=null;
        this.firstname=null;
        this.lastname=null;
        this.dateOfBirth=null;
        this.gender=null;
    }
}
