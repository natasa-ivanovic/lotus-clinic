package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  Nurse.java
 * Author:  Shejv
 * Purpose: Defines the Class Nurse
 ***********************************************************************/

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "nurses")
public class Nurse extends MedicalStaff {
	
	public Nurse() {}
}