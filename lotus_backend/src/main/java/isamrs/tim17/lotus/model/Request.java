package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  Request.java
 * Author:  Shejv
 * Purpose: Defines the Class Request
 ***********************************************************************/

import java.util.*;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.DiscriminatorOptions;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="req_type", discriminatorType=DiscriminatorType.STRING, length = 20)
@DiscriminatorOptions(force = true)
@Table(name = "requests")
public abstract class Request {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private RequestStatus status;

	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}
}