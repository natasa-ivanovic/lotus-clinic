package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.tim17.lotus.model.Patient;
import isamrs.tim17.lotus.model.User;
import isamrs.tim17.lotus.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository users;
	
	public User findOne(long id) {
		return users.findById(id).orElseGet(null);
	}

	public List<User> findAll() {
		return users.findAll();
	}
	
	public User save(User user) {
		return users.save(user);
	}

	public void remove(long id) {
		users.deleteById(id);
	}

	public User findByUsername(String email) {
		return users.findByUsername(email);
	}


}
