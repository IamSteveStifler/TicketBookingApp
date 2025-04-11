/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package TicketBookingApp;

import TicketBookingApp.entities.User;
import TicketBookingApp.services.UserBookingService;
import TicketBookingApp.utils.UserServiceUtil;
import java.io.IOException;
import java.util.*;

public class App {

    public static void printException(String exception) {
        System.out.println(exception);
    }

    public static Boolean isUserLoggedIn(UserBookingService userBookingService) {
        if(!userBookingService.checkUserLoggedIn()) {
            printException("You cant perform this operation");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public static void main(String[] args) {
        System.out.println("App is running!!");
        try(Scanner sc = new Scanner(System.in)){
            int option = 0;
            UserBookingService userBookingService;
            try {
                userBookingService = new UserBookingService();
            } catch (IOException ex) {
                printException("Some error occurred!");
                return;
            }
            while (option != 7) {
                System.out.println("Choose option below:");
                System.out.println("1. Sign up");
                System.out.println("2. Login");
                System.out.println("3. Fetch Bookings");
                System.out.println("4. Search Trains");
                System.out.println("5. Book a Seat");
                System.out.println("6. Cancel my Booking");
                System.out.println("7. Exit the App");
                option = sc.nextInt();
                switch (option) {
                    case 1 -> {
                        System.out.print("Enter username & password:");
                        String username = sc.next();
                        String password = sc.next();
                        User newUser = new User(UUID.randomUUID().toString(),
                                username, "",
                                UserServiceUtil.hashPassword(password),
                                new ArrayList<>());
                        if(userBookingService.signupUser(newUser)) {
                            System.out.println("User created successfully!");
                        } else {
                            printException("Some error occurred!");
                            return;
                        }
                    }
                    case 2 -> {
                        System.out.print("Enter username & password:");
                        String username = sc.next();
                        String password = sc.next();
                        var fetchedUserFromDB = userBookingService.loginUser(username, password);
                        if(fetchedUserFromDB.isPresent()) {
                            userBookingService = new UserBookingService(fetchedUserFromDB.get());
                            System.out.println("User logged in!");
                        } else {
                            printException("User doesn't exist!");
                        }
                    }
                    case 3 -> {
                        if(!isUserLoggedIn(userBookingService)) {
                            break;
                        }
                        userBookingService.fetchBooking();
                    }
                    case 4 -> {
                        if(!isUserLoggedIn(userBookingService)) {
                            break;
                        }
                        System.out.println("Enter source & destination:");
                        String origin = sc.next();
                        String destination = sc.next();
                        var listOfTrains = userBookingService.searchTrain(origin, destination);
                        if(listOfTrains.isEmpty()) {
                            printException("No trains between "+origin+" and "+destination);
                        } else {
                            listOfTrains.forEach(
                                    t -> System.out.println(t.getTrainNo())
                            );
                        }
                    }

                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
