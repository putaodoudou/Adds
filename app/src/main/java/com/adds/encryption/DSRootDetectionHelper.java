package com.adds.encryption;

import java.io.File;

/**
 * Helper class for detecting Rooted devices.
 *
 * @author cs94758
 */
public class DSRootDetectionHelper {
	/**
	 * Checks if the device is rooted.
	 *
	 * @return <code>true</code> if the device is rooted, <code>false</code> otherwise.
	 */
	public static boolean isRooted() {
		if (isCommandExecuted()) {
			return true;
		}
		if (getBuildInfo()) {
			return true;
		}
		if (getSuperUserInfo()) {
			return true;
		}
		return false;
	}


	/**
	 * Method 1
	 * get info from build info
	 */
	private static boolean getBuildInfo() {
		//
		String buildTags = android.os.Build.TAGS;
		if (buildTags != null && buildTags.contains("test-keys")) {
			return true;
		}
		return false;
	}

	/**
	 * Method 2
	 * Check whether superUser is present
	 */
	private static boolean getSuperUserInfo() {
		try {
			File file = new File("/system/app/Superuser.apk");
			if (file.exists()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Method 3
	 * executes "su" command on the system
	 */
	private static boolean isCommandExecuted() {
		try {
			ProcessBuilder processBuilder = new ProcessBuilder("su");
			Process process = processBuilder.start();
			process.destroy();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
