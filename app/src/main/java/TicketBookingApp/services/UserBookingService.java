package TicketBookingApp.services;

import TicketBookingApp.entities.User;
import TicketBookingApp.utils.UserServiceUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private User user;
    private List<User> userList;
    private static final String USERS_PATH = "app/src/main/java/TicketBookingApp/localDB/users.json";
    private ObjectMapper objectMapper;

    public UserBookingService(User user) throws IOException {
        this.user = user;
        File usersFile = new File(USERS_PATH);
        this.userList = objectMapper.readValue(usersFile, new TypeReference<List<User>>() {});
    }

    public Boolean loginUser() {
        Optional<User> foundUser = userList
                .stream()
                .filter(u -> u.getName().equals(user.getName()) &&
                        UserServiceUtil.checkHashPassword(u.getPassword(), user.getHashedPassword()))
                .findFirst();
        return foundUser.isPresent();
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

    public void saveUserToFile() throws IOException {
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    public void fetchBooking() {
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

}
