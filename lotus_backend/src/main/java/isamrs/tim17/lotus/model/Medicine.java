package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  Medicine.java
 * Author:  Shejv
 * Purpose: Defines the Class Medicine
 ***********************************************************************/

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "medicines")
public class Medicine {
	@Id
   private long id;
	@Column(name = "name", nullable = false, unique = false)
   private String name;
   
   //public ClinicalCentre clinicCentre;

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

   
}