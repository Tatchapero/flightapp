package dk.cphbusiness.flightdemo;

import dk.cphbusiness.flightdemo.service.FlightService;

public class Main {
    public static void main(String[] args) {
        FlightService flightService = new FlightService();

        flightService.totalFlightTimeByAirline("Lufthansa");
        flightService.averageFlightTimeByAirline("Lufthansa");
    }
}
