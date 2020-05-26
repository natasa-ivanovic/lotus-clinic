package isamrs.tim17.lotus.model;

/***********************************************************************
 * Module:  Pricelist.java
 * Author:  Shejv
 * Purpose: Defines the Class Pricelist
 ***********************************************************************/


public class Pricelist {
	private float price;
	private float discount;
	private AppointmentType type;
	private Clinic clinic;

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public AppointmentType getType() {
		return type;
	}

	public void setType(AppointmentType type) {
		this.type = type;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

}