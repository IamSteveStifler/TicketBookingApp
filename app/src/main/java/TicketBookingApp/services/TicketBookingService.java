package TicketBookingApp.services;

import TicketBookingApp.entities.Train;
import TicketBookingApp.entities.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TicketBookingService {

    private List<Train> trainList;
    private User user;
    private static final String TRAINS_PATH =
            "/Documents/TicketBookingApp/app/src/main/java/TicketBookingApp/localDB/trains.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TicketBookingService(User user) throws IOException {
        this.user = user;
        loadTrainFromFile();
    }

    public File initializeTrainsFile() {
        String home = System.getProperty("user.home");
        return new File(home + TRAINS_PATH);
    }

    public void loadTrainFromFile() throws IOException {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        this.trainList = objectMapper.readValue(initializeTrainsFile(), new TypeReference<List<Train>>() {});
    }

    public List<Train> searchTrain(String origin, String destination) {
        return trainList
                .stream()
                .filter(t ->
                    (
                            t.getStations().contains(origin) &&
                            t.getStations().contains(destination) &&
                            t.getStations().indexOf(origin) < t.getStations().indexOf(destination)
                    )
                ).toList();
    }
}
