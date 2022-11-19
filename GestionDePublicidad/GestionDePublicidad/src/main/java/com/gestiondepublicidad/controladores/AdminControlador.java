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
        return "panel_admin.html";
    }

    //LISTAR
    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "usuario_list.html";
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
    public String registro(@RequestParam MultipartFile archivo, @RequestParam String nombre,
            @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, ModelMap modelo) {
        try {
            usuarioServicio.registrar(archivo, nombre, email, password, password2);
            modelo.put("éxito", "Usuario registrado correctamente!");
            return "usuario_cargado.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registro.html";
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
