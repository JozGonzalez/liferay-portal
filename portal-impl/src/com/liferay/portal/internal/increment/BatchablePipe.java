/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.internal.increment;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 */
public class BatchablePipe<K, V> {

	public boolean put(IncreasableEntry<K, V> increasableEntry) {
		K key = increasableEntry.getKey();

		while (true) {
			IncreasableEntryWrapper<K, V> previousIncreasableEntryWrapper =
				concurrentMap.putIfAbsent(
					key, new IncreasableEntryWrapper<K, V>(increasableEntry));

			if (previousIncreasableEntryWrapper == null) {
				queue.offer(increasableEntry);

				return true;
			}

			IncreasableEntry<K, V> previousIncreasableEntry =
				previousIncreasableEntryWrapper.increasableEntry;

			IncreasableEntry<K, V> newIncreasableEntry =
				increasableEntry.increase(previousIncreasableEntry.getValue());

			if (concurrentMap.replace(
					key, previousIncreasableEntryWrapper,
					new IncreasableEntryWrapper<K, V>(newIncreasableEntry))) {

				queue.offer(newIncreasableEntry);

				return false;
			}
		}
	}

	public IncreasableEntry<K, V> take() {
		while (true) {
			IncreasableEntry<K, V> increasableEntry = queue.poll();

			if (increasableEntry == null) {
				return null;
			}

			if (concurrentMap.remove(
					increasableEntry.getKey(),
					new IncreasableEntryWrapper<K, V>(increasableEntry))) {

				return increasableEntry;
			}
		}
	}

	protected final ConcurrentMap<K, IncreasableEntryWrapper<K, V>>
		concurrentMap = new ConcurrentHashMap<>();
	protected final Queue<IncreasableEntry<K, V>> queue =
		new ConcurrentLinkedQueue<>();

	protected static class IncreasableEntryWrapper<K, V> {

		public IncreasableEntryWrapper(
			IncreasableEntry<K, V> increasableEntry) {

			this.increasableEntry = increasableEntry;
		}

		@Override
		public boolean equals(Object object) {
			IncreasableEntryWrapper<K, V> increasableEntryWrapper =
				(IncreasableEntryWrapper<K, V>)object;

			if (increasableEntry == increasableEntryWrapper.increasableEntry) {
				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			return increasableEntry.hashCode();
		}

		@Override
		public String toString() {
			return increasableEntry.toString();
		}

		protected final IncreasableEntry<K, V> increasableEntry;

	}

}