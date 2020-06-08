package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  Operation.java
 * Author:  Shejv
 * Purpose: Defines the Class Operation
 ***********************************************************************/

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "operations")
public class Operation {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//	private Date startDate;
//	private Date endDate;
//	private OperationStatus status;
//
//	public AppointmentType type;
//	public MedicalRecord patient;
//	public Room room;
//	public Doctor[] doctor;

}