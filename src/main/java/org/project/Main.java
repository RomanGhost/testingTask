package org.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

public class Main {
    public static void main(String[] args) {
        try {
            TicketInfo ticketInfo = new TicketInfo();

            List<TicketInfo.Ticket> tickets = loadTicketsFromFile("tickets.json");
            List<TicketInfo.Ticket> filteredTickets = filterTickets(tickets, "VVO", "TLV");
            ticketInfo.setTickets(filteredTickets);

            if (filteredTickets.isEmpty()) {
                System.err.println("No tickets found for the specified route (Vladivostok to Tel Aviv).");
                return;
            }

            Map<String, Long> minTimesPerCarrier = ticketInfo.calculateMinimumFlightTimes();
            minTimesPerCarrier.forEach((carrier, time) ->
                    System.out.println("Carrier: " + carrier + ", Min Flight Time: " + time + " minutes"));

            double averagePrice = ticketInfo.getAverage();
            double medianPrice = ticketInfo.getMedian();
            double difference = abs(averagePrice-medianPrice);

            System.out.println("Average Price: " + averagePrice);
            System.out.println("Median Price: " + medianPrice);
            System.out.println("Difference: " + difference);

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private static List<TicketInfo.Ticket> loadTicketsFromFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("The file '" + filePath + "' does not exist.");
        }
        TicketInfo ticketInfo = objectMapper.readValue(file, TicketInfo.class);
        return ticketInfo.getTickets();
    }

    private static List<TicketInfo.Ticket> filterTickets(List<TicketInfo.Ticket> tickets, String origin, String destination) {
        return tickets.stream()
                .filter(t -> origin.equals(t.getOrigin()) && destination.equals(t.getDestination()))
                .collect(Collectors.toList());
    }

}
