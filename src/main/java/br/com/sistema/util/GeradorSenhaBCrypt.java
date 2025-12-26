package br.com.sistema.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorSenhaBCrypt {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String senhaPlana = "admin123";
        String senhaBCrypt = encoder.encode(senhaPlana);

        System.out.println("Senha plana: " + senhaPlana);
        System.out.println("Senha BCrypt: " + senhaBCrypt);

        // Testar se a senha está correta
        boolean senhaCorreta = encoder.matches(senhaPlana, senhaBCrypt);
        System.out.println("Senha válida: " + senhaCorreta);
    }
}
