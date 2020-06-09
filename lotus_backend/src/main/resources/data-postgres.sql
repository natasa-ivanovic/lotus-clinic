insert into clinics (address, description, name) values ('Jevrejska 2', 'General clinic', 'Clinic1');
insert into clinics (address, description, name) values ('Fruskogorska 12', 'Specialist clinic', 'Clinic2');

insert into rooms (name, clinic_id) values ('C1-Room 100', 1);
insert into rooms (name, clinic_id) values ('C1-Room 105', 1);
insert into rooms (name, clinic_id) values ('C2-Room 210', 2);
insert into rooms (name, clinic_id) values ('C2-Room 230', 2);

insert into appointment_types (name, price, discount, clinic_id) values ('Office visit', 200, 0, 1);
insert into appointment_types (name, price, discount, clinic_id) values ('Physical evaluation', 300, 50, 1);
insert into appointment_types (name, price, discount, clinic_id) values ('Dentist appointment', 400, 0, 2);
insert into appointment_types (name, price, discount, clinic_id) values ('Optometrist appointment', 500, 10, 2);

/* clinic 1 prices */
insert into appointment_price (discount, price, clinic_id, type_id) values (0, 1000, 1, 1);
insert into appointment_price (discount, price, clinic_id, type_id) values (0, 500, 1, 2);
insert into appointment_price (discount, price, clinic_id, type_id) values (0, 2000, 1, 4);

/* clinic 2 prices */
insert into appointment_price (discount, price, clinic_id, type_id) values (0, 1500, 2, 1);
insert into appointment_price (discount, price, clinic_id, type_id) values (0, 750, 2, 2);
insert into appointment_price (discount, price, clinic_id, type_id) values (0, 1500, 2, 3);


insert into clinical_centres (name) values ('Centre 1');

/* patients */
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinical_centre_id, role, enabled, last_password_reset_date) values (12345678912, 'Novosadski put 17', '1998-09-23 02:00:00', 'Subotica', 'Srbija', 'patient1@gmail.com', 1, 'Packijento', '$2y$10$Hgm8RGydwLrN9vbZCP5SQ.Va7TTnA1RMH3W8FBERwO6tEISeIj9ee', '0699114909', 'Pacijentic', 1, 'PATIENT', true, '2020-04-30 16:00:00.508-07');
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinical_centre_id, role, enabled, last_password_reset_date) values (23456789012, 'Bulevar oslobodjenja 182', '1998-05-01 02:00:00', 'Novi Sad', 'Srbija', 'patient2@gmail.com', 0, 'Pacijenta', '$2y$10$rOD92NoFS6CAv90TTa4u7uqLIOKL8qm0P2WCbAVAnUv6GGHBIDhQC', '066124909', 'Pacijentanovic', 1, 'PATIENT', true, '2020-04-30 16:00:00.508-07');
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinical_centre_id, role, enabled, last_password_reset_date) values (51927654821, 'Puskinova 13', '1997-01-05 02:00:00', 'Novi Sad', 'Srbija', 'patient3@gmail.com', 0, 'Pacijenta', '$2y$10$Wrm.29pcasDLrhgZ.ZNfIetGo8RUX/tGEdT.MF7uJqnSTb859bn8K', '066124909', 'Pacijentanovic', 1, 'PATIENT', true, '2020-04-30 16:00:00.508-07');
/* doctors */
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinic_id, role, enabled, last_password_reset_date, specialty_id) values (42367854326, 'Fruskogorska 66', '1995-02-22 02:00:00', 'Novi Sad', 'Srbija', 'doctor1@gmail.com', 1, 'Doktorko', '$2y$10$z8hfS/qHY011xAZ4qpMutOdAVRufZbPYVQyXOsO37InW4nwqTA6V2', '0699114909', 'Dokic', 1, 'DOCTOR', true, '2020-04-30 16:00:00.508-07', 1);
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinic_id, role, enabled, last_password_reset_date, specialty_id) values (53451231231, 'Bulevar Cara Lazara 15', '1996-01-15 02:00:00', 'Novi Sad', 'Srbija', 'doctor2@gmail.com', 0, 'Doktorka', '$2y$10$0Z7PPfPQ94jhjz5j.6UAt.JQxw5w7RdvCr.LdMhenZWnu4vJW.s1y', '0699114909', 'Dakic', 2, 'DOCTOR', true, '2020-04-30 16:00:00.508-07', 4);

/* nurses */
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinic_id, role, enabled, last_password_reset_date) values (65451231354, 'Novosadski put 17', '1997-02-22 02:00:00', 'Novi Sad', 'Srbija', 'nurse1@gmail.com', 1, 'Medinka', '$2a$10$t7KnHQ52xqsAdt7m.WXYs.1GdxOOMbPcbLJBBmFzhVaiCKkp9OKGu', '0699114909', 'Sestric', 1, 'NURSE', true, '2020-04-30 16:00:00.508-07');
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinic_id, role, enabled, last_password_reset_date) values (54326162342, 'Futoska 5', '1996-01-15 02:00:00', 'Novi Sad', 'Srbija', 'nurse2@gmail.com', 0, 'Medinko', '$2a$10$lIe3MF.s.bJ801yZMnyMn.xvi2KJEOT9Z3b.V0Kx2piI2zaiaEWqO', '0699114909', 'Bratic', 2, 'NURSE', true, '2020-04-30 16:00:00.508-07');
/* clinic admin*/
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinic_id, role, enabled, last_password_reset_date) values (39845792020, 'Fruskogorska 13', '1997-02-22 02:00:00', 'Novi Sad', 'Srbija', 'admin1@gmail.com', 1, 'Admin1', '$2a$10$6ofB7GAQD./9NBiw1G/gXeX/T6HJxZOJwRImL.iSFlenlq/pJN7AW', '0699114909', 'Sestric', 1, 'ADMIN', true, '2020-04-30 16:00:00.508-07');
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinic_id, role, enabled, last_password_reset_date) values (02384556392, 'Bulevar Kralja Petra', '1996-01-15 02:00:00', 'Novi Sad', 'Srbija', 'admin2@gmail.com', 0, 'Admin2', '$2a$10$1nLp9miNCMRxUqBP9GD6geDdJweGwzanlC9LSuOLogua4qQHTw9NW', '0699114909', 'Bratic', 2, 'ADMIN', true, '2020-04-30 16:00:00.508-07');
/*centre admin*/
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinical_centre_id, role, enabled, last_password_reset_date) values (19475395694, 'Fruskogorska 13', '1997-02-22 02:00:00', 'Novi Sad', 'Srbija', 'cadmin1@gmail.com', 1, 'Adminac1', '$2a$10$AVLSu/VvFmH8Y..pb/w.ke9Vikpen4OCjjYE.CHOyqE7Mu.HU8WvG', '0699114909', 'Sestric', 1, 'CENTRE_ADMIN', true, '2020-04-30 16:00:00.508-07');
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinical_centre_id, role, enabled, last_password_reset_date) values (20572038573, 'Bulevar Kralja Petra', '1996-01-15 02:00:00', 'Novi Sad', 'Srbija', 'cadmin2@gmail.com', 0, 'Adminac2', '$2a$10$7T98siUqqzPjKqHENnHOyeSf9FaRN404vBH44r7As38GOCxUwtBle', '0699114909', 'Bratic', 1, 'CENTRE_ADMIN', true, '2020-04-30 16:00:00.508-07');

/* more doctors, inserted here so we don't have to manually change ids from other data, ids start from 12 */

insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinic_id, role, enabled, last_password_reset_date, specialty_id) values (65412378925, 'Kraljievca Marka 1', '1994-02-22 02:00:00', 'Novi Sad', 'Srbija', 'doctor3@gmail.com', 0, 'Dragana', '$2y$10$OJZywu3dD0EHOYHP/gnSR.rtbn/RPO.TM5FNbwGYW0vDmaQtfVJV.', '0698594526', 'Doktoric', 1, 'DOCTOR', true, '2020-04-30 16:00:00.508-07', 1);
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinic_id, role, enabled, last_password_reset_date, specialty_id) values (96854575153, 'Berislava Berica 62', '1989-01-15 02:00:00', 'Novi Sad', 'Srbija', 'doctor4@gmail.com', 1, 'Dominik', '$2y$10$FIBWbrMtr1SU.jZIum2uheCvqYGOhzw1XdXYmYd.zlwQnVNxAgLra', '066489123', 'Dominicevic', 2, 'DOCTOR', true, '2020-04-30 16:00:00.508-07', 6);
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinic_id, role, enabled, last_password_reset_date, specialty_id) values (63594857861, 'Kisacka 11', '1992-02-22 02:00:00', 'Novi Sad', 'Srbija', 'doctor5@gmail.com', 1, 'Davor', '$2y$10$rS0kN.RAOu/AwT1HjT2LieuQ2n/R.xXFFK/GuC1dfyExtuGWDphjG', '063789417', 'Davorovic', 1, 'DOCTOR', true, '2020-04-30 16:00:00.508-07', 3);
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinic_id, role, enabled, last_password_reset_date, specialty_id) values (36995475628, 'Dositejeva 96', '1990-01-15 02:00:00', 'Novi Sad', 'Srbija', 'doctor6@gmail.com', 1, 'Dimitrije', '$2y$10$LNnQfpepzcKbH1YySP74lOsZ7r1FDCcYiKJfHrD/6dj0HxoJQ53KO', '069546209', 'Dimitrijevic', 2, 'DOCTOR', true, '2020-04-30 16:00:00.508-07', 5);



/* inactive patients */
insert into users (ssid, address, birth_date, city, country, username, gender, name, password, phone_number, surname, clinical_centre_id, role, enabled, last_password_reset_date) values (42334854326, 'Novosadski put 17', '1998-09-23 02:00:00', 'Subotica', 'Srbija', 'patient4@gmail.com', 1, 'Packijento', '$2a$10$xyNTH/znnPkKlzWbeN1SiOAfQjGK9oqK7WAxzZGHMhI2YaSpoM8Ze', '0699114909', 'Pacijentic', 1, 'PATIENT', false, '2020-04-30 16:00:00.508-07');

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

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (12, 2);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (13, 2);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (14, 2);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (15, 2);

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (6, 3);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (7, 3);

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (8, 4);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (9, 4);

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (10, 5);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (11, 5);

insert into medicines (name, clinical_centre_id) values ('Acetaminophen', 1);
insert into medicines (name, clinical_centre_id) values ('Ibuprofene', 1);
insert into medicines (name, clinical_centre_id) values ('Iben', 1);

insert into diagnosis (name, clinical_centre_id) values ('Tuberculosis', 1);
insert into diagnosis (name, clinical_centre_id) values ('Cold', 1);
insert into diagnosis (name, clinical_centre_id) values ('COVID-19', 1);

insert into allergies (name, clinical_centre_id) values ('Pollen', 1);
insert into allergies (name, clinical_centre_id) values ('Pork', 1);
insert into allergies (name, clinical_centre_id) values ('Ibuprofen', 1);
insert into allergies (name, clinical_centre_id) values ('Tomato', 1);

insert into medical_records (blood_type, height, weight, patient_id) values ('A', '196', '95', 1);
insert into medical_records (blood_type, height, weight, patient_id) values ('B', '170', '57', 2);
insert into medical_records (blood_type, height, weight, patient_id) values ('A', '175', '65', 3);

insert into allergies_medical_records (allergies_id, medical_records_patient_id) values (1, 2);
insert into allergies_medical_records (allergies_id, medical_records_patient_id) values (2, 2);
insert into allergies_medical_records (allergies_id, medical_records_patient_id) values (3, 3);
insert into allergies_medical_records (allergies_id, medical_records_patient_id) values (4, 3);

/* appointments and calendar entries */
insert into appointments (start_date, end_date, price, discount, status, appointment_type_id, room_id, doctor_id, clinic_id, medical_record_patient_id, reviewed, information) values ('2020-05-12 17:25:00.508-07', '2020-05-12 17:55:00.508-07', 100, 0, 4, 1, 1, 4, 1, 1, false, 'Patient reports coughing up blood, chest pains for over a week and night sweats.');
insert into calendar (start_date, end_date, medical_person_id, appointment_id) values('2020-05-12 17:25:00.508-07', '2020-05-12 17:55:00.508-07', 4, 1);
insert into appointments (start_date, end_date, price, discount, status, appointment_type_id, room_id, doctor_id, clinic_id, medical_record_patient_id, reviewed, information) values ('2020-05-12 12:30:00.508-07', '2020-05-12 13:00:00.508-07', 100, 0, 4, 1, 3, 5, 2, 1, false, 'Patient reports symptoms of a common cold. To be treated with over the counter medication and stay at home.');
insert into calendar (start_date, end_date, medical_person_id, appointment_id) values('2020-05-12 12:30:00.508-07', '2020-05-12 13:00:00.508-07', 4, 2);
insert into appointments (start_date, end_date, price, discount, status, appointment_type_id, room_id, doctor_id, clinic_id, medical_record_patient_id, reviewed, information) values ('2020-05-12 22:00:00.508-07', '2020-05-12 22:30:00.508-07', 100, 0, 4, 1, 1, 4, 1, 3, false, 'Patient tested positive for COVID-19. Report immediately to specialist clinic');
insert into calendar (start_date, end_date, medical_person_id, appointment_id) values('2020-05-12 22:00:00.508-07', '2020-05-12 22:30:00.508-07', 4, 3);
/*insert into appointments (start_date, end_date, price, discount, status, appointment_type_id, room_id, doctor_id, clinic_id, medical_record_patient_id, reviewed) values ('2020-05-11 23:00:00.508-07', '2020-05-11 23:30:00.508-07', 100, 0, 1, 1, 1, 4, 1, 1, false);*/


insert into appointments (start_date, end_date, price, discount, status, appointment_type_id, room_id, doctor_id, clinic_id, medical_record_patient_id, reviewed) values ('2020-05-24 10:00:00.508-07', '2020-05-24 10:30:00.508-07', 100, 0, 1, 2, 3, 5, 2, 1, false);
insert into calendar (start_date, end_date, medical_person_id, appointment_id) values('2020-05-24 10:00:00.508-07', '2020-05-24 10:30:00.508-07', 5, 4);
insert into appointments (start_date, end_date, price, discount, status, appointment_type_id, room_id, doctor_id, clinic_id, medical_record_patient_id, reviewed) values ('2020-05-24 14:00:00.508-07', '2020-05-24 14:30:00.508-07', 100, 0, 1, 2, 3, 5, 2, 1, false);
insert into calendar (start_date, end_date, medical_person_id, appointment_id) values('2020-05-24 14:00:00.508-07', '2020-05-24 14:30:00.508-07', 5, 5);
insert into appointments (start_date, end_date, price, discount, status, appointment_type_id, room_id, doctor_id, clinic_id, medical_record_patient_id, reviewed) values ('2020-05-24 18:00:00.508-07', '2020-05-24 18:30:00.508-07', 100, 0, 1, 2, 4, 5, 2, 2, false);
insert into calendar (start_date, end_date, medical_person_id, appointment_id) values('2020-05-24 18:00:00.508-07', '2020-05-24 18:30:00.508-07', 5, 6);
insert into appointments (start_date, end_date, price, discount, status, appointment_type_id, room_id, doctor_id, clinic_id, medical_record_patient_id, reviewed) values ('2020-05-24 19:00:00.508-07', '2020-05-24 19:30:00.508-07', 100, 0, 1, 2, 4, 5, 2, 2, false);
insert into calendar (start_date, end_date, medical_person_id, appointment_id) values('2020-05-24 19:00:00.508-07', '2020-05-24 19:30:00.508-07', 5, 7);


insert into diagnosis_appointments(diagnosis_id, appointments_id) values (1, 1);
insert into diagnosis_appointments(diagnosis_id, appointments_id) values (2, 2);
insert into diagnosis_appointments(diagnosis_id, appointments_id) values (3, 3);

/* requests */
insert into requests(req_type, status, date, type, doctor, patient) values('ROOM', 0, '2020-05-19 10:00:00.508-07', 0, 5, 1);
insert into requests(req_type, status, date, type, doctor, patient) values('ROOM', 0, '2020-05-19 16:00:00.508-07', 0, 5, 2);
insert into requests(req_type, status, date, type, doctor, patient) values('ROOM', 0, '2020-07-19 17:00:00.508-07', 0, 5, 3);

insert into requests(req_type, status, patient_id) values('REGISTRATION', 0, 16);



/* doctor reviews */

insert into doctor_review (grade, doctor_id) values (5, 4);
insert into doctor_review (grade, doctor_id) values (4.5, 5);
insert into doctor_review (grade, doctor_id) values (3.5, 12);
insert into doctor_review (grade, doctor_id) values (4, 13);
insert into doctor_review (grade, doctor_id) values (5, 14);
insert into doctor_review (grade, doctor_id) values (2, 15);

insert into doctor_review (grade, doctor_id) values (4.5, 4);
insert into doctor_review (grade, doctor_id) values (4, 5);
insert into doctor_review (grade, doctor_id) values (4, 12);
insert into doctor_review (grade, doctor_id) values (3.5, 13);
insert into doctor_review (grade, doctor_id) values (3, 14);
insert into doctor_review (grade, doctor_id) values (3, 15);


