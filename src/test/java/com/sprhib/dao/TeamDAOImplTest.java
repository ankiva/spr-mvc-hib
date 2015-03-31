package com.sprhib.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sprhib.init.BaseTestConfig;
import com.sprhib.model.Organization;
import com.sprhib.model.Team;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes=BaseTestConfig.class)
@Transactional
public class TeamDAOImplTest {

	@Autowired
	private TeamDAO teamDAO;
	
	@Autowired
	private OrganizationDAO orgDAO;
	
	@Test
	public void testGetNullTeam(){
		Team result = teamDAO.getTeam(89);
		Assert.assertNull(result);
	}
	
	@Test
	public void testAddGet(){
		Team elem = new Team();
		elem.setName("paju");
		teamDAO.addTeam(elem);
		
		Team result = teamDAO.getTeam(elem.getId());
		Assert.assertNotNull(result);
		Assert.assertEquals(elem, result);
		
		Assert.assertEquals(1, teamDAO.getTeams().size());
	}
	
	@Test
	public void testGetMultiple(){
		Team elem1 = new Team();
		elem1.setName("paju");
		teamDAO.addTeam(elem1);
		Team elem2 = new Team();
		elem2.setName("manna");
		teamDAO.addTeam(elem2);
		
		Assert.assertEquals(2, teamDAO.getTeams().size());
	}
	
	@Test
	public void testDelete(){
		Team elem = new Team();
		elem.setName("paju");
		teamDAO.addTeam(elem);
		Assert.assertEquals(1, teamDAO.getTeams().size());
				
		teamDAO.deleteTeam(elem.getId());
		
		Team result = teamDAO.getTeam(elem.getId());
		
		Assert.assertNull(result);
		
		Assert.assertEquals(0, teamDAO.getTeams().size());
	}
	
	@Test
	public void testUpdate(){
		Team elem = new Team();
		elem.setName("paju");
		teamDAO.addTeam(elem);
		
		Assert.assertEquals(1, teamDAO.getTeams().size());
		
		elem.setName("juu");
		
		teamDAO.updateTeam(elem);
		
		Assert.assertEquals(1, teamDAO.getTeams().size());
		
		Assert.assertEquals(elem.getName(), "juu");
		
		Team result = teamDAO.getTeam(elem.getId());
		Assert.assertNotNull(result);
		Assert.assertEquals(elem, result);
	}
	
	@Test
	public void testTeamWithOrg(){
		Organization org = new Organization();
		org.setName("mamm");
		
		Team team = new Team();
		team.setName("kik");
		team.setRating(3);
		team.setOrganization(org);
		
		teamDAO.addTeam(team);
		
		Team result = teamDAO.getTeam(team.getId());
		
		Assert.assertEquals(team, result);
		Assert.assertEquals(result.getOrganization(), org);
	}
}
