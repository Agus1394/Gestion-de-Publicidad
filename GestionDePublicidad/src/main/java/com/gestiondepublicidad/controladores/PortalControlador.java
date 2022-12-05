package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Proyecto;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.repositorios.UsuarioRepositorio;
import com.gestiondepublicidad.servicios.ProyectoServicio;
import com.gestiondepublicidad.servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    ProyectoServicio proyectoServicio;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    //CREAR
    @GetMapping("/registro")
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registrar")
    public String registro(@RequestParam String nombre,
            @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, ModelMap modelo) {
        try {
            usuarioServicio.registrar(nombre, email, password, password2);
            modelo.put("exito", "Usuario registrado correctamente!");
            return "index.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registro.html";
        }
    }

    //LOGIN
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o contraseña invalidos");
        }
        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_TRABAJADOR', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(ModelMap modelo, HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        Usuario usuario = usuarioServicio.usuariosPorEmail(logueado.getEmail());
        modelo.put("usuario", usuario);
        if (logueado.getRol().toString().equals("ADMIN")) {
            return "dashboard.html";
        } else if (logueado.getRol().toString().equals("USER")) {
            modelo.addAttribute("proyectos", proyectoServicio.buscarPorUsuario(usuario.getId_usuario()));
            return "indexCliente.html";
        } else if (logueado.getRol().toString().equals("TRABAJADOR")) {
            modelo.addAttribute("proyectos", proyectoServicio.buscarPorUsuario(usuario.getId_usuario()));
            return "indexTrabajador.html";
        }
        return "index.html";
    }

    //----------------------------------------PROYECTOS--------------------------------------------
    //FILTRAR POR NOMBRE
    @GetMapping("/cliente/tablaProyectos")
    public String listarProyectos(ModelMap modelo) {
        List<Proyecto> proyectos = proyectoServicio.listarTodos();
        modelo.addAttribute("proyectos", proyectos);
        return "indexCliente.html";
    }

    //filtrar por nombre
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/cliente/tablaProyectos/nombre")
    public String filtrarPorNombre(@RequestParam String nombre, ModelMap modelo) {
        List<Proyecto> proyectos = new ArrayList<Proyecto>();
        if (nombre.isEmpty() || nombre == null) {
            proyectos = proyectoServicio.listarTodos();
        } else {
            proyectos = proyectoServicio.buscarPorNombre(nombre.toUpperCase());
        }
        modelo.addAttribute("proyectos", proyectos);
        return "redirect:/inicio";
    }

    //FILTRAR POR ESTADO DEL PROYECTO
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/cliente/tablaProyectos/estado")
    public String filtrarPorEstadoProyecto(@RequestParam String estado, ModelMap modelo)
            throws MiException {

        List<Proyecto> proyectos = new ArrayList<Proyecto>();

        if (estado.isEmpty() || estado == null) {
            proyectos = proyectoServicio.listarTodos();
        } else {
            proyectos = proyectoServicio.filtrarProyectoPorEstado(estado);
        }
        modelo.addAttribute("proyectos", proyectos);
        return "redirect:/inicio";
    }

    //FILTRAR POR FECHA DE INICIO
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/cliente/tablaProyectos/fechaInicio")
    public String ordenarProyectosPorFechaInicio(@RequestParam String fechaInicio, ModelMap modelo) {
        List<Proyecto> proyectos = new ArrayList<Proyecto>();

        if (fechaInicio.isEmpty() || fechaInicio == null) {
            proyectos = proyectoServicio.listarTodos();
        } else {
            proyectos = proyectoServicio.ordenarProyectosPorFechaInicio(fechaInicio);
        }

        modelo.addAttribute("proyectos", proyectos);
        return "redirect:/inicio";
    }

    //FILTRAR POR FECHA DE FIN
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/cliente/tablaProyectos/fechaFin")
    public String ordenarProyectosPorFechaFin(@RequestParam String fechaFin, ModelMap modelo) {
        List<Proyecto> proyectos = new ArrayList<Proyecto>();

        if (fechaFin.isEmpty() || fechaFin == null) {
            proyectos = proyectoServicio.listarTodos();
        } else {
            proyectos = proyectoServicio.ordenarProyectosPorFechaFin(fechaFin);
        }

        modelo.addAttribute("proyectos", proyectos);
        return "redirect:/inicio";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        // Usuario usuario1 = usuarioServicio.usuariosPorEmail(usuario.getEmail());
        modelo.put("usuario", usuario);
        return "perfil.html";
    }

    //ACTUALIZAR
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/perfil/{id}")

    //REVISAR FOTO Y RETURN DEL TRY
    public String actualizar(@RequestParam MultipartFile archivo, @PathVariable String id,
            @RequestParam String nombre, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2,
            ModelMap modelo) throws MiException {

        try {
            usuarioServicio.actualizar(archivo, id, nombre, email, password, password2);
            modelo.put("exito", "Usuario actualizado correctamente!");
            return "perfil.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "perfil.html";
        }

    }
}
