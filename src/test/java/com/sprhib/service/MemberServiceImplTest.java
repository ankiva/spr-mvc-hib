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

import com.sprhib.dao.MemberDAO;
import com.sprhib.model.Member;

public class MemberServiceImplTest {

	@InjectMocks
	private MemberService memberService = new MemberServiceImpl();
	
	@Mock
	private MemberDAO orgDAOMock;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testAdd(){
		Member team = new Member();
		team.setFirstName("huu");
		team.setLastName("foo");
		
		memberService.addMember(team);
		Mockito.verify(orgDAOMock).addMember(team);
	}
	
	@Test
	public void testUpdate(){
		Member team = new Member();
		team.setId(2);
		team.setFirstName("huu");
		
		memberService.updateMember(team);
		Mockito.verify(orgDAOMock).updateMember(team);
	}
	
	@Test
	public void testGet(){
		Member team = new Member();
		team.setId(2);
		team.setFirstName("huu");
		team.setLastName("foo");
		
		Mockito.when(orgDAOMock.getMember(team.getId())).thenReturn(team);
		
		Member result = memberService.getMember(team.getId());
		
		Mockito.verify(orgDAOMock).getMember(team.getId());
		Assert.assertEquals(team, result);
	}
	
	@Test
	public void testDelete(){
		int id = 5;
		
		memberService.deleteMember(id);
		Mockito.verify(orgDAOMock).deleteMember(id);
	}
	
	@Test
	public void testGetAll(){
		Member team = new Member();
		team.setId(2);
		team.setFirstName("huu");
		team.setLastName("foo");
		
		Mockito.when(orgDAOMock.getMembers()).thenReturn(Arrays.asList(team));
		
		List<Member> result = memberService.getMembers();
		Assert.assertEquals(result, Arrays.asList(team));
	}

}
