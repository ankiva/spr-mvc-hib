package com.sprhib.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import com.sprhib.controller.OrganizationController;
import com.sprhib.dao.OrganizationDAO;
import com.sprhib.init.BaseTestConfig;
import com.sprhib.model.Organization;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = BaseTestConfig.class)
@Transactional
public class OrganizationControllerTest {
	
	private static final Log LOG = LogFactory.getLog(OrganizationControllerTest.class);

	@Autowired
	private ConfigurableWebApplicationContext wac;

	private MockMvc mockMvc;

	@Autowired
	private OrganizationDAO orgDao;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void testAddPage() throws Exception {
		this.mockMvc.perform(get("/organization/add"))
		.andExpect(view().name("add-organization-form"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.model().attribute("organization", new Organization()));
	}
	
	@Test
	@Transactional
	public void testAdd() throws Exception {
		Organization org = new Organization();
		org.setName("uudu");
		
		this.mockMvc.perform(post("/organization/add").flashAttr("organization", org))
		.andExpect(view().name("home"))
		.andExpect(status().isOk());
		
		LOG.info("adding test: " + org);

		LOG.info("all orgs: " + orgDao.getOrganizations());
		Assert.assertEquals(org, orgDao.getOrganization(org.getId()));
	}
	
	@Test
	@Transactional
	public void testEditPage() throws Exception {
		Organization org = new Organization();
		org.setName("koll");
		orgDao.addOrganization(org);
		
		this.mockMvc.perform(get("/organization/edit/" + org.getId()))
		.andExpect(view().name("edit-organization-form"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.model().attribute("organization", org));
	}
	
	@Test
	@Transactional
	public void testEdit() throws Exception {
		Organization org = new Organization();
		org.setName("kooo");
		orgDao.addOrganization(org);
		
		org.setName("uuu");
		
		this.mockMvc.perform(post("/organization/edit/" + org.getId()).flashAttr("organization", org))
		.andExpect(view().name("home"))
		.andExpect(status().isOk());

		Assert.assertEquals(org, orgDao.getOrganization(org.getId()));
	}
	
	@Test
	@Transactional
	public void testDelete() throws Exception {
		Organization org = new Organization();
		org.setName("jooo");
		orgDao.addOrganization(org);
		
		int id = org.getId();
		
		this.mockMvc.perform(get("/organization/delete/" + id))
		.andExpect(status().isOk())
		.andExpect(view().name("home"));
		
		Assert.assertNull(orgDao.getOrganization(id));
	}

	@Test
	public void getEmptyList() throws Exception {
		this.mockMvc.perform(get("/organization/list"))
				.andExpect(view().name("list-of-organizations"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("organizations", Collections.<Organization>emptyList()));
	}

	@Test
	public void getList() throws Exception {
		Organization org = new Organization();
		org.setName("abu");

		orgDao.addOrganization(org);

		this.mockMvc.perform(get("/organization/list"))
				.andExpect(view().name("list-of-organizations"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("organizations", Arrays.asList(org)));
	}
}
