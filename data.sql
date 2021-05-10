/* Authorities */
insert into clinicadb.authority (`name`) values ('ROLE_ADMIN');
insert into clinicadb.authority (`name`) values ('ROLE_DOCTOR');
insert into clinicadb.authority (`name`) values ('ROLE_PATIENT');
insert into clinicadb.authority (`name`) values ('ROLE_ANONYMOUS');

/* Drugs */
insert into clinicadb.drug (`name`, `cost`, `other_details`) values ('drug 1', 20.5, 'drug 1 description');
insert into clinicadb.drug (`name`, `cost`, `other_details`) values ('drug 2', 20.5, 'drug 2 description');
insert into clinicadb.drug (`name`, `cost`, `other_details`) values ('drug 3', 20.5, 'drug 3 description');
insert into clinicadb.drug (`name`, `cost`, `other_details`) values ('drug 4', 20.5, 'drug 4 description');
insert into clinicadb.drug (`name`, `cost`, `other_details`) values ('drug 5', 20.5, 'drug 5 description');
insert into clinicadb.drug (`name`, `cost`, `other_details`) values ('drug 6', 20.5, 'drug 6 description');
insert into clinicadb.drug (`name`, `cost`, `other_details`) values ('drug 7', 20.5, 'drug 7 description');
insert into clinicadb.drug (`name`, `cost`, `other_details`) values ('drug 8', 20.5, 'drug 8 description');
insert into clinicadb.drug (`name`, `cost`, `other_details`) values ('drug 9', 20.5, 'drug 9 description');
insert into clinicadb.drug (`name`, `cost`, `other_details`) values ('drug 10', 20.5, 'drug 10 description');

/* Admins */
insert into clinicadb.user (`email`, `password`, `authority_id`, `created_at`, `activated`) values ('admin@clinica.com', '$2a$10$JS8waCeEdXQ36gQPbeg6u.ue9JxeedKhLBLgjFeQJtyTaetL21r0q', 1, '2021-05-09 12:23:07', 1);
insert into clinicadb.user (`email`, `password`, `authority_id`, `created_at`, `activated`) values ('clinica@clinica.com', '$2a$10$JS8waCeEdXQ36gQPbeg6u.ue9JxeedKhLBLgjFeQJtyTaetL21r0q', 1, '2021-05-09 12:23:20', 1);

SELECT * FROM clinicadb.authority;
SELECT * FROM clinicadb.patient;
SELECT * FROM clinicadb.doctor;
SELECT * FROM clinicadb.user;
SELECT * FROM clinicadb.visit;
SELECT * FROM clinicadb.diagnosis;
SELECT * FROM clinicadb.diagnosis_drug;
SELECT * FROM clinicadb.drug;

insert into clinicadb.diagnosis_drugs (`diagnosis_id`, `drugs_id`) values (6, 1);
insert into clinicadb.diagnosis_drugs (`diagnosis_id`, `drugs_id`) values (6, 3);