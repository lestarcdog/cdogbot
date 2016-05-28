CREATE TABLE chat
(
   id serial, 
   message tsvector NOT NULL, 
   sender character varying(70) NOT NULL, 
   is_sender_me boolean NOT NULL, 
   sent_time timestamp without time zone, 
   next_message_id integer, 
   PRIMARY KEY (id),
   FOREIGN KEY (next_message_id) REFERENCES chat(id)
   
) 
WITH (
  OIDS = FALSE
)
;
