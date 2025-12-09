package cl.TomP22.pnb.service.impl;

import cl.TomP22.pnb.model.Usuario;
import cl.TomP22.pnb.repository.IUsuarioRepository;
import cl.TomP22.pnb.repository.impl.UsuarioRepositoryImpl;
import cl.TomP22.pnb.service.UsuarioService;
import cl.TomP22.pnb.util.PasswordHasher;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsuarioServiceImpl implements UsuarioService {

    private final IUsuarioRepository repository;

    public UsuarioServiceImpl() {
        this.repository = new UsuarioRepositoryImpl();
    }

    @Override
    public List<Usuario> listarTodos() {
        return repository.listarTodos();
    }
    
    @Override
    public List<Usuario> buscar(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return listarTodos();
        }
        String filtro = texto.toLowerCase();
        return repository.listarTodos().stream()
                .filter(u -> u.getUsername().toLowerCase().contains(filtro) ||
                             u.getNombreCompleto().toLowerCase().contains(filtro))
                .collect(Collectors.toList());
    }

    @Override
    public Usuario buscarPorId(int id) {
        return repository.buscarPorId(id).orElse(null);
    }

    @Override
    public Usuario autenticar(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("El username es obligatorio");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
     
        Optional<Usuario> usuario = repository.autenticar(username, password);
        
        if (usuario.isPresent()) {
            return usuario.get();
        } else {
            throw new RuntimeException("Credenciales inválidas o usuario inactivo");
        }
    }

    @Override
    public void crear(String username, String password, String nombreCompleto, String rol) {
        validarDatosUsuario(username, password, nombreCompleto, rol);

        if (repository.buscarPorUsername(username).isPresent()) {
            throw new IllegalArgumentException("El username '" + username + "' ya existe");
        }

        Usuario nuevo = new Usuario();
        nuevo.setUsername(username.trim().toLowerCase());
   
        nuevo.setPassword(PasswordHasher.hash(password)); 
        nuevo.setNombreCompleto(nombreCompleto.trim());
        nuevo.setRol(rol);
        nuevo.setActivo(true);
        
        repository.guardar(nuevo);
    }

    @Override
    public void actualizar(int id, String username, String password, String nombreCompleto, String rol, boolean activo) {

        if (username == null || username.trim().isEmpty()) throw new IllegalArgumentException("Username obligatorio");
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) throw new IllegalArgumentException("Nombre obligatorio");
        if (rol == null) throw new IllegalArgumentException("Rol obligatorio");

        Usuario usuarioAntiguo = repository.buscarPorId(id).orElse(null);
        if (usuarioAntiguo == null) {
            throw new RuntimeException("Usuario no encontrado para actualizar");
        }

        if (!usuarioAntiguo.getUsername().equalsIgnoreCase(username)) {
            if (repository.buscarPorUsername(username).isPresent()) {
                throw new RuntimeException("El username '" + username + "' ya existe");
            }
        }

        usuarioAntiguo.setUsername(username.trim().toLowerCase());

        if (password != null && !password.trim().isEmpty()) {
            usuarioAntiguo.setPassword(PasswordHasher.hash(password));
        }
        
        usuarioAntiguo.setNombreCompleto(nombreCompleto.trim());
        usuarioAntiguo.setRol(rol);
        usuarioAntiguo.setActivo(activo);
        
        repository.actualizar(usuarioAntiguo);
    }

    @Override
    public void eliminar(int id) {
        Usuario usuario = repository.buscarPorId(id).orElse(null);
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
        if (activo) {
            repository.activar(id);
        } else {
            repository.desactivar(id);
        }
    }

    private void validarDatosUsuario(String username, String password, String nombreCompleto, String rol) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("El username es obligatorio");
        }
        if (username.trim().length() < 3) {
            throw new IllegalArgumentException("El username debe tener al menos 3 caracteres");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo es obligatorio");
        }
        if (rol == null || (!rol.equals("ADMIN") && !rol.equals("OPERADOR") && !rol.equals("CAJERO"))) {
            throw new IllegalArgumentException("El rol debe ser ADMIN, OPERADOR o CAJERO");
        }
    }
    
    private int contarAdminsActivos() {
        return (int) repository.listarTodos().stream()
                .filter(u -> "ADMIN".equals(u.getRol()) && u.isActivo())
                .count();
    }
}