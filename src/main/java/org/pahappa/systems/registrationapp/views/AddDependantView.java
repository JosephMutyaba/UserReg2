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
    private String lastname;
    private String dateOfBirth;
    private String gender;
    private Long user_id;

    DependantService dependantService = new DependantService();
    UserService userService = new UserService();

    public AddDependantView() {}

    @ManagedProperty(value = "#{userView}")
    private UserView userView;

    public void setUserView(UserView userView) {
        this.userView = userView;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
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

    @PostConstruct
    public void init() {
        user = new User();
        userView = new UserView();

        // Retrieve the user identifier from the cookie
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, Object> cookies = facesContext.getExternalContext().getRequestCookieMap();
        Cookie cookie = (Cookie) cookies.get("selectedUser");

        if (cookie != null) {
            String username = cookie.getValue();
            user = userService.getUserOfUsername(username);
            user_id = user.getId(); // Assuming user.getId() exists
        }
    }

    public String addDependant() {
        try {
            System.out.println("addDependant called"); // Debug statement
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dob = sdf.parse(dateOfBirth);

            Dependant dependant = new Dependant();
            dependant.setUsername(username);
            dependant.setFirstname(firstname);
            dependant.setLastname(lastname);
            dependant.setDateOfBirth(dob);
            dependant.setGender(gender);
            dependant.setUser(user); // Set the user retrieved from the cookie

            // Perform validations
            dependantService.validateUsername(dependant.getUsername());
            dependantService.validateName(dependant.getFirstname(), "First name");
            dependantService.validateName(dependant.getLastname(), "Last name");
            dependantService.validateDateOfBirth(dependant.getDateOfBirth());

            dependantService.addDependant(dependant);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dependant added successfully"));

            System.out.println("Dependant added successfully: " + user.getUsername()); // Debug statement

            return "/pages/getUser";


        } catch (InvalidNameException | InvalidDateFormatException | UsernameAlreadyExistsException | ParseException e) {
            System.out.println("Exception: " + e.getMessage()); // Debug statement
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));

            return "/pages/getUser";
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
}
