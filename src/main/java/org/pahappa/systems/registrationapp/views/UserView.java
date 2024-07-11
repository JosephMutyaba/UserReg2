package org.pahappa.systems.registrationapp.views;

import org.pahappa.systems.registrationapp.exception.DatabaseException;
import org.pahappa.systems.registrationapp.models.Dependant;
import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.DependantService;
import org.pahappa.systems.registrationapp.services.UserService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ManagedBean
@SessionScoped
public class UserView implements Serializable {

    private final UserService userService = new UserService();
    private final DependantService dependantService = new DependantService();
    private List<User> users;
    private List<Dependant> dependants;
    private User user = new User();
    private String username;
    private String gender;
    private String searchCriteria;
    private String searchValue;

    /////////////////////
    private String searchUsername;
    private String searchFirstName;
    private String searchLastName;

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

//        if (femaleCount > 0) {
//            this.maleFemaleRatio = String.format("%.1f:1", (double) maleCount / femaleCount);
//        } else {
//            this.maleFemaleRatio = "N/A";
//        }
        this.maleFemaleRatio=calculateRatio(maleUserCount, femaleUserCount);
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


    public String getSearchLastName() {
        return searchLastName;
    }

    public void setSearchLastName(String searchLastName) {
        this.searchLastName = searchLastName;
    }

    public String getSearchFirstName() {
        return searchFirstName;
    }

    public void setSearchFirstName(String searchFirstName) {
        this.searchFirstName = searchFirstName;
    }

    public String getSearchUsername() {
        return searchUsername;
    }

    public void setSearchUsername(String searchUsername) {
        this.searchUsername = searchUsername;
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

    public void deleteAllUsers() {
        boolean allUsersDeleted = userService.deleteAllUsers();
        if (allUsersDeleted) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("All users have been successfully deleted!"));
            users = userService.displayAllUsers();
//            return "/pages/displayUsers"; // Navigate to the index page
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Deletion unsuccessful, please try again!"));
//            return null;
        }
    }


    // Method to calculate the number of users with dependants
    public long usersWithDependants() {
        users= userService.displayAllUsers();
        return users.stream().filter(user -> !user.getDependants().isEmpty()).count();
    }

    // Method to calculate the number of users without dependants
    public long usersWithoutDependants() {
        users= userService.displayAllUsers();
        return users.stream().filter(user -> user.getDependants().isEmpty()).count();
    }

    // Method to calculate the dependency ratio
    public String dependencyRatio() {
        long usersWithDependants = usersWithDependants();
        long usersWithoutDependants = usersWithoutDependants();
        return usersWithDependants + ":" + usersWithoutDependants;
    }

    //////////////////////////////

    public List<User> getUsers() {
        if ((searchUsername == null || searchUsername.isEmpty()) &&
                (searchFirstName == null || searchFirstName.isEmpty()) &&
                (searchLastName == null || searchLastName.isEmpty())) {
            return userService.displayAllUsers();
        } else {
            return searchUsers();
        }
    }


    public List<User> searchUsers() {
        users= userService.displayAllUsers();
        return users.stream()
                .filter(user -> (searchUsername == null || searchUsername.isEmpty() || user.getUsername().contains(searchUsername)) &&
                        (searchFirstName == null || searchFirstName.isEmpty() || user.getFirstname().contains(searchFirstName)) &&
                        (searchLastName == null || searchLastName.isEmpty() || user.getLastname().contains(searchLastName)))
                .collect(Collectors.toList());
    }


    public void search() {
        users = getUsers();
    }

    public void reset() {
        searchUsername = null;
        searchFirstName = null;
        searchLastName = null;
        users = userService.displayAllUsers();
    }

    ////////// DEPENDANT UNDER USER ROLE:USER
    private String selectedGender; // Add getter and setter

    public String getSelectedGender() {
        return selectedGender;
    }

    public void setSelectedGender(String selectedGender) {
        this.selectedGender = selectedGender;
    }

    ////SEARCH FOR DEPENDANTS
    /////////////////////
    private String searchDependantUsername;
    private String searchDependantFirstName;
    private String searchDependantLastName;

    public String getSearchDependantUsername() {
        return searchDependantUsername;
    }

    public void setSearchDependantUsername(String searchDependantUsername) {
        this.searchDependantUsername = searchDependantUsername;
    }

    public String getSearchDependantFirstName() {
        return searchDependantFirstName;
    }

    public void setSearchDependantFirstName(String searchDependantFirstName) {
        this.searchDependantFirstName = searchDependantFirstName;
    }

    public String getSearchDependantLastName() {
        return searchDependantLastName;
    }

    public void setSearchDependantLastName(String searchDependantLastName) {
        this.searchDependantLastName = searchDependantLastName;
    }


    public List<Dependant> getDependants() {
        // Retrieve all dependants for the user
        dependants = dependantService.getAllDependantsByUserId(user.getId());

        // Apply gender filter if selected
        if (selectedGender != null && !selectedGender.isEmpty()) {
            dependants = dependants.stream()
                    .filter(dependant -> dependant.getGender().equalsIgnoreCase(selectedGender))
                    .collect(Collectors.toList());
        }

        // Apply search criteria if provided
        if ((searchDependantUsername != null && !searchDependantUsername.isEmpty()) ||
                (searchDependantFirstName != null && !searchDependantFirstName.isEmpty()) ||
                (searchDependantLastName != null && !searchDependantLastName.isEmpty())) {
            dependants = dependants.stream()
                    .filter(dependant -> (searchDependantUsername == null || searchDependantUsername.isEmpty() || dependant.getUsername().contains(searchDependantUsername)) &&
                            (searchDependantFirstName == null || searchDependantFirstName.isEmpty() || dependant.getFirstname().contains(searchDependantFirstName)) &&
                            (searchDependantLastName == null || searchDependantLastName.isEmpty() || dependant.getLastname().contains(searchDependantLastName)))
                    .collect(Collectors.toList());
        }

        return dependants;
    }

    public void searchingDependants() {
        dependants = getDependants();
    }

    public void resetDependants() {
        searchDependantUsername = null;
        searchDependantFirstName = null;
        searchDependantLastName = null;
        selectedGender = null;
        dependants = dependantService.getAllDependantsByUserId(user.getId());
    }



    /////////////// RATIO   ////////
    public String calculateRatio(int males, int females) {
        // Handle case where females count is zero
        if (females == 0) {
            if (males == 0) {
                return "0:0"; // Both males and females are zero
            } else {
                return "Infinity"; // Males are non-zero, females are zero
            }
        }

        // Calculate the ratio of males to females
        int gcd = gcd(males, females);
        int simplifiedMales = males / gcd;
        int simplifiedFemales = females / gcd;

        return simplifiedMales + ":" + simplifiedFemales;
    }

    // Method to find greatest common divisor (gcd) using Euclid's algorithm
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

}
