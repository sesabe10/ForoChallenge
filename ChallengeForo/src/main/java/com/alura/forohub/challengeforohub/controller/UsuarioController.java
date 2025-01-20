package com.alura.forohub.challengeforohub.controller;

import com.alura.forohub.challengeforohub.domain.usuario.Usuario;
import com.alura.forohub.challengeforohub.domain.usuario.dto.ActualizarUsuarioDTO;
import com.alura.forohub.challengeforohub.domain.usuario.dto.CrearUsuarioDTO;
import com.alura.forohub.challengeforohub.domain.usuario.dto.DetalleUsuarioDTO;
import com.alura.forohub.challengeforohub.domain.usuario.repository.UsuarioRepository;
import com.alura.forohub.challengeforohub.domain.usuario.validations.create.ValidarCrearUsuario;
import com.alura.forohub.challengeforohub.domain.usuario.validations.update.ValidarActualizarUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Usuario", description = "Crear topicos y publica respuestas")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    List<ValidarCrearUsuario> crearValidador;

    @Autowired
    List<ValidarActualizarUsuario> actualizarValidador;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra un nuevo usuario en la BD")
    public ResponseEntity<DetalleUsuarioDTO> crearUsuario(@RequestBody @Valid CrearUsuarioDTO crearUsuarioDTO,
                                                          UriComponentsBuilder uriBuilder){
        crearValidador.forEach(v -> v.validate(crearUsuarioDTO));

        String hashedPassword = passwordEncoder.encode(crearUsuarioDTO.password());
        Usuario usuario = new Usuario(crearUsuarioDTO, hashedPassword);

        repository.save(usuario);
        var uri = uriBuilder.path("/usuarios/{username}").buildAndExpand(usuario.getUsername()).toUri();
        return ResponseEntity.created(uri).body(new DetalleUsuarioDTO(usuario));
    }

    @GetMapping("/all")
    @Operation(summary = "Enumera todos los usuarios independientemente de su estado")
    public ResponseEntity<Page<DetalleUsuarioDTO>> leerTodosUsuarios(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable){
        var pagina = repository.findAll(pageable).map(DetalleUsuarioDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping
    @Operation(summary = "Lista solo usuarios habilitados")
    public ResponseEntity<Page<DetalleUsuarioDTO>> leerUsuariosActivos(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable){
        var pagina = repository.findAllByEnabledTrue(pageable).map(DetalleUsuarioDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Lee un único usuario por su nombre de usuario")
    public ResponseEntity<DetalleUsuarioDTO> leerUnUsuario(@PathVariable String username){
        Usuario usuario = (Usuario) repository.findByUsername(username);
        var datosUsuario = new DetalleUsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getEnabled()
        );
        return ResponseEntity.ok(datosUsuario);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Lee un único usuario por su ID")
    public ResponseEntity<DetalleUsuarioDTO>leerUnUsuario(@PathVariable Long id){
        Usuario usuario = repository.getReferenceById(id);
        var datosUsuario = new DetalleUsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getEnabled()
        );
        return ResponseEntity.ok(datosUsuario);
    }

    @PutMapping("/{username}")
    @Transactional
    @Operation(summary = "Actualiza la contraseña de un usuario, rol, nombre y apellido, correo electrónico o estado habilitado")
    public ResponseEntity<DetalleUsuarioDTO> actualizarUsuario(@RequestBody @Valid ActualizarUsuarioDTO actualizarUsuarioDTO, @PathVariable String username){
        actualizarValidador.forEach(v -> v.validate(actualizarUsuarioDTO));

        Usuario usuario = (Usuario) repository.findByUsername(username);

        if (actualizarUsuarioDTO.password() != null){
            String hashedPassword = passwordEncoder.encode(actualizarUsuarioDTO.password());
            usuario.actualizarUsuarioConPassword(actualizarUsuarioDTO, hashedPassword);

        }else {
            usuario.actualizarUsuario(actualizarUsuarioDTO);
        }

        var datosUsuario = new DetalleUsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getEnabled()
        );
        return ResponseEntity.ok(datosUsuario);
    }

    @DeleteMapping("/{username}")
    @Transactional
    @Operation(summary = "Deshabilita a un usuario")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String username){
        Usuario usuario = (Usuario) repository.findByUsername(username);
        usuario.eliminarUsuario();
        return ResponseEntity.noContent().build();
    }
}
