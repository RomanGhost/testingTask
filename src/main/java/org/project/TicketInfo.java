package org.project;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.*;

public class TicketInfo {
    private List<Ticket> tickets;

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Map<String, Long> calculateMinimumFlightTimes(){
        Map<String, Long> minTimesPerCarrier = new HashMap<>();
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("dd.MM.yy H:mm")
                .toFormatter();

        for (TicketInfo.Ticket ticket : tickets) {
            try {
                LocalDateTime departure = LocalDateTime.parse(ticket.getDepartureDate() + " " + ticket.getDepartureTime(), formatter);
                LocalDateTime arrival = LocalDateTime.parse(ticket.getArrivalDate() + " " + ticket.getArrivalTime(), formatter);
                long durationMinutes = java.time.Duration.between(departure, arrival).toMinutes();
                long minCarrier = minTimesPerCarrier.getOrDefault(ticket.getCarrier(), durationMinutes);
                if (minCarrier >= durationMinutes)
                    minTimesPerCarrier.put(ticket.getCarrier(), durationMinutes);

            } catch (DateTimeParseException e) {
                System.err.println("Error parsing date/time for ticket: " + e.getMessage());
            }
        }
        return minTimesPerCarrier;
    }

    public double getMedian(){
        List<Integer> prices = this.getPrices();
        if (prices == null || prices.isEmpty()) {
            throw new IllegalArgumentException("Price list is empty or null.");
        }

        // Create a new ArrayList from the immutable list to make it modifiable
        List<Integer> modifiableList = new ArrayList<>(prices);
        Collections.sort(modifiableList);
        System.out.println(modifiableList);

        int middle = modifiableList.size() / 2;
        if (modifiableList.size() % 2 == 0) {
            return (modifiableList.get(middle - 1) + modifiableList.get(middle)) / 2.0;
        } else {
            return modifiableList.get(middle);
        }
    }

    public double getAverage(){
        List<Integer> prices = this.getPrices();
        return prices.stream().mapToDouble(p -> p).average().orElseThrow(() -> new IllegalArgumentException("Error calculating average price."));
    }

    private List<Integer> getPrices(){
        return tickets.stream().map(TicketInfo.Ticket::getPrice).toList();
    }

    public static class Ticket {
        @JsonProperty("origin")
        private String origin;

        @JsonProperty("origin_name")
        private String originName;

        @JsonProperty("destination")
        private String destination;

        @JsonProperty("destination_name")
        private String destinationName;

        @JsonProperty("departure_date")
        private String departureDate;

        @JsonProperty("departure_time")
        private String departureTime;

        @JsonProperty("arrival_date")
        private String arrivalDate;

        @JsonProperty("arrival_time")
        private String arrivalTime;

        @JsonProperty("carrier")
        private String carrier;

        @JsonProperty("stops")
        private int stops;

        @JsonProperty("price")
        private int price;
        public Ticket() {};
        public Ticket(
                String origin,
                String originName,
                String destination,
                String destinationName,
                String departureDate,
                String departureTime,
                String arrivalDate,
                String arrivalTime,
                String carrier,
                int stops,
                int price
        ) {
            this.origin=origin;
            this.originName=originName;
            this.destination=destination;
            this.destinationName=destinationName;
            this.departureDate=departureDate;
            this.departureTime=departureTime;
            this.arrivalDate=arrivalDate;
            this.arrivalTime=arrivalTime;
            this.carrier=carrier;
            this.stops=stops;
            this.price=price;
        }


        // Геттеры и сеттеры
        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getOriginName() {
            return originName;
        }

        public void setOriginName(String originName) {
            this.originName = originName;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getDestinationName() {
            return destinationName;
        }

        public void setDestinationName(String destinationName) {
            this.destinationName = destinationName;
        }

        public String getDepartureDate() {
            return departureDate;
        }

        public void setDepartureDate(String departureDate) {
            this.departureDate = departureDate;
        }

        public String getDepartureTime() {
            return departureTime;
        }

        public void setDepartureTime(String departureTime) {
            this.departureTime = departureTime;
        }

        public String getArrivalDate() {
            return arrivalDate;
        }

        public void setArrivalDate(String arrivalDate) {
            this.arrivalDate = arrivalDate;
        }

        public String getArrivalTime() {
            return arrivalTime;
        }

        public void setArrivalTime(String arrivalTime) {
            this.arrivalTime = arrivalTime;
        }

        public String getCarrier() {
            return carrier;
        }

        public void setCarrier(String carrier) {
            this.carrier = carrier;
        }

        public int getStops() {
            return stops;
        }

        public void setStops(int stops) {
            this.stops = stops;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
