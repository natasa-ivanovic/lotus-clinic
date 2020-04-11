package isamrs.tim17.lotus.model;

public class Doctor {
	private String email;
	private String password;
	private String name;
	private String surname;
	private String address;
	private String city;
	private String country;
	private String phone;
	private int id;
		
	public Doctor() {}
	
	public Doctor(String email, String password, String name, String surname, String address, String city,
			String country, String phone, int id) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Doctor [email=" + email + ", password=" + password + ", name=" + name + ", surname=" + surname
				+ ", address=" + address + ", city=" + city + ", country=" + country + ", phone=" + phone + ", id=" + id
				+ "]";
	}
	
	
	
	
}
