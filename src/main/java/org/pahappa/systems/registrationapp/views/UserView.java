package org.pahappa.systems.registrationapp.views;

import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UserView {

    private final Scanner scanner;

    public UserView(){
        this.scanner = new Scanner(System.in);
    }

    UserService userService = new UserService();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void displayMenu() {
        System.out.println("********* User Registration System *********");
        boolean running = true;

        while (running) {
            System.out.println("\nChoose an operation:");
            System.out.println("1. Register a user");
            System.out.println("2. Display all users");
            System.out.println("3. Get a user of username");
            System.out.println("4. Update user details of username");
            System.out.println("5. Delete User of username");
            System.out.println("6. Delete all users");
            System.out.println("7. Exit");
            try{
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                switch (choice) {
                    case 1:
                        registerUser();
                        break;
                    case 2:
                        displayAllUsers();
                        break;
                    case 3:
                        getUserOfUsername();
                        break;
                    case 4:
                        updateUserOfUsername();
                        break;
                    case 5:
                        deleteUserOfUsername();
                        break;
                    case 6:
                        deleteAllUsers();
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }catch (Exception e){
                System.out.println("Invalid choice. Please try again.");
                scanner.nextLine(); // Consume the newline character
            }
        }
    }


    private void registerUser() {
        try {
            String username = "";
            String firstName = "";
            String lastName = "";
            Date dob = null;

            do {
                System.out.println("Enter your username: ");
                username = scanner.nextLine();
                try {
                    userService.validateUsername(username);
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } while (true);

            do{
                System.out.println("Enter your First name: ");
                firstName = scanner.nextLine();
                try {
                    userService.validateName(firstName, "First name");
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } while (true);

            do {
                System.out.println("Enter your Last name: ");
                lastName = scanner.nextLine();
                try {
                    userService.validateName(lastName, "Last name");
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } while (true);

            do {
                System.out.println("Enter your Date of Birth (DD/MM/YYYY): ");
                String dateOfBirth = scanner.nextLine();
                try {
                    userService.dateSyntax(dateOfBirth);
                    userService.dateSyntax(dateOfBirth);
                    dob = userService.parseDateOfBirth(dateOfBirth);
                    userService.validateDateOfBirth(dob);
                    break;
                } catch (ParseException e) {
                    System.out.println("Invalid date format. Please follow the date format shown above.");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } while (true);

            // Setting the credentials of new user
            User user = new User();
            user.setFirstname(firstName);
            user.setLastname(lastName);
            user.setUsername(username);
            user.setDateOfBirth(dob);

            boolean isRegistered = userService.registerUser(user);
            if (isRegistered) {
                System.out.println("You have successfully registered!\n");
            } else {
                System.out.println("Something went wrong, please try again!\n");
            }

        } catch (Exception e) {
            System.out.println("Enter the correct format of your credentials\n");
        }
    }

    private void displayAllUsers() {
        List<User> retrievedUsers = userService.displayAllUsers();
        if (retrievedUsers == null) {
            System.out.println("No users have been registered.");
        }else {
            for (User user : retrievedUsers) {
                printUser(user);
            }
        }
    }

    private void getUserOfUsername() {
        System.out.print("Enter person username: ");
        String username = scanner.nextLine();
        User user = userService.getUserOfUsername(username);

        if (user != null) {
            printUser(user);
        } else {
            System.out.println("Person not found");
        }
    }


    private void updateUserOfUsername() {
        try {
            String username = "";
            String firstName = "";
            String lastName = "";
            Date dob = null;

            System.out.println("Enter the username: ");
            username = scanner.nextLine();

            if(!userService.checkIfUserExists(username)){
                System.out.println("User not found");
            }else {
                do {
                    System.out.println("Enter your First name: ");
                    firstName = scanner.nextLine();
                    try {
                        userService.validateName(firstName, "First name");
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } while (true);

                do {
                    System.out.println("Enter your Last name: ");
                    lastName = scanner.nextLine();
                    try {
                        userService.validateName(lastName, "Last name");
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } while (true);

                do {
                    System.out.println("Enter your Date of Birth (DD/MM/YYYY): ");
                    String dateOfBirth = scanner.nextLine();
                    try {
                        dob = userService.parseDateOfBirth(dateOfBirth);
                        userService.validateDateOfBirth(dob);
                        break;
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please follow the date format shown above.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } while (true);

                // Setting the credentials of existing user
                User user = userService.getUserOfUsername(username);
                user.setFirstname(firstName);
                user.setLastname(lastName);
                user.setDateOfBirth(dob);

                boolean isUpdated = userService.updateUserOfUsername(user);
                if (isUpdated) {
                    System.out.println("User details have been successfully updated!\n");
                } else {
                    System.out.println("Something went wrong, please try again!\n");
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteUserOfUsername() {

        boolean wantToDelete = wantToDelete();
        if (wantToDelete) {
            System.out.print("Enter person username: ");
            String username = scanner.nextLine();
            boolean userDeleted=userService.deleteUserOfUsername(username);
            if (userDeleted) {
                System.out.println("Person deleted successfully");
            }else {
                System.out.println("Person not found");
            }
        }else {
            System.out.println("Deletion cancelled.");
        }
    }

    private void deleteAllUsers() {
        boolean proceedToDelete = wantToDelete();
        if (proceedToDelete) {
            boolean allUsersDeleted=userService.deleteAllUsers();
            if (allUsersDeleted) {
                System.out.println("All users have been successfully deleted!\n");
            }else {
                System.out.println("Deletion Unsuccessful, please try again!\n");
            }
        }else {
            System.out.println("Deletion Cancelled.\n");
        }
    }

    private boolean wantToDelete(){
        System.out.println("Are you sure you want to delete?\n1. Delete\n2.Cancel");
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice==1;
        } catch (Exception e) {
            return false;
        }
    }

    private void printUser(User user){
        System.out.println("Username: " + user.getUsername());
        System.out.println("Name: " + user.getFirstname() + " " + user.getLastname());
        String dob = sdf.format(user.getDateOfBirth());
        System.out.println("Date of birth: " + dob);
        System.out.println("*******************************************************\n");
    }
}
