package com.example.demo.service;

import com.example.demo.entity.Flight;
import com.example.demo.entity.Schedule;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FlightRepository;
import com.example.demo.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final ScheduleRepository scheduleRepository;

    public FlightService(FlightRepository flightRepository, ScheduleRepository scheduleRepository) {
        this.flightRepository = flightRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<Flight> getAllFlights(String sort) {
        if ("asc".equalsIgnoreCase(sort)) {
            return flightRepository.findAllByOrderByFlightNumberAsc();
        }
        return flightRepository.findAll();
    }

    public Flight getFlight(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
    }

    public List<Schedule> getFlightSchedules(Long flightId, LocalDateTime date) {
        return scheduleRepository.findByFlightIdAndDepartureTimeAfter(
                flightId, date != null ? date : LocalDateTime.now());
    }
}