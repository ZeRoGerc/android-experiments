package com.zerogerc.application.fingerprint;

import android.app.AlertDialog;
import android.app.Dialog;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintManager.CryptoObject;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zerogerc.application.FingerprintHelper;
import com.zerogerc.application.FingerprintHelper.FingerprintHelperCallback;
import com.zerogerc.application.R;

import butterknife.BindView;
import butterknife.ButterKnife;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintDialog extends DialogFragment {

    @BindView(R.id.fingerprint_dialog_icon)
    ImageView iconView;

    @BindView(R.id.fingerprint_dialog_text)
    TextView textView;

    @Nullable
    private FingerprintHelper fingerprintHelper;

    @Nullable
    private CryptoObject cryptoObject;

    @Override
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.fingerprint_dialog, null, false);
        ButterKnife.bind(this, view);

        fingerprintHelper = new FingerprintHelper(
                getActivity().getSystemService(FingerprintManager.class),
                (FingerprintHelperCallback) getActivity(),
                iconView,
                textView
        );

        if (!fingerprintHelper.isFingerprintAuthAvailable()) {
            ((FingerprintHelperCallback) getActivity()).onFingerprintAuthenticationError();
        }

        fingerprintHelper.startAuthenticate(cryptoObject);

        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.sign_in)
                .setView(view)
                .setNegativeButton(R.string.cancel, null)
                .setOnDismissListener(dialog -> {
                    if (fingerprintHelper != null) {
                        fingerprintHelper.stopAuthenticate();
                    }
                })
                .create();
    }

    public void setCryptoObject(@NonNull CryptoObject cryptoObject) {
        this.cryptoObject = cryptoObject;
    }
}
