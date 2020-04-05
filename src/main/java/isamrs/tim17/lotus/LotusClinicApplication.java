package isamrs.tim17.lotus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import isamrs.tim17.lotus.model.Patient;

@SpringBootApplication
public class LotusClinicApplication {

	public static void main(String[] args) {
		SpringApplication.run(LotusClinicApplication.class, args);
		
		Patient p = new Patient("mario@gmail.com", "mario", "Mario", "Kujundzic", "Jovana Jovanovic Zmaja 4", "Subotica", "Srbija", "066 126 215", 1);
		System.out.println(p);
	}

}
