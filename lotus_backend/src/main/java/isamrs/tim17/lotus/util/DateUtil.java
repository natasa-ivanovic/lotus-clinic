package isamrs.tim17.lotus.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import isamrs.tim17.lotus.model.Appointment;
import isamrs.tim17.lotus.model.AppointmentStatus;

public class DateUtil {

	public static final int WEEKDAYWORKSTART = 8;
	public static final int WEEKDAYWORKEND = 18;
	public static final int WEEKDAYBREAKSTART = 11;

	public static final int WEEKENDWORKSTART = 8;
	public static final int WEEKENDWORKEND = 13;
	public static final int WEEKENDBREAKSTART = 11;

	public static final int BREAKDURATIONHOURS = 1;

	public static final int TERMSPERHOUR = 2;

	private DateUtil() {}
	
	public static boolean overlap(Date start1, Date end1, Date start2, Date end2) {
		return start1.getTime() <= end2.getTime() && start2.getTime() <= end1.getTime();
	}

	public static Date addMinutes(Date startDate, int minutes) {
		final long ONE_MINUTE_IN_MILLIS = 60000;
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		long start = cal.getTimeInMillis();
		return new Date(start + (minutes * ONE_MINUTE_IN_MILLIS));
	}

	public static Date endOfDay(Date startDate) {
		Date end = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		end = cal.getTime();
		return end;
	}

	public static List<Date> getAllTerms(Date day) {
		List<Date> data = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return data;
		}
		int workStart;
		int workEnd;
		int breakStart;
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			workStart = WEEKENDWORKSTART;
			workEnd = WEEKENDWORKEND;
			breakStart = WEEKENDBREAKSTART;
		} else {
			workStart = WEEKDAYWORKSTART;
			workEnd = WEEKDAYWORKEND;
			breakStart = WEEKDAYBREAKSTART;
		}
		int currentTime = workStart;
		cal.set(Calendar.HOUR_OF_DAY, currentTime);
		cal.set(Calendar.MINUTE, 0);
		int termDuration = 60 / TERMSPERHOUR;
		// TODO: maybe change so term duration is flexible?
		while (currentTime != workEnd) {
			if (currentTime == breakStart) {
				currentTime += BREAKDURATIONHOURS;
				cal.add(Calendar.HOUR_OF_DAY, BREAKDURATIONHOURS);
				continue;
			}
			for (int i = 0; i != TERMSPERHOUR; i++) {
				Date newDate = cal.getTime();
				data.add(newDate);
				cal.add(Calendar.MINUTE, termDuration);
			}
			currentTime++;
		}
		return data;
	}

	/**
	 * Removes all dates from accepted list that are in use by any appointments in
	 * accepted list;
	 * 
	 * @param dates - list of dates to be removed from
	 * @param apps  - list of appointments to be checked againts
	 * @return list<Date> of free dates for that day
	 */
	public static List<Date> removeOverlap(List<Date> dates, List<Appointment> apps) {
		Calendar startApp = Calendar.getInstance();
		Calendar endApp = Calendar.getInstance();
		Calendar startTerm = Calendar.getInstance();
		for (Appointment a : apps) {
			if (a.getStatus().equals(AppointmentStatus.PREMADE) || a.getStatus().equals(AppointmentStatus.CANCELED))
				continue;
			startApp.setTime(a.getStartDate());
			endApp.setTime(a.getEndDate());
			for (Date d : dates) {
				startTerm.setTime(d);
				// pretpostavka - uvek je ista duzina pregleda i pocetak je u isto vreme
				if (startApp.get(Calendar.HOUR_OF_DAY) == startTerm.get(Calendar.HOUR_OF_DAY)
						&& startApp.get(Calendar.MINUTE) == startTerm.get(Calendar.MINUTE)) {
					dates.remove(d);
					break;					
				}
			}
		}
		return dates;
	}
	
	public static List<Date> getSevenDays(Date startDate) {
		List<Date> days = new ArrayList<>();
		for (int i = 0; i <= 6; i++) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.add(Calendar.DATE, i);
			Date newDate = cal.getTime();
			days.add(newDate);
		}
		return days;
	}
}
