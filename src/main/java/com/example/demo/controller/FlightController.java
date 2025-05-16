package com.example.demo.controller;

import com.example.demo.entity.Flight;
import com.example.demo.entity.Schedule;
import com.example.demo.service.FlightService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights(@RequestParam(required = false) String sort) {
        return ResponseEntity.ok(flightService.getAllFlights(sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlight(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlight(id));
    }

    @GetMapping("/{id}/schedules")
    public ResponseEntity<List<Schedule>> getFlightSchedules(
            @PathVariable Long id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dates) {
        return ResponseEntity.ok(flightService.getFlightSchedules(id, dates));
    }
}