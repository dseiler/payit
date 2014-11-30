package ch.truesolutions.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Daniel Seiler
 */
public class EasyDatePicker {

	private SimpleDateFormat sdf = new SimpleDateFormat();
	
	private int weekBeginDay = 1; //0=Sunday,1=Monday,2=Tuesday,...,6=Saturday    
	private List specialDates = new ArrayList();
	private String[] monthNames = new String[12];
	private String dayNames = "M T W T F S S";
	private boolean weekendSelectable = false;
	private String dateFormat = "dd.MM.yyyy";
	private CalendarCell[] calendarCells = new CalendarCell[42];
	private Date preselectedDate = null;

	public static final int DAY = Calendar.DATE;
	public static final int MONTH = Calendar.MONTH;
	public static final int YEAR = Calendar.YEAR;
	
	/**
	 * 
	 */
	public EasyDatePicker() {
		super();
		init();
	}
	
	private void init(){
		setDateFormat(dateFormat);
		monthNames = new String[]{"January","Fabruary","March","April","May","June","July","August","September","October","November","December"};
		for(int i = 0; i< 42; i++){
			calendarCells[i] = new CalendarCell();
		}

	}

	public void setDayNames(String dayNames) // e.g. "S M T W T F S"
	{
		this.dayNames = dayNames;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
		sdf.applyPattern(dateFormat);
	}
    
	public void setWeekBeginDay(int weekBeginDay){ //0=Sunday,1=Monday,2=Tuesday,...,6=Saturday
		this.weekBeginDay = weekBeginDay;
	}

	public CalendarCell[] getCalendarCellsForMonth(int year, int month){
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(year,month,1);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-weekBeginDay;
		if(dayOfWeek <= 0) dayOfWeek += 7;
		// substract the day of week from the first of this month to get the beginning of the week date
		cal.add(Calendar.DATE,-1*dayOfWeek);

		for(int i = 0; i< 42; i++){
			cal.add(Calendar.DATE,1);
			calendarCells[i].date = cal.getTime();
			calendarCells[i].selectable = true;
			switch(cal.get(Calendar.DAY_OF_WEEK)){
				case Calendar.SATURDAY:
				case Calendar.SUNDAY:
					calendarCells[i].weekend = true;
					calendarCells[i].selectable = false;
					break;
				default:
					break;
			}
			if(isSpecialDate(cal)) {
				calendarCells[i].selectable = false;
			}
		}
		
		return calendarCells;
	}
	
	public int getCellNumberForDate(Date date){
		int preselectedMonth = getDateField(date,EasyDatePicker.MONTH);
		int preselectedDay = getDateField(date,EasyDatePicker.DAY);
		for(int i=0; i<42; i++ ) {
			if((preselectedMonth == getDateField(calendarCells[i].date,EasyDatePicker.MONTH)) && 
				(preselectedDay == getDateField(calendarCells[i].date,EasyDatePicker.DAY))){
				return i;
			}
		}
		return -1;
	}

	// utility methods
	public boolean isSpecialDate(Calendar cal) {
		boolean isSpecialDate = false;
        
		// update the Vector of special dates if needed
		getSpecialDates(cal.get(Calendar.YEAR));
		// go through the vector of special dates and compare it with this date
		Iterator it = specialDates.iterator();
		while(it.hasNext()) {
			Calendar c = (Calendar)(it.next());
			if((c.get(Calendar.DATE) == cal.get(Calendar.DATE)) &&
			(c.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) &&
			(c.get(Calendar.YEAR) == cal.get(Calendar.YEAR))) {
				isSpecialDate = true;
				return isSpecialDate;
			}
		}
        
		return isSpecialDate;
	}
    
	public String getNextSelectableDateAsString() {
		// get the calendar of today
		Calendar defaultCal = Calendar.getInstance();
		// get the next valid date
		while (true) {
			defaultCal.add(Calendar.DATE, 1);
			if (!((((defaultCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				|| (defaultCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY))
				&& !weekendSelectable)
				|| (isSpecialDate(defaultCal) == true))) {
				break;
			}
		}
		return formatDate(defaultCal.getTime());
	}
	
	public String formatDate(Date date){
		return sdf.format(date);
	}
	
	public Date parseDate(String date) {
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(
				"date parse exception date:" + date + ", e:" + e);
		}
	}

    
	private List getSpecialDates(int year) {
		//if(year == currentYear) {
		//	return specialDates;
		//}
        
		specialDates.clear();
		//currentYear = year;
        
		// calculating routine
		int d = 0;
		int m = 0;
		int j = year;
		int century = j / 100;
        
		if (century==16)
		{d=10; m=202;}
		else if (century==17)
		{d=11; m=203;}
		else if (century==18)
		{d=12; m=203;}
		else if (century==19)
		{d=13; m=204;}
		else if (century==20)
		{d=13; m=204;}
		else if (century==21)
		{d=14; m=204;}
		else if (century==22)
		{d=15; m=205;}
		else if (century==23)
		{d=16; m=206;}
		int q= j / 4;
		int a= j % 19;
		int b=(m-11*a)%30;
		if (((century==16)||(century==19)||(century==20)||(century==21)||(century==22))&&(b>=28))
		{b--;}
		int c=(j+q+b-d)%7;
        
		int em=0;
		int am=2;
		int wm=2;
		int ed=28+b-c;
		int ad=ed-22;
		int wd=ed-12;
        
		if (ed>31)
		{em=1; ed=ed-31;}
		if (ad>31)
		{am=3; ad=ad-31;}
		if (wd>31)
		{wm=3; wd=wd-31;}
        
		Calendar newyearCal = Calendar.getInstance();		// 1.Januar
		newyearCal.set(j,0,1);
		specialDates.add(newyearCal);
        
		Calendar berchtoldCal = Calendar.getInstance();		// 2.Januar
		berchtoldCal.set(j,0,2);
		specialDates.add(berchtoldCal);
        
		// calculate the good friday
		Calendar goodfridayCal = Calendar.getInstance();	// Karfreitag
		goodfridayCal.set(j,em+2,ed); // easter sunday
		goodfridayCal.add(Calendar.DATE, -2);
		specialDates.add(goodfridayCal);
        
		// calculate the easter monday
		Calendar eastermondayCal = Calendar.getInstance();	// Ostermontag
		eastermondayCal.set(j,em+2,ed); // easter sunday
		eastermondayCal.add(Calendar.DATE, 1);
		specialDates.add(eastermondayCal);
        
		Calendar labourdayCal = Calendar.getInstance();		// 1.Mai
		labourdayCal.set(j,4,1);
		specialDates.add(labourdayCal);
        
		// calculate the ascension thursday
		Calendar ascensionCal = Calendar.getInstance();		// Auffahrt
		ascensionCal.set(j,am+2,ad);
		specialDates.add(ascensionCal);
        
		// calculate the withmonday
		Calendar withmondayCal = Calendar.getInstance();	// Pfingstmontag
		withmondayCal.set(j,wm+2,wd); // with sunday
		withmondayCal.add(Calendar.DATE, 1);
		specialDates.add(withmondayCal);
        
		Calendar birthdayCal = Calendar.getInstance();		// 1.August
		birthdayCal.set(j,7,1);
		specialDates.add(birthdayCal);
        
		Calendar beforxmasCal = Calendar.getInstance();		// 24.Dezember
		beforxmasCal.set(j,11,24);
		specialDates.add(beforxmasCal);
        
		Calendar xmasCal = Calendar.getInstance();		// 25.Dezember
		xmasCal.set(j,11,25);
		specialDates.add(xmasCal);
        
		Calendar stephanCal = Calendar.getInstance();		// 26.Dezember
		stephanCal.set(j,11,26);
		specialDates.add(stephanCal);
        
		Calendar silvesterCal = Calendar.getInstance();		// 31.Dezember
		silvesterCal.set(j,11,31);
		specialDates.add(silvesterCal);
        
		return specialDates;
	}

	/**
	 * @return
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * @return
	 */
	public String getDayNames() {
		return dayNames;
	}

	/**
	 * @return
	 */
	public int getWeekBeginDay() {
		return weekBeginDay;
	}

	/**
	 * @return
	 */
	public boolean isWeekendSelectable() {
		return weekendSelectable;
	}

	/**
	 * @param b
	 */
	public void setWeekendSelectable(boolean b) {
		weekendSelectable = b;
	}
	
	public int getCurrentDateField(int field){
		return GregorianCalendar.getInstance().get(field);
	}
	
	public int getDateField( Date dBuffer, int field) {
		Calendar calBuffer = GregorianCalendar.getInstance();
		calBuffer.setTime( dBuffer );
		return calBuffer.get( field );
	}


	/**
	 * @return
	 */
	public String[] getMonthNames() {
		return monthNames;
	}

	/**
	 * @param strings
	 */
	public void setMonthNames(String[] monthNames) {
		if(monthNames.length != 12){
			throw new RuntimeException("try to set ilegal month names !");
		}
		this.monthNames = monthNames;
	}

	/**
	 * @return
	 */
	public Date getPreselectedDate() {
		if(preselectedDate != null){
			return preselectedDate;
		} else {
			return Calendar.getInstance().getTime();
		}
	}

	/**
	 * @param date
	 */
	public void setPreselectedDate(Date date) {
		preselectedDate = date;
	}

	public void setPreselectedDate(String date) {
		try{
			preselectedDate = parseDate(date);
		} catch(RuntimeException e){
			System.out.println(e);
		}
	}

}
