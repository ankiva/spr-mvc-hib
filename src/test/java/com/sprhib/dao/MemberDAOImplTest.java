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
import com.sprhib.model.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes=BaseTestConfig.class)
@Transactional
public class MemberDAOImplTest {

	@Autowired
	private MemberDAO memberDAO;
	
	@Test
	public void testGetNullMember(){
		Member result = memberDAO.getMember(89);
		Assert.assertNull(result);
	}
	
	@Test
	public void testAddGet(){
		Member elem = new Member();
		elem.setFirstName("paju");
		memberDAO.addMember(elem);
		
		Member result = memberDAO.getMember(elem.getId());
		Assert.assertNotNull(result);
		Assert.assertEquals(elem, result);
		
		Assert.assertEquals(1, memberDAO.getMembers().size());
	}
	
	@Test
	public void testGetMultiple(){
		Member elem1 = new Member();
		elem1.setFirstName("paju");
		memberDAO.addMember(elem1);
		Member elem2 = new Member();
		elem2.setFirstName("manna");
		elem2.setLastName("oop");
		memberDAO.addMember(elem2);
		
		Assert.assertEquals(2, memberDAO.getMembers().size());
	}
	
	@Test
	public void testDelete(){
		Member elem = new Member();
		elem.setLastName("paju");
		memberDAO.addMember(elem);
		Assert.assertEquals(1, memberDAO.getMembers().size());
				
		memberDAO.deleteMember(elem.getId());
		
		Member result = memberDAO.getMember(elem.getId());
		
		Assert.assertNull(result);
		
		Assert.assertEquals(0, memberDAO.getMembers().size());
	}
	
	@Test
	public void testUpdate(){
		Member elem = new Member();
		elem.setLastName("paju");
		memberDAO.addMember(elem);
		
		Assert.assertEquals(1, memberDAO.getMembers().size());
		
		elem.setLastName("juu");
		
		memberDAO.updateMember(elem);
		
		Assert.assertEquals(1, memberDAO.getMembers().size());
		
		Assert.assertEquals(elem.getLastName(), "juu");
		
		Member result = memberDAO.getMember(elem.getId());
		Assert.assertNotNull(result);
		Assert.assertEquals(elem, result);
	}
}
