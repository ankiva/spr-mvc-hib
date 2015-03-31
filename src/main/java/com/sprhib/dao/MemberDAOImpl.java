package com.sprhib.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sprhib.model.Member;

@Repository
public class MemberDAOImpl extends AbstractDAO implements MemberDAO {

	@Override
	public void addMember(Member member) {
		getCurrentSession().save(member);
	}

	@Override
	public void updateMember(Member member) {
		Member memberToUpdate = getMember(member.getId());
		memberToUpdate.setFirstName(member.getFirstName());
		memberToUpdate.setLastName(member.getLastName());
		memberToUpdate.setTeams(member.getTeams());
		getCurrentSession().update(memberToUpdate);
	}

	@Override
	public Member getMember(int id) {
		return (Member) getCurrentSession().get(Member.class, id);
	}

	@Override
	public void deleteMember(int id) {
		Member member = getMember(id);
		if (member != null) {
			getCurrentSession().delete(member);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Member> getMembers() {
		return getCurrentSession().createCriteria(Member.class).list();
	}

}
