
UPDATE mail_templates SET template = '<!DOCTYPE html>
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
																	 </html>' where name = 'club_enquiry'
