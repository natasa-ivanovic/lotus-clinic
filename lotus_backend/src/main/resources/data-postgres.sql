insert into clinics (id, address, description, name) values (0, 'Jevrejska 1', 'General clinic', 'Clinic1');
insert into clinics (id, address, description, name) values (1, 'Fruskogorska 12', 'Specialist clinic', 'Clinic2');


insert into patients (id, address, birth_date, city, country, email, gender, name, password, phone_number, surname, clinic_id) values (12345678912, 'Novosadski put 27', '1998-09-23 02:00:00', 'Subotica', 'Srbija', 'mario@gmail.com', 1, 'Mario', 'mario', '0699114909', 'Kujundzic', 0);
insert into patients (id, address, birth_date, city, country, email, gender, name, password, phone_number, surname, clinic_id) values (23456789012, 'Bulevar oslobodjenja 182', '1998-05-01 02:00:00', 'Novi Sad', 'Srbija', 'natasa@gmail.com', 0, 'Natasa', 'natasa', '066124909', 'Ivanovic', 0);
insert into patients (id, address, birth_date, city, country, email, gender, name, password, phone_number, surname, clinic_id) values (34567890123, 'Adice 12', '1998-06-12 02:00:00', 'Novi Sad', 'Srbija', 'bela@gmail.com', 1, 'Bela', 'bela', '069324909', 'Vajda', 1);

insert into doctors (id, address, birth_date, city, country, email, gender, name, password, phone_number, surname, clinic_id) values (42367854326, 'Novosadski put 17', '1995-02-22 02:00:00', 'Novi Sad', 'Srbija', 'doc1@gmail.com', 1, 'Doktorko', 'doc1', '0699114909', 'Dokic', 0);
insert into doctors (id, address, birth_date, city, country, email, gender, name, password, phone_number, surname, clinic_id) values (53451231231, 'Futoska 5', '1996-01-15 02:00:00', 'Novi Sad', 'Srbija', 'doc2@gmail.com', 0, 'Doktorka', 'doc2', '0699114909', 'Dakic', 1);

insert into nurses (id, address, birth_date, city, country, email, gender, name, password, phone_number, surname, clinic_id) values (65451231354, 'Novosadski put 17', '1997-02-22 02:00:00', 'Novi Sad', 'Srbija', 'nurse1@gmail.com', 1, 'Medinka', 'nurse1', '0699114909', 'Sestric', 0);
insert into nurses (id, address, birth_date, city, country, email, gender, name, password, phone_number, surname, clinic_id) values (54326162342, 'Futoska 5', '1996-01-15 02:00:00', 'Novi Sad', 'Srbija', 'nurse2@gmail.com', 0, 'Medinko', 'nurse2', '0699114909', 'Bratic', 1);
