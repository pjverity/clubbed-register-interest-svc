-- Allow visitors to pass on their phone number to get a call-back
-- Longest format would be: "+44 (0)1234 567 890"

ALTER TABLE enquiries ADD phone_number CHAR(19) NULL;

-- Should have been 'NOT NULL' as it's always set the the time the enquiry event was generated,
-- and not the time the DB persisted it, which could be different if the DB is down. So not this:
-- ALTER TABLE enquiries ALTER enquiry_time SET DEFAULT timezone('UTC'::text, now());

ALTER TABLE enquiries ALTER enquiry_time SET NOT NULL;

