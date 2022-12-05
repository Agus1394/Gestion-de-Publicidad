package com.gestiondepublicidad.controladores;

import java.util.List;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.servicios.UsuarioServicio;
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
@Secured({"ROLE_TRABAJADOR"})
@RequestMapping("/trabajador")
public class TrabajadorControlador {

    @Autowired
    UsuarioServicio usuarioServicio;
    ProyectoServicio proyectoServicio;


    @GetMapping("/indexTrabajador")
    //inicio del trabajador tiene una tabla con sus proyectos
    public List<Proyecto> panelAdministrativoTrabajador(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        Usuario usuario1 = usuarioServicio.getOne(usuario.getId_usuario()));
        List <Proyecto> proyectos = proyectoServicio.buscarPorUsuario(usuario1.getId_usuario()));
        modelo.put("proyectos", proyectos);

        return "indexTrabajador.html";
    }

    //------------------------------------------FILTROS PROYECTOS-----------------------------------

    @PostMapping("/indexTrabajador/nombre")
    public String listarProyectosPorNombre(@RequestParam String nombre, ModelMap modelo, HttpSession session) {
        try{
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            Usuario usuario1 = usuarioServicio.getOne(usuario.getId_usuario()));
            if(nombre.isEmpty() || nombre == null){
               panelAdministrativoTrabajador();
            }else{
                List<Proyecto> proyectos = new ArrayList<Proyecto>();
                proyectos = proyectoServicio.proyectosPorNombreYID(nombre.toUpperCase(), usuario1.getId_usuario());
                modelo.put("proyectos", proyectos);
            }
            modelo.addAttribute("proyectos", proyectos);
        }catch(Exception e){
            modelo.put("error", e.getMessage());

        }finally {
            return "indexTrabajador.html";
        }
    }

    @GetMapping("/indexTrabajador/estado")
    public String listarProyectosPorEstado(@RequestParam String estado, ModelMap modelo,HttpSession session) {
        try{
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            Usuario usuario1 = usuarioServicio.getOne(usuario.getId_usuario()));
            List<Proyecto> proyectos = new ArrayList<Proyecto>();
            if(estado.isEmpty() || estado == null){
                panelAdministrativoTrabajador();
            }else{
                proyectos = proyectoServicio.proyectosPorEstadoYID(estado.toUpperCase(), usuario1.getId_usuario());
            }
            modelo.addAttribute("proyectos", proyectos);
        }catch(Exception e){
            modelo.put("error", e.getMessage());

        }finally {
            return "indexTrabajador.html";
        }
    }

    @GetMapping("/indexTrabajador/fechaFin")
    public String listarProyectosPorFechaFin(@RequestParam String fechaFin, ModelMap modelo, HttpSession session) {
        try{
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            Usuario usuario1 = usuarioServicio.getOne(usuario.getId_usuario()));
            List<Proyecto> proyectos = new ArrayList<Proyecto>();
            if(fechaFin.isEmpty() || fechaFin == null){
                panelAdministrativoTrabajador();
            }else{
                proyectos = proyectoServicio.proyectosPorFechaFinYID(fechaFin.toUpperCase(), usuario1.getId_usuario());
            }
            modelo.addAttribute("proyectos", proyectos);
        }catch(Exception e){
            modelo.put("error", e.getMessage());

        }finally {
            return "indexTrabajador.html";
        }
    }

    @GetMapping("/indexTrabajador/fechaInicio")
    public String listarProyectosPorFechaInico(@RequestParam String fechaInicio, ModelMap modelo, HttpSession session) {
        try{
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            Usuario usuario1 = usuarioServicio.getOne(usuario.getId_usuario()));
            List<Proyecto> proyectos = new ArrayList<Proyecto>();
            if(fechaInicio.isEmpty() || fechaInicio == null){
                panelAdministrativoTrabajador();
            }else{
                proyectos = proyectoServicio.proyectosPorFechaInicioYID(fechaInicio.toUpperCase(), usuario1.getId_usuario());
            }
            modelo.addAttribute("proyectos", proyectos);
        }catch(Exception e){
            modelo.put("error", e.getMessage());

        }finally {
            return "indexTrabajador.html";
        }
    }

//------------------------------------------------------------------------------------------------------

    //MODIFICARSE
    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {
        Usuario usuario = usuarioServicio.getOne(id);
        modelo.put("usuario", usuario);
        return "perfil.html";
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
            return "perfil.html";
        }
    }

}
