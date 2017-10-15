<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
	      integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">

	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"
	        integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
</head>

<body>
<div class="container text-center">

	<header>
		<div class="row">
			<div class="col">
				<img class="m-4" src="${logoUrl}">
			</div>
		</div>
		<div class="row">
			<div class="col lead">Thank you for registering your interest in ${mailMetaData.fromName}!
				<hr/>
			</div>
		</div>
	</header>

	<main>
		<div class="row mt-2">
			<div class="col">
				<p>We will write to you with all the information regarding our courses and runs.</p>

				<p>If you no longer wish to receive updates from us, please just <a href="mailto:${mailMetaData.fromAddress} (${mailMetaData.fromName})?subject=Unsubscribe">drop us a
					mail</a> and we'll remove you from our list.</p>

				<p>Many thanks, <strong>${mailMetaData.fromName}</strong>.</p>
			</div>
		</div>
	</main>
</div>
<footer>
	<div class="container-fluid bg-light text-secondary p-3 mt-3">
		<div class="row justify-content-center align-items-center">
			<div class="col-12 col-sm-4">
				<ul class="list-inline mb-0">
				<#if twitterUrl??>
					<li class="list-inline-item"><img src="http://server.v-home.co.uk/twitter-black-shape.svg" width="15" height="15"/> <a href="${twitterUrl}">Twitter</a></li>
				</#if>
				<#if facebookUrl??>
					<li class="list-inline-item"><img src="http://server.v-home.co.uk/facebook-logo.svg" width="15" height="15"/> <a href="${facebookUrl}">Facebook</a></li>
				</#if>
				</ul>
			</div>
			<div class="col-12 col-sm-4 text-right">
			  <a href="mailto:${mailMetaData.fromAddress}">${mailMetaData.fromAddress}</a>
			</div>
		</div>
	</div>
</footer>
</body>
</html>