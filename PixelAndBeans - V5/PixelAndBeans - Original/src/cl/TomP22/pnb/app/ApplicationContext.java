package cl.TomP22.pnb.app;

import cl.TomP22.pnb.controller.*;
import cl.TomP22.pnb.service.*;
import cl.TomP22.pnb.service.impl.*;

public class ApplicationContext {
    
    private static ApplicationContext instance;

    // Servicios
    private UsuarioService usuarioService;
    private ProductoService productoService;
    private VentaService ventaService;
    
    // Controladores
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

        inicializarServicios();

        inicializarControllers();
    }
    
    private void inicializarServicios() {
        this.usuarioService = new UsuarioServiceImpl();
        this.productoService = new ProductoServiceImpl();
        this.ventaService = new VentaServiceImpl();
    }
    
    private void inicializarControllers() {
        this.loginController = new LoginController(usuarioService); 
        this.usuarioController = new UsuarioController(usuarioService);
        this.productoController = new ProductoController(productoService);
        this.ventaController = new VentaController(ventaService);
    }
    
    // --- Getters ---
    
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