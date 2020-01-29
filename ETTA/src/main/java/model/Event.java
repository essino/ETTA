package model;

import java.sql.Time;
import java.util.Date;

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
	
	public boolean isFullday() {
		return fullday;
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

	public Event() {
		
	}
}
