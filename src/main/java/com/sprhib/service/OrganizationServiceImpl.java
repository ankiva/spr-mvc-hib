package com.sprhib.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprhib.dao.OrganizationDAO;
import com.sprhib.model.Organization;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationDAO organizationDAO;

	@Override
	public void addOrganization(Organization org) {
		organizationDAO.addOrganization(org);
	}

	@Override
	public void updateOrganization(Organization org) {
		organizationDAO.updateOrganization(org);
	}

	@Override
	public Organization getOrganization(int id) {
		return organizationDAO.getOrganization(id);
	}

	@Override
	public void deleteOrganization(int id) {
		organizationDAO.deleteOrganization(id);
	}

	@Override
	public List<Organization> getOrganizations() {
		return organizationDAO.getOrganizations();
	}

}
