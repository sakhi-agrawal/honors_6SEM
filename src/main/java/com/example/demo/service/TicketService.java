package com.example.demo.service;

import com.example.demo.dto.TicketRequest;
import com.example.demo.entity.Schedule;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.TicketStatus;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ScheduleRepository scheduleRepository;

    public TicketService(TicketRepository ticketRepository, ScheduleRepository scheduleRepository) {
        this.ticketRepository = ticketRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public Ticket createTicket(TicketRequest ticketRequest) {
        Schedule schedule = scheduleRepository.findById(ticketRequest.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + ticketRequest.getScheduleId()));

        if (schedule.getAvailableSeats() <= 0) {
            throw new ValidationException("No seats available for this schedule");
        }

        Ticket ticket = new Ticket();
        ticket.setSchedule(schedule);
        ticket.setPassengerName(ticketRequest.getPassengerName());
        ticket.setSeatNumber(generateSeatNumber());
        ticket.setPrice(calculatePrice(schedule));
        ticket.setStatus(TicketStatus.BOOKED);

        schedule.setAvailableSeats(schedule.getAvailableSeats() - 1);
        scheduleRepository.save(schedule);

        return ticketRepository.save(ticket);
    }

    public Ticket getTicket(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
    }

    @Transactional
    public void cancelTicket(Long id) {
        Ticket ticket = ticketRepository.findByIdAndStatus(id, TicketStatus.BOOKED)
                .orElseThrow(() -> new ValidationException("Active ticket not found with id: " + id));

        ticket.setStatus(TicketStatus.CANCELLED);
        Schedule schedule = ticket.getSchedule();
        schedule.setAvailableSeats(schedule.getAvailableSeats() + 1);

        ticketRepository.save(ticket);
        scheduleRepository.save(schedule);
    }

    private String generateSeatNumber() {
        return "A" + (int)(Math.random() * 100); // Simple seat number generator
    }

    private BigDecimal calculatePrice(Schedule schedule) {
        return BigDecimal.valueOf(150.00); // Fixed price for simplicity
    }
}