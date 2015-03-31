package com.sprhib.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.sprhib.model.Team;
import com.sprhib.service.OrganizationService;
import com.sprhib.service.TeamService;

@Controller
@RequestMapping(value="/team")
public class TeamController {
	
	private static final Log LOG = LogFactory.getLog(TeamController.class);
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public ModelAndView addTeamPage() {
		ModelAndView modelAndView = new ModelAndView("add-team-form");
		modelAndView.addObject("team", new Team());
		modelAndView.addObject("organizations", organizationService.getOrganizations());
		return modelAndView;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public ModelAndView addingTeam(@ModelAttribute Team team, BindingResult result, HttpServletRequest request) {
		if(result.hasErrors()){
			LOG.info("binding errors");
			for(ObjectError e : result.getAllErrors()){
				LOG.info("error: " + e);
			}
		}
		
		LOG.info("adding " + team + "; bytes=" + Arrays.toString(Hex.encode(team.getName().getBytes())));
		ModelAndView modelAndView = new ModelAndView("home");
		teamService.addTeam(team);
		
		String messageCode = "team.created";
		modelAndView.addObject("message", messageSource.getMessage(messageCode, null, null, RequestContextUtils.getLocale(request)));
		
		return modelAndView;
	}
	
	@RequestMapping(value="/list")
	public ModelAndView listOfTeams(RequestContext context) {
		ModelAndView modelAndView = new ModelAndView("list-of-teams");
		
		List<Team> teams = teamService.getTeams();
		modelAndView.addObject("teams", teams);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editTeamPage(@PathVariable Integer id) {
		ModelAndView modelAndView = new ModelAndView("edit-team-form");
		Team team = teamService.getTeam(id);
		modelAndView.addObject("team",team);
		modelAndView.addObject("organizations", organizationService.getOrganizations());
		return modelAndView;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView edditingTeam(@ModelAttribute Team team, @PathVariable Integer id, BindingResult result, HttpServletRequest request) {
		if(result.hasErrors()){
			LOG.info("binding errors");
			for(ObjectError e : result.getAllErrors()){
				LOG.info("error: " + e);
			}
		}
		ModelAndView modelAndView = new ModelAndView("home");
		
		teamService.updateTeam(team);
		
		String messageCode = "team.edited";
		modelAndView.addObject("message", messageSource.getMessage(messageCode, null, null, RequestContextUtils.getLocale(request)));
		
		return modelAndView;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteTeam(@PathVariable Integer id, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("home");
		teamService.deleteTeam(id);
		String messageCode = "team.deleted";
		modelAndView.addObject("message", messageSource.getMessage(messageCode, null, null, RequestContextUtils.getLocale(request)));
		return modelAndView;
	}

}
