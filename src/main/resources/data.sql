INSERT INTO app_user (DTYPE, authority, email, enabled , username, password, first_name, last_name) VALUES ('Admin','ROLE_ADMIN', 'EMAIL1@MAIL.RU','true','ADMIN','$2a$10$UX9Lvz3dyPt169Ebv8COs.EtWMPd01Myes.pVZel5ORfSrIQrDk8i','FIRST NAME1', 'LAST NAME1');
INSERT INTO app_user (DTYPE, authority, email, enabled , username, password, first_name, last_name) VALUES ('Learner','ROLE_USER', 'EMAIL2@MAIL.RU','true','LEARNER','$2a$10$UX9Lvz3dyPt169Ebv8COs.EtWMPd01Myes.pVZel5ORfSrIQrDk8i','FIRST NAME2', 'LAST NAME2');
INSERT INTO app_user (DTYPE, authority, email, enabled , username, password, first_name, last_name) VALUES ('Trainer','ROLE_TRAINER', 'EMAIL3@MAIL.RU','true','TRAINER','$2a$10$UX9Lvz3dyPt169Ebv8COs.EtWMPd01Myes.pVZel5ORfSrIQrDk8i','FIRST NAME3', 'LAST NAME3');
INSERT INTO app_user (DTYPE, authority, email, enabled , username, password, first_name, last_name) VALUES ('SecurityUser','ROLE_SECURITY', 'EMAIL4@MAIL.RU','true','SECURITY','$2a$10$UX9Lvz3dyPt169Ebv8COs.EtWMPd01Myes.pVZel5ORfSrIQrDk8i','FIRST NAME4', 'LAST NAME4');

INSERT INTO room (area) VALUES (123);

INSERT INTO room_request (requester_id, room_id, start_time, end_time, approved_admin, approved_security
) VALUES (3,1,'2020-05-20 0:0:0.0','2020-05-20 1:0:0.0','true','true');
INSERT INTO room_request (requester_id, room_id, start_time, end_time, approved_admin, approved_security
) VALUES (3,1,'2020-05-10 0:0:0.0','2020-05-10 1:0:0.0','true','true');

INSERT INTO trainer_request (requester_id, trainer_id,start_time, end_time, approved_trainer,
approved_security) VALUES (2,3,'2020-05-20 0:0:0.0','2020-05-20 1:0:0.0','true','true');
INSERT INTO trainer_request (requester_id, trainer_id,start_time, end_time, approved_trainer,
approved_security) VALUES (2,3,'2020-05-10 0:0:0.0','2020-05-10 1:0:0.0','true','true');


INSERT INTO records (type,out,to,date) VALUES ('ROOM_REQ','Trainer','Room','2020-05-05');
INSERT INTO records (type,out,to,date) VALUES ('TRAIN_REQ','Learner','Trainer','2020-05-05');
