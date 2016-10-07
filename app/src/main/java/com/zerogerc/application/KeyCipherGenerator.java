package com.zerogerc.application;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

@RequiresApi(api = Build.VERSION_CODES.M)
public class KeyCipherGenerator {

    @NonNull
    public static final String PASSWORD = "1111";

    @NonNull
    public static final String KEY_STORE = "AndroidKeyStore";

    @Nullable
    private KeyStore keyStore;

    @Nullable
    private KeyGenerator keyGenerator;

    @Nullable
    private Cipher cipher;

    /**
     * @return true if initialization successful
     */
    public boolean initKeyStore() {
        try {
            keyStore = KeyStore.getInstance(KEY_STORE);
        } catch (KeyStoreException e) {
            return false;
        }
        return true;
    }

    /**
     * @return true if initialization successful
     */
    public boolean initKeyGenerator() {
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEY_STORE);
            return true;
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            return false;
        }
    }

    public boolean initCipher(@NonNull Cipher cipher, @NonNull String keyName) {
        if (keyStore == null) {
            throw new IllegalStateException("KeyStore must be initializaed");
        }

        try {
            keyStore.load(null);
            SecretKey secretKey = (SecretKey) keyStore.getKey(keyName, null);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return true;
        } catch (IOException
                | NoSuchAlgorithmException
                | CertificateException
                | UnrecoverableKeyException
                | KeyStoreException
                | InvalidKeyException e
                )
        {
            return false;
        }
    }

    public boolean createKey(@NonNull String keyName, boolean invalidatedByBiometricEnrollment) {
        if (keyStore == null || keyGenerator == null) {
            throw new IllegalStateException("KeyStore and KeyGenerator must be initialized");
        }
        // The enrolling flow for fingerprint. This is where you ask the user to set up fingerprint
        // for your flow. Use of keys is necessary if you need to know if the set of
        // enrolled fingerprints has changed.
        try {
            keyStore.load(null);
            // Set the alias of the entry in Android KeyStore where the key will appear
            // and the constrains (purposes) in the constructor of the Builder

            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(
                    keyName,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT
            )
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    // Require the user to authenticate with a fingerprint to authorize every use
                    // of the key
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);

            // This is a workaround to avoid crashes on devices whose API level is < 24
            // because KeyGenParameterSpec.Builder#setInvalidatedByBiometricEnrollment is only
            // visible on API level +24.
            // Ideally there should be a compat library for KeyGenParameterSpec.Builder but
            // which isn't available yet.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setInvalidatedByBiometricEnrollment(invalidatedByBiometricEnrollment);
            }
            keyGenerator.init(builder.build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | CertificateException | IOException e) {
            return false;
        }
        return true;
    }

    @NonNull
    public Cipher getDefaultCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                + KeyProperties.BLOCK_MODE_CBC + "/"
                + KeyProperties.ENCRYPTION_PADDING_PKCS7
        );
    }


}
