package io.devzona.springboot.emailproducer.utils;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarUtils {

    public static final String DAYS = "days";
    public static final String HOURS = "hours";
    public static final String MINUTES = "mins";
    public static final String SECONDS = "secs";
    public static final String YEARS = "years";
    public static final String MONTHS = "months";
    public static final String WEEKS = "weeks";
    public static final String DAY = "day";
    public static final String HOUR = "hour";
    public static final String MINUTE = "min";
    public static final String SECOND = "sec";
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String WEEK = "week";
    public static final String AGO = "ago";
    public static final String DATE_FORMATTER = "yyyyMMdd";
    public static final String DATABASE_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DAY_MONTH_YEAR_FORMAT = "dd-MMM, yyyy";
    public static final String DISPLAY_MONTH_YEAR_FORMAT = "MMM, ''yy";
    public static final String DISPLAY_DAY_MONTH_YEAR_FORMAT = "dd MMM yy";
    public static final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DISPLAY_MONTH_YEAR_FORMAT_POSS_DATE = "MMM yy";
    public static String DISPLAY_DATE_FORMAT = "MMM dd, ''yy";
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MMM/dd/");

    public static String formatDate(Date date, String formatString) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        return sdf.format(date);
    }

    public static int changeInPercent(double value, int percent) {
        Double finalValue = value + ((percent * 0.01) * value);
        return finalValue.intValue();
    }

    public static int calculateDiffDays(Date dateEarly, Date dateLater) {
        return (int) ((dateLater.getTime() - dateEarly.getTime())
                / (24 * 60 * 60 * 1000));
    }

    public static int calculateDiffMonth(Date dateEarly, Date dateLater) {
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(dateEarly);
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(dateLater);
        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        return diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
    }

    public static long calculateDiffDaysByTruncatingTime(Date dateEarly, Date dateLater) {
        dateEarly = DateUtils.truncate(dateEarly, Calendar.DAY_OF_MONTH);
        dateLater = DateUtils.truncate(dateLater, Calendar.DAY_OF_MONTH);
        return (dateLater.getTime() - dateEarly.getTime())
                / (24 * 60 * 60 * 1000);
    }

    public static String getdiffernceBetWeenTwoDates(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        int diffInDays = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
        int diffInWeeks = diffInDays / 7;
        int diffInMonths = diffInDays / 30;
        int diffInYears = diffInDays / 365;
        StringBuffer str = new StringBuffer();
        if (diffInYears > 0) {
            str.append(diffInYears + " Years Ago");
        } else if (diffInMonths > 0) {
            str.append(diffInMonths + " Months Ago");
        } else if (diffInWeeks > 0) {
            str.append(diffInWeeks + " Weeks Ago");
        } else if (diffInDays > 0) {
            str.append(diffInDays + " Days Ago");
        } else if (diffHours > 0l) {
            str.append(diffHours + " Hours Ago");
        } else if (diffMinutes > 0l) {
            str.append(diffMinutes + " Minutes Ago");
        } else if (diffSeconds > 0l) {
            str.append(diffSeconds + " Seconds Ago");
        } else {
            str.append(" Less Than a Minute Ago");
        }
        return str.toString();
    }

    public static String getMomentsAgoTimeFormat(Date date) {
        Date currentDate = new Date();

        if (date.getTime() <= currentDate.getTime()) {

            Interval interval = new Interval(date.getTime(), currentDate.getTime());
            Period period = interval.toPeriod();

            Integer yearCount = period.getYears();
            if (yearCount != null && yearCount > 0) {
                String year;
                if (yearCount > 1) {
                    year = yearCount + " " + YEARS;
                } else {
                    year = yearCount + " " + YEAR;
                }
                return year;
            }

            Integer mnthCount = period.getMonths();
            if (mnthCount != null && mnthCount > 0) {
                String month;
                if (mnthCount > 1) {
                    month = mnthCount + " " + MONTHS;
                } else {
                    month = mnthCount + " " + MONTH;
                }
                return month;
            }

            Integer weekCount = period.getWeeks();
            if (weekCount != null && weekCount > 0) {
                String week;
                if (weekCount > 1) {
                    week = weekCount + " " + WEEKS;
                } else {
                    week = weekCount + " " + WEEK;
                }
                return week;
            }

            Integer dayCount = period.getDays();
            if (dayCount != null && dayCount > 0) {
                String day;
                if (dayCount > 1) {
                    day = dayCount + " " + DAYS;
                } else {
                    day = dayCount + " " + DAY;
                }
                return day;
            }

            Integer hourCount = period.getHours();
            if (hourCount != null && hourCount > 0) {
                String hour;
                if (hourCount > 1) {
                    hour = hourCount + " " + HOURS;
                } else {
                    hour = hourCount + " " + HOUR;
                }
                return hour;
            }

            Integer minCount = period.getMinutes();
            if (minCount != null && minCount > 0) {
                String min;
                if (minCount > 1) {
                    min = minCount + " " + MINUTES;
                } else {
                    min = minCount + " " + MINUTE;
                }
                return min;
            }

            Integer secCount = period.getSeconds();
            if (secCount != null && secCount > 0) {
                String sec;
                if (secCount > 1) {
                    sec = secCount + " " + SECONDS;
                } else {
                    sec = secCount + " " + SECOND;
                }
                return sec;
            }
        }
        return "1" + " " + SECOND;
    }

    public static String DateMonthBeforeCurrentDate(Date date, String formatString, int numofMonths) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        if (date != null && !StringUtils.isEmpty(formatString)) {
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(sdf.format(date)));
            } catch (ParseException e) {
            }
            c.add(Calendar.MONTH, -numofMonths);  // number of month to deduct
            return sdf.format(c.getTime());
        } else {
            return null;
        }
    }

    public static int getDaysBetweenToDates(Date startDate, Date endDate) {
        // Create Calendar instance
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        // Set the Calendar's time with the given Date.
        calendar1.setTime(startDate);
        calendar2.setTime(endDate);

        /*
         * Use getTimeInMillis() method to get the Calendar's time value in
         * milliseconds. This method returns the current time as UTC
         * milliseconds from the epoch
         */
        long miliSecondForDate1 = calendar1.getTimeInMillis();
        long miliSecondForDate2 = calendar2.getTimeInMillis();

        double diffInDays = (double) (miliSecondForDate2 - miliSecondForDate1) / (24 * 60 * 60 * 1000);

        return (int) Math.ceil(diffInDays);
    }

    public static String dateToStringFormat(Date date) {
        return new SimpleDateFormat(DISPLAY_DATE_FORMAT).format(date);
    }

    public static String dateToStringFormat(Date date, SimpleDateFormat format) {
        return format.format(date);
    }

    public static String dateToStringFormat(Date date, String formatString) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        return sdf.format(date);
    }
}
