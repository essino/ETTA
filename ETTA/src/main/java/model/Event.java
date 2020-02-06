package model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.*;

@Entity
@Table(name="Event")
public class Event {

	@Id
	@Column(name="event_id")
	private int event_id;

	@Column(name="title")
	private String title;

	@Column(name="fullday")
	private boolean fullday;

	@Column(name="startDate")
	private Date startDate;

	@Column(name="endDate")
	private Date endDate;

	@Column(name="startTime")
	private Time startTime;

	@Column(name="endTime")
	private Time endTime;

	@Column(name="recurring")
	private boolean recurring;

	@Column(name="rrule")
	private String rrule;

	/*
	@Column(name="calendar")
	private Calendar calendar;
	*/
	
	@Column(name="calendar")
	private String calendar;
	
	public Event(int event_id, String title, boolean fullday, Date startDate, Date endDate, Time startTime,
			Time endTime, boolean recurring, String rrule, String calendar) {
		this.event_id = event_id;
		this.title = title;
		this.fullday = fullday;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.recurring = recurring;
		this.rrule = rrule;
		this.calendar = calendar;
	}

	public Event() {
	
	}

	public boolean isRecurring() {
		return recurring;
	}

	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}

	public String getRrule() {
		return rrule;
	}

	public void setRrule(String rrule) {
		this.rrule = rrule;
	}

	/*
	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
*/
	public boolean isFullday() {
		return fullday;
	}

	public String getCalendar() {
		return calendar;
	}

	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}

	public void setFullday(boolean fullday) {
		this.fullday = fullday;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	


}