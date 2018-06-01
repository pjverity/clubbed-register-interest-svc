package uk.co.vhome.clubbed.svc.emailnotifier.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "mail_templates")
public class MailTemplate
{
	@Id
	private Long Id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String template;

	@Column(nullable = false)
	private LocalDateTime modified;

	public Long getId()
	{
		return Id;
	}

	public void setId(Long id)
	{
		Id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTemplate()
	{
		return template;
	}

	public void setTemplate(String template)
	{
		this.template = template;
	}

	public LocalDateTime getModified()
	{
		return modified;
	}

	public void setModified(LocalDateTime modified)
	{
		this.modified = modified;
	}

}
