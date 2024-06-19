package org.pahappa.systems.registrationapp.views;

import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.UserService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

@ManagedBean
@SessionScoped
public class UserView implements Serializable {

    private UserService userService = new UserService();
    private List<User> users;
    private User user = new User();
    private String username;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public List<User> getUsers() {
        if (users == null) {
            users = userService.displayAllUsers();
        }
        return users;
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

    public void getUserOfUsername() {
        User foundUser = userService.getUserOfUsername(username);
        if (foundUser != null) {
            user = foundUser;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User not found"));
        }
    }

    public String updateUser() {
        try {
            userService.validateName(user.getFirstname(), "First name");
            userService.validateName(user.getLastname(), "Last name");
            userService.validateDateOfBirth(user.getDateOfBirth());

            boolean isUpdated = userService.updateUserOfUsername(user);
            if (isUpdated) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User details have been successfully updated!"));
                return "index"; // Navigate to the index page
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Something went wrong, please try again!"));
                return null;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    public String deleteUser() {
        boolean userDeleted = userService.deleteUserOfUsername(username);
        if (userDeleted) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User deleted successfully"));
            return "index"; // Navigate to the index page
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User not found"));
            return null;
        }
    }

    public String deleteAllUsers() {
        boolean allUsersDeleted = userService.deleteAllUsers();
        if (allUsersDeleted) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("All users have been successfully deleted!"));
            return "index"; // Navigate to the index page
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Deletion unsuccessful, please try again!"));
            return null;
        }
    }
}














//package org.pahappa.systems.registrationapp.views;
//
//import org.pahappa.systems.registrationapp.models.User;
//import org.pahappa.systems.registrationapp.services.UserService;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Scanner;
//
//public class UserView {
//
//    private final Scanner scanner;
//
//    public UserView(){
//        this.scanner = new Scanner(System.in);
//    }
//
//    UserService userService = new UserService();
//
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//    public void displayMenu() {
//        System.out.println("********* User Registration System *********");
//        boolean running = true;
//
//        while (running) {
//            System.out.println("\nChoose an operation:");
//            System.out.println("1. Register a user");
//            System.out.println("2. Display all users");
//            System.out.println("3. Get a user of username");
//            System.out.println("4. Update user details of username");
//            System.out.println("5. Delete User of username");
//            System.out.println("6. Delete all users");
//            System.out.println("7. Exit");
//            try{
//                int choice = scanner.nextInt();
//                scanner.nextLine(); // Consume the newline character
//                switch (choice) {
//                    case 1:
//                        registerUser();
//                        break;
//                    case 2:
//                        displayAllUsers();
//                        break;
//                    case 3:
//                        getUserOfUsername();
//                        break;
//                    case 4:
//                        updateUserOfUsername();
//                        break;
//                    case 5:
//                        deleteUserOfUsername();
//                        break;
//                    case 6:
//                        deleteAllUsers();
//                        break;
//                    case 7:
//                        System.out.println("Exiting...");
//                        running = false;
//                        break;
//                    default:
//                        System.out.println("Invalid choice. Please try again.");
//                }
//            }catch (Exception e){
//                System.out.println("Invalid choice. Please try again.");
//                scanner.nextLine(); // Consume the newline character
//            }
//        }
//    }
//
//
//    private void registerUser() {
//        try {
//            String username = "";
//            String firstName = "";
//            String lastName = "";
//            Date dob = null;
//
//            do {
//                System.out.println("Enter your username: ->Type \"exit\" to exit");
//                username = scanner.nextLine();
//                if (username.equals("exit")) {
//                    return;
//                }
//                try {
//                    userService.validateUsername(username);
//                    break;
//                } catch (Exception e) {
//                    System.out.println(e.getMessage());
//                }
//            } while (true);
//
//            do {
//                System.out.println("Enter your First name: ->Type \"exit\" to exit");
//                firstName = scanner.nextLine();
//                if (firstName.equals("exit")) {
//                    return;
//                }
//                try {
//                    userService.validateName(firstName, "First name");
//                    break;
//                } catch (Exception e) {
//                    System.out.println(e.getMessage());
//                }
//            } while (true);
//
//            do {
//                System.out.println("Enter your Last name: ->Type \"exit\" to exit");
//                lastName = scanner.nextLine();
//                if (lastName.equals("exit")) {
//                    return;
//                }
//                try {
//                    userService.validateName(lastName, "Last name");
//                    break;
//                } catch (Exception e) {
//                    System.out.println(e.getMessage());
//                }
//            } while (true);
//
//            do {
//                System.out.println("Enter your Date of Birth (DD/MM/YYYY): ->Type \"exit\" to exit");
//                String dateOfBirth = scanner.nextLine();
//                if (dateOfBirth.equals("exit")) {
//                    return;
//                }
//                try {
//                    userService.dateSyntax(dateOfBirth);
//                    dob = userService.parseDateOfBirth(dateOfBirth);
//                    userService.validateDateOfBirth(dob);
//                    break;
//                } catch (ParseException e) {
//                    System.out.println("Invalid date format. Please follow the date format shown above.");
//                } catch (Exception e) {
//                    System.out.println(e.getMessage());
//                }
//            } while (true);
//
//            // Setting the credentials of new user
//            User user = new User();
//            user.setFirstname(firstName);
//            user.setLastname(lastName);
//            user.setUsername(username);
//            user.setDateOfBirth(dob);
//
//            boolean isRegistered = userService.registerUser(user);
//            if (isRegistered) {
//                System.out.println("You have successfully registered!\n");
//            } else {
//                System.out.println("Something went wrong, please try again!\n");
//            }
//
//        } catch (Exception e) {
//            System.out.println("Enter the correct format of your credentials\n");
//        }
//    }
//
//    private void displayAllUsers() {
//        List<User> retrievedUsers = userService.displayAllUsers();
//        if (retrievedUsers == null) {
//            System.out.println("No users have been registered.");
//        }else {
//            for (User user : retrievedUsers) {
//                printUser(user);
//            }
//        }
//    }
//
//    private void getUserOfUsername() {
//        System.out.print("Enter person username: ");
//        String username = scanner.nextLine();
//        User user = userService.getUserOfUsername(username);
//
//        if (user != null) {
//            printUser(user);
//        } else {
//            System.out.println("Person not found");
//        }
//    }
//
//
//    private void updateUserOfUsername() {
//        try {
//            String username = "";
//            String firstName = "";
//            String lastName = "";
//            Date dob = null;
//
//            do {
//                System.out.println("Enter the username: ->Type \"exit\" to exit");
//                username = scanner.nextLine();
//                if (username.equals("exit")) {
//                    return;
//                }
//
//                if(!userService.checkIfUserExists(username)){
//                    System.out.println("User not found");
//                }else {
//                    User user = userService.getUserOfUsername(username);
//                    do {
//                        boolean enterNewField= enterNewFieldOrKeepOldOne();
//                        if (!enterNewField) {
//                            break;
//                        }
//
//                        System.out.println("Enter your First name: ->Type \"exit\" to exit");
//                        firstName = scanner.nextLine();
//                        if (firstName.trim().equals("exit")) {
//                            return;
//                        }
//
//                        // validate and proceed
//                        try {
//                            userService.validateName(firstName, "First name");
//                            user.setFirstname(firstName);
//                            break;
//                        } catch (Exception e) {
//                            System.out.println(e.getMessage());
//                        }
//                    } while (true);
//
//                    do {
//                        boolean enterNewField= enterNewFieldOrKeepOldOne();
//                        if (!enterNewField) {
//                            break;
//                        }
//                        System.out.println("Enter your Last name: ->Type \"exit\" to exit");
//                        lastName = scanner.nextLine();
//                        if (lastName.trim().equals("exit")) {
//                            return;
//                        }
//                        try {
//                            userService.validateName(lastName, "Last name");
//                            user.setLastname(lastName);
//                            break;
//                        } catch (Exception e) {
//                            System.out.println(e.getMessage());
//                        }
//                    } while (true);
//
//                    do {
//                        boolean enterNewField= enterNewFieldOrKeepOldOne();
//                        if (!enterNewField) {
//                            break;
//                        }
//
//                        System.out.println("Enter your Date of Birth (DD/MM/YYYY): ->Type \"exit\" to exit");
//                        String dateOfBirth = scanner.nextLine();
//                        if (firstName.trim().equals("exit")) {
//                            return;
//                        }
//                        try {
//                            dob = userService.parseDateOfBirth(dateOfBirth);
//                            userService.validateDateOfBirth(dob);
//                            user.setDateOfBirth(dob);
//                            break;
//                        } catch (ParseException e) {
//                            System.out.println("Invalid date format. Please follow the date format shown above.");
//                        } catch (Exception e) {
//                            System.out.println(e.getMessage());
//                        }
//                    } while (true);
//
//                    //Update the values in the DB
//                    boolean isUpdated = userService.updateUserOfUsername(user);
//                    if (isUpdated) {
//                        System.out.println("User details have been successfully updated!\n");
//                    } else {
//                        System.out.println("Something went wrong, please try again!\n");
//                    }
//                }
//
//
//            }while (true);
//
//
//
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private void deleteUserOfUsername() {
//        System.out.print("Enter username of user to be deleted: ");
//        String username = scanner.nextLine();
//        boolean wantToDelete = wantToDelete();
//        if (wantToDelete) {
//            boolean userDeleted=userService.deleteUserOfUsername(username);
//            if (userDeleted) {
//                System.out.println("Person deleted successfully");
//            }else {
//                System.out.println("Person not found");
//            }
//        }else {
//            System.out.println("Deletion cancelled.");
//        }
//    }
//
//    private void deleteAllUsers() {
//        boolean proceedToDelete = wantToDelete();
//        if (proceedToDelete) {
//            boolean allUsersDeleted=userService.deleteAllUsers();
//            if (allUsersDeleted) {
//                System.out.println("All users have been successfully deleted!\n");
//            }else {
//                System.out.println("Deletion Unsuccessful, please try again!\n");
//            }
//        }else {
//            System.out.println("Deletion Cancelled.\n");
//        }
//    }
//
//    private boolean wantToDelete(){
//        System.out.println("Are you sure you want to delete?\n1. Delete\n2. Cancel");
//        try {
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//            return choice==1;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    private void printUser(User user){
//        System.out.println("Username: " + user.getUsername());
//        System.out.println("Name: " + user.getFirstname() + " " + user.getLastname());
//        String dob = sdf.format(user.getDateOfBirth());
//        System.out.println("Date of birth: " + dob);
//        System.out.println("*******************************************************\n");
//    }
//
//    private boolean enterNewFieldOrKeepOldOne(){
//        System.out.println("Keep old value of this field or enter a new one?\n1. Enter new value\n2. Keep old value");
//        try {
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//            return choice==1;
//        }catch (Exception e) {
//            return false;
//        }
//    }
//}
