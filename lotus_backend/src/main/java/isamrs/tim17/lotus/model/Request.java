package isamrs.tim17.lotus.model;

import javax.persistence.Column;

/***********************************************************************
 * Module:  Request.java
 * Author:  Shejv
 * Purpose: Defines the Class Request
 ***********************************************************************/

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.DiscriminatorOptions;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="req_type", discriminatorType=DiscriminatorType.STRING, length = 20)
@DiscriminatorOptions(force = true)
@Table(name = "requests")
public abstract class Request {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@PrimaryKeyJoinColumn
	private Long id;
	@Column(name = "status", unique = false, nullable = false)
	private RequestStatus status;
	@ManyToOne(fetch = FetchType.EAGER)
	private ClinicalCentreAdministrator admin;
	@ManyToOne(fetch = FetchType.LAZY)
	private Clinic clinic;
	
	@Version
	private Long version;
	
	public Request() {
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public RequestStatus getStatus() {
		return status;
	}
	public void setStatus(RequestStatus status) {
		this.status = status;
	}
	public ClinicalCentreAdministrator getAdmin() {
		return admin;
	}
	public void setAdmin(ClinicalCentreAdministrator admin) {
		this.admin = admin;
	}
	
	public Clinic getClinic() {
		return clinic;
	}
	
	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	
}