package com.adds.encryption;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.iid.InstanceID;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import static com.adds.authentication.DSAuthenticationConstants.AUTHCONSTANTS.KEYSTORE_FILE_NAME;

/**
 * Helper class for Encrypting and Decrypting of
 * user credentials
 *
 * @author cs94758
 */
public class DSCryptographyHelper {

	private static final String FILE_NAME = KEYSTORE_FILE_NAME;
	private static final String KEYSTORE_PASSWORD = "samplepassword";
	private static Context mContext;

	/**
	 * Main Method to encrypt the user credentials.
	 *
	 * @param credential string
	 * @param context
	 */
	public static String encryptUserCredential(String credential, Context context) throws NoSuchAlgorithmException, CertificateException,
			KeyStoreException, IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {

		//generate key for encryption
		SecretKey secretKey = generateKey();
		//save the key in keystore
		storeKey(context, secretKey);
		//encrypting password into byte array
		byte[] encryptedDataBytes = encrypt(secretKey, credential.getBytes("UTF-8"));

		return Base64.encodeToString(encryptedDataBytes, Base64.DEFAULT);
	}

	/**
	 * Main Method to decrypt the user credentials.
	 *
	 * @param credential encrypted string
	 * @param context
	 */
	public static String decryptUserCredential(String credential, Context context) throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException,
			KeyStoreException, IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {

		byte[] encryptedBytes = Base64.decode(credential, Base64.DEFAULT);
		//retreive saved secretKey
		SecretKey secretKey = retrieveKey(context);
		//decrypt saved password
		String decryptedCredential = decrypt(secretKey, encryptedBytes);

		return decryptedCredential;
	}

	/**
	 * Method to delete keyStore, whenever necessary.
	 *
	 * @param context
	 */
	public static boolean deleteKeyStore(Context context) {
		File keyStoreFile = getKeyStoreFile(context);
		mContext = context;

		if (keyStoreFile.exists()) {
			//Delete InstanceId which is used as keystore password.
			DeleteInstanceId deleteInstanceId = new DeleteInstanceId();
			deleteInstanceId.execute();
			//Reset all values.
//			CCSharedPreferencUtils.setBooleanPref(CCCoreConst.SETTINGS.FINGER_TOUCH_ENABLED, false);
//			CCSharedPreferencUtils.setPref(CCAuthenticationModuleGlobal.AUTHENTICATIONMODULE.FINGERPRINT_CREDENTIAL_PASSWORD, "");
//			CCSharedPreferencUtils.setPref(CCAuthenticationModuleGlobal.AUTHENTICATIONMODULE.FINGERPRINT_CREDENTIAL_USERNAME, "");
//			CCSharedPreferencUtils.setPref(CCAuthenticationModuleGlobal.AUTHENTICATIONMODULE.FINGERPRINT_SECRETKEY_ALIASNAME, "");
//			CCSharedPreferencUtils.setBooleanPref(CCAuthenticationModuleGlobal.AUTHENTICATIONMODULE.CREDENTIAL_REMEMBER, false);
			return keyStoreFile.delete();
		}
		return true;
	}

	/**
	 * Get or Load keyStore.
	 *
	 * @param context
	 */
	private static KeyStore getKeyStore(Context context) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {

		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

		char[] dummyPassword = KEYSTORE_PASSWORD.toCharArray();

		//KeyStore password (Instance id, Unique for an application and once deleted it
		// generates a new id for application).
		String instanceId = InstanceID.getInstance(context).getId();
		char[] keyStorePassword = instanceId.toCharArray();

		File keyStoreFile = getKeyStoreFile(context);
		if (!keyStoreFile.exists()) {
			keyStoreFile.createNewFile();
			keyStore.load(null, keyStorePassword);
		} else {
			FileInputStream fileInputStream = new FileInputStream(keyStoreFile);
			keyStore.load(fileInputStream, keyStorePassword);
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
		return keyStore;
	}

	/**
	 * Get keyStore path (in application package).
	 *
	 * @param context
	 */
	private static File getKeyStoreFile(Context context) {
		return new File(context.getApplicationInfo().dataDir, FILE_NAME);
	}

	/**
	 * Generate secretKey using AES and SecureRandom.
	 */
	private static SecretKey generateKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES);
		SecureRandom secureRandom = new SecureRandom();
		keyGenerator.init(256, secureRandom);
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey;
	}

	/**
	 * Store the generated secretKey.
	 *
	 * @param context
	 * @param secretKey
	 */
	private static void storeKey(Context context, SecretKey secretKey) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {

		KeyStore keyStore = getKeyStore(context);

		char[] dummyPassword = KEYSTORE_PASSWORD.toCharArray();

		//Password for secretKey (UUID, unique for a device).
		char[] secretKeyPassword = Settings.Secure.getString(context.getContentResolver(),
				Settings.Secure.ANDROID_ID).toCharArray();

		//Alias name to store the secretKey.
		String aliasName = getAliasName(keyStore);
		keyStore.setKeyEntry(aliasName, secretKey, secretKeyPassword, null);

		//Fill keyStore file with some random keys to confuse hackers
		setRandomKeys(keyStore);

		//Save Keystore into a file.
		File keyStoreFile = getKeyStoreFile(context);
		FileOutputStream outputStream = new FileOutputStream(keyStoreFile);
		String instanceId = InstanceID.getInstance(context).getId();
		char[] keyStorePassword = instanceId.toCharArray();
		keyStore.store(outputStream, keyStorePassword);
		if (outputStream != null) {
			outputStream.close();
		}
	}

	/**
	 * Add some random keys to keyStore.
	 *
	 * @param keyStore
	 */
	private static void setRandomKeys(KeyStore keyStore) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
		keyStore.setKeyEntry(UUID.randomUUID().toString(), generateKey(), null, null);
		keyStore.setKeyEntry(UUID.randomUUID().toString(), generateKey(), null, null);
		keyStore.setKeyEntry(UUID.randomUUID().toString(), generateKey(), null, null);
	}

	/**
	 * Get alias name for secretKey.
	 *
	 * @param keyStore
	 */
	private static String getAliasName(KeyStore keyStore) throws IOException, NoSuchAlgorithmException,
			IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, CertificateException, KeyStoreException {

		//Random UUID as alias name.
		String aliasName = UUID.randomUUID().toString();
		//Secretkey for saving alias name.
		SecretKey aliasSecretKey = generateKey();
		//Encrypt alias name.
		byte[] encryptedAliasName = encrypt(aliasSecretKey, aliasName.getBytes("UTF-8"));
		//aliasName alias
//		String aliasKeyAliasName = Base64.encodeToString(ApplicationContextHolder.getAppContext().getClass().getName().getBytes("UTF-8"), Base64.DEFAULT);
		//aliasName password
//		String aliasKeyPassword = Base64.encodeToString(CBRootDetectionHelper.class.getName().getBytes("UTF-8"), Base64.DEFAULT);
//		keyStore.setKeyEntry(aliasKeyAliasName, aliasSecretKey, aliasKeyPassword.toCharArray(), null);
		//Store encrypted alias name
//		CCSharedPreferencUtils.setPref(CCAuthenticationModuleGlobal.AUTHENTICATIONMODULE.FINGERPRINT_SECRETKEY_ALIASNAME,
//				Base64.encodeToString(encryptedAliasName, Base64.DEFAULT));

		return aliasName;
	}

	/**
	 * Retreive secretKey from Keystore.
	 *
	 * @param context
	 */
	private static SecretKey retrieveKey(Context context) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException,
			UnrecoverableKeyException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
		KeyStore keyStore = getKeyStore(context);
		//get encrypted alias from preference.
//		byte[] encryptedAlias = Base64.decode(CCSharedPreferencUtils.getPref
//				(CCAuthenticationModuleGlobal.AUTHENTICATIONMODULE.FINGERPRINT_SECRETKEY_ALIASNAME), Base64.DEFAULT);

		char[] dummyPassword = KEYSTORE_PASSWORD.toCharArray();
		char[] secretKeyPassword = Settings.Secure.getString(context.getContentResolver(),
				Settings.Secure.ANDROID_ID).toCharArray();
		//Decrypt alias name.
		String aliasName = decrypt(getAliasNameSecretKey(keyStore), null);
		SecretKey secretKey = (SecretKey) keyStore.getKey(aliasName, secretKeyPassword);

		return secretKey;
	}

	/**
	 * get secretKey to decrypt alias name.
	 *
	 * @param keyStore
	 */
	private static SecretKey getAliasNameSecretKey(KeyStore keyStore) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, UnrecoverableKeyException {
		//alias name secretKey aliasName.
//		String aliasKeyAliasName = Base64.encodeToString(ApplicationContextHolder.getAppContext().getClass().getName().getBytes("UTF-8"), Base64.DEFAULT);
		//alias name secretKey password.
		String aliasKeyPassword = Base64.encodeToString(DSRootDetectionHelper.class.getName().getBytes("UTF-8"), Base64.DEFAULT);

//		SecretKey key = (SecretKey) keyStore.getKey(aliasKeyAliasName, aliasKeyPassword.toCharArray());
//		return key;
		return null;
	}

	/**
	 * Method to encrypt input data using AES.
	 *
	 * @param secretKey
	 * @param inputDataBytes
	 */
	private static byte[] encrypt(SecretKey secretKey, byte[] inputDataBytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
			BadPaddingException, IllegalBlockSizeException {

		Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedData = cipher.doFinal(inputDataBytes);
		return encryptedData;
	}

	/**
	 * Method to decrypt input data using AES.
	 *
	 * @param secretKey
	 * @param encryptedBytes
	 */
	private static String decrypt(SecretKey secretKey, byte[] encryptedBytes) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {

		Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		String decryptedData = new String(cipher.doFinal(encryptedBytes), "UTF-8");
		return decryptedData;
	}

	/**
	 * Convert byte array to hexadecimal string
	 *
	 * @param bytes
	 */
	private static String byteArrayToHexString(byte[] bytes) {
		StringBuffer buffer = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			int value = bytes[i] & 0xff;
			if (value < 16) {
				buffer.append('0');
			}
			buffer.append(Integer.toHexString(value));
		}
		return buffer.toString().toUpperCase();
	}

	/**
	 * Convert hex string to byte array
	 *
	 * @param string hexadecimal
	 */
	private static byte[] hexStringToByteArray(String string) {
		byte[] bytes = new byte[string.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			int index = i * 2;
			int value = Integer.parseInt(string.substring(index, index + 2), 16);
			bytes[i] = (byte) value;
		}
		return bytes;
	}

	/**
	 * Delete instance ID when deleting keyStore.
	 */
	private static class DeleteInstanceId extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				InstanceID.getInstance(mContext).deleteInstanceID();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
