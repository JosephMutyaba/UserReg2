package org.pahappa.systems.registrationapp.views;

import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.UserService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

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

            String username="";
            String firstName="";
            String lastName="";
            Date dob=null;
            do{
                System.out.println("Enter your username: ");
                username = scanner.nextLine();

                if(Pattern.matches("[@'a-z]['a-z0-9]+", username.trim())){
                    if (username.length()<4) {
                        System.out.println("username cannot be null and should be at least 4 characters.");
                    }else{
                        if (userService.checkIfUserExists(username)) {
                            System.out.println("That username is already in use.Try a different username:");
                        }else {
                            username = username.trim();
                            break;
                        }
                    }
                }else{
                    System.out.println("username cannot be null and should be at least 4 characters. Please try again.");
                }

            }while (true);

            do{
                System.out.println("Enter your First name: ");
                firstName = scanner.nextLine();
                if(!(Pattern.matches("[A-Z][a-z]+", firstName.trim()))){
                    System.out.println("first name cannot be null and should be at least 2 characters. Please try again.");
                }else {
                    break;
                }
            }while (true);

            do{
                System.out.println("Enter your Last name: ");
                lastName = scanner.nextLine();
                if(!(Pattern.matches("[A-Z][a-z]+",lastName.trim()))){
                    System.out.println("Last name cannot be null and should be at least 2 characters. Please try again.");
                }else {
                    break;
                }
            }while (true);

            do{
                System.out.println("Enter your Date of Birth((DD/MM/YYYY)): ");
                String dateOfBirth = scanner.nextLine();
                if(!(Pattern.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}",dateOfBirth.trim()))){
                    System.out.println("Invalid date. Please follow the date format shown above.");
                }else {

                    boolean dateInFuture = checkIfDateOfBirthIsFuture(dateOfBirth);
                    if (!dateInFuture) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        dob = sdf.parse(dateOfBirth);
                        break;
                    }

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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<User> users = userService.displayAllUsers();
        if (!users.isEmpty()) {
            for (User user : users) {
                String dob= sdf.format(user.getDateOfBirth());
                System.out.println("Username: "+user.getUsername());
                System.out.println("Name: "+user.getFirstname()+" "+user.getLastname());
                System.out.println("Date of Birth: "+dob+"\n");
                System.out.println("*************************************************************");
            }
        }else {
            System.out.println("There are no currently registered users in the system.");
        }
    }

    private void getUserOfUsername() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        try {
            User user = userService.getUserOfUsername(username);
            String dob= sdf.format(user.getDateOfBirth()); // converting date of birht to format d/MM/yyyy

            System.out.println("Username: "+username);
            System.out.println("First name: "+user.getFirstname());
            System.out.println("Last name: "+user.getLastname());
            System.out.println("Date of Birth: "+dob);
            System.out.println();
        } catch (Exception e) {
            System.out.println("User does not exist\n");
        }
    }

    private void updateUserOfUsername() {
        String username;
        String firstName;
        String lastName;
        Date dateOfBirth;
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

                //handling update for new first name
                do {
                    System.out.println("Enter new First name: ");
                    firstName=scanner.nextLine();
                    // maintain previous first name if user types nothing
                    if(!(Pattern.matches("[A-Z][a-z]+",firstName.trim()))){
                        System.out.println("Invalid entry. It should be Capitalised with only characters[A-Z][a-z].");
                        boolean maintainPreviousRecord=maintainPreviousRecord();
                        if (maintainPreviousRecord) {
                            System.out.println("First name not changed");
                            firstName=user.getFirstname();
                            break;
                        }
                    }else {
                        System.out.println("name accepted.");
                        break;
                    }
                }while (true);

                // handling update for last name
                do {
                    System.out.println("Enter new Last name: ");
                    lastName=scanner.nextLine();

                    if(Pattern.matches("[A-Z][a-z]+",lastName.trim())){
                        System.out.println("Invalid entry. It should be Capitalised with only characters[A-Z][a-z].");
                        boolean maintainPreviousRecord=maintainPreviousRecord();
                        if (maintainPreviousRecord) {
                            // maintain previous last name if user types nothing
                            System.out.println("Last name not changed");
                            lastName= user.getLastname();
                            break;
                        }
                    }else {
                        System.out.println("name accepted");
                        break;
                    }
                }while (true);

                // handling new date of birth
                do {
                    System.out.println("Enter new Date of Birth (DD/MM/YYYY): ");
                    String dob=scanner.nextLine();

                    if (!(Pattern.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}",dob.trim()))) {
                        System.out.println("Invalid entry. It should be a valid date of birth, follow the format(DD/MM/YYYY) .");
                        boolean maintainPreviousRecord=maintainPreviousRecord();
                        if (maintainPreviousRecord) {
                            // maintain previous DoB if user types nothing
                            System.out.println("Date of Birth not changed");
                            dateOfBirth=user.getDateOfBirth();
                            break;
                        }
                    }else {
                        boolean dateInFuture=checkIfDateOfBirthIsFuture(dob);
                        if (!dateInFuture) {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            dateOfBirth = sdf.parse(dob);
                            break;
                        }else {
                            boolean maintainPreviousRecord=maintainPreviousRecord();
                            if (maintainPreviousRecord) {
                                // maintain previous DoB if user types nothing
                                System.out.println("Date of Birth not changed");
                                dateOfBirth=user.getDateOfBirth();
                                break;
                            }
                        }
                    }
                }while (true);

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
            System.out.println("""
                    Could not update user.
                    Make sure you enter
                    the correct format of the information
                    """);
        }
    }

    private void deleteUserOfUsername() {
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();

        if(userService.checkIfUserExists(username)){
            if (confirmDelete()) {
                boolean usersDeleted=userService.deleteUserOfUsername(username);
                if (usersDeleted) {
                    System.out.println("User has been successfully deleted!\n");
                }else {
                    System.out.println("Deletion Unsuccessful, please try again!\n");
                }
            }else {
                System.out.println("User not deleted\n");
            }
        }else {
            System.out.println("That user does not exist\n");
        }

    }

    private void deleteAllUsers() {
        if (confirmDelete()) {
            boolean allUsersDeleted=userService.deleteAllUsers();
            if (allUsersDeleted) {
                System.out.println("All users have been successfully deleted!\n");
            }else {
                System.out.println("Deletion Unsuccessful, please try again!\n");
            }
        }else {
            System.out.println("Deletion Unsuccessful!\n");
        }
    }

    private boolean confirmDelete(){
        System.out.println("Are you sure you want to delete?\n1. Confirm\n2. Cancel");
        int confirm=scanner.nextInt();
        scanner.nextLine();
        return confirm == 1;
    }

    private boolean maintainPreviousRecord(){
        System.out.println("Maintain previous record?\n1. Yes maintain\n2. Try entering correct entry again");
        int maintain=scanner.nextInt();
        scanner.nextLine();
        return maintain == 1;
    }

    private boolean checkIfDateOfBirthIsFuture(String dateOfBirth){
        int birthYear=0, birthMonth=0, birthDay=0;
        LocalDate localDate = LocalDate.now();

        String[] bdContents = dateOfBirth.split("/");
        birthDay=Integer.parseInt(bdContents[0]);
        birthMonth=Integer.parseInt(bdContents[1]);
        birthYear=Integer.parseInt(bdContents[2]);

        if ((birthDay>29 || birthDay<1) && birthMonth == 2) {
            System.out.println("February cannot have more than 29 days!");
            return true;
        }else if (birthDay>31 || birthDay < 1) {
            System.out.println("Day of month must range from 1 to 31");
            return true;
        }

        if (birthMonth > 12 || birthMonth < 1) {
            System.out.println("Months only range from 1 to 12!");
            return true;
        }

        if(birthYear == localDate.getYear()){
            if (birthMonth == localDate.getMonthValue()) {
                if (!(birthDay > localDate.getDayOfMonth())) {
                    System.out.println("day: "+localDate.getDayOfMonth());
                    return false;
                }else {
                    System.out.println("You can't enter a future date");
                    return true;
                }
            }else if (!(birthMonth > localDate.getMonthValue())) {
                //System.out.println("Month: "+localDate.getMonthValue());
                return false;
            }else {
                System.out.println("You can't enter a future date");
                return true;
            }
        }else if (!(birthYear > localDate.getYear())) {
            System.out.println("byr:"+birthYear);
            System.out.println("yr: "+localDate.getYear());
            return false;

        }else {
            System.out.println("You can't enter a future date");
            return  true;
        }
    }
}
