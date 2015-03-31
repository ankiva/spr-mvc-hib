package com.sprhib.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.sprhib.model.Organization;
import com.sprhib.service.OrganizationService;

@Controller
@RequestMapping("/organization")
public class OrganizationController {
	
	private static final Log LOG = LogFactory.getLog(OrganizationController.class);

	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public ModelAndView addOrganizationPage() {
		ModelAndView modelAndView = new ModelAndView("add-organization-form");
		modelAndView.addObject("organization", new Organization());
		return modelAndView;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public ModelAndView addingOrganization(@ModelAttribute Organization organization, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("home");
		organizationService.addOrganization(organization);
		String messageCode = "organization.created";
		modelAndView.addObject("organizationmessage", messageSource.getMessage(messageCode, null, null, RequestContextUtils.getLocale(request)));
		
		return modelAndView;
	}
	
	@RequestMapping(value="/list")
	public String listOfOrganizations(Model model) {
		List<Organization> organizations = organizationService.getOrganizations();
		model.addAttribute("organizations", organizations);
		return "list-of-organizations";
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editOrganizationPage(@PathVariable Integer id) {
		ModelAndView modelAndView = new ModelAndView("edit-organization-form");
		Organization organization = organizationService.getOrganization(id);
		modelAndView.addObject("organization", organization);
		return modelAndView;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView edditingOrganization(@ModelAttribute Organization organization, @PathVariable Integer id, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("home");
		organizationService.updateOrganization(organization);
		String messageCode = "organization.edited";		
		modelAndView.addObject("organizationmessage", messageSource.getMessage(messageCode, null, null, RequestContextUtils.getLocale(request)));
		
		return modelAndView;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteOrganization(@PathVariable Integer id, HttpServletRequest request) {
		LOG.info("delete request for id " + id);
		ModelAndView modelAndView = new ModelAndView("home");
		organizationService.deleteOrganization(id);
		String messageCode = "organization.deleted";
		
		modelAndView.addObject("organizationmessage", messageSource.getMessage(messageCode, null, null, RequestContextUtils.getLocale(request)));
		return modelAndView;
	}
}
