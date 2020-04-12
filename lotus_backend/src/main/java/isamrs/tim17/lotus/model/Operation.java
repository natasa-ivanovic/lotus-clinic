package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  Operation.java
 * Author:  Shejv
 * Purpose: Defines the Class Operation
 ***********************************************************************/

import java.util.*;

public class Operation {
   private int id;
   private Date startDate;
   private Date endDate;
   private OperationStatus status;
   
   public AppointmentType type;
   public MedicalRecord patient;
   public Room room;
   public Doctor[] doctor;

}