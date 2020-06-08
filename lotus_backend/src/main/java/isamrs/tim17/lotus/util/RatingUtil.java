package isamrs.tim17.lotus.util;

import java.util.List;

import isamrs.tim17.lotus.model.ClinicReview;
import isamrs.tim17.lotus.model.DoctorReview;

public class RatingUtil {

	public static double getAverageDoctorRating(List<DoctorReview> reviews) {
		double result = 0.0;
		int counter = 0;
		for (DoctorReview dr : reviews) {
			result += dr.getGrade();
			counter++;
		}
		if (counter == 0)
			return 0;
		return result / counter;
	}

	public static double getAverageClinicRating(List<ClinicReview> reviews) {
		double result = 0.0;
		int counter = 0;
		for (ClinicReview cr : reviews) {
			result += cr.getGrade();
			counter++;
		}
		if (counter == 0)
			return 0;
		return result / counter;

	}
}
