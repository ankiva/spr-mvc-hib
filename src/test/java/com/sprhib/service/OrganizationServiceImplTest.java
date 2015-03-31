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

import com.sprhib.dao.OrganizationDAO;
import com.sprhib.model.Organization;

public class OrganizationServiceImplTest {
	
	@InjectMocks
	private OrganizationService orgService = new OrganizationServiceImpl();
	
	@Mock
	private OrganizationDAO orgDAOMock;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testAdd(){
		Organization org = new Organization();
		org.setName("huu");
		
		orgService.addOrganization(org);
		Mockito.verify(orgDAOMock).addOrganization(org);
	}
	
	@Test
	public void testUpdate(){
		Organization org = new Organization();
		org.setId(2);
		org.setName("huu");
		
		orgService.updateOrganization(org);
		Mockito.verify(orgDAOMock).updateOrganization(org);
	}
	
	@Test
	public void testGet(){
		Organization org = new Organization();
		org.setId(2);
		org.setName("huu");
		
		Mockito.when(orgDAOMock.getOrganization(org.getId())).thenReturn(org);
		
		Organization result = orgService.getOrganization(org.getId());
		
		Mockito.verify(orgDAOMock).getOrganization(org.getId());
		Assert.assertEquals(org, result);
	}
	
	@Test
	public void testDelete(){
		int id = 5;
		
		orgService.deleteOrganization(id);
		Mockito.verify(orgDAOMock).deleteOrganization(id);
	}
	
	@Test
	public void testGetAll(){
		Organization org = new Organization();
		org.setId(2);
		org.setName("huu");
		
		Mockito.when(orgDAOMock.getOrganizations()).thenReturn(Arrays.asList(org));
		
		List<Organization> result = orgService.getOrganizations();
		Assert.assertEquals(result, Arrays.asList(org));
	}
}
