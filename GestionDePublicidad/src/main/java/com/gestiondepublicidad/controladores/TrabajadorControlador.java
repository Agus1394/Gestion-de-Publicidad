package com.gestiondepublicidad.controladores;

import java.util.List;

import com.gestiondepublicidad.entidades.Nota;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.repositorios.NotaRepositorio;
import com.gestiondepublicidad.servicios.NotaServicio;
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

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/trabajador")
public class TrabajadorControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    NotaServicio notaServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativoCliente(ModelMap modelo) {
        return "panel_trabajador.html";
    }

    //LISTAR TRABAJADORES POR NOMBRE
    @GetMapping("/lista_trabajadores")
    public String listar(String nombre, ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.usuariosPorNombreYRol(nombre, "TRABAJADOR");
        modelo.addAttribute("usuarios", usuarios);
        return "trabajador_list.html";
    }

    @GetMapping("/lista_trabajadores/{email}")
    public String listarPorEmail(@PathVariable String email, ModelMap modelo) {
        Usuario usuario = usuarioServicio.BusquedaPorEmail(email, "TRABAJADOR");
        modelo.addAttribute("usuario", usuario);
        return "trabajador_list.html";
    }

    @GetMapping("/lista_trabajadores/{puesto_empresa}")
    public String listarPorPuestoEmpresa(@PathVariable String puesto_emoresa, ModelMap modelo) {
        List<Usuario> usuario = usuarioServicio.BusquedaPorPuesto(puesto_emoresa, "TRABAJADOR");
        modelo.addAttribute("usuario", usuario);
        return "trabajador_list.html";
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

    //ELIMINAR USUARIO/CLIENTE
    @PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        usuarioServicio.eliminarUsuario(id);
        return "redirect:/trabajador/usuarios";

    }


    //CREAR NOTA
    @GetMapping("/nota/crear")
    public String crearNota(){
        return "formularioNota.html";
    }

    @PostMapping("/nota/creado")
    public String notaCreada(String descripcion, String titulo, HttpSession session, ModelMap modelo){
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        notaServicio.crearNota(descripcion,titulo);
        Nota nota = new Nota();
        List<Nota> notas =  notaServicio.listar(logueado.getId_usuario());
        notas.add(nota) ;
        modelo.addAttribute("notas", notas);
        return "listarNotas.html";
    }

    // NOTAS TRABAJADOR
    @PreAuthorize("hasRole('ROLE_TRABAJADOR')")
    @GetMapping("/listaNota")
    public String listarNota(ModelMap modelo, HttpSession session){
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        modelo.addAttribute("notas", notaServicio.listar(logueado.getId_usuario()));
        return "listarNotas.html";
    }

    // EDITAR NOTA TRABAJADOR
    @PreAuthorize("hasRole('ROLE_TRABAJADOR')")
    @GetMapping("/nota/{id_nota}")
    public String editarNota(@PathVariable String id_nota){
        return "formularioNota.html";
    }

    @PostMapping("/nota/{id_nota}/editada")
    public String editarNota(@PathVariable String id_nota, String titulo, String descripcion, ModelMap modelo, HttpSession session) throws MiException {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        try{
            notaServicio.actualizar(id_nota, titulo, descripcion, logueado);
            modelo.put("exito", "nota guardada con exito");
        }catch (Exception e){
            modelo.put("error", e.getMessage());
        }finally {
            modelo.addAttribute("notas", notaServicio.listar(logueado.getId_usuario()));
            return "listarNotas.html";
        }
    }
}



