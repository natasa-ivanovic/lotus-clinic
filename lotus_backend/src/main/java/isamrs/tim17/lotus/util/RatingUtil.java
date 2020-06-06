package isamrs.tim17.lotus.util;

import java.util.List;

import isamrs.tim17.lotus.model.ClinicReview;
import isamrs.tim17.lotus.model.DoctorReview;

public class RatingUtil {

	private RatingUtil() {}
	
	public static double getAverageDoctorRating(List<DoctorReview> reviews) {
		if (reviews.isEmpty())
			return 0;
		double result = 0.0;
		for (DoctorReview dr : reviews) {
			result += dr.getGrade();
		}
		return result / reviews.size();
	}

	public static double getAverageClinicRating(List<ClinicReview> reviews) {
		if (reviews.isEmpty())
			return 0;
		double result = 0.0;
		for (ClinicReview cr : reviews) {
			result += cr.getGrade();
		}
		return result / reviews.size();

	}
}
