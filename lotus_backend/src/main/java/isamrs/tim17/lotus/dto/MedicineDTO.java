package isamrs.tim17.lotus.dto;

import isamrs.tim17.lotus.model.Medicine;

public class MedicineDTO {
	   private long id;
	   private String name;
	   
	public MedicineDTO() {
		super();
	}
	
	public MedicineDTO(Medicine medicine) {
		this.id = medicine.getId();
		this.name = medicine.getName();
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
