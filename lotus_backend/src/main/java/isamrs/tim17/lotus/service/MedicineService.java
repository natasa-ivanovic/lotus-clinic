package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isamrs.tim17.lotus.model.Medicine;
import isamrs.tim17.lotus.repository.MedicineRepository;

@Service
@Transactional(readOnly = true)
public class MedicineService {

	@Autowired
	private MedicineRepository medicines;
	
	public Medicine findOne(long id) {
		return medicines.findById(id).orElse(null);
	}
	
	public List<Medicine> findAll() {
		return medicines.findAll();
	}
	
	@Transactional(readOnly = false)
	public Medicine save(Medicine medicine) {
		return medicines.save(medicine);
	}
	
	@Transactional(readOnly = false)
	public void remove (long id) {
		medicines.deleteById(id);
	}
}
