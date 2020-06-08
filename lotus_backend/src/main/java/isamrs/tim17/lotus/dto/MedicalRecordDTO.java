package isamrs.tim17.lotus.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import isamrs.tim17.lotus.model.Allergy;
import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.MedicalRecord;

public class MedicalRecordDTO {
	private long id;
	private float height;
	private float weight;
	private List<AllergyDTO> allergies;
	private String bloodType;
	
	private Set<PremadeAppDTO> apps = new HashSet<>();
	
	public MedicalRecordDTO() {}

	public MedicalRecordDTO(MedicalRecord m) {
		super();
		this.id = m.getId();
		this.height = m.getHeight();
		this.weight = m.getWeight();
		
		if (!m.getAllergies().isEmpty()) {
			this.allergies = new ArrayList<>();
			Iterator<Allergy> it = m.getAllergies().iterator();
			while (it.hasNext()) {
				AllergyDTO a = new AllergyDTO(it.next());
				allergies.add(a);
			}
		}
		
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

	public List<AllergyDTO> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<AllergyDTO> allergies) {
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
