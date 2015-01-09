package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipHelper {

	private static final int BUFFER_SIZE = 4096;

	public static void zipRecursively(File srcFile, OutputStream outputStream) throws IOException {
		try (ZipOutputStream zipStream = new ZipOutputStream(outputStream)) {
			addFileToZipRecursively(srcFile, zipStream, "");
		}
	}

	private static void addFileToZipRecursively(File srcFile, ZipOutputStream zipStream, String zipPath)
			throws IOException {
		if (srcFile.isDirectory()) {
			File[] subFiles = srcFile.listFiles();

			if (subFiles.length == 0) {
				zipStream.putNextEntry(new ZipEntry(zipPath + "/" + srcFile.getName() + "/"));
			} else {
				String subFileZipPath = zipPath.equals("") ? srcFile.getName() : zipPath + "/" + srcFile.getName();
				for (File subFile : subFiles) {
					addFileToZipRecursively(subFile, zipStream, subFileZipPath);
				}
			}
		} else {
			saveFileDataToZip(srcFile, zipStream, zipPath);
		}
	}

	private static void saveFileDataToZip(File srcFile, ZipOutputStream zip, String zipPath)
			throws FileNotFoundException, IOException {
		try (FileInputStream in = new FileInputStream(srcFile)) {
			zip.putNextEntry(new ZipEntry(zipPath + "/" + srcFile.getName()));

			byte[] buf = new byte[BUFFER_SIZE];
			int len;
			while ((len = in.read(buf)) > 0) {
				zip.write(buf, 0, len);
			}
		}
	}

	public static void unzip(InputStream inputStream, File destDir) throws IOException {
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		try (ZipInputStream zipStream = new ZipInputStream(inputStream)) {
			ZipEntry entry;
			while ((entry = zipStream.getNextEntry()) != null) {
				String filePath = destDir.getAbsolutePath() + File.separator + entry.getName();
				if (entry.isDirectory()) {
					File dir = new File(filePath);
					dir.mkdir();
				} else {
					extractFile(zipStream, filePath);
				}
				zipStream.closeEntry();
				entry = zipStream.getNextEntry();
			}
		}
	}

	private static void extractFile(ZipInputStream zipStream, String filePath) throws IOException {
		try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
				BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream)) {
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = zipStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
		}
	}
}