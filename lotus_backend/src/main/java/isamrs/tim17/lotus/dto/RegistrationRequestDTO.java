package isamrs.tim17.lotus.dto;

import isamrs.tim17.lotus.model.RegistrationRequest;

public class RegistrationRequestDTO {
	long id;
	UserDTO user;
	
	public RegistrationRequestDTO() {
		super();
	}

	public RegistrationRequestDTO(UserDTO user, long id) {
		super();
		this.user = user;
		this.id = id;
	}

	public RegistrationRequestDTO(RegistrationRequest rq) {
		this.id = rq.getId();
		this.user = new UserDTO(rq.getPatient());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
}
