package com.sprhib.service;

import java.util.List;

import com.sprhib.model.Organization;

public interface OrganizationService {

	void addOrganization(Organization org);

	void updateOrganization(Organization org);

	Organization getOrganization(int id);

	void deleteOrganization(int id);

	List<Organization> getOrganizations();
}
