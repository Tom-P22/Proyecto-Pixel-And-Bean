package cl.TomP22.pnb.app;

import cl.TomP22.pnb.controller.*;
import cl.TomP22.pnb.repository.*;
import cl.TomP22.pnb.repository.mock.*;
import cl.TomP22.pnb.service.*;
import cl.TomP22.pnb.service.impl.*;

public class ApplicationContext {
    
    private static ApplicationContext instance;
    
    private IUsuarioRepository usuarioRepository;
    private IProductoRepository productoRepository;
    private IVentaRepository ventaRepository;
    
    private UsuarioService usuarioService;
    private ProductoService productoService;
    private VentaService ventaService;
    
    private LoginController loginController;
    private UsuarioController usuarioController;
    private ProductoController productoController;
    private VentaController ventaController;
    
    private ApplicationContext() {
        inicializar();
    }
    
    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }
    
    private void inicializar() {
        inicializarRepositorios();
        inicializarServicios();
        inicializarControllers();
    }
    
    private void inicializarRepositorios() {

        usuarioRepository = new UsuarioRepositoryMock();
        productoRepository = new ProductoRepositoryMock();
        ventaRepository = new VentaRepositoryMock();
    }
    
    private void inicializarServicios() {

        usuarioService = new UsuarioServiceImpl(usuarioRepository);
        productoService = new ProductoServiceImpl(productoRepository);
        ventaService = new VentaServiceImpl(ventaRepository);
    }
    
    private void inicializarControllers() {

        loginController = new LoginController(usuarioService); 
        usuarioController = new UsuarioController(usuarioService);
        productoController = new ProductoController(productoService);
        ventaController = new VentaController(ventaService);
    }
    
    public LoginController getLoginController() {
        return loginController;
    }
    
    public UsuarioController getUsuarioController() {
        return usuarioController;
    }
    
    public ProductoController getProductoController() {
        return productoController;
    }
    
    public VentaController getVentaController() {
        return ventaController;
    }
    
    public static void reset() {
        instance = null;
    }
}