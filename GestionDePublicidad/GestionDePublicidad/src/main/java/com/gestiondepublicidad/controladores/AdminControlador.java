package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.servicios.UsuarioServicio;
import java.util.List;
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
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap modelo) {
        return "dashboard.html";
    }

    //LISTAR
    @GetMapping("/tablaClientes")
    public String listarClientes(ModelMap modelo) {
        List<Usuario> listaClientes = usuarioServicio.listarClientes();
        modelo.addAttribute("usuarios", listaClientes);
        return "tablaClientes.html";
    }

    @GetMapping("/tablaTrabajadores")
    public String listarTrabajadores(ModelMap modelo) {
        List<Usuario> listaTrabajadores = usuarioServicio.listarTrabajadores();
        modelo.addAttribute("usuarios", listaTrabajadores);
        return "tablaTrabajadores.html";
    }

    @PostMapping("/cliente/flitroPorNombre/{nombre}")
    public String listarClientesPorNombre(ModelMap modelo, @PathVariable String nombre) {
        List<Usuario> listaClientes = usuarioServicio.listarClientesPorNombre(nombre);
        modelo.addAttribute("listaClientes", listaClientes);
        return "tablaClientes.html";
    }

    @PostMapping("/cliente/filtroPorEmail/{email}")
    public String listarClientesPorEmail(ModelMap modelo, @PathVariable String email) {
        List<Usuario> listaClientes = usuarioServicio.listarClientesPorEmail(email);
        modelo.addAttribute("listaClientes", listaClientes);
        return "tablaClientes.html";
    }

    @PostMapping("/trabajador/filtroPorNombre/{nombre}")
    public String listarTrabajadoresPorNombre(ModelMap modelo, @PathVariable String nombre) {
        List<Usuario> listaTrabajadores = usuarioServicio.listarTrabajadoresPorNombre(nombre);
        modelo.addAttribute("listaTrabajadores", listaTrabajadores);
        return "tablaTrabajadores.html";
    }

    @PostMapping("/trabajador/filtroPorEmail/{email}")
    public String listarTrabajadoresPorEmail(ModelMap modelo, @PathVariable String email) {
        List<Usuario> listaClientes = usuarioServicio.listarTrabajadoresPorEmail(email);
        modelo.addAttribute("listaClientes", listaClientes);
        return "tablaClientes.html";
    }

    //MODIFICAR ROL USUARIOS
    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id) {
        usuarioServicio.cambiarRol(id);
        return "redirect:/admin/usuarios";
    }

    //REGISTRAR USUARIO/CLIENTE DESDE ADMIN
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
            return "registro_usuario.html";
        }

    }

    //MODIFICAR USUARIO
    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {
        Usuario usuario = usuarioServicio.getOne(id);
        modelo.put("usuario", usuario);
        return "editar_usuario.html";
    }

    @PostMapping("/modificar/{id}")
    public String actualizar(@RequestParam MultipartFile archivo, @PathVariable String id, @RequestParam String nombre, @RequestParam String email,
            ModelMap modelo) throws MiException {

        Usuario usuario = usuarioServicio.getOne(id);
        modelo.put("usuario", usuario);

        try {
            usuarioServicio.adminEditar(archivo, id, nombre, email);
            modelo.put("exito", "Usuario actualizado correctamente!");
            return "editar_usuario.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "editar_usuario.html";
        }
    }

    //ELIMINAR USUARIO
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        usuarioServicio.eliminarUsuario(id);
        return "redirect:/admin/usuarios";

    }
}
