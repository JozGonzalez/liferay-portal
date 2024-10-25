/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.BaseDDMTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValuesTransformerTest extends BaseDDMTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpJSONFactoryUtil();
	}

	@Test
	public void testTransformNestedRepeatableTextFormFieldValue()
		throws Exception {

		DDMForm ddmForm = createDDMForm();

		DDMFormField nameDDMFormField = new DDMFormField("Name", "textarea");

		DDMFormField phoneDDMFormField = new DDMFormField("Phone", "text");

		phoneDDMFormField.setRepeatable(true);

		nameDDMFormField.addNestedDDMFormField(phoneDDMFormField);

		ddmForm.addDDMFormField(nameDDMFormField);

		DDMFormValues ddmFormValues = createDDMFormValues(ddmForm);

		DDMFormFieldValue nameDDMFormFieldValue = createDDMFormFieldValue(
			"Name", new UnlocalizedValue("Joe Smith"));

		nameDDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue("Phone", new UnlocalizedValue("123")));
		nameDDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue("Phone", new UnlocalizedValue("456")));
		nameDDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue("Phone", new UnlocalizedValue("789")));

		ddmFormValues.addDDMFormFieldValue(nameDDMFormFieldValue);

		DDMFormValuesTransformer ddmFormValuesTransformer =
			new DDMFormValuesTransformer(ddmFormValues);

		String prefix = "+1";

		ddmFormValuesTransformer.addTransformer(
			new DDMFormFieldValuePrefixAppender(prefix));

		ddmFormValuesTransformer.transform();

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		nameDDMFormFieldValue = ddmFormFieldValues.get(0);

		testDDMFormFieldValue(ddmFormFieldValues.get(0), "Joe Smith");

		List<DDMFormFieldValue> phoneDDMFormFieldValues =
			nameDDMFormFieldValue.getNestedDDMFormFieldValues();

		testDDMFormFieldValue(
			phoneDDMFormFieldValues.get(0), prefix.concat("123"));
		testDDMFormFieldValue(
			phoneDDMFormFieldValues.get(1), prefix.concat("456"));
		testDDMFormFieldValue(
			phoneDDMFormFieldValues.get(2), prefix.concat("789"));
	}

	@Test
	public void testTransformTextFormFieldValue() throws Exception {
		DDMForm ddmForm = createDDMForm();

		addTextDDMFormFields(ddmForm, "FirstName", "LastName");

		DDMFormField ddmFormField = new DDMFormField("Description", "textarea");

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = createDDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("FirstName", new UnlocalizedValue("Joe")));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("LastName", new UnlocalizedValue("Smith")));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"Description", new UnlocalizedValue("Description Value")));

		DDMFormValuesTransformer ddmFormValuesTransformer =
			new DDMFormValuesTransformer(ddmFormValues);

		String prefix = StringPool.UNDERLINE;

		ddmFormValuesTransformer.addTransformer(
			new DDMFormFieldValuePrefixAppender(prefix));

		ddmFormValuesTransformer.transform();

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		testDDMFormFieldValue(ddmFormFieldValues.get(0), prefix.concat("Joe"));
		testDDMFormFieldValue(
			ddmFormFieldValues.get(1), prefix.concat("Smith"));
		testDDMFormFieldValue(ddmFormFieldValues.get(2), "Description Value");
	}

	@Override
	protected DDMFormField createTextDDMFormField(String name) {
		return createTextDDMFormField(name, name, false, false, false);
	}

	protected void testDDMFormFieldValue(
		DDMFormFieldValue ddmFormFieldValue, String expectedValue) {

		Value value = ddmFormFieldValue.getValue();

		Assert.assertEquals(
			expectedValue, value.getString(value.getDefaultLocale()));
	}

	private static class DDMFormFieldValuePrefixAppender
		implements DDMFormFieldValueTransformer {

		@Override
		public String getFieldType() {
			return "text";
		}

		@Override
		public void transform(DDMFormFieldValue ddmFormFieldValue) {
			Value value = ddmFormFieldValue.getValue();

			for (Locale locale : value.getAvailableLocales()) {
				value.addString(
					locale, _prefix.concat(value.getString(locale)));
			}
		}

		private DDMFormFieldValuePrefixAppender(String prefix) {
			_prefix = prefix;
		}

		private final String _prefix;

	}

}