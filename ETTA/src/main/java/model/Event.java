package model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.*;

/**
 * Model class for Events. Used in the creation of the database table for Events through Hibernate.
 */

@Entity
@Table(name="Event")
public class Event {

	/**
	 * a unique integer for identifying the event
	 */
	@Id
	//@GeneratedValue
	@Column(name="event_id")
	private int event_id;

	/**
	 * String containing the title of the event
	 */
	@Column(name="title")
	private String title;
	
	/**
	 * location of the event in String
	 */
	@Column(name="location")
	private String location;

	/**
	 * boolean indicating whether or not the event lasts a whole day
	 */
	@Column(name="fullday")
	private boolean fullday;

	/**
	 * start Date of the event
	 */
	@Column(name="startDate")
	private Date startDate;

	/**
	 * end Date of the event
	 */
	@Column(name="endDate")
	private Date endDate;

	/**
	 * Time of day when the event starts
	 */
	@Column(name="startTime")
	private Time startTime;

	/**
	 * Time of day when the event ends
	 */
	@Column(name="endTime")
	private Time endTime;

	/**
	 * Boolean indicating whether or not the event is recurring
	 */
	@Column(name="recurring")
	private boolean recurring;

	/**
	 * String describing how often the events recurs
	 */
	@Column(name="rrule")
	private String rrule;
	
	/**
	 * String describing how often the events recurs
	 */
	@Column(name="calendar")
	private String calendar;
	
	/**
	 * Constructor to create events.
	 *@param event_id the id number of the event in the database
	 *@param title String describing what the event is. 
	 *@param fullday boolean showing if the event lasts all day
	 *@param startDate Date on which the event starts
	 *@param endDate Date on which the event ends
	 *@param recurring boolean showing if the event recurs
	 *@param rrule String describing how often the events recurs
	 *@param calendar the calendar to which the event belongs to
	 */
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
	
	/**
	 * Constructor to create events.
	 *@param title String describing what the event is. 
	 *@param fullday boolean showing if the event lasts all day
	 *@param startDate Date on which the event starts
	 *@param endDate Date on which the event ends
	 *@param recurring boolean showing if the event recurs
	 *@param rrule String describing how often the events recurs
	 *@param calendar the calendar to which the event belongs to
	 */
	public Event(String title, String location, boolean fullday, Date startDate, Date endDate, Time startTime,
			Time endTime, boolean recurring, String rrule, String calendar) {
		this.title = title;
		this.location = location;
		this.fullday = fullday;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.recurring = recurring;
		this.rrule = rrule;
		this.calendar = calendar;
	}

	/**
	 * Constructor to create borrowed events. No parameters.
	 */
	public Event() {
	
	}
	
	/**
	 * Function to fetch the recurrence of the event from the database.
	 * @return recurring boolean showing if the event recurs
	 */
	public boolean isRecurring() {
		return recurring;
	}
	/**
	 * Function to register the recurrence of the event  in the database.
	 * @param recurring boolean showing if the event recurs
	 */
	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}
	/**
	 * Function to fetch the recurrence period of the event from the database.
	 * @return rrule String describing how often the events recurs
	 */
	public String getRrule() {
		return rrule;
	}
	/**
	 * Function to register the recurrence period of the event  in the database.
	 * @param rrule String describing how often the events recurs
	 */
	public void setRrule(String rrule) {
		this.rrule = rrule;
	}
	
	/**
	 * Function to fetch if the event lasts all day from the database.
	 * @return fullday boolean showing if the event lasts all day
	 */
	public boolean isFullday() {
		return fullday;
	}
	
	/**
	 * Function to register that the event lasts all day in the database.
	 * @param fullday boolean showing if the event lasts all day
	 */
	public void setFullday(boolean fullday) {
		this.fullday = fullday;
	}
	/**
	 * Function to fetch the calendar to which the event belongs to from the database.
	 * @return calendar the calendar to which the event belongs to
	 */
	public String getCalendar() {
		return calendar;
	}
	/**
	 * Function to register the calendar to which the event belongs to in the database.
	 * @param calendar the calendar to which the event belongs to
	 */
	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}

	/**
	 * Function to fetch the start date of the event from the database.
	 * @return startDate Date on which the event starts
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * Function to register the start date of the event  in the database.
	 * @param startDate Date on which the event starts
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * Function to fetch the end date of the event from the database.
	 * @return endDate Date on which the event ends
	 */
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	 * Function to register the end date of the event in the database.
	 * @param endDate Date on which the event ends
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Function to fetch the start time of the event from the database.
	 * @return startTime Time at which the event starts
	 */
	public Time getStartTime() {
		return startTime;
	}
	
	/**
	 * Function to register the end time of the event in the database.
	 * @param startTime Time at which the event starts
	 */
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * Function to fetch the end time of the event from the database.
	 * @return endTime Time at which the event ends
	 */
	public Time getEndTime() {
		return endTime;
	}
	/**
	 * Function to register the end time of the event in the database.
	 * @param endTime Time at which the event ends
	 */
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	/**
	 * Function to fetch the id number of the event from the database.
	 * @return event_id the id number of the event in the database
	 */
	public int getEvent_id() {
		return event_id;
	}

	/**
	 * Function to register the id number of the event into the database
	 * @param event_id  Int the id number of the event in the database
	 */
	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

	/**
	 * Function to fetch the name of the event from the database.
	 * @return title the name of the event from the database
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Function to register the name of the event in the database.
	 * @param title String the name of the event in the database
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Function to fetch the location of the event from the database.
	 * @return location the location of the event from the database
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Function to register the location of the event in the database.
	 * @param location String the location of the event in the database
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
}