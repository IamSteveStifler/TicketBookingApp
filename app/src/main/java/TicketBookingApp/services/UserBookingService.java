package TicketBookingApp.services;

import TicketBookingApp.entities.Train;
import TicketBookingApp.entities.User;
import TicketBookingApp.utils.UserServiceUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private User user;
    private List<User> userList;
    private TicketBookingService ticketBookingService;
    private static final String USERS_PATH =
            "/Documents/TicketBookingApp/app/src/main/java/TicketBookingApp/localDB/users.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserBookingService() throws IOException{
        loadUserFromFile();
    }

    public UserBookingService(User user) throws IOException {
        this.user = user;
        ticketBookingService = new TicketBookingService(user);
        loadUserFromFile();
    }

    public Boolean checkUserLoggedIn() {
        return (user != null);
    }

    public File initializeUsersFile() {
        String home = System.getProperty("user.home");
        return new File(home + USERS_PATH);
    }

    public void loadUserFromFile() throws IOException {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        this.userList = objectMapper.readValue(initializeUsersFile(), new TypeReference<List<User>>() {});
    }

    public void saveUserToFile() throws IOException {
        objectMapper.writeValue(initializeUsersFile(), userList);
    }

    public Optional<User> loginUser(String username, String password) {
        return userList
                .stream()
                .filter(u -> u.getName().equals(username) &&
                        UserServiceUtil.checkHashPassword(password, u.getHashedPassword()))
                .findFirst();
    }

    public Boolean signupUser(User newUser) {
        try {
            userList.add(newUser);
            saveUserToFile();
            return Boolean.TRUE;
        } catch (IOException e) {
            return Boolean.FALSE;
        }
    }

    public void fetchBooking() {
        if(user.getTicketsBooked().isEmpty()) {
            System.out.println("You don't have any tickets booked!");
            return;
        }
        this.user.printBookedTickets();
    }

    public Boolean cancelBooking(String ticketId) throws IOException {
        this.user.setTicketsBooked(this.user.getTicketsBooked()
                .stream()
                .filter(t -> !t.getTicketId().equals(ticketId))
                .toList());
        this.userList
                .forEach(u -> {
                    if(u.getUserId().equals(this.user.getUserId())) u.setTicketsBooked(this.user.getTicketsBooked());
                });
        saveUserToFile();
        return Boolean.TRUE;
    }

    public List<Train> searchTrain(String origin, String destination) {
        return ticketBookingService.searchTrain(origin, destination);
    }

}
