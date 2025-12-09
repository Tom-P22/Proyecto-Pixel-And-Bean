package cl.TomP22.pnb.service.impl;

import cl.TomP22.pnb.model.Usuario;
import cl.TomP22.pnb.repository.IUsuarioRepository;
import cl.TomP22.pnb.service.UsuarioService;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioServiceImpl implements UsuarioService {

    private final IUsuarioRepository repository;

    public UsuarioServiceImpl(IUsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("El username es obligatorio");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        Usuario usuario = repository.buscarPorUsername(username.trim());

        if (usuario == null) {
            throw new RuntimeException("Credenciales inválidas");
        }

        if (!usuario.isActivo()) {
            throw new RuntimeException("Usuario inactivo. Contacta al administrador");
        }

        if (!usuario.getPassword().equals(password)) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return usuario;
    }

    @Override
    public List<Usuario> listarTodos() {
        return repository.listarTodos();
    }
    
    public List<Usuario> listarActivos() {
        return repository.listarTodos().stream()
                .filter(Usuario::isActivo)
                .collect(Collectors.toList());
    }

    @Override
    public Usuario buscarPorId(int id) {
        return repository.buscarPorId(id);
    }

    @Override
    public List<Usuario> buscarPorUsername(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return listarTodos();
        }
        
        String textoLower = texto.toLowerCase();
        return repository.listarTodos().stream()
                .filter(u -> u.getUsername().toLowerCase().contains(textoLower) ||
                             u.getNombreCompleto().toLowerCase().contains(textoLower))
                .collect(Collectors.toList());
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        validarDatosUsuario(usuario.getUsername(), usuario.getPassword(), usuario.getNombreCompleto(), usuario.getRol());

        if (usuario.getId() == 0 && repository.existeUsername(usuario.getUsername())) {
            throw new RuntimeException("El username '" + usuario.getUsername() + "' ya existe");
        }

        usuario.setUsername(usuario.getUsername().trim().toLowerCase());
        usuario.setNombreCompleto(usuario.getNombreCompleto().trim());
        
        repository.guardar(usuario);
        return usuario;
    }

    @Override
    public void actualizar(Usuario usuario) {
        validarDatosUsuario(usuario.getUsername(), usuario.getPassword(), usuario.getNombreCompleto(), usuario.getRol());

        Usuario usuarioAntiguo = repository.buscarPorId(usuario.getId());
        if (usuarioAntiguo == null) {
            throw new RuntimeException("Usuario no encontrado para actualizar");
        }

        if (!usuarioAntiguo.getUsername().equalsIgnoreCase(usuario.getUsername()) && 
            repository.existeUsername(usuario.getUsername())) {
            throw new RuntimeException("El username '" + usuario.getUsername() + "' ya existe");
        }

        usuario.setUsername(usuario.getUsername().trim().toLowerCase());
        usuario.setNombreCompleto(usuario.getNombreCompleto().trim());
        
        repository.actualizar(usuario);
    }

    @Override
    public void eliminar(int id) {
        Usuario usuario = repository.buscarPorId(id);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if ("ADMIN".equals(usuario.getRol()) && usuario.isActivo()) {
            int adminsActivos = contarAdminsActivos(); 
            if (adminsActivos <= 1) {
                throw new RuntimeException("No se puede eliminar el último administrador activo");
            }
        }

        repository.eliminar(id);
    }

    @Override
    public void cambiarEstado(int id, boolean activo) {
        Usuario usuario = repository.buscarPorId(id);
        if (usuario != null) {
            usuario.setActivo(activo);
            repository.actualizar(usuario);
        }
    }
    
    private void validarDatosUsuario(String username, String password, String nombreCompleto, String rol) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("El username es obligatorio");
        }
        if (username.trim().length() < 4) {
            throw new IllegalArgumentException("El username debe tener al menos 4 caracteres");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        if (password.length() < 4) { 
            throw new IllegalArgumentException("La contraseña debe tener al menos 4 caracteres");
        }

        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo es obligatorio");
        }

        if (rol == null || rol.trim().isEmpty()) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }
        
        if (!rol.equals("ADMIN") && !rol.equals("OPERADOR") && !rol.equals("CAJERO")) {
            throw new IllegalArgumentException("El rol debe ser ADMIN, OPERADOR o CAJERO");
        }
    }
    
    private int contarAdminsActivos() {
        return (int) repository.listarTodos().stream()
                .filter(u -> "ADMIN".equals(u.getRol()) && u.isActivo())
                .count();
    }
}