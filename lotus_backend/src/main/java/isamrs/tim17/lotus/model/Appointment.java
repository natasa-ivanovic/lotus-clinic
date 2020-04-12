package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  Appointment.java
 * Author:  Shejv
 * Purpose: Defines the Class Appointment
 ***********************************************************************/

import java.util.*;

public class Appointment {
   private String information;
   private int id;
   private Date startDate;
   private Date endDate;
   private AppointmentStatus status;
   
   public MedicalRecord patient;
   public Doctor doctor;
   public Room room;
   public AppointmentType type;
   public Diagnosis diagnosis;

}