package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  User.java
 * Author:  Shejv
 * Purpose: Defines the Class User
 ***********************************************************************/

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.DiscriminatorOptions;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import isamrs.tim17.lotus.dto.UserDTO;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="role", discriminatorType=DiscriminatorType.STRING, length = 20)
@DiscriminatorOptions(force = true)
@Table(name = "users")
public abstract class User implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn  
	private Long id;
	@Column(name = "username", unique = true, nullable = false)
	private String username;
	@Column(name = "password", unique = false, nullable = false)
	private String password;
	@Column(name = "name", unique = false, nullable = false)
	private String name;
	@Column(name = "surname", unique = false, nullable = false)
	private String surname;
	@Column(name = "address", unique = false, nullable = false)
	private String address;
	@Column(name = "city", unique = false, nullable = false)
	private String city;
	@Column(name = "country", unique = false, nullable = false)
	private String country;
	@Column(name = "phoneNumber", unique = false, nullable = false)
	private String phoneNumber;
	@Column(name = "birthDate", unique = false, nullable = false)
	private Date birthDate;
	@Column(name = "gender", unique = false, nullable = false)
	private Gender gender;
	@Column(name = "ssid", unique = true, nullable = false)
	private String ssid;
	
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;

    @Column(name = "enabled")
    private boolean enabled;
    

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;
    
    @Column(name="role", insertable = false, updatable = false)
    protected String role;
    
    @Version
    private Long version;
    
	public User() {
		this.lastPasswordResetDate = Timestamp.from(Instant.now());
	}
	
	public User(String email, String password, String name, String surname, String address, String city, String country,
			String phoneNumber, String ssid, Date birthDate, Gender gender) {
		super();
		this.username = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.ssid = ssid;
		this.birthDate = birthDate;
		this.gender = gender;
	}
	
	

	public User(UserDTO user) {
		this();
		setUsername(user.getUsername());
		setName(user.getName());
		setSurname(user.getSurname());
		setAddress(user.getAddress());
		setCity(user.getCity());
		setCountry(user.getCountry());
		setPhoneNumber(user.getPhoneNumber());
		setGender(user.getGender());
		setSsid(user.getSsid());
		setBirthDate(user.getBirthDate());
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + username + ", password=" + password + ", name=" + name + ", surname="
				+ surname + ", address=" + address + ", city=" + city + ", country=" + country + ", phoneNumber="
				+ phoneNumber + ", birthDate=" + birthDate + ", gender=" + gender + "]";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

    public void setPassword(String password) {
        Timestamp now = new Timestamp(new Date().getTime());
        this.setLastPasswordResetDate(now);
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }   
    
    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
	


    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }
    
    public String getRole() {
        return this.role;
    }
    
}