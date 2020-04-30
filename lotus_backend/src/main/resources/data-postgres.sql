insert into clinics (address, description, name) values ('Jevrejska 2', 'General clinic', 'Clinic1');
insert into clinics (address, description, name) values ('Fruskogorska 12', 'Specialist clinic', 'Clinic2');

insert into rooms (name, clinic_id) values ('Operation room', 1);
insert into rooms (name, clinic_id) values ('Appointment room', 1);
insert into rooms (name, clinic_id) values ('Specijalist room', 2);
insert into rooms (name, clinic_id) values ('Appointment room', 2);

insert into appointment_types (name, clinic_id) values ('Examination', 1);
insert into appointment_types (name, clinic_id) values ('Check-up', 1);
insert into appointment_types (name, clinic_id) values ('Specialist appointment', 2);
insert into appointment_types (name, clinic_id) values ('Post-operation check-up', 2);

insert into clinical_centres (name) values ('Centre 1');

/* patients */
insert into users (ssid, address, birth_date, city, country, email, gender, name, password, phone_number, surname, clinic_id, role) values (12345678912, 'Novosadski put 27', '1998-09-23 02:00:00', 'Subotica', 'Srbija', 'mario@gmail.com', 1, 'Mario', 'mario', '0699114909', 'Kujundzic', 1, 'PATIENT');
insert into users (ssid, address, birth_date, city, country, email, gender, name, password, phone_number, surname, clinic_id, role) values (23456789012, 'Bulevar oslobodjenja 182', '1998-05-01 02:00:00', 'Novi Sad', 'Srbija', 'natasa@gmail.com', 0, 'Natasa', 'natasa', '066124909', 'Ivanovic', 1, 'PATIENT');
insert into users (ssid, address, birth_date, city, country, email, gender, name, password, phone_number, surname, clinic_id, role) values (34567890123, 'Adice 12', '1998-06-12 02:00:00', 'Novi Sad', 'Srbija', 'bela@gmail.com', 1, 'Bela', 'bela', '069324909', 'Vajda', 2, 'PATIENT');
/* doctors */
insert into users (ssid, address, birth_date, city, country, email, gender, name, password, phone_number, surname, clinic_id, role) values (42367854326, 'Novosadski put 17', '1995-02-22 02:00:00', 'Novi Sad', 'Srbija', 'doc1@gmail.com', 1, 'Doktorko', 'doc1', '0699114909', 'Dokic', 1, 'DOCTOR');
insert into users (ssid, address, birth_date, city, country, email, gender, name, password, phone_number, surname, clinic_id, role) values (53451231231, 'Futoska 5', '1996-01-15 02:00:00', 'Novi Sad', 'Srbija', 'doc2@gmail.com', 0, 'Doktorka', 'doc2', '0699114909', 'Dakic', 2, 'DOCTOR');
/* nurses */
insert into users (ssid, address, birth_date, city, country, email, gender, name, password, phone_number, surname, clinic_id, role) values (65451231354, 'Novosadski put 17', '1997-02-22 02:00:00', 'Novi Sad', 'Srbija', 'nurse1@gmail.com', 1, 'Medinka', 'nurse1', '0699114909', 'Sestric', 1, 'NURSE');
insert into users (ssid, address, birth_date, city, country, email, gender, name, password, phone_number, surname, clinic_id, role) values (54326162342, 'Futoska 5', '1996-01-15 02:00:00', 'Novi Sad', 'Srbija', 'nurse2@gmail.com', 0, 'Medinko', 'nurse2', '0699114909', 'Bratic', 2, 'NURSE');
/*centre admin*/
insert into users (ssid, address, birth_date, city, country, email, gender, name, password, phone_number, surname, clinical_centre_id, role) values (19475395694, 'Fruskogorska 13', '1997-02-22 02:00:00', 'Novi Sad', 'Srbija', 'centreadmin1@gmail.com', 1, 'Adminac1', 'cadmin1', '0699114909', 'Sestric', 1, 'CENTRE_ADMIN');
insert into users (ssid, address, birth_date, city, country, email, gender, name, password, phone_number, surname, clinical_centre_id, role) values (20572038573, 'Bulevar Kralja Petra', '1996-01-15 02:00:00', 'Novi Sad', 'Srbija', 'centreadmin2@gmail.com', 0, 'Adminac2', 'cadmin2', '0699114909', 'Bratic', 1, 'CENTRE_ADMIN');
/* clinic admin*/
insert into users (ssid, address, birth_date, city, country, email, gender, name, password, phone_number, surname, clinic_id, role) values (39845792020, 'Fruskogorska 13', '1997-02-22 02:00:00', 'Novi Sad', 'Srbija', 'clinicadmin1@gmail.com', 1, 'Admin1', 'admin1', '0699114909', 'Sestric', 1, 'ADMIN');
insert into users (ssid, address, birth_date, city, country, email, gender, name, password, phone_number, surname, clinic_id, role) values (02384556392, 'Bulevar Kralja Petra', '1996-01-15 02:00:00', 'Novi Sad', 'Srbija', 'clinicadmin2@gmail.com', 0, 'Admin2', 'admin2', '0699114909', 'Bratic', 2, 'ADMIN');

insert into medicines (id, name) values (1, 'Acetaminophen');
insert into medicines (id, name) values (2, 'Ibuprofen');

insert into diagnosis (id, name) values (1, 'Tuberculosis');
insert into diagnosis (id, name) values (2, 'Cold');
insert into diagnosis (id, name) values (3, 'COVID-19');

insert into medical_records (allergies, blood_type, height, weight, patient_id) values ('polen', 'A', '196', '95', 1);
insert into medical_records (allergies, blood_type, height, weight, patient_id) values ('polen', 'A', '170', '57', 2);
insert into medical_records (allergies, blood_type, height, weight, patient_id) values ('polen', 'A', '175', '65', 3);

/*insert into appointments (start_date, end_date, room_id, medical_record_id, doctor_id, diagnosis_id,*/ 




 