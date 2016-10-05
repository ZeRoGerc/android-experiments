package com.zerogerc.application.fingerprint;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class FingerprintFragment extends Fragment {

    static final String DEFAULT_KEY_NAME = "default_key";

    @Nullable
    private KeyGenerator keyGenerator;

    @Nullable
    private KeyStore keyStore;

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateKey() {
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            int purpose = KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT;
            String padding = KeyProperties.ENCRYPTION_PADDING_PKCS7;

            keyGenerator.init(new KeyGenParameterSpec.Builder(DEFAULT_KEY_NAME, purpose)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(padding)
                    .build()
            );

            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public SecretKey getKey(@NonNull String keyName) throws CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException, KeyStoreException {
        if (keyStore != null) {
            keyStore.load(null);
            return (SecretKey) keyStore.getKey(keyName, null);
        }
        return null;
    }



}
