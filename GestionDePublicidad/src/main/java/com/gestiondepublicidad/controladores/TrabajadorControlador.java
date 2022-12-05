package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Proyecto;
import java.util.List;

import com.gestiondepublicidad.entidades.Nota;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.excepciones.MiException;

import com.gestiondepublicidad.servicios.NotaServicio;
import com.gestiondepublicidad.servicios.ProyectoServicio;

import com.gestiondepublicidad.servicios.UsuarioServicio;
import java.util.ArrayList;
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

import javax.servlet.http.HttpSession;

@Controller
@PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
@RequestMapping("/trabajador")
public class TrabajadorControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    ProyectoServicio proyectoServicio;

    @Autowired
    NotaServicio notaServicio;




    @GetMapping("/indexTrabajador")
    //inicio del trabajador tiene una tabla con sus proyectos
    public String panelAdministrativoTrabajador(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        Usuario usuario1 = usuarioServicio.getOne(usuario.getId_usuario());
        List <Proyecto> proyectos = proyectoServicio.buscarPorUsuario(usuario1.getId_usuario());
        modelo.put("proyectos", proyectos);

        return "indexTrabajador.html";
    }

    @PostMapping("/crearProyecto")
    public String crearProyecto (){
     
        return "";
    }
    
    
    //------------------------------------------FILTROS ID y PROYECTOS-----------------------------------

    @PostMapping("/nombre")
    public String listarProyectosPorNombre(@RequestParam String nombre, ModelMap modelo, HttpSession session) {
        try{
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            Usuario usuario1 = usuarioServicio.getOne(usuario.getId_usuario());
            List<Proyecto> proyectos = new ArrayList<Proyecto>();
            if(nombre.isEmpty() || nombre == null){
               panelAdministrativoTrabajador(modelo, session);
            }else{
                proyectos = proyectoServicio.proyectosPorIdYNombre(nombre.toUpperCase(), usuario1.getId_usuario());
                modelo.put("proyectos", proyectos);
            }
            modelo.addAttribute("proyectos", proyectos);
        }catch(Exception e){
            modelo.put("error", e.getMessage());

        }finally {
            return "/indexTrabajador";
        }
    }

    @PostMapping("/estado")
    public String listarProyectosPorEstado(@RequestParam String estado, ModelMap modelo,HttpSession session) {
        try{
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            Usuario usuario1 = usuarioServicio.getOne(usuario.getId_usuario());
            List<Proyecto> proyectos = new ArrayList<Proyecto>();
            if(estado.isEmpty() || estado == null){
                panelAdministrativoTrabajador(modelo, session);
            }else{
                proyectos = proyectoServicio.proyectosPorIdYEstado(estado.toUpperCase(), usuario1.getId_usuario());
            }
            modelo.addAttribute("proyectos", proyectos);
        }catch(Exception e){
            modelo.put("error", e.getMessage());

        }finally {
            return "/indexTrabajador";
        }
    }

    @PostMapping("/fechaFin")
    public String listarProyectosPorFechaFin(@RequestParam String fechaFin, ModelMap modelo, HttpSession session) {
        try{
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            Usuario usuario1 = usuarioServicio.getOne(usuario.getId_usuario());
            List<Proyecto> proyectos = new ArrayList<Proyecto>();
            if(fechaFin.isEmpty() || fechaFin == null){
                panelAdministrativoTrabajador(modelo, session);
            }else{
                proyectos = proyectoServicio.proyectosPorIdYFechaFin(fechaFin.toUpperCase(), usuario1.getId_usuario());
            }
            modelo.addAttribute("proyectos", proyectos);
        }catch(Exception e){
            modelo.put("error", e.getMessage());

        }finally {
            return "/indexTrabajador";
        }
    }

    @PostMapping("/fechaInicio")
    public String listarProyectosPorFechaInico(@RequestParam String fechaInicio, ModelMap modelo, HttpSession session) {
        try{
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            Usuario usuario1 = usuarioServicio.getOne(usuario.getId_usuario());
            List<Proyecto> proyectos = new ArrayList<Proyecto>();
            if(fechaInicio.isEmpty() || fechaInicio == null){
                panelAdministrativoTrabajador(modelo, session);
            }else{
                proyectos = proyectoServicio.proyectosPorIdYFechaInicio(fechaInicio.toUpperCase(), usuario1.getId_usuario());
            }
            modelo.addAttribute("proyectos", proyectos);
        }catch(Exception e){
            modelo.put("error", e.getMessage());

        }finally {
            return "/indexTrabajador";
        }
    }

//-------------------------------------PERFIL----------------------------------------------------

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

    
   //---------------------------NOTA--------------------------------------------------------


    //CREAR NOTA
    @PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
    @GetMapping("/nota/crear")
    public String crearNota(){
        return "formularioNota.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
    @PostMapping("/nota/creado")
    public String notaCreada(String descripcion, String titulo, HttpSession session, ModelMap modelo){
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        Usuario usuario = usuarioServicio.getOne(logueado.getId_usuario());

        Nota nota = new Nota();
        nota.setTitulo(titulo);
        nota.setDescripcion(descripcion);
        notaServicio.crearNota(nota);
        usuarioServicio.agregarNotaUsuario(usuario, nota);

        modelo.put("nota", nota);
        modelo.put("exito", "nota creada con éxito");
        return "redirect:/trabajador/nota/" + nota.getId_nota();
    }

    // NOTAS TRABAJADOR
    @PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
    @GetMapping("/listaNota")
    public String listarNota(ModelMap modelo, HttpSession session){
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        modelo.addAttribute("notas", notaServicio.listarNotas(logueado.getId_usuario()));
        return "listarNotas.html";
    }

    // EDITAR NOTA TRABAJADOR
    @PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
    @GetMapping("/nota/{id_nota}")
    public String editarNota(@PathVariable String id_nota){
        return "formularioNota.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
    @PostMapping("/nota/{id_nota}/editada")
    public String editarNota(@PathVariable String id_nota, String titulo, String descripcion, ModelMap modelo, HttpSession session) throws MiException {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        try{
            notaServicio.actualizar(id_nota, titulo, descripcion, logueado);
            modelo.put("exito", "nota guardada con exito");
        }catch (Exception e){
            modelo.put("error", e.getMessage());
        }finally {
            modelo.addAttribute("notas", notaServicio.listarNotas(logueado.getId_usuario()));
            return "listarNotas.html";
        }
    }

//    @PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
    @GetMapping("/nota/eliminar/{id}")
    public String eliminarNota(@PathVariable String id, HttpSession session) throws MiException {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        notaServicio.eliminar(id);
        return "listarNotas.html";
    }

}





