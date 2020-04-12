package isamrs.tim17.lotus.model;
/***********************************************************************
 * Module:  Calendar.java
 * Author:  Shejv
 * Purpose: Defines the Class Calendar
 ***********************************************************************/

import java.util.*;

public class Calendar {
   private Date startDate;
   private Date endDate;
   private CalendarEntry type;
   
   public java.util.Collection<MedicalStaff> staff;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<MedicalStaff> getStaff() {
      if (staff == null)
         staff = new java.util.ArrayList<MedicalStaff>();
      return staff;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorStaff() {
      if (staff == null)
         staff = new java.util.ArrayList<MedicalStaff>();
      return staff.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newStaff */
   public void setStaff(java.util.Collection<MedicalStaff> newStaff) {
      removeAllStaff();
      for (java.util.Iterator iter = newStaff.iterator(); iter.hasNext();)
         addStaff((MedicalStaff)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newMedicalStaff */
   public void addStaff(MedicalStaff newMedicalStaff) {
      if (newMedicalStaff == null)
         return;
      if (this.staff == null)
         this.staff = new java.util.ArrayList<MedicalStaff>();
      if (!this.staff.contains(newMedicalStaff))
         this.staff.add(newMedicalStaff);
   }
   
   /** @pdGenerated default remove
     * @param oldMedicalStaff */
   public void removeStaff(MedicalStaff oldMedicalStaff) {
      if (oldMedicalStaff == null)
         return;
      if (this.staff != null)
         if (this.staff.contains(oldMedicalStaff))
            this.staff.remove(oldMedicalStaff);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllStaff() {
      if (staff != null)
         staff.clear();
   }

}