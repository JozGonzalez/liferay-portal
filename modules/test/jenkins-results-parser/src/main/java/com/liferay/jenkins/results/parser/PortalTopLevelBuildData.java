/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PortalTopLevelBuildData
	extends BaseTopLevelBuildData implements PortalBuildData {

	public static boolean isValidJSONObject(JSONObject jsonObject) {
		return isValidJSONObject(jsonObject, _TYPE);
	}

	@Override
	public Job.BuildProfile getBuildProfile() {
		String portalUpstreamBranchName = getPortalUpstreamBranchName();

		if (portalUpstreamBranchName.equals("master") ||
			portalUpstreamBranchName.startsWith("7")) {

			return Job.BuildProfile.DXP;
		}

		return Job.BuildProfile.PORTAL;
	}

	@Override
	public String getPortalBranchSHA() {
		return getString("portal_branch_sha");
	}

	@Override
	public String getPortalGitHubBranchName() {
		return getGitHubBranchName(getPortalGitHubURL());
	}

	@Override
	public String getPortalGitHubRepositoryName() {
		return getGitHubRepositoryName(getPortalGitHubURL());
	}

	@Override
	public String getPortalGitHubURL() {
		return getString("portal_github_url");
	}

	@Override
	public String getPortalGitHubUsername() {
		return getGitHubUsername(getPortalGitHubURL());
	}

	@Override
	public String getPortalUpstreamBranchName() {
		return getString("portal_upstream_branch_name");
	}

	@Override
	public void setPortalBranchSHA(String portalBranchSHA) {
		put("portal_branch_sha", portalBranchSHA);
	}

	@Override
	public void setPortalGitHubURL(String portalGitHubURL) {
		put("portal_github_url", portalGitHubURL);
	}

	@Override
	public void setPortalUpstreamBranchName(String portalUpstreamBranchName) {
		put("portal_upstream_branch_name", portalUpstreamBranchName);
	}

	protected PortalTopLevelBuildData(
		String runID, String jobName, String buildURL) {

		super(runID, jobName, buildURL);

		setPortalGitHubURL(_getPortalGitHubURL());
		setPortalUpstreamBranchName(_getPortalUpstreamBranchName());

		validateKeys(_REQUIRED_KEYS);
	}

	@Override
	protected String getType() {
		return _TYPE;
	}

	private String _getPortalGitHubURL() {
		String portalGitHubURL = optString("portal_github_url");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(portalGitHubURL)) {
			return portalGitHubURL;
		}

		return URL_PORTAL_GITHUB_BRANCH_DEFAULT;
	}

	private String _getPortalUpstreamBranchName() {
		String portalUpstreamBranchName = optString(
			"portal_upstream_branch_name");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(portalUpstreamBranchName)) {
			return portalUpstreamBranchName;
		}

		return NAME_PORTAL_UPSTREAM_BRANCH_DEFAULT;
	}

	private static final String[] _REQUIRED_KEYS = {
		"portal_github_url", "portal_upstream_branch_name"
	};

	private static final String _TYPE = "portal_top_level";

}