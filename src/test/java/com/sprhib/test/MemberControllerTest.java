package com.sprhib.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import com.sprhib.controller.OrganizationController;
import com.sprhib.dao.MemberDAO;
import com.sprhib.dao.TeamDAO;
import com.sprhib.init.BaseTestConfig;
import com.sprhib.model.Member;
import com.sprhib.model.Team;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = BaseTestConfig.class)
@Transactional
public class MemberControllerTest {
	
	private static final Log LOG = LogFactory.getLog(MemberControllerTest.class);

	@Autowired
	private ConfigurableWebApplicationContext wac;

	private MockMvc mockMvc;

	@Autowired
	private MemberDAO memberDao;
	
	@Autowired
	private TeamDAO teamDao;
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private HibernateTransactionManager txManager;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void testAddPage() throws Exception {
		this.mockMvc.perform(get("/member/add"))
		.andExpect(view().name("add-member-form"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.model().attribute("member", new Member()));
	}
	
	@Test
	@Transactional
	public void testAdd() throws Exception {
		Member member = new Member();
		member.setFirstName("uudu");
		
		this.mockMvc.perform(post("/member/add").flashAttr("member", member))
		.andExpect(view().name("home"))
		.andExpect(status().isOk());
		
		LOG.info("adding test: " + member);

		LOG.info("all members: " + memberDao.getMembers());
		Assert.assertEquals(member, memberDao.getMember(member.getId()));
	}
	
	@Test
	@Transactional
	public void testAddWithAssociations() throws Exception {
		Member member = new Member();
		member.setFirstName("uudu");
		member.setLastName("kuu");
		
		Team team = new Team();
		team.setName("oi");
		teamDao.addTeam(team);
		
		member.setTeams(new HashSet<Team>());
		member.getTeams().add(team);
		
		LOG.info("adding test, before: " + member);
		
		this.mockMvc.perform(post("/member/add").flashAttr("member", member))
		.andExpect(view().name("home"))
		.andExpect(status().isOk());
		
		LOG.info("adding test: " + member);

		Member m = memberDao.getMember(member.getId());
		LOG.info("all members: " + memberDao.getMembers());
		Assert.assertEquals(member, m);
		Assert.assertNotNull(m.getTeams());
		Assert.assertEquals(1, m.getTeams().size());
		Assert.assertTrue(m.getTeams().contains(team));
		
		LOG.info("isactive=" + sessionFactory.getCurrentSession().getTransaction().isActive());
//		sessionFactory.getCurrentSession().getTransaction().commit();
		
//		LOG.info("isactive=" + sessionFactory.getCurrentSession().getTransaction().isActive());
//		sessionFactory.getCurrentSession().getTransaction().
//		sessionFactory.getCurrentSession().beginTransaction();
//		LOG.info("isactive=" + sessionFactory.getCurrentSession().getTransaction().isActive());
//		
//		LOG.info("all members after commit: " + memberDao.getMembers());
//		
//		List s = sessionFactory.getCurrentSession().createSQLQuery("select * from member_teams").list();
//		LOG.info("assoc result: " + s.getClass() + ";" + s);
//		for(Object o : s){
//			LOG.info("assoc row: " + o.getClass() + "; " + Arrays.toString((Object[])o));
//		}
	}
	
	@Test
	@Transactional
	public void testEditPage() throws Exception {
		Member member = new Member();
		member.setFirstName("koll");
		memberDao.addMember(member);
		
		this.mockMvc.perform(get("/member/edit/" + member.getId()))
		.andExpect(view().name("edit-member-form"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.model().attribute("member", member));
	}
	
	@Test
	@Transactional
	public void testEdit() throws Exception {
		Member member = new Member();
		member.setFirstName("kooo");
		memberDao.addMember(member);
		
		member.setFirstName("uuu");
		
		this.mockMvc.perform(post("/member/edit/" + member.getId()).flashAttr("member", member))
		.andExpect(view().name("home"))
		.andExpect(status().isOk());

		Assert.assertEquals(member, memberDao.getMember(member.getId()));
	}
	
	@Test
	@Transactional
	public void testDelete() throws Exception {
		Member member = new Member();
		member.setFirstName("jooo");
		memberDao.addMember(member);
		
		int id = member.getId();
		
		this.mockMvc.perform(get("/member/delete/" + id))
		.andExpect(status().isOk())
		.andExpect(view().name("home"));
		
		Assert.assertNull(memberDao.getMember(id));
	}

	@Test
	public void getEmptyList() throws Exception {
		this.mockMvc.perform(get("/member/list"))
				.andExpect(view().name("list-of-members"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("members", Collections.<Member>emptyList()));
	}

	@Test
	public void getList() throws Exception {
		Member member = new Member();
		member.setFirstName("abu");

		memberDao.addMember(member);

		this.mockMvc.perform(get("/member/list"))
				.andExpect(view().name("list-of-members"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("members", Arrays.asList(member)));
	}
}
