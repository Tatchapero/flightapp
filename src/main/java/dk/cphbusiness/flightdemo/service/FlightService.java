package dk.cphbusiness.flightdemo.service;

import dk.cphbusiness.flightdemo.FlightReader;
import dk.cphbusiness.flightdemo.FlightWriter;
import dk.cphbusiness.flightdemo.dtos.FlightDTO;
import dk.cphbusiness.flightdemo.dtos.FlightInfoDTO;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FlightService {
    List<FlightDTO> flights;
    List<FlightInfoDTO> flightInfoList;

    public FlightService() {
        try {
            this.flights = FlightReader.getFlightsFromFile("flights.json");
            this.flightInfoList = FlightReader.getFlightInfoDetails(flights);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void totalFlightTimeByAirline(String airline) {
        Duration totalFlightTime = flightInfoList.stream()
                .filter(x -> x.getAirline() != null)
                .filter(x -> x.getAirline().equals(airline))
                .map(FlightInfoDTO::getDuration)
                .filter(Objects::nonNull)
                .reduce(Duration.ZERO, Duration::plus);
        System.out.println(airline + ": " + totalFlightTime);
    }

    public void averageFlightTimeByAirline(String airline) {
        Double averageFlightTime = flightInfoList.stream()
                .filter(x -> x.getAirline() != null)
                .filter(x -> x.getAirline().equals(airline))
                .map(x -> x.getDuration().toMinutes())
                .collect(Collectors.averagingLong(x -> x));
        System.out.println(airline + ": " + Duration.ofMinutes(averageFlightTime.longValue()));
    }

    public void listFlightsByDepartureAndArrival(String departureAirport, String arrivalAirport) {
        flightInfoList.stream()
                .filter(x -> x.getOrigin() != null)
                .filter(x -> x.getDestination() != null)
                .filter(x -> x.getOrigin().contains(departureAirport))
                .filter(x -> x.getDestination().contains(arrivalAirport))
                .forEach(System.out::println);
    }
}
