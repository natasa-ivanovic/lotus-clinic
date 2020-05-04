package isamrs.tim17.lotus.dto;

import java.util.HashSet;
import java.util.Set;

import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.MedicalRecord;

public class MedicalRecordDTO {
	private long id;
	private float height;
	private float weight;
	private String allergies;
	private String bloodType;
	
	private Set<PremadeAppDTO> apps = new HashSet<PremadeAppDTO>();

	public MedicalRecordDTO(MedicalRecord m) {
		super();
		this.id = m.getId();
		this.height = m.getHeight();
		this.weight = m.getWeight();
		this.allergies = m.getAllergies();
		this.bloodType = m.getBloodType();
		for (Appointment a : m.getAppointments()) {
			apps.add(new PremadeAppDTO(a));
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getAllergies() {
		return allergies;
	}

	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public Set<PremadeAppDTO> getApps() {
		return apps;
	}

	public void setApps(Set<PremadeAppDTO> apps) {
		this.apps = apps;
	}
	
	
}
