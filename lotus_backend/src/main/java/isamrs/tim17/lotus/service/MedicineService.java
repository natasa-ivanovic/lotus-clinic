package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.Medicine;
import isamrs.tim17.lotus.repository.MedicineRepository;

@Service
public class MedicineService {

	@Autowired
	private MedicineRepository medicines;
	
	public Medicine finOne(long id) {
		return medicines.findById(id).orElse(null);
	}
	
	public List<Medicine> findAll() {
		return medicines.findAll();
	}
	
	public Medicine save(Medicine medicine) {
		return medicines.save(medicine);
	}
	
	public void remove (long id) {
		medicines.deleteById(id);
	}
}
