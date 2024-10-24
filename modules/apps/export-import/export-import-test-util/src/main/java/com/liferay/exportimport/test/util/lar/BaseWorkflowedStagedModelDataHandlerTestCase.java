/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.test.util.lar;

import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.WorkflowedModel;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Daniel Kocsis
 */
public abstract class BaseWorkflowedStagedModelDataHandlerTestCase
	extends BaseStagedModelDataHandlerTestCase {

	@Test
	public void testExportWorkflowedStagedModels() throws Exception {
		initExport();

		List<StagedModel> stagedModels = null;

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			stagedModels = addWorkflowedStagedModels(stagingGroup);
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}

		for (StagedModel stagedModel : stagedModels) {
			Assert.assertTrue(
				"Staged model is not a workflowed model",
				stagedModel instanceof WorkflowedModel);

			try {
				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, stagedModel);
			}
			catch (PortletDataException portletDataException) {
				Assert.assertEquals(
					"An unexpected error occurred during the export",
					PortletDataException.STATUS_UNAVAILABLE,
					portletDataException.getType());
			}

			validateWorkflowedExport(portletDataContext, stagedModel);
		}
	}

	protected abstract List<StagedModel> addWorkflowedStagedModels(Group group)
		throws Exception;

	protected Element getExportStagedModelElement(
		PortletDataContext portletDataContext, StagedModel stagedModel) {

		Element rootElement = portletDataContext.getExportDataRootElement();

		Class<?> modelClass = stagedModel.getModelClass();

		Element modelElement = rootElement.element(modelClass.getSimpleName());

		Assert.assertNotNull("Unable to find model element", modelElement);

		XPath xPath = SAXReaderUtil.createXPath(
			"staged-model[@path ='" +
				ExportImportPathUtil.getModelPath(stagedModel) + "']");

		return (Element)xPath.selectSingleNode(modelElement);
	}

	protected void validateWorkflowedExport(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {

		StagedModelDataHandler<?> stagedModelDataHandler =
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				stagedModel.getModelClassName());

		WorkflowedModel workflowedModel = (WorkflowedModel)stagedModel;

		Element exportStagedModelElement = getExportStagedModelElement(
			portletDataContext, stagedModel);

		if (ArrayUtil.contains(
				stagedModelDataHandler.getExportableStatuses(),
				workflowedModel.getStatus())) {

			Assert.assertNotNull(
				"Staged model should be exported", exportStagedModelElement);
		}
		else {
			Assert.assertNull(
				"Staged model should not be exported",
				exportStagedModelElement);
		}
	}

}