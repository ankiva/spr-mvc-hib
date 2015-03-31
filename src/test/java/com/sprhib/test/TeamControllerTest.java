package com.sprhib.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import com.sprhib.controller.OrganizationController;
import com.sprhib.dao.OrganizationDAO;
import com.sprhib.dao.TeamDAO;
import com.sprhib.init.BaseTestConfig;
import com.sprhib.model.Organization;
import com.sprhib.model.Team;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = BaseTestConfig.class)
@Transactional
public class TeamControllerTest {
	
	private static final Log LOG = LogFactory.getLog(TeamControllerTest.class);

	@Autowired
	private ConfigurableWebApplicationContext wac;

	private MockMvc mockMvc;

	@Autowired
	private TeamDAO teamDao;
	
	@Autowired
	private OrganizationDAO orgDao;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void testAddPage() throws Exception {
		this.mockMvc.perform(get("/team/add"))
		.andExpect(view().name("add-team-form"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.model().attribute("team", new Team()));
	}
	
	@Test
	@Transactional
	public void testAdd() throws Exception {
		Team team = new Team();
		team.setName("uudu");
		
		this.mockMvc.perform(post("/team/add").flashAttr("team", team))
		.andExpect(view().name("home"))
		.andExpect(status().isOk());
		
		LOG.info("adding test: " + team);

		LOG.info("all teams: " + teamDao.getTeams());
		Assert.assertEquals(team, teamDao.getTeam(team.getId()));
	}
	
	@Test
	@Transactional
	public void testAddWithAssociation() throws Exception {
		Team team = new Team();
		team.setName("gyyt");
		
		Organization org = new Organization();
		org.setName("aas");
		orgDao.addOrganization(org);
		
		team.setOrganization(org);
		
		this.mockMvc.perform(post("/team/add").flashAttr("team", team))
		.andExpect(view().name("home"))
		.andExpect(status().isOk());
		
		LOG.info("adding test: " + team);

		LOG.info("all teams: " + teamDao.getTeams());
		Team inserted = teamDao.getTeam(team.getId());
		Assert.assertEquals(team, inserted);
		Assert.assertEquals(org, inserted.getOrganization());
	}
	
	@Test
	@Transactional
	public void testEditPage() throws Exception {
		Team team = new Team();
		team.setName("koll");
		teamDao.addTeam(team);
		
		this.mockMvc.perform(get("/team/edit/" + team.getId()))
		.andExpect(view().name("edit-team-form"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.model().attribute("team", team));
	}
	
	@Test
	@Transactional
	public void testEdit() throws Exception {
		Team team = new Team();
		team.setName("kooo");
		team.setRating(5);
		teamDao.addTeam(team);
		
		team.setName("uuu");
		
		this.mockMvc.perform(post("/team/edit/" + team.getId()).flashAttr("team", team))
		.andExpect(view().name("home"))
		.andExpect(status().isOk());

		Assert.assertEquals(team, teamDao.getTeam(team.getId()));
	}
	
	@Test
	@Transactional
	public void testDelete() throws Exception {
		Team team = new Team();
		team.setName("jooo");
		teamDao.addTeam(team);
		
		int id = team.getId();
		
		this.mockMvc.perform(get("/team/delete/" + id))
		.andExpect(status().isOk())
		.andExpect(view().name("home"));
		
		Assert.assertNull(teamDao.getTeam(id));
	}

	@Test
	public void getEmptyList() throws Exception {
		this.mockMvc.perform(get("/team/list"))
				.andExpect(view().name("list-of-teams"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("teams", Collections.<Team>emptyList()));
	}

	@Test
	public void getList() throws Exception {
		Team team = new Team();
		team.setName("abu");

		teamDao.addTeam(team);

		this.mockMvc.perform(get("/team/list"))
				.andExpect(view().name("list-of-teams"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("teams", Arrays.asList(team)));
	}
}
