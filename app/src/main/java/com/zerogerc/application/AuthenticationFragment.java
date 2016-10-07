package com.zerogerc.application;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.zerogerc.application.FingerprintHelper.isFingerprintAuthAvailable;
import static com.zerogerc.application.KeyCipherGenerator.PASSWORD;

public class AuthenticationFragment extends Fragment {

    @BindView(R.id.auth_fragment_password_view)
    EditText passwordView;

    @Nullable
    private Unbinder unbinder;

    @Override
    @NonNull
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.auth_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final FingerprintManager fingerprintManager = getActivity().getSystemService(FingerprintManager.class);

            if (isFingerprintAuthAvailable(fingerprintManager)) {
                ((AuthenticationFragmentCallbacks) getActivity()).onFingerprintRequested();
            }
        }
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }

    public void fingerprintAuthSuccessful() {
        ((AuthenticationFragmentCallbacks) getActivity()).onAuthorizationFinished();
    }

    public void fingerprintAuthFailed() {
        // nothing to do here, just wait while user enter password
    }

    @OnClick(R.id.auth_fragment_button)
    public void onAuthClick() {
        final String entered = passwordView.getText().toString();
        if (PASSWORD.equals(entered)) {
            ((AuthenticationFragmentCallbacks) getActivity()).onAuthorizationFinished();
        } else {
            showToast(R.string.invalid_password_string);
        }
    }

    private void showToast(@StringRes int messageRes) {
        Toast.makeText(getContext(), messageRes, Toast.LENGTH_SHORT).show();
    }

    public interface AuthenticationFragmentCallbacks {

        void onFingerprintRequested();

        void onAuthorizationFinished();
    }
}
