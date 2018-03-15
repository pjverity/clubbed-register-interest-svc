-- Used to track whether a newly registered visitor clicked the link in the welcome e-mail to redeem the free token

ALTER TABLE enquiries ADD token_accepted BOOLEAN NULL DEFAULT FALSE;
