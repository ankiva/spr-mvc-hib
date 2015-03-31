package com.sprhib.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.sprhib.model.Member;
import com.sprhib.service.MemberService;
import com.sprhib.service.TeamService;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	private static final Log LOG = LogFactory.getLog(MemberController.class);

	@Autowired
	private TeamService teamService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public ModelAndView addMemberPage() {
		ModelAndView modelAndView = new ModelAndView("add-member-form");
		modelAndView.addObject("member", new Member());
		modelAndView.addObject("allteams", teamService.getTeams());
		return modelAndView;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public ModelAndView addingMember(@ModelAttribute Member member, BindingResult result, HttpServletRequest request) {
		if(result.hasErrors()){
			LOG.info("binding errors");
			for(ObjectError e : result.getAllErrors()){
				LOG.info("error: " + e);
			}
		}
		ModelAndView modelAndView = new ModelAndView("home");
		memberService.addMember(member);
		
		String messageCode = "member.created";
		modelAndView.addObject("membermessage", messageSource.getMessage(messageCode, null, null, RequestContextUtils.getLocale(request)));
		
		return modelAndView;
	}
	
	@RequestMapping(value="/list")
	public ModelAndView listOfMembers() {
		ModelAndView modelAndView = new ModelAndView("list-of-members");
		
		List<Member> members = memberService.getMembers();
		for(Member m : members){
			m.getTeams();
		}
		modelAndView.addObject("members", members);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editMemberPage(@PathVariable Integer id) {
		ModelAndView modelAndView = new ModelAndView("edit-member-form");
		Member member = memberService.getMember(id);
		modelAndView.addObject("member",member);
		modelAndView.addObject("allteams", teamService.getTeams());
		return modelAndView;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView edditingMember(@ModelAttribute Member member, @PathVariable Integer id, HttpServletRequest request) {
		
		ModelAndView modelAndView = new ModelAndView("home");
		
		memberService.updateMember(member);
		
		String messageCode = "member.edited";
		modelAndView.addObject("membermessage", messageSource.getMessage(messageCode, null, null, RequestContextUtils.getLocale(request)));
		
		return modelAndView;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteMember(@PathVariable Integer id, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("home");
		memberService.deleteMember(id);
		String messageCode = "member.deleted";
		modelAndView.addObject("membermessage", messageSource.getMessage(messageCode, null, null, RequestContextUtils.getLocale(request)));
		return modelAndView;
	}
}
