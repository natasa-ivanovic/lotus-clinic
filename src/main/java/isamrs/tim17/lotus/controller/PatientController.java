package isamrs.tim17.lotus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PatientController {
	
	@RequestMapping
	public String home() {
		return "index.html";
	}
}
