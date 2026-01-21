package com.javanauta.usuario.business;

import com.javanauta.usuario.infrastructure.clients.ViaCepClient;
import com.javanauta.usuario.infrastructure.clients.ViaCepDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor

public class ViaCepService {

    private final ViaCepClient client;

    public ViaCepDTO buscarDadosEndereco(String cep) {
        return client.buscaDadosEndereco(processarCep(cep));

    }

    private String processarCep(String cep) {
        String cepFormatdo = cep.replace(" ", "").replace("-", "");

        if (!cepFormatdo.matches("\\d+") || !Objects.equals(cepFormatdo.length(), 8)) {
            throw new IllegalArgumentException("O cep contem caracteres invalidos, favor verificar");
        }
        return cepFormatdo;
    }
}
