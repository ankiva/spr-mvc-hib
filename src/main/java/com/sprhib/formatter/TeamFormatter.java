package com.sprhib.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import com.sprhib.model.Team;
import com.sprhib.service.TeamService;

public class TeamFormatter implements Formatter<Team>{

	@Autowired
	private TeamService teamService;
	
	@Override
	public String print(Team object, Locale locale) {
		return "" + object.getId();
	}

	@Override
	public Team parse(String text, Locale locale) throws ParseException {
		Team team = teamService.getTeam(Integer.parseInt(text));
		if(team == null){
			throw new ParseException(String.format("Cannot find team by id %s", text), 0);
		}
		return team;
	}

}
