--- Drop columns with private data
ALTER TABLE enquiries
	DROP COLUMN first_name;
ALTER TABLE enquiries
	DROP COLUMN last_name;
ALTER TABLE enquiries
	DROP COLUMN phone_number;

--- Re-purpose the email_address field to be an Id we can use to uniquely track token claims
ALTER TABLE enquiries
	RENAME email_address TO enquiry_id;

ALTER TABLE enquiries
	RENAME CONSTRAINT enquiries_email_address_pkey TO enquiries_enquiry_id_pkey;

ALTER INDEX enquiries_email_address_uidx
	RENAME TO enquiries_enquiry_id_uidx;

--- MD5-hash all current email addresses to obscure them and make then irreversible
UPDATE enquiries e1
SET enquiry_id = (SELECT md5(enquiry_id)
                  FROM enquiries e2
                  WHERE e1.enquiry_id = e2.enquiry_id);

--- Shrink the column to 32 chars - the MD5 size
ALTER TABLE enquiries
	ALTER COLUMN enquiry_id TYPE CHAR(32);


--------------------------- DDL Changes ----------------------------
--- A merge of the scripts taken from the email-handler application
--------------------------------------------------------------------

--- Used by services that generate HTML formatted email messages

CREATE TABLE mail_templates
(
	id       SERIAL                                           NOT NULL
		CONSTRAINT mail_templates_id_pkey
		PRIMARY KEY,
	name     VARCHAR(255)                                     NOT NULL UNIQUE,
	template TEXT                                             NOT NULL,
	modified TIMESTAMP DEFAULT timezone('UTC' :: TEXT, now()) NOT NULL
);

CREATE UNIQUE INDEX mail_templates_name_uidx
	ON mail_templates (name);

------------------
-- DML Changes ---
------------------

INSERT INTO mail_templates VALUES (DEFAULT, 'club_enquiry',
                                   '<!DOCTYPE html>
																	 <html lang="en">

																	 <head>
																		 <meta charset="utf-8">
																		 <meta http-equiv="X-UA-Compatible" content="IE=edge">
																		 <meta name="viewport" content="width=device-width, initial-scale=1">

																		 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
																					 integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

																		 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
																						 crossorigin="anonymous"></script>
																	 </head>

																	 <body>
																	 <div class="container text-center">
																		 <header>
																			 <div class="row">
																				 <div class="col">
																					 <img class="m-4" src="http://${web-host}$/images/logo.svg">
																				 </div>
																			 </div>
																			 <div class="row">
																				 <div class="col lead">Hi ${firstName}, thanks for registering your interest in ${domain-long-name}$!
																					 <hr/>
																				 </div>
																			 </div>
																		 </header>

																		 <main>
																			 <div class="row mt-2">
																				 <div class="col">
																					 <p>We will write to you with all the information regarding our courses and runs.</p>

																					 <p class="mb-4">As promised, you get your first run absolutely FREE! So just click the button below to notify our team...</p>

																					 <p class="p-4 m-4"><a class="btn btn-success" href="http://${api-host}$/enquiries/v2/token-claim/emails/${emailAddress}">Claim Free Token</a></p>

																					 <p>Many thanks, <strong>${domain-long-name}$</strong></p>

																					 <p style="font-size: .75em" class="mt-4">If you no longer wish to receive updates from us, please just <a href="mailto:${domain-email-address}$ (${domain-long-name}$!)?subject=Unsubscribe">drop us a
																						 mail</a> and we''ll remove you from our list.</p>
																				 </div>
																			 </div>
																		 </main>
																	 </div>
																	 <footer>
																		 <div class="container-fluid bg-light text-secondary p-3 mt-3">
																			 <div class="row justify-content-center align-items-center">
																				 <div class="col-12 col-sm-4">
																					 <ul class="list-inline mb-0">
																						 <#if facebookUrl??>
																							 <li class="list-inline-item"><img src="http://server.v-home.co.uk/facebook-logo.svg" width="15" height="15"/> <a href="${facebookUrl}">Facebook</a></li>
																						 </#if>
																						 <#if twitterUrl??>
																							 <li class="list-inline-item"><img src="http://server.v-home.co.uk/twitter-black-shape.svg" width="15" height="15"/> <a href="${twitterUrl}">Twitter</a></li>
																						 </#if>
																					 </ul>
																				 </div>
																				 <div class="col-12 col-sm-4 text-right">
																					 <a href="mailto:${domain-email-address}$">${domain-email-address}$</a>
																				 </div>
																			 </div>
																		 </div>
																	 </footer>
																	 </body>
																	 </html>', DEFAULT);

INSERT INTO mail_templates VALUES (DEFAULT, 'admin_notification',
                                   '<!DOCTYPE html>
																	 <html lang="en">

																	 <head>
																		 <meta charset="utf-8">
																		 <meta http-equiv="X-UA-Compatible" content="IE=edge">
																		 <meta name="viewport" content="width=device-width, initial-scale=1">

																		 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
																					 integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

																		 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
																						 crossorigin="anonymous"></script>
																	 </head>

																	 <body>

																	 <main>
																		 <div class="container p-4">
																			 <div class="text-center">
																				 <h1>Notification</h1>
																				 <h5>
																					 <span class="badge badge-success">${notificationCategory}</span>
																				 </h5>
																				 <hr/>
																			 </div>
																			 <div class="row mt-4">
																				 <div class="col">
																					 <div class="card">
																						 <div class="card-header">
																							 ${domain}$
																						 </div>
																						 <div class="card-body">
																							 <#if firstName??>
																							 <div class="row">
																								 <div class="col d-none d-sm-block text-sm-right font-weight-light">Name</div>
																								 <div class="col text-center text-sm-left">${firstName} ${lastName}</div>
																							 </div>
																							 </#if>
																							 <div class="row">
																								 <div class="col d-none d-sm-block text-sm-right font-weight-light">Email Address</div>
																								 <div class="col text-center text-sm-left"><a href="mailto:paul.verity@hotmail.co.uk">${emailAddress}</a></div>
																							 </div>
																							 <#if phoneNumber??>
																							 <div class="row">
																								 <div class="col d-none d-sm-block text-sm-right font-weight-light">Phone Number</div>
																								 <div class="col text-center text-sm-left"><a href="tel:+44 07894704209">${phoneNumber}</a></div>
																							 </div>
																							 </#if>
																						 </div>
																					 </div>
																				 </div>
																			 </div>
																		 </div>
																	 </main>
																	 </body>
																	 </html>', DEFAULT);