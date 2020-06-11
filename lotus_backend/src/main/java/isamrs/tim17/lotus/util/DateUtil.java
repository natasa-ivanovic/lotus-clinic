package isamrs.tim17.lotus.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import isamrs.tim17.lotus.model.CalendarEntry;

public class DateUtil {

	public final static int weekdayWorkStart = 8;
	public final static int weekdayWorkEnd = 18;
	public final static int weekdayBreakStart = 11;

	public final static int weekendWorkStart = 8;
	public final static int weekendWorkEnd = 13;
	public final static int weekendBreakStart = 11;

	public final static int breakDurationHours = 1;

	public final static int termsPerHour = 2;

	public static boolean overlap(Date start1, Date end1, Date start2, Date end2) {
		return start1.getTime() <= end2.getTime() && start2.getTime() <= end1.getTime();
	}

	public static Date addMinutes(Date startDate, int minutes) {
		long one_minute_in_millis = 60000;
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		long start = cal.getTimeInMillis();
		Date endDate = new Date(start + (minutes * one_minute_in_millis));
		return endDate;
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

	/**
	 * This method is used to get all available terms for a given day.
	 * 
	 * @param day Date object which represents the day.
	 * @param startOnDayHour Boolean which specifies whether the terms should start from 
	 * 		the earliest available (false) or from the Hour data from the day Date object (true)
	 * @return List<Date> all available terms for the day.
	 */
	public static List<Date> getAllTerms(Date day, boolean startOnDayHour) {
		List<Date> data = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return data;
		}
		int workStart, workEnd, breakStart;
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			if (startOnDayHour)
				workStart = cal.get(Calendar.HOUR_OF_DAY);
			else
				workStart = weekendWorkStart;
			workEnd = weekendWorkEnd;
			breakStart = weekendBreakStart;
		} else {
			if (startOnDayHour)
				workStart = cal.get(Calendar.HOUR_OF_DAY);
			else
				workStart = weekdayWorkStart;
			workEnd = weekdayWorkEnd;
			breakStart = weekdayBreakStart;
		}
		int currentTime = workStart;
		cal.set(Calendar.HOUR_OF_DAY, currentTime);
		if (!startOnDayHour)
			cal.set(Calendar.MINUTE, 0);
		int termDuration = 60 / termsPerHour;
		while (currentTime != workEnd) {
			if (currentTime == breakStart) {
				currentTime += breakDurationHours;
				cal.add(Calendar.HOUR_OF_DAY, breakDurationHours);
				continue;
			}
			int startMinutes = cal.get(Calendar.MINUTE) == 0 ? 0 : 1;
			for (int i = startMinutes; i != termsPerHour; i++) {
				Date newDate = cal.getTime();
				data.add(newDate);
				cal.add(Calendar.MINUTE, termDuration);
			}
			currentTime++;
		}
		return data;
	}

	/**
	 * Removes all dates from accepted list that are in use by any calendar entries in
	 * accepted list;
	 * 
	 * @param dates - list of dates to be removed from
	 * @param apps  - list of appointments to be checked against
	 * @return list<Date> of free dates for that day
	 */
	public static List<Date> removeOverlap(List<Date> dates, List<CalendarEntry> entries) {
		Calendar startApp = Calendar.getInstance();
		Calendar startTerm = Calendar.getInstance();
		for (CalendarEntry c : entries) {
			startApp.setTime(c.getStartDate());
			for (Date d : dates) {
				if (c.getVacation() != null) 
					if (c.getStartDate().before(d) && c.getEndDate().after(d))
						return new ArrayList<Date>();
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
