package isamrs.tim17.lotus.dto;

import isamrs.tim17.lotus.model.Allergy;

public class AllergyDTO {
	
	private long id;
	private String name;
	
	public AllergyDTO() {}
	
	public AllergyDTO(Allergy allergy) {
		this.id = allergy.getId();
		this.name = allergy.getName();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
}
