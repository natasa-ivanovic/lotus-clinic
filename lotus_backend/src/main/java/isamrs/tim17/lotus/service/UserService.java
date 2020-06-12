package isamrs.tim17.lotus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isamrs.tim17.lotus.model.User;
import isamrs.tim17.lotus.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserService {
	
	@Autowired
	private UserRepository users;
	
	public User findOne(long id) {
		return users.findOneById(id);
	}

	public List<User> findAll() {
		return users.findAll();
	}

	@Transactional(readOnly = false)
	public User save(User user) {
		return users.save(user);
	}

	@Transactional(readOnly = false)
	public void remove(long id) {
		users.deleteById(id);
	}

	public User findByUsername(String email) {
		return users.findByUsername(email);
	}


}
