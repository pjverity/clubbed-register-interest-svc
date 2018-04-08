package uk.co.vhome.clubbed.svc.enquiryhandler.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Simple representation of a scheduled event
 */
@Entity
@Table(name = "schedules")
public class Schedule
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private LocalDate commences;

	private LocalTime time;

	private LocalTime duration;

	private String name;

	private String location;

	private Boolean active;

	private Schedule()
	{
	}

	public Integer getId()
	{
		return id;
	}

	public LocalDate getCommences()
	{
		return commences;
	}

	public LocalTime getTime()
	{
		return time;
	}

	public LocalTime getDuration()
	{
		return duration;
	}

	public String getName()
	{
		return name;
	}

	public String getLocation()
	{
		return location;
	}

	public Boolean getActive()
	{
		return active;
	}
}
