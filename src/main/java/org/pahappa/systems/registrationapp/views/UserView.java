package org.pahappa.systems.registrationapp.views;

import org.pahappa.systems.registrationapp.models.Dependant;
import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.DependantService;
import org.pahappa.systems.registrationapp.services.UserService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class UserView implements Serializable {

    private UserService userService = new UserService();
    private DependantService dependantService = new DependantService();
    private List<User> users;
    private List<Dependant> dependants;
    private User user = new User();
    private String username;
    private String gender;
    private String searchCriteria;
    private String searchValue;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");




    ///
    private int maleUserCount;
    private int femaleUserCount;
    private String maleFemaleRatio;




    @PostConstruct
    public void init() {
        users = userService.displayAllUsers();
        dependants = dependantService.getAllDependantsByUserId(user.getId());

        // Compute counts and ratios
        computeStatistics();
    }


    public int getMaleUserCount() {
        computeStatistics();
        return maleUserCount;
    }

    public void setMaleUserCount(int maleUserCount) {
        this.maleUserCount = maleUserCount;
    }

    public int getFemaleUserCount() {
        computeStatistics();
        return femaleUserCount;
    }

    public void setFemaleUserCount(int femaleUserCount) {
        this.femaleUserCount = femaleUserCount;
    }

    public String getMaleFemaleRatio() {
        // Compute counts and ratios
        computeStatistics();
        return maleFemaleRatio;
    }

    public void setMaleFemaleRatio(String maleFemaleRatio) {
        this.maleFemaleRatio = maleFemaleRatio;
    }

    private void computeStatistics() {
        users = userService.displayAllUsers();

        long maleCount = users.stream()
                .filter(user -> user.getGender().equalsIgnoreCase("Male"))
                .count();

        long femaleCount = users.stream()
                .filter(user -> user.getGender().equalsIgnoreCase("Female"))
                .count();

        this.maleUserCount = (int) maleCount;
        this.femaleUserCount = (int) femaleCount;

        if (femaleCount > 0) {
            this.maleFemaleRatio = String.format("%.2f:1", (double) maleCount / femaleCount);
        } else {
            this.maleFemaleRatio = "N/A";
        }
    }



    public void loadUserData() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String usernameParam = params.get("user");
        if (usernameParam != null) {
            User foundUser = userService.getUserOfUsername(usernameParam);
            if (foundUser != null) {
                this.user = foundUser;
                this.username = foundUser.getUsername();
                this.dependants = dependantService.getAllDependantsByUserId(user.getId());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User not found", "User not found"));
            }
        }
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

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

    public void searchDependants() {
        switch (searchCriteria) {
            case "gender":
                System.out.println("\n\nSearch by gender entered\n\n\n");
                this.dependants = dependantService.searchDependantsByGenderAndUserId(searchValue, user.getId());
                System.out.println("Dependants"+dependants);
                break;
            case "username":
                System.out.println("\n\nSearch by username entered\n\n\n");
                this.dependants = dependantService.searchDependantsByUsernameAndUserId(searchValue, user.getId());
                System.out.println("Dependants"+dependants);

                break;
            case "firstname":
                System.out.println("\n\nSearch by firstname entered\n\n\n");
                this.dependants = dependantService.searchDependantsByFirstnameAndUserId(searchValue, user.getId());
                System.out.println("Dependants"+dependants);

                break;
            case "lastname":
                System.out.println("\n\nSearch by lastname entered\n\n\n");
                this.dependants = dependantService.searchDependantsByLastnameAndUserId(searchValue, user.getId());
                System.out.println("Dependants"+dependants);

                break;
            default:
                System.out.println("\n\nSearch by default entered\n\n\n");
                this.dependants = dependantService.getAllDependantsByUserId(user.getId());
                System.out.println("Dependants"+dependants);

                break;
        }
        // Return null to prevent navigation and handle updates through AJAX
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":userDetailsForm:dependantsTable");
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



    private String backUrl;

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public void navigateBack() {
        if (backUrl != null && !backUrl.isEmpty()) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            try {
                externalContext.redirect(backUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Long index= 0L;

    public Long getIndex() {
        return index++;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    private String searchTerm;
    private Date dateFrom;
    private Date dateTo;
    private String sortBy;

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public void search(){}

}
