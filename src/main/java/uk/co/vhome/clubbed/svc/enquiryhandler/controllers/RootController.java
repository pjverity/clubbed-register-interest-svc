package uk.co.vhome.clubbed.svc.enquiryhandler.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RootController
{
	@GetMapping
	ResponseEntity<String> home()
	{
		return ResponseEntity.ok("OK");
	}
}
