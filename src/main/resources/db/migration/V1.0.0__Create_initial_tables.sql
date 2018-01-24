------------------
-- DDL Changes ---
------------------

-- This table is used to record the names and email addresses of people making new enquiries

CREATE TABLE enquiries
(
  email_address VARCHAR(255) NOT NULL
    CONSTRAINT enquiries_email_address_pkey
    PRIMARY KEY,
  first_name VARCHAR(32) NOT NULL,
  last_name VARCHAR(64) NOT NULL ,
  enquiry_time TIMESTAMP
);

CREATE UNIQUE INDEX enquiries_email_address_uidx
  ON enquiries (email_address);

------------------
-- DML Changes ---
------------------
