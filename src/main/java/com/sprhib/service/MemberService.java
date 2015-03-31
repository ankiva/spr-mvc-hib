package com.sprhib.service;

import java.util.List;

import com.sprhib.model.Member;

public interface MemberService {

	void addMember(Member member);

	void updateMember(Member member);

	Member getMember(int id);

	void deleteMember(int id);

	List<Member> getMembers();
}
