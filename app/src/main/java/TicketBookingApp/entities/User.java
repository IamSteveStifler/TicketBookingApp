package TicketBookingApp.entities;

import java.util.List;

public class User {
    private String userId;
    private String name;
    private String password;
    private String hashedPassword;
    private List<Ticket> ticketsBooked;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public List<Ticket> getTicketsBooked() {
        return ticketsBooked;
    }

    public void setTicketsBooked(List<Ticket> ticketsBooked) {
        this.ticketsBooked = ticketsBooked;
    }

    public void printBookedTickets() {
        ticketsBooked.forEach(e -> System.out.println(
                e.getTicketId() + " " + e.getOrigin() + "->" + e.getDestination()
        ));
    }

}
