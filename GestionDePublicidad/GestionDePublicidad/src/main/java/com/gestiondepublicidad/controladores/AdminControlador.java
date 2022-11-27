package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.enumeraciones.Rol;
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

    @GetMapping("/tablaUsuarios")
    public String listarUsuarios(ModelMap modelo) {
        List<Usuario> listaUsuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", listaUsuarios);
        return "tablaUsuarios.html";
    }

    //LISTAR
    @GetMapping("/tablaUsuarios/{rol}")
    public String listarUsuariosRol(@PathVariable("rol") String rol, ModelMap modelo) {
        List<Usuario> listaUsuarios = usuarioServicio.buscarPorRol(rol);
        modelo.addAttribute("usuarios", listaUsuarios);
        return "tablaUsuarios.html";
    }

    @PostMapping("/tablaUsuarios/{nombre}")
    public String listarClientesPorNombre(ModelMap modelo, @PathVariable String nombre) {
        try{
            List<Usuario> listaClientes = usuarioServicio.listarClientesPorNombre(nombre);
            modelo.addAttribute("listaClientes", listaClientes);
        }catch(Exception e){
            modelo.put("error", e.getMessage());

        }finally {
            return "tablaClientes.html";
        }
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
            return "redirect:/admin/usuarios";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "listarUsuarios";
        }

    }

    //MODIFICAR ROL USUARIO
    @GetMapping("/modificarRol/{id}")
    public String modificarRol(ModelMap modelo, @PathVariable String id) {
        Usuario usuario = usuarioServicio.getOne(id);
        modelo.put("usuario", usuario);
        return "editar_usuario.html";
    }

    @PostMapping("/modificarRol/{id}")
    public String modificarRol(@RequestParam String id, String rol, ModelMap modelo) throws MiException {

        Usuario usuario = usuarioServicio.getOne(id);
        modelo.put("usuario", usuario);

        try {
            if (usuario.getRol().toString().equals(rol)){
                throw new MiException("El usuario ya tiene este rol");
            }else if (rol.equals("CLIENTE")){
                usuarioServicio.cambiarRol(id, Rol.CLIENTE);
            } else if (rol.equals("USER")) {
                usuarioServicio.cambiarRol(id, Rol.USER);
            }else if (rol.equals("ADMIN")) {
                usuarioServicio.cambiarRol(id, Rol.ADMIN);
            }

            modelo.put("exito", "Usuario actualizado correctamente!");
            return "editar_usuario.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("usuario", usuario);
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
