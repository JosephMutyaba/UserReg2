package org.pahappa.systems.registrationapp.views;

import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.UserService;

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
            System.out.println("Enter your username: ");
            String username="";
            String firstName="";
            String lastName="";
            Date dob=null;
            do{
                username = scanner.nextLine();

                if(username.trim().isEmpty() || username.length()<4){
                    System.out.println("username cannot be null and should be at least 4 characters. Please try again.");
                }else if (userService.checkIfUserExists(username)) {
                    System.out.println("That username is already in use.Try a different username:");
                }else{
                    break;
                }

            }while (true);

            do{
                System.out.println("Enter your First name: ");
                firstName = scanner.nextLine();
                if(firstName.trim().isEmpty() || firstName.length()<2){
                    System.out.println("first name cannot be null and should be at least 2 characters. Please try again.");
                }else {
                    break;
                }
            }while (true);

            do{
                System.out.println("Enter your Last name: ");
                lastName = scanner.nextLine();
                if(lastName.trim().isEmpty() || lastName.length()<2){
                    System.out.println("Last name cannot be null and should be at least 2 characters. Please try again.");
                }else {
                    break;
                }
            }while (true);

            do{
                System.out.println("Enter your Date of Birth((DD/MM/YYYY)): ");
                String dateOfBirth = scanner.nextLine();
                if(dateOfBirth.trim().isEmpty() || dateOfBirth.length()<8 || dateOfBirth.length()>10){
                    System.out.println("Date of Birth cannot be null and \nshould be between 8 and 10 characters. Please try again.");
                }else {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    dob = sdf.parse(dateOfBirth);
                    break;
                }
            }while (true);

            // setting the credentials of new user
            User user = new User();
            user.setFirstname(firstName);
            user.setLastname(lastName);
            user.setUsername(username);
            user.setDateOfBirth(dob);

            boolean isRegistered=userService.registerUser(user);
            if (isRegistered) {
                System.out.println("You have successfully registered!\n");
            }else {
                System.out.println("Something went wrong, please try again!\n");
            }

        } catch (Exception e) {
            System.out.println("Enter the correct format of your credentials\n");
        }
    }

    private void displayAllUsers() {
        List<User> users = userService.displayAllUsers();

        for (User user : users) {
            System.out.println("Username: "+user.getUsername());
            System.out.println("Name: "+user.getFirstname()+" "+user.getLastname());
            System.out.println("Date of Birth: "+user.getDateOfBirth()+"\n");
            System.out.println("*************************************************************");
        }
    }

    private void getUserOfUsername() {
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        try {
            User user = userService.getUserOfUsername(username);
            System.out.println("Username: "+username);
            System.out.println("First name: "+user.getFirstname());
            System.out.println("Last name: "+user.getLastname());
            System.out.println("Date of Birth: "+user.getDateOfBirth());
            System.out.println();
        } catch (Exception e) {
            System.out.println("User does not exist\n");
        }
    }

    private void updateUserOfUsername() {
        String username="";
        String firstName="";
        String lastName="";
        Date dateOfBirth= null;
        do{
            System.out.println("Enter your username: ");
            username = scanner.nextLine();
            if(username.trim().isEmpty()){
                System.out.println("username cannot be null. Please try again.");
            }else {
                break;
            }
        }while(true);

        try {
            if (userService.checkIfUserExists(username)) {
                User user = userService.getUserOfUsername(username);

                System.out.println("Enter your First name: ");
                firstName=scanner.nextLine();
                // maintain previous first name if user types nothing
                if(firstName.trim().isEmpty()){
                    System.out.println("First name not changed");
                   firstName=user.getFirstname();
                }

                System.out.println("Enter your Last name: ");
                lastName=scanner.nextLine();
                // maintain previous last name if user types nothing
                if(lastName.trim().isEmpty()){
                    System.out.println("Last name not changed");
                    lastName= user.getLastname();
                }

                System.out.println("Enter your Date of Birth (DD/MM/YYYY): ");
                String dob=scanner.nextLine();
                // maintain previous DoB if user types nothing
                if (dob.trim().isEmpty()) {
                    System.out.println("Date of Birth not changed");
                    dateOfBirth=user.getDateOfBirth();
                }else {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    dateOfBirth = sdf.parse(dob);
                }

                boolean userUpdated=userService.updateUserOfUsername(username, firstName,lastName,dateOfBirth);
                if (userUpdated) {
                    System.out.println("User updated successfully\n");
                }else {
                    System.out.println("Sorry, User could not be updated\n");
                }
            }else {
                System.out.println("There is no such username! \n");
            }
        } catch (Exception e) {
            System.out.println("Could not update user. Make sure you enter the correct format of the information");
        }
    }

    private void deleteUserOfUsername() {
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        boolean usersDeleted=userService.deleteUserOfUsername(username);
        if (usersDeleted) {
            System.out.println("User has been successfully deleted!\n");
        }else {
            System.out.println("Deletion Unsuccessful, please try again!\n");
        }
    }

    private void deleteAllUsers() {
        boolean allUsersDeleted=userService.deleteAllUsers();
        if (allUsersDeleted) {
            System.out.println("All users have been successfully deleted!\n");
        }else {
            System.out.println("Deletion Unsuccessful, please try again!\n");
        }
    }
}
