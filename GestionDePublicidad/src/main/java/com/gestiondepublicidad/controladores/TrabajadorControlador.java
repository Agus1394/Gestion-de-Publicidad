package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Proyecto;
import java.util.List;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.servicios.ProyectoServicio;
import com.gestiondepublicidad.servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/trabajador")
public class TrabajadorControlador {

    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    private ProyectoServicio proyectoServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativoCliente(ModelMap modelo) {
        return "panel_trabajador.html";
    }

    //REGISTRAR USUARIO/CLIENTE DESDE Trabajador
    @GetMapping("/registrar")
    public String registrar() {
        return "registro_usuario.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre,
            @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, ModelMap modelo) {
        try {
            usuarioServicio.registrar(nombre, email, password, password2);
            modelo.put("éxito", "Usuario registrado correctamente!");
            return "usuario_cargado.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registro.html";
        }

    }

    //MODIFICAR Trabajador
    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {
        Usuario usuario = usuarioServicio.getOne(id);
        modelo.put("usuario", usuario);
        return "editar_trabajador.html";
    }

    @PostMapping("/modificar/{id}")
    public String actualizar(@RequestParam MultipartFile archivo, @PathVariable String id,
            @RequestParam String nombre, @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, ModelMap modelo) throws MiException {

        Usuario usuario = usuarioServicio.getOne(id);
        modelo.put("usuario", usuario);

        try {
            usuarioServicio.actualizar(archivo, id, nombre, email, password, password2);
            modelo.put("exito", "Usuario actualizado correctamente!");
            return "redirect:/trabajador";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "editar_trabajador.html";
        }
    }

    //----------------------------------------PROYECTOS--------------------------------------------
    //FILTRAR POR NOMBRE
    @GetMapping("/tablaProyectos")
    public String listarProyectos(ModelMap modelo) {
        List<Proyecto> proyectos = proyectoServicio.listarTodos();
        modelo.addAttribute("proyectos", proyectos);
        return "tablaProyectosTrabajador.html";
    }

    //filtrar por nombre
    @PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
    @PostMapping("/tablaProyectos/nombre")
    public String filtrarPorNombre(@RequestParam String nombre, ModelMap modelo) {
        List<Proyecto> proyectos = new ArrayList<Proyecto>();
        if (nombre.isEmpty() || nombre == null) {
            proyectos = proyectoServicio.listarTodos();
        } else {
            proyectos = proyectoServicio.buscarPorNombre(nombre.toUpperCase());
        }
        modelo.addAttribute("proyectos", proyectos);
        return "tablaProyectosTrabajador.html ";
    }

    //FILTRAR POR ESTADO DEL PROYECTO
    @PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
    @PostMapping("/tablaProyectos/estado")
    public String filtrarPorEstadoProyecto(@RequestParam String estado, ModelMap modelo) throws MiException {

        List<Proyecto> proyectos = new ArrayList<Proyecto>();

        if (estado.isEmpty() || estado == null) {
            proyectos = proyectoServicio.listarTodos();
        } else {
            proyectos = proyectoServicio.filtrarProyectoPorEstado(estado);
        }
        modelo.addAttribute("proyectos", proyectos);
        return "tablaProyectosTrabajador.html";
    }

    //FILTRAR POR FECHA DE INICIO
    @PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
    @PostMapping("/tablaProyectos/fechaInicio")
    public String ordenarProyectosPorFechaInicio(@RequestParam String fechaInicio, ModelMap modelo) {
        List<Proyecto> proyectos = new ArrayList<Proyecto>();

        if (fechaInicio.isEmpty() || fechaInicio == null) {
            proyectos = proyectoServicio.listarTodos();
        } else {
            proyectos = proyectoServicio.ordenarProyectosPorFechaInicio(fechaInicio);
        }

        modelo.addAttribute("proyectos", proyectos);
        return "tablaProyectosTrabajador.html";
    }

    //FILTRAR POR FECHA DE FIN
    @PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
    @PostMapping("/tablaProyectos/fechaFin")
    public String ordenarProyectosPorFechaFin(@RequestParam String fechaFin, ModelMap modelo) {
        List<Proyecto> proyectos = new ArrayList<Proyecto>();

        if (fechaFin.isEmpty() || fechaFin == null) {
            proyectos = proyectoServicio.listarTodos();
        } else {
            proyectos = proyectoServicio.ordenarProyectosPorFechaFin(fechaFin);
        }

        modelo.addAttribute("proyectos", proyectos);
        return "tablaProyectosTrabajador.html";
    }


    //ELIMINAR USUARIO/CLIENTE
    @PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        usuarioServicio.eliminarUsuario(id);
        return "redirect:/trabajador/usuarios";

    }
}
