package com.sprhib.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sprhib.model.Organization;

@Repository
public class OrganizationDAOImpl extends AbstractDAO implements OrganizationDAO {

	@Override
	public void addOrganization(Organization org) {
		getCurrentSession().save(org);
	}

	@Override
	public void updateOrganization(Organization org) {
		Organization organizationToUpdate = getOrganization(org.getId());
		organizationToUpdate.setName(org.getName());
		getCurrentSession().update(organizationToUpdate);
	}

	@Override
	public Organization getOrganization(int id) {
		return (Organization) getCurrentSession().get(Organization.class, id);
	}

	@Override
	public void deleteOrganization(int id) {
		Organization org = getOrganization(id);
		if (org != null) {
			getCurrentSession().delete(org);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> getOrganizations() {
		return getCurrentSession().createCriteria(Organization.class).list();
	}

}
