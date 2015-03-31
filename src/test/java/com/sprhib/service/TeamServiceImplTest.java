package com.sprhib.service;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.sprhib.dao.TeamDAO;
import com.sprhib.model.Team;

public class TeamServiceImplTest {

	@InjectMocks
	private TeamService teamService = new TeamServiceImpl();
	
	@Mock
	private TeamDAO teamDAOMock;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testAdd(){
		Team team = new Team();
		team.setName("huu");
		team.setRating(3);
		
		teamService.addTeam(team);
		Mockito.verify(teamDAOMock).addTeam(team);
	}
	
	@Test
	public void testUpdate(){
		Team team = new Team();
		team.setId(2);
		team.setName("huu");
		
		teamService.updateTeam(team);
		Mockito.verify(teamDAOMock).updateTeam(team);
	}
	
	@Test
	public void testGet(){
		Team team = new Team();
		team.setId(2);
		team.setName("huu");
		team.setRating(3);
		
		Mockito.when(teamDAOMock.getTeam(team.getId())).thenReturn(team);
		
		Team result = teamService.getTeam(team.getId());
		
		Mockito.verify(teamDAOMock).getTeam(team.getId());
		Assert.assertEquals(team, result);
	}
	
	@Test
	public void testDelete(){
		int id = 5;
		
		teamService.deleteTeam(id);
		Mockito.verify(teamDAOMock).deleteTeam(id);
	}
	
	@Test
	public void testGetAll(){
		Team team = new Team();
		team.setId(2);
		team.setName("huu");
		team.setRating(3);
		
		Mockito.when(teamDAOMock.getTeams()).thenReturn(Arrays.asList(team));
		
		List<Team> result = teamService.getTeams();
		Assert.assertEquals(result, Arrays.asList(team));
	}
}
