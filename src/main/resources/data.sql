INSERT INTO FLIGHT (flight_number, origin, destination) VALUES
('FL001', 'New York', 'London'),
('FL002', 'London', 'Paris'),
('FL003', 'Paris', 'Tokyo');

INSERT INTO SCHEDULE (flight_id, departure_time, arrival_time, available_seats) VALUES
(1, '2025-05-17 10:00:00', '2025-05-17 22:00:00', 100),
(2, '2025-05-17 14:00:00', '2025-05-17 16:00:00', 150),
(3, '2025-05-17 18:00:00', '2025-05-18 10:00:00', 200); 