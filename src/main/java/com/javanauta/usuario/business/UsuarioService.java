package com.javanauta.usuario.business;

import com.javanauta.usuario.business.converter.UsuarioConverter;
import com.javanauta.usuario.business.dto.EnderecoDTO;
import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.infrastructure.entity.Endereco;
import com.javanauta.usuario.infrastructure.entity.Telefone;
import com.javanauta.usuario.infrastructure.exceptions.ConflictException;
import com.javanauta.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import com.javanauta.usuario.infrastructure.repository.EnderecoRepository;
import com.javanauta.usuario.infrastructure.repository.TelefoneRepository;
import com.javanauta.usuario.infrastructure.repository.UsuarioRepository;
import com.javanauta.usuario.infrastructure.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class UsuarioService {

    public final UsuarioRepository usuarioRepository;
    public final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;


    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email ja cadastrado " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email ja cadastrado " + e.getCause());
        }
    }


    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    public UsuarioDTO buscarUsuarioPorEmail (String email){
        try {
            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email)
                            .orElseThrow(
                            () -> new ResourceNotFoundException("Email não encontrado " + email)
                            )
            );
        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado " + email);
        }
    }
    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);

    }
    public UsuarioDTO atualizaDadosUsuario (String token , UsuarioDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null );

        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não Localizado"));

        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);
        usuario.setSenha(passwordEncoder.encode(usuario.getPassword()));

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

    }

    public EnderecoDTO atualizaEndereco (Long idEndereco , EnderecoDTO enderecoDTO){

        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                new ResourceNotFoundException("Id não Encontrado " + idEndereco));
        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO,entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone (Long idTelefone , TelefoneDTO dto){

        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
                new ResourceNotFoundException("Id não Encontrado " + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(dto , entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    public EnderecoDTO cadastraEndereco (String token , EnderecoDTO dto ){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado " + email));

        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto , usuario.getId());
        Endereco enderecoEntity = enderecoRepository.save(endereco);
        //
        return usuarioConverter.paraEnderecoDTO(enderecoEntity);
    }
    public TelefoneDTO cadastraTelefone (String token , TelefoneDTO dto){
    String email = jwtUtil.extrairEmailToken(token.substring(7));
    Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(()->
            new ResourceNotFoundException("Email não localizado " + email));

    Telefone telefone = usuarioConverter.paraTelefoneEntity(dto , usuario.getId());
    Telefone telefoneEntity = telefoneRepository.save(telefone);
    return usuarioConverter.paraTelefoneDTO(telefoneEntity);}
}
