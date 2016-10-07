package com.zerogerc.application;

import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintManager.CryptoObject;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.UiThread;
import android.widget.ImageView;
import android.widget.TextView;

import static android.hardware.fingerprint.FingerprintManager.AuthenticationCallback;

/**
 * <p>
 * Utils class for ease of work with fingerprint library. Wraps most of a complicated parts of work with fingerprint.
 * All methods of fingerprint library require Android M so I will use RequiresApi(api = Build.VERSION_CODES.M) for the entire class. <br>
 * </p>
 *
 * <p>
 * N.B. Now the protection level of USE_FINGERPRINT permission is normal instead of dangerous.
 * See http://developer.android.com/reference/android/Manifest.permission.html#USE_FINGERPRINT
 * So I will use "noinspection ResourceType" in order to prevent false positive warnings from Android Studio
 * </p>
 */
@RequiresApi(api = Build.VERSION_CODES.M)
@UiThread
public class FingerprintHelper extends AuthenticationCallback {

    private static final long ERROR_TIMEOUT_MILLIS = 1600;

    private static final long SUCCESS_DELAY_MILLIS = 1300;

    @NonNull
    private final FingerprintManager fingerprintManager;

    @NonNull
    private final FingerprintHelperCallback callback;

    /**
     * ImageView to show icons of fingerprint authentication callbacks.
     */
    @NonNull
    private final ImageView iconView;

    /**
     * TextView to show messages of fingerprint authentication callbacks.
     */
    @NonNull
    private final TextView textView;

    @NonNull
    private final Handler callbackHandler;

    @Nullable
    private CancellationSignal cancellationSignal;

    private boolean selfCancelled;


    @NonNull
    private final Runnable resetStateRunnable = this::resetNormalState;

    @NonNull
    private final String fingerprintNotRecognizedString;

    public FingerprintHelper(
            @NonNull FingerprintManager fingerprintManager,
            @NonNull FingerprintHelperCallback callback,
            @NonNull ImageView iconView,
            @NonNull TextView textView
    ) {
        this.fingerprintManager = fingerprintManager;
        this.callback = callback;
        this.iconView = iconView;
        this.textView = textView;

        callbackHandler = new Handler(Looper.getMainLooper());
        fingerprintNotRecognizedString = textView.getResources().getString(R.string.fingerprint_not_recognized);
    }

    public boolean isFingerprintAuthAvailable() {
        return FingerprintHelper.isFingerprintAuthAvailable(fingerprintManager);
    }

    public void startAuthenticate(@Nullable CryptoObject cryptoObject) {
        if (!isFingerprintAuthAvailable()) {
            return;
        }

        cancellationSignal = new CancellationSignal();
        selfCancelled = false;

        //noinspection ResourceType
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    public void stopAuthenticate() {
        if (cancellationSignal != null && !cancellationSignal.isCanceled()) {
            selfCancelled = true;
            cancellationSignal.cancel();
            cancellationSignal = null;
        }
    }

    @Override
    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
        if (!selfCancelled) {
            showError(errString);

            callbackHandler.postDelayed(() -> {
                callback.onFingerprintAuthenticationError();
            }, ERROR_TIMEOUT_MILLIS);
        }
    }

    @Override
    public void onAuthenticationHelp(int helpCode, @NonNull CharSequence helpString) {
        showError(helpString);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        textView.removeCallbacks(resetStateRunnable);

        iconView.setImageResource(R.drawable.ic_fingerprint_success);
        textView.setTextAppearance(R.style.FingerprintAuthTextSuccess);
        textView.setText(R.string.fingerprint_success);

        callbackHandler.postDelayed(callback::onFingerprintAuthenticationSucceed, SUCCESS_DELAY_MILLIS);
    }

    @Override
    public void onAuthenticationFailed() {
        showError(fingerprintNotRecognizedString);
    }

    private void showError(@NonNull CharSequence errorMessage) {
        iconView.setImageResource(R.drawable.ic_fingerprint_error);
        textView.setText(errorMessage);
        textView.setTextAppearance(R.style.FingerprintAuthTextError);

        textView.removeCallbacks(resetStateRunnable);
        textView.postDelayed(resetStateRunnable, ERROR_TIMEOUT_MILLIS);
    }

    private void resetNormalState() {
        textView.setText(R.string.fingerprint_confirm);
        textView.setTextAppearance(R.style.FingerprintAuthTextNormal);
        iconView.setImageResource(R.drawable.ic_fp_40px);
    }

    public static boolean isFingerprintAuthAvailable(@NonNull FingerprintManager fingerprintManager) {
        //noinspection ResourceType
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && fingerprintManager.isHardwareDetected()
                && fingerprintManager.hasEnrolledFingerprints();
    }

    public interface FingerprintHelperCallback {

        void onFingerprintAuthenticationSucceed();

        void onFingerprintAuthenticationError();
    }
}
