import org.junit.Before;
import org.junit.Test;
import org.project.Main;
import org.project.TicketInfo;

import java.util.*;

import static java.lang.Math.abs;
import static junit.framework.TestCase.assertEquals;

public class TicketInfoTests {
    private Main mainApp;
    private TicketInfo testTicketsInfo;

    @Before
    public void setUp() {
        mainApp = new Main();
        List<TicketInfo.Ticket> testTickets = Arrays.asList(
                new TicketInfo.Ticket("VVO", "Владивосток", "TLV", "Тель-Авив", "12.05.18", "9:40", "12.05.18", "19:25", "SU", 3, 12450),
                new TicketInfo.Ticket("VVO", "Владивосток", "TLV", "Тель-Авив", "12.05.18", "16:50", "12.05.18", "23:35", "SU", 1, 16700),
                new TicketInfo.Ticket("VVO", "Владивосток", "TLV", "Тель-Авив", "12.05.18", "17:00", "12.05.18", "23:30", "TK", 2, 11000)
        );
        testTicketsInfo = new TicketInfo();
        testTicketsInfo.setTickets(testTickets);
    }

    @Test
    public void testMinFlightTimes() {
        Map<String, Long> minTimes = testTicketsInfo.calculateMinimumFlightTimes();
        assertEquals(Long.valueOf(579), minTimes.get("SU"));
        assertEquals(Long.valueOf(390), minTimes.get("TK"));
    }

    @Test
    public void testPriceCalculations() {
        double averagePrice = testTicketsInfo.getAverage();
        double medianPrice = testTicketsInfo.getMedian();
        double difference = abs(averagePrice-medianPrice);
        assertEquals(13383.33, averagePrice, 0.01); // Average
        assertEquals(12450, medianPrice, 0.01);    // Median
        assertEquals(933.33, difference, 0.01);  // Difference
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyList() {
        testTicketsInfo.setTickets(List.of());
        testTicketsInfo.calculateMinimumFlightTimes();
    }
}
