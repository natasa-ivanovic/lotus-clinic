package isamrs.tim17.lotus.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@DiscriminatorValue("VACATION")
public class VacationRequest extends Request {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
}