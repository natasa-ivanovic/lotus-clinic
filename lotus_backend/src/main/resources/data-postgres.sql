insert into clinics (address, description, name) values ('Jevrejska 2', 'General clinic', 'Clinic1');
insert into clinics (address, description, name) values ('Fruskogorska 12', 'Specialist clinic', 'Clinic2');

insert into rooms (name, clinic_id) values ('C1-Room 100', 1);
insert into rooms (name, clinic_id) values ('C1-Room 105', 1);
insert into rooms (name, clinic_id) values ('C2-Room 210', 2);
insert into rooms (name, clinic_id) values ('C2-Room 230', 2);

insert into appointment_types (name, clinic_id) values ('Office visit', 1);
insert into appointment_types (name, clinic_id) values ('Physical evaluation', 1);
insert into appointment_types (name, clinic_id) values ('Dentist appointment', 2);
insert into appointment_types (name, clinic_id) values ('Optometrist appointment', 2);

insert into clinical_centres (name) values ('Centre 1');

/* patients */
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinical_centre_id, role, enabled, last_password_reset_date) values (12345678912, 'Novosadski put 17', '1998-09-23 02:00:00', 'Subotica', 'Srbija', 'patient1@gmail.com', 1, 'Packijento', '$2y$10$Hgm8RGydwLrN9vbZCP5SQ.Va7TTnA1RMH3W8FBERwO6tEISeIj9ee', '0699114909', 'Pacijentic', 1, 'PATIENT', true, '2020-04-30 16:00:00.508-07');
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinical_centre_id, role, enabled, last_password_reset_date) values (23456789012, 'Bulevar oslobodjenja 182', '1998-05-01 02:00:00', 'Novi Sad', 'Srbija', 'patient2@gmail.com', 0, 'Pacijenta', '$2y$10$rOD92NoFS6CAv90TTa4u7uqLIOKL8qm0P2WCbAVAnUv6GGHBIDhQC', '066124909', 'Pacijentanovic', 1, 'PATIENT', true, '2020-04-30 16:00:00.508-07');
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinical_centre_id, role, enabled, last_password_reset_date) values (51927654821, 'Puskinova 13', '1997-01-05 02:00:00', 'Novi Sad', 'Srbija', 'patient3@gmail.com', 0, 'Pacijenta', '$2y$10$Wrm.29pcasDLrhgZ.ZNfIetGo8RUX/tGEdT.MF7uJqnSTb859bn8K', '066124909', 'Pacijentanovic', 1, 'PATIENT', true, '2020-04-30 16:00:00.508-07');
/* doctors */
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinic_id, role, enabled, last_password_reset_date, specialty_id) values (42367854326, 'Fruskogorska 66', '1995-02-22 02:00:00', 'Novi Sad', 'Srbija', 'doctor1@gmail.com', 1, 'Doktorko', '$2y$10$z8hfS/qHY011xAZ4qpMutOdAVRufZbPYVQyXOsO37InW4nwqTA6V2', '0699114909', 'Dokic', 1, 'DOCTOR', true, '2020-04-30 16:00:00.508-07', 1);
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinic_id, role, enabled, last_password_reset_date, specialty_id) values (53451231231, 'Bulevar Cara Lazara 15', '1996-01-15 02:00:00', 'Novi Sad', 'Srbija', 'doctor2@gmail.com', 0, 'Doktorka', '$2y$10$0Z7PPfPQ94jhjz5j.6UAt.JQxw5w7RdvCr.LdMhenZWnu4vJW.s1y', '0699114909', 'Dakic', 2, 'DOCTOR', true, '2020-04-30 16:00:00.508-07', 2);
/* nurses */
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinic_id, role, enabled, last_password_reset_date) values (65451231354, 'Novosadski put 17', '1997-02-22 02:00:00', 'Novi Sad', 'Srbija', 'nurse1@gmail.com', 1, 'Medinka', '$2a$10$t7KnHQ52xqsAdt7m.WXYs.1GdxOOMbPcbLJBBmFzhVaiCKkp9OKGu', '0699114909', 'Sestric', 1, 'NURSE', true, '2020-04-30 16:00:00.508-07');
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinic_id, role, enabled, last_password_reset_date) values (54326162342, 'Futoska 5', '1996-01-15 02:00:00', 'Novi Sad', 'Srbija', 'nurse2@gmail.com', 0, 'Medinko', '$2a$10$lIe3MF.s.bJ801yZMnyMn.xvi2KJEOT9Z3b.V0Kx2piI2zaiaEWqO', '0699114909', 'Bratic', 2, 'NURSE', true, '2020-04-30 16:00:00.508-07');
/* clinic admin*/
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinic_id, role, enabled, last_password_reset_date) values (39845792020, 'Fruskogorska 13', '1997-02-22 02:00:00', 'Novi Sad', 'Srbija', 'admin1@gmail.com', 1, 'Admin1', '$2a$10$6ofB7GAQD./9NBiw1G/gXeX/T6HJxZOJwRImL.iSFlenlq/pJN7AW', '0699114909', 'Sestric', 1, 'ADMIN', true, '2020-04-30 16:00:00.508-07');
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinic_id, role, enabled, last_password_reset_date) values (02384556392, 'Bulevar Kralja Petra', '1996-01-15 02:00:00', 'Novi Sad', 'Srbija', 'admin2@gmail.com', 0, 'Admin2', '$2a$10$1nLp9miNCMRxUqBP9GD6geDdJweGwzanlC9LSuOLogua4qQHTw9NW', '0699114909', 'Bratic', 2, 'ADMIN', true, '2020-04-30 16:00:00.508-07');
/*centre admin*/
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinical_centre_id, role, enabled, last_password_reset_date) values (19475395694, 'Fruskogorska 13', '1997-02-22 02:00:00', 'Novi Sad', 'Srbija', 'cadmin1@gmail.com', 1, 'Adminac1', '$2a$10$AVLSu/VvFmH8Y..pb/w.ke9Vikpen4OCjjYE.CHOyqE7Mu.HU8WvG', '0699114909', 'Sestric', 1, 'CENTRE_ADMIN', true, '2020-04-30 16:00:00.508-07');
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinical_centre_id, role, enabled, last_password_reset_date) values (20572038573, 'Bulevar Kralja Petra', '1996-01-15 02:00:00', 'Novi Sad', 'Srbija', 'cadmin2@gmail.com', 0, 'Adminac2', '$2a$10$7T98siUqqzPjKqHENnHOyeSf9FaRN404vBH44r7As38GOCxUwtBle', '0699114909', 'Bratic', 1, 'CENTRE_ADMIN', true, '2020-04-30 16:00:00.508-07');

/* unactive patients */
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinical_centre_id, role, enabled, last_password_reset_date) values (42334854326, 'Novosadski put 17', '1998-09-23 02:00:00', 'Subotica', 'Srbija', ''patient4@gmail.com', 1, 'Packijento', '$2a$10$xyNTH/znnPkKlzWbeN1SiOAfQjGK9oqK7WAxzZGHMhI2YaSpoM8Ze', '0699114909', 'Pacijentic', 1, 'PATIENT', false, '2020-04-30 16:00:00.508-07');

insert into authority (name) values ('ROLE_PATIENT');
insert into authority (name) values ('ROLE_DOCTOR');
insert into authority (name) values ('ROLE_NURSE');
insert into authority (name) values ('ROLE_ADMIN');
insert into authority (name) values ('ROLE_CENTRE_ADMIN');

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (3, 1);

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (4, 2);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (5, 2);

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (6, 3);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (7, 3);

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (8, 4);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (9, 4);

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (10, 5);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (11, 5);

insert into medicines (name) values ('Acetaminophen');
insert into medicines (name) values ('Ibuprofene');
insert into medicines (name) values ('Iben');

insert into diagnosis (name) values ('Tuberculosis');
insert into diagnosis (name) values ('Cold');
insert into diagnosis (name) values ('COVID-19');

insert into medical_records (allergies, blood_type, height, weight, patient_id) values ('polen', 'A', '196', '95', 1);
insert into medical_records (allergies, blood_type, height, weight, patient_id) values ('pork, ibuprofen', 'B', '170', '57', 2);
insert into medical_records (allergies, blood_type, height, weight, patient_id) values ('tomato', 'A', '175', '65', 3);

insert into appointments (start_date, end_date, status, appointment_type_id, room_id, doctor_id, clinic_id) values ('2020-05-05 16:00:00.508-07', '2020-05-05 16:30:00.508-07', 0, 1, 1, 4, 1);
insert into appointments (start_date, end_date, status, appointment_type_id, room_id, doctor_id, clinic_id) values ('2020-05-05 16:30:00.508-07', '2020-05-05 17:00:00.508-07', 0, 1, 1, 4, 1);
insert into appointments (start_date, end_date, status, appointment_type_id, room_id, doctor_id, clinic_id) values ('2020-05-06 17:00:00.508-07', '2020-05-06 17:30:00.508-07', 0, 1, 1, 4, 1);
insert into appointments (start_date, end_date, status, appointment_type_id, room_id, doctor_id, clinic_id) values ('2020-05-06 17:30:00.508-07', '2020-05-06 18:00:00.508-07', 0, 1, 1, 4, 1);
insert into appointments (start_date, end_date, status, appointment_type_id, room_id, doctor_id, clinic_id) values ('2020-05-07 16:00:00.508-07', '2020-05-07 16:30:00.508-07', 0, 2, 2, 5, 2);
insert into appointments (start_date, end_date, status, appointment_type_id, room_id, doctor_id, clinic_id) values ('2020-05-07 16:30:00.508-07', '2020-05-07 17:00:00.508-07', 0, 2, 2, 5, 2);
insert into appointments (start_date, end_date, status, appointment_type_id, room_id, doctor_id, clinic_id) values ('2020-05-08 17:00:00.508-07', '2020-05-08 17:30:00.508-07', 0, 2, 2, 5, 2);
insert into appointments (start_date, end_date, status, appointment_type_id, room_id, doctor_id, clinic_id) values ('2020-05-08 17:30:00.508-07', '2020-05-08 18:00:00.508-07', 0, 2, 2, 5, 2);

/* requests */
insert into requests(req_type, status, patient_id) values('REGISTRATION', 0, 12);

