package com.tasks.sign_in.presentation;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tasks.data.auth.AuthRetrofitClient;
import com.tasks.domain.auth.data.AuthResponse;
import com.tasks.sign_in.R;
import com.tasks.sign_in.SignInRouter;

public class SignInFragment extends Fragment {

    private SignInRouter signInRouter;
    private static SignInViewModel mViewModel;
    private EditText etLogin;
    private EditText etPassword;
    private Button btnSignIn;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SignInRouter) {
            signInRouter = (SignInRouter) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SignInContract");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        signInRouter = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        etLogin = view.findViewById(R.id.etLogin);
        etPassword = view.findViewById(R.id.etPassword);
        btnSignIn = view.findViewById(R.id.btnSignIn);

        mViewModel = new ViewModelProvider(
                this,
                new SignInViewModelFactory(signInRouter, AuthRetrofitClient.getApiService())
        ).get(SignInViewModel.class);

        btnSignIn.setOnClickListener(v -> signIn());

        return view;
    }

    private void signIn() {
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();
        String group = getString(R.string.GROUP_KEY);

        mViewModel.signIn(login, password, group, new SignInHandler(requireContext()));
    }

    private static class SignInHandler implements SignInViewModel.SignInCallback {
        private final Context mContext;

        public SignInHandler(Context context) {
            mContext = context;
        }

        @Override
        public void onSuccess(AuthResponse response) {
            if (response.getResultCode() == -1)
                Toast.makeText(mContext, R.string.user_not_found, Toast.LENGTH_LONG).show();
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(com.tasks.core.R.string.success);
                builder.setMessage(response.toStringFormatted());
                builder.setOnDismissListener(v -> { mViewModel.navigateToUniversity(); });
                builder.setPositiveButton(com.tasks.core.R.string.Ok_ru, (dialog, which) -> {
                    dialog.dismiss();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }

        @Override
        public void onError(String errorMessage) {
            Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}

