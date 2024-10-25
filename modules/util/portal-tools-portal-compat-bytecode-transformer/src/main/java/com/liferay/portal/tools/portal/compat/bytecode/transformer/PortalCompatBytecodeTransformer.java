/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.portal.compat.bytecode.transformer;

import java.io.IOException;
import java.io.OutputStream;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * @author Tong Wang
 */
public class PortalCompatBytecodeTransformer {

	public static void main(String[] args) throws IOException {
		Path classesDir = Paths.get(System.getProperty("classes.dir"));

		if (!Files.exists(classesDir)) {
			return;
		}

		Files.walkFileTree(
			classesDir,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String fileName = String.valueOf(filePath.getFileName());

					if (fileName.endsWith(".class")) {
						_transform(filePath);
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private static void _transform(Path path) throws IOException {
		ClassReader classReader = new ClassReader(Files.readAllBytes(path));

		ClassWriter classWriter = new ClassWriter(
			classReader, ClassWriter.COMPUTE_FRAMES);

		classReader.accept(new PortalCompatClassVisitor(classWriter), 0);

		try (OutputStream outputStream = Files.newOutputStream(path)) {
			outputStream.write(classWriter.toByteArray());
		}
	}

}