/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal;

import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class QuartzTriggerFactoryTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDailyEveryTwoDaysTrigger() {
		Date startDate = _dateOf(2017, 0, 1, 6, 0);

		Trigger trigger = _triggerOf(startDate, null, "0 0 7 1/2 * ? *");

		_assertFireDates(
			trigger, startDate, _dateOf(2017, 0, 1, 7, 0),
			_dateOf(2017, 0, 3, 7, 0), _dateOf(2017, 0, 5, 7, 0));
	}

	@Test
	public void testDailyEveryWeekdayTrigger() {
		Date startDate = _dateOf(2017, 0, 1, 6, 0);

		Trigger trigger = _triggerOf(startDate, null, "0 0 7 ? * MON-FRI *");

		_assertFireDates(
			trigger, startDate, _dateOf(2017, 0, 2, 7, 0),
			_dateOf(2017, 0, 3, 7, 0), _dateOf(2017, 0, 4, 7, 0),
			_dateOf(2017, 0, 5, 7, 0), _dateOf(2017, 0, 6, 7, 0),
			_dateOf(2017, 0, 9, 7, 0));
	}

	@Test
	public void testMonthlyDay12OfEveryTwoMonthsTrigger() {
		Date startDate = _dateOf(2017, 0, 1, 6, 0);

		Trigger trigger = _triggerOf(startDate, null, "0 0 13 12 1/2 ? *");

		_assertFireDates(
			trigger, startDate, _dateOf(2017, 0, 12, 13, 0),
			_dateOf(2017, 2, 12, 13, 0), _dateOf(2017, 4, 12, 13, 0));
	}

	@Test
	public void testMonthlyThirdTuesdayOfEveryThreeMonthsTrigger() {
		Date startDate = _dateOf(2017, 0, 1, 6, 0);

		Trigger trigger = _triggerOf(startDate, null, "0 0 14 ? 1/3 TUE#3 *");

		_assertFireDates(
			trigger, startDate, _dateOf(2017, 0, 17, 14, 0),
			_dateOf(2017, 3, 18, 14, 0), _dateOf(2017, 6, 18, 14, 0));
	}

	@Test
	public void testWeeklyEverySundayTrigger() {
		Date startDate = _dateOf(2017, 0, 1, 6, 0);

		Trigger trigger = _triggerOf(startDate, null, "0 0 15 ? * SUN/1 *");

		_assertFireDates(
			trigger, startDate, _dateOf(2017, 0, 1, 15, 0),
			_dateOf(2017, 0, 8, 15, 0), _dateOf(2017, 0, 15, 15, 0));
	}

	@Test
	public void testWeeklyMondayAndWednesdayTrigger() {
		Date startDate = _dateOf(2017, 0, 1, 6, 0);

		Trigger trigger = _triggerOf(startDate, null, "0 0 12 ? * MON,WED/1 *");

		_assertFireDates(
			trigger, startDate, _dateOf(2017, 0, 2, 12, 0),
			_dateOf(2017, 0, 4, 12, 0), _dateOf(2017, 0, 9, 12, 0),
			_dateOf(2017, 0, 11, 12, 0));
	}

	@Test
	public void testYearlyEveryTwoYearsJanuary12Trigger() {
		Date startDate = _dateOf(2017, 0, 1, 6, 0);

		Trigger trigger = _triggerOf(startDate, null, "0 0 12 12 1 ? 2017/2");

		_assertFireDates(
			trigger, startDate, _dateOf(2017, 0, 12, 12, 0),
			_dateOf(2019, 0, 12, 12, 0), _dateOf(2021, 0, 12, 12, 0));
	}

	@Test
	public void testYearlyEveryTwoYearsThirdSundayOfMarchTrigger() {
		Date startDate = _dateOf(2017, 0, 1, 6, 0);

		Trigger trigger = _triggerOf(startDate, null, "0 0 12 ? 3 1#3 2017/2");

		_assertFireDates(
			trigger, startDate, _dateOf(2017, 2, 19, 12, 0),
			_dateOf(2019, 2, 17, 12, 0), _dateOf(2021, 2, 21, 12, 0));
	}

	private void _assertFireDates(
		Trigger trigger, Date startDate, Date... expectedFireDates) {

		Date fireDate = startDate;

		for (Date exprectedFireDate : expectedFireDates) {
			fireDate = trigger.getFireDateAfter(fireDate);

			Assert.assertEquals(exprectedFireDate, fireDate);
		}
	}

	private Date _dateOf(int year, int month, int date, int hour, int minute) {
		Calendar calendar = new GregorianCalendar(
			TimeZoneUtil.getDefault(), LocaleUtil.getDefault());

		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, date);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	private Trigger _triggerOf(
		Date startDate, Date endDate, String cronExpression) {

		return _quartzTriggerFactory.createTrigger(
			StringUtil.randomString(), StringUtil.randomString(), startDate,
			endDate, cronExpression);
	}

	private final QuartzTriggerFactory _quartzTriggerFactory =
		new QuartzTriggerFactory();

}