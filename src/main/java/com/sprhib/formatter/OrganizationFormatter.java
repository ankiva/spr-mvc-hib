package com.sprhib.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import com.sprhib.model.Organization;
import com.sprhib.service.OrganizationService;

public class OrganizationFormatter implements Formatter<Organization> {

	@Autowired
	private OrganizationService organizationService;
	
	@Override
	public String print(Organization object, Locale locale) {
		return "" + object.getId();
	}

	@Override
	public Organization parse(String text, Locale locale) throws ParseException {
		Organization organization = organizationService.getOrganization(Integer.parseInt(text));
		if(organization == null){
			throw new ParseException(String.format("No organization exists with id %s", text), 0);
		}
		return organization;
	}

}
