------------------
-- DDL Changes ---
------------------

-- This table is used to record all scheduled events

CREATE TABLE schedules
(
	id        SERIAL                 NOT NULL
		CONSTRAINT schedules_id_pkey
		PRIMARY KEY,
	commences DATE                   NOT NULL,
	time      TIME WITHOUT TIME ZONE NOT NULL,
	duration  BIGINT                 NOT NULL, -- No thorough support for INTERVAL yet
	name      VARCHAR(255)           NOT NULL,
	location  VARCHAR(255)           NOT NULL,
	active    BOOLEAN                NOT NULL DEFAULT TRUE
);

------------------
-- DML Changes ---
------------------
