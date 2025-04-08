package TicketBookingApp.services;

import TicketBookingApp.entities.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UserBookingService {
    private User user;
    private List<User> users;
    private static final String USERS_PATH = "app/src/main/java/TicketBookingApp/localDB/users.json";
    private ObjectMapper objectMapper;
    public UserBookingService(User user) throws IOException {
        this.user = user;
        File usersFile = new File(USERS_PATH);
        this.users = objectMapper.readValue(usersFile, new TypeReference<List<User>>() {});
    }

}
