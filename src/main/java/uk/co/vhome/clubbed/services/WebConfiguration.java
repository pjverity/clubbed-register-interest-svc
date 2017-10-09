package uk.co.vhome.clubbed.services;

import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfiguration implements WebMvcConfigurer
{
	/*
	 * By default, Spring will strip off anything in the last part of a URL
	 * that ends in .+, interpreting it as a file extension. As the last part
	 * of the URL is an e-mail address, we don't want the address to be truncated,
	 * so we disable this default behaviour
	 */
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer)
	{
		configurer.setUseSuffixPatternMatch(false);
	}

}
