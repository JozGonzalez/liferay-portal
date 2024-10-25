/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.BaseDDMTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.bean.BeanPropertiesImpl;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class DDMFormDeepCopyTest extends BaseDDMTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() {
		setUpBeanPropertiesUtil();
	}

	@Test
	public void testNestedFields() throws Exception {
		DDMForm ddmForm = createDDMForm();

		DDMFormField nameDDMFormField = new DDMFormField("Name", "textarea");

		ddmForm.addDDMFormField(nameDDMFormField);

		DDMFormField phoneDDMFormField = new DDMFormField("Phone", "text");

		nameDDMFormField.addNestedDDMFormField(phoneDDMFormField);

		DDMFormField contactDDMFormField = new DDMFormField("Name", "text");

		nameDDMFormField.addNestedDDMFormField(contactDDMFormField);

		DDMFormField addressDDMFormField = new DDMFormField("ZipCode", "text");

		contactDDMFormField.addNestedDDMFormField(addressDDMFormField);

		DDMForm copyDDMForm = BeanPropertiesUtil.deepCopyProperties(ddmForm);

		List<DDMFormField> copyDDMFormFields = copyDDMForm.getDDMFormFields();

		Assert.assertEquals(
			copyDDMFormFields.toString(), 1, copyDDMFormFields.size());

		DDMFormField copyDDMFormField = copyDDMFormFields.get(0);

		Assert.assertEquals("Name", copyDDMFormField.getName());
		Assert.assertEquals("textarea", copyDDMFormField.getType());

		List<DDMFormField> copyNestedDDMFormFields =
			copyDDMFormField.getNestedDDMFormFields();

		Assert.assertEquals(
			copyNestedDDMFormFields.toString(), 2,
			copyNestedDDMFormFields.size());

		DDMFormField copyNestedDDMFormField = copyNestedDDMFormFields.get(0);

		Assert.assertEquals("Phone", copyNestedDDMFormField.getName());
		Assert.assertEquals("text", copyNestedDDMFormField.getType());

		copyNestedDDMFormField = copyNestedDDMFormFields.get(1);

		Assert.assertEquals("Name", copyNestedDDMFormField.getName());
		Assert.assertEquals("text", copyNestedDDMFormField.getType());

		List<DDMFormField> nestedDDMFormFields =
			copyNestedDDMFormField.getNestedDDMFormFields();

		Assert.assertEquals(
			nestedDDMFormFields.toString(), 1, nestedDDMFormFields.size());

		copyNestedDDMFormField = nestedDDMFormFields.get(0);

		Assert.assertEquals("ZipCode", copyNestedDDMFormField.getName());
		Assert.assertEquals("text", copyNestedDDMFormField.getType());
	}

	@Test
	public void testSomeFieldProperties() throws Exception {
		DDMForm ddmForm = createDDMForm();

		DDMFormField nameDDMFormField = new DDMFormField("Name", "textarea");

		ddmForm.addDDMFormField(nameDDMFormField);

		nameDDMFormField.setFieldNamespace("namespace");
		nameDDMFormField.setIndexType("indexType");

		LocalizedValue label = new LocalizedValue(LocaleUtil.BRAZIL);

		label.addString(LocaleUtil.BRAZIL, "teste");

		nameDDMFormField.setLabel(label);

		nameDDMFormField.setLocalizable(true);
		nameDDMFormField.setMultiple(true);
		nameDDMFormField.setReadOnly(true);
		nameDDMFormField.setRepeatable(true);
		nameDDMFormField.setRequired(true);
		nameDDMFormField.setShowLabel(true);

		LocalizedValue style = new LocalizedValue(LocaleUtil.ENGLISH);

		style.addString(LocaleUtil.ENGLISH, "style");

		nameDDMFormField.setStyle(style);

		LocalizedValue tip = new LocalizedValue(LocaleUtil.FRANCE);

		tip.addString(LocaleUtil.FRANCE, "tip");

		nameDDMFormField.setTip(tip);

		nameDDMFormField.setVisibilityExpression("expression");

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.setDefaultLocale(LocaleUtil.BRAZIL);

		ddmFormFieldOptions.addOptionLabel(
			"teste", LocaleUtil.ENGLISH, "label");

		nameDDMFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		DDMForm copyDDMForm = BeanPropertiesUtil.deepCopyProperties(ddmForm);

		List<DDMFormField> copyDDMFormFields = copyDDMForm.getDDMFormFields();

		DDMFormField copyDDMFormField = copyDDMFormFields.get(0);

		Assert.assertEquals("Name", copyDDMFormField.getName());
		Assert.assertEquals("textarea", copyDDMFormField.getType());
		Assert.assertEquals("namespace", copyDDMFormField.getFieldNamespace());
		Assert.assertEquals("indexType", copyDDMFormField.getIndexType());

		LocalizedValue copyLabel = copyDDMFormField.getLabel();

		Assert.assertEquals(LocaleUtil.BRAZIL, copyLabel.getDefaultLocale());
		Assert.assertEquals("teste", copyLabel.getString(LocaleUtil.BRAZIL));

		Assert.assertTrue(copyDDMFormField.isLocalizable());
		Assert.assertTrue(copyDDMFormField.isMultiple());
		Assert.assertTrue(copyDDMFormField.isReadOnly());
		Assert.assertTrue(copyDDMFormField.isRepeatable());
		Assert.assertTrue(copyDDMFormField.isRequired());
		Assert.assertTrue(copyDDMFormField.isShowLabel());

		LocalizedValue copyStyle = copyDDMFormField.getStyle();

		Assert.assertEquals(LocaleUtil.ENGLISH, copyStyle.getDefaultLocale());
		Assert.assertEquals("style", copyStyle.getString(LocaleUtil.ENGLISH));

		LocalizedValue copyTip = copyDDMFormField.getTip();

		Assert.assertEquals(LocaleUtil.FRANCE, copyTip.getDefaultLocale());
		Assert.assertEquals("tip", copyTip.getString(LocaleUtil.FRANCE));

		Assert.assertEquals(
			"expression", copyDDMFormField.getVisibilityExpression());

		DDMFormFieldOptions copyDDMFormFieldOptions =
			copyDDMFormField.getDDMFormFieldOptions();

		Assert.assertEquals(
			LocaleUtil.BRAZIL, copyDDMFormFieldOptions.getDefaultLocale());

		Map<String, LocalizedValue> copyOptions =
			copyDDMFormFieldOptions.getOptions();

		Assert.assertTrue(copyOptions.containsKey("teste"));

		LocalizedValue copyTesteOptionLabel = copyOptions.get("teste");

		Assert.assertEquals(
			"label", copyTesteOptionLabel.getString(LocaleUtil.ENGLISH));
	}

	@Test
	public void testWithFields() throws Exception {
		DDMForm ddmForm = createDDMForm();

		DDMFormField nameDDMFormField = new DDMFormField("Name", "textarea");

		DDMFormField phoneDDMFormField = new DDMFormField("Phone", "text");

		ddmForm.addDDMFormField(nameDDMFormField);
		ddmForm.addDDMFormField(phoneDDMFormField);

		DDMForm copyDDMForm = BeanPropertiesUtil.deepCopyProperties(ddmForm);

		List<DDMFormField> copyDDMFormFields = copyDDMForm.getDDMFormFields();

		Assert.assertEquals(
			copyDDMFormFields.toString(), 2, copyDDMFormFields.size());

		DDMFormField copyDDMFormField = copyDDMFormFields.get(0);

		Assert.assertEquals("Name", copyDDMFormField.getName());
		Assert.assertEquals("textarea", copyDDMFormField.getType());

		copyDDMFormField = copyDDMFormFields.get(1);

		Assert.assertEquals("Phone", copyDDMFormField.getName());
		Assert.assertEquals("text", copyDDMFormField.getType());
	}

	@Test
	public void testWithoutFields() throws Exception {
		DDMForm ddmForm = createDDMForm();

		DDMForm copyDDMForm = BeanPropertiesUtil.deepCopyProperties(ddmForm);

		Set<Locale> availableLocales = copyDDMForm.getAvailableLocales();

		Assert.assertFalse(
			availableLocales.toString(), availableLocales.isEmpty());
		Assert.assertEquals(availableLocales.toArray()[0], LocaleUtil.US);
	}

	protected void setUpBeanPropertiesUtil() {
		BeanPropertiesUtil beanPropertiesUtil = new BeanPropertiesUtil();

		beanPropertiesUtil.setBeanProperties(new BeanPropertiesImpl());
	}

}