package com.zerogerc.application;

import android.hardware.fingerprint.FingerprintManager.CryptoObject;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zerogerc.application.AuthenticationFragment.AuthenticationFragmentCallbacks;
import com.zerogerc.application.FingerprintHelper.FingerprintHelperCallback;
import com.zerogerc.application.fingerprint.FingerprintDialog;
import com.zerogerc.application.model.MainFragment;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity
        implements
        AuthenticationFragmentCallbacks,
        FingerprintHelperCallback
{
    @NonNull
    private static final String DEFAULT_KEY_NAME = "default_key";

    @Nullable
    private FingerprintDialog fingerprintDialog;

    @Nullable
    private KeyCipherGenerator keyCipherGenerator;

    @Nullable
    private Cipher cipher;

    @Nullable
    private CryptoObject cryptoObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new AuthenticationFragment(), AuthenticationFragment.class.getName())
                    .commit();
        }
    }

    @Override
    public void onFingerprintRequested() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            keyCipherGenerator = new KeyCipherGenerator();
            try {
                cipher = keyCipherGenerator.getDefaultCipher();
            } catch (NoSuchPaddingException | NoSuchAlgorithmException e){
                onFingerprintAuthenticationError();
            }

            boolean initSuccessful = true;
            initSuccessful &= keyCipherGenerator.initKeyStore();
            initSuccessful &= keyCipherGenerator.initKeyGenerator();
            initSuccessful &= keyCipherGenerator.createKey(DEFAULT_KEY_NAME, true);
            initSuccessful &= keyCipherGenerator.initCipher(cipher, DEFAULT_KEY_NAME);
            if (initSuccessful) {
                cryptoObject = new CryptoObject(cipher);
                fingerprintDialog = new FingerprintDialog();

                fingerprintDialog.show(getSupportFragmentManager(), FingerprintDialog.class.getName());
                fingerprintDialog.setCryptoObject(cryptoObject);
                return;
            }
        }
        onFingerprintAuthenticationError();
    }

    @Override
    public void onAuthorizationFinished() {
        // TODO: encrypt if fingerprint
        Toast.makeText(this, R.string.auth_successful, LENGTH_SHORT).show();
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MainFragment(), MainFragment.class.getName())
                .commit();
    }

    @Override
    public void onFingerprintAuthenticationSucceed() {
        if (fingerprintDialog != null) {
            fingerprintDialog.dismiss();
        }

        AuthenticationFragment fragment =
                ((AuthenticationFragment) getSupportFragmentManager().findFragmentByTag(AuthenticationFragment.class.getName()));
        fragment.fingerprintAuthSuccessful();
    }

    @Override
    public void onFingerprintAuthenticationError() {
        if (fingerprintDialog != null) {
            fingerprintDialog.dismiss();
        }

        AuthenticationFragment fragment =
                ((AuthenticationFragment) getSupportFragmentManager().findFragmentByTag(AuthenticationFragment.class.getName()));
        fragment.fingerprintAuthFailed();
    }
}
