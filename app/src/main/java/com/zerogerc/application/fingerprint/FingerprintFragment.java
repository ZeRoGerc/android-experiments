package com.zerogerc.application.fingerprint;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.widget.Toast;

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

public class FingerprintFragment extends Fragment {

    static final String DEFAULT_KEY_NAME = "default_key";

    @Nullable
    private KeyGenerator keyGenerator;

    @Nullable
    private KeyStore keyStore;

    @Nullable
    private FingerprintManager fingerprintManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void fingerprintAuthentification() {
        fingerprintManager =
                (FingerprintManager) getContext().getSystemService(Context.FINGERPRINT_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        generateKey();
        if (fingerprintManager != null) {
            try {
                fingerprintManager.authenticate(
                        new FingerprintManager.CryptoObject(createCipher()),
                        new CancellationSignal(),
                        0,
                        new FingerprintManager.AuthenticationCallback() {
                            @Override
                            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                                super.onAuthenticationSucceeded(result);
                            }
                        },
                        null
                );
                Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Cipher createCipher()
            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            UnrecoverableKeyException,
            CertificateException,
            KeyStoreException,
            IOException,
            InvalidKeyException {
        Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                + KeyProperties.BLOCK_MODE_CBC + "/"
                + KeyProperties.ENCRYPTION_PADDING_PKCS7
        );

        SecretKey secretKey = getKey(DEFAULT_KEY_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return cipher;
    }

    @Nullable
    public SecretKey getKey(@NonNull String keyName)
            throws
            CertificateException,
            NoSuchAlgorithmException,
            IOException,
            UnrecoverableKeyException,
            KeyStoreException {
        if (keyStore != null) {
            keyStore.load(null);
            return (SecretKey) keyStore.getKey(keyName, null);
        }
        return null;
    }


}
