package com.example.app2.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app2.R;
import com.example.app2.data.UsuarioRepository;
import com.example.app2.model.UsuarioModel;

/**
 * Fragmento de ajustes de usuario.
 *
 * Funcionalidades principales:
 * - Permite al usuario ver y actualizar sus datos personales (nombre, email y contraseña).
 * - Muestra los datos actuales del usuario en los campos de texto.
 * - Valida los campos antes de actualizar (campos vacíos, formato de email, coincidencia de contraseñas, longitud mínima).
 * - Actualiza los datos en la base de datos local usando UsuarioRepository.
 * - Muestra mensajes de éxito o error según el resultado de la actualización.
 * - Permite volver atrás con el botón correspondiente.
 *
 * Uso típico:
 * - Se utiliza en la sección de ajustes de la app, recibiendo un objeto UsuarioModel por argumentos.
 * - El usuario puede modificar sus datos y guardarlos.
 */
public class AjustesFragment extends Fragment {

    private UsuarioRepository accesoDatos;
    private EditText nombre, email, password, passwordValidation;
    private UsuarioModel usuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ajustes, container, false);

        initViews(view);
        loadUserData();
        setupButtons(view);

        return view;
    }

    /**
     * Inicializa las vistas y el repositorio de usuarios.
     */
    private void initViews(View view) {
        nombre = view.findViewById(R.id.editTextNombre);
        email = view.findViewById(R.id.editTextEmail);
        password = view.findViewById(R.id.editTextPassword);
        passwordValidation = view.findViewById(R.id.editTextPasswordValidation);
        accesoDatos = new UsuarioRepository(requireContext());
    }

    /**
     * Carga los datos del usuario recibidos por argumentos.
     */
    private void loadUserData() {
        if (getArguments() != null) {
            usuario = getArguments().getParcelable("usuario");
            if (usuario != null) {
                populateFields();
            }
        }
    }

    /**
     * Rellena los campos de texto con los datos del usuario.
     */
    private void populateFields() {
        nombre.setText(usuario.getNombre());
        email.setText(usuario.getMail());
        password.setText(usuario.getContrasena());
        passwordValidation.setText(usuario.getContrasena());
    }

    /**
     * Configura los botones de actualizar y volver atrás.
     */
    private void setupButtons(View view) {
        Button btnUpdate = view.findViewById(R.id.buttonLoginRegistro);
        Button btnBack = view.findViewById(R.id.buttonSignUpRegistro);

        btnUpdate.setOnClickListener(v -> validateAndUpdateUser());
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
    }

    /**
     * Valida los campos y actualiza los datos del usuario si son correctos.
     */
    private void validateAndUpdateUser() {
        String nombreStr = nombre.getText().toString().trim();
        String emailStr = email.getText().toString().trim();
        String passStr = password.getText().toString().trim();
        String passValidationStr = passwordValidation.getText().toString().trim();

        if (!validateInputs(nombreStr, emailStr, passStr, passValidationStr)) {
            return;
        }

        updateUserData(nombreStr, emailStr, passStr);
    }

    /**
     * Valida los datos ingresados por el usuario.
     */
    private boolean validateInputs(String nombre, String email, String pass, String passValidation) {
        if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            showToast("Todos los campos son obligatorios");
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Email no válido");
            return false;
        }

        if (pass.length() < 6) {
            showToast("La contraseña debe tener al menos 6 caracteres");
            return false;
        }

        if (!pass.equals(passValidation)) {
            showToast("Las contraseñas no coinciden");
            return false;
        }

        return true;
    }

    /**
     * Actualiza los datos del usuario en la base de datos.
     */
    private void updateUserData(String nombre, String email, String pass) {
        usuario.setNombre(nombre);
        usuario.setMail(email);
        usuario.setContrasena(pass);

        boolean success = accesoDatos.actualizarDatosUsuario(usuario);
        if (success) {
            showToast("Datos actualizados correctamente");
        } else {
            showToast("Error al actualizar los datos");
        }
    }

    /**
     * Muestra un mensaje Toast en pantalla.
     */
    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Crea una nueva instancia del fragmento con el usuario como argumento.
     */
    public static AjustesFragment newInstance(UsuarioModel usuario) {
        AjustesFragment fragment = new AjustesFragment();
        Bundle args = new Bundle();
        args.putParcelable("usuario", usuario);
        fragment.setArguments(args);
        return fragment;
    }
}