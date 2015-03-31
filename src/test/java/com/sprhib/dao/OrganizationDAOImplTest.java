package com.sprhib.dao;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
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

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes=BaseTestConfig.class)
@Transactional
public class OrganizationDAOImplTest {
	
	private static final Log LOG = LogFactory.getLog(OrganizationDAOImplTest.class);

	@Autowired
	private OrganizationDAO organizationDAO;
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private SessionFactory sessionFactory;
		
	@Test
	public void testGetNullOrganization(){
		Organization result = organizationDAO.getOrganization(89);
		Assert.assertNull(result);
	}
	
	@Test
	public void testAddGet(){
		Organization org = new Organization();
		org.setName("paju");
		organizationDAO.addOrganization(org);
		
		Organization result = organizationDAO.getOrganization(org.getId());
		Assert.assertNotNull(result);
		Assert.assertEquals(org, result);
		
		LOG.info("orgs = " + organizationDAO.getOrganizations());
		Assert.assertEquals(1, organizationDAO.getOrganizations().size());
		
		List esult = sessionFactory.getCurrentSession().createQuery("from Organization").list();
		LOG.info("selected: " + esult.getClass() + ";" + esult);
		for(Object o : esult){
			LOG.info("selected row: " + o.getClass() + "; " + o);
		}
		List s = sessionFactory.getCurrentSession().createSQLQuery("select * from Organization").list();
		LOG.info("selected: " + s.getClass() + ";" + s);
		for(Object o : s){
			LOG.info("selected row: " + o.getClass() + "; " + Arrays.toString((Object[])o));
		}
		
	}
	
	@Test
	public void testGetMultiple(){
		Organization org1 = new Organization();
		org1.setName("paju");
		organizationDAO.addOrganization(org1);
		Organization org2 = new Organization();
		org2.setName("paju");
		organizationDAO.addOrganization(org2);
		
		Assert.assertEquals(2, organizationDAO.getOrganizations().size());
	}
	
	@Test
	public void testDelete(){
		Organization org = new Organization();
		org.setName("paju");
		organizationDAO.addOrganization(org);
		LOG.info("generated id=" + org.getId());
		Assert.assertEquals(1, organizationDAO.getOrganizations().size());
		LOG.info("orgs = " + organizationDAO.getOrganizations());
		
		Organization result = organizationDAO.getOrganization(org.getId());
		LOG.info("enne del " + result);
		
		organizationDAO.deleteOrganization(org.getId());
		
		result = organizationDAO.getOrganization(org.getId());
		LOG.info("p�rast del " + result);
		
		LOG.info("orgs = " + organizationDAO.getOrganizations());
		
		Assert.assertEquals(0, organizationDAO.getOrganizations().size());
	}
	
	@Test
	public void testUpdate(){
		Organization org = new Organization();
		org.setName("paju");
		organizationDAO.addOrganization(org);
		
		Assert.assertEquals(1, organizationDAO.getOrganizations().size());
		
		org.setName("juu");
		
		organizationDAO.updateOrganization(org);
		
		Assert.assertEquals(1, organizationDAO.getOrganizations().size());
		
		Assert.assertEquals(org.getName(), "juu");
		
		Organization result = organizationDAO.getOrganization(org.getId());
		Assert.assertNotNull(result);
		Assert.assertEquals(org, result);
	}
}
