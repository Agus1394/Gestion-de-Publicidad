package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.enumeraciones.Rol;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.servicios.UsuarioServicio;
import java.util.ArrayList;
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

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "dashboard.html";
    }

    @GetMapping("/tablaUsuarios")
    public String listarUsuarios(ModelMap modelo) {
        List<Usuario> listaUsuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", listaUsuarios);
        return "tablaClientes.html";
    }

    //LISTAR
    @GetMapping("/tablaClientes")
    public String listarClientes(ModelMap modelo) {
        List<Usuario> listaUsuarios = usuarioServicio.buscarPorRol("USER");
        modelo.addAttribute("usuarios", listaUsuarios);
        return "tablaClientes.html";
    }
    @PostMapping("/tablaClientes/search")
    public String buscarClientePorNombre(@RequestParam String nombre, ModelMap modelo) throws Exception {
        try{
            List<Usuario> usuarios = new ArrayList<Usuario>();
            if(nombre.isEmpty() || nombre == null){
                usuarios = usuarioServicio.buscarPorRol("USER");
            }else{
                usuarios = usuarioServicio.usuariosPorNombreYRol(nombre.toUpperCase(), "USER");
            }
            modelo.addAttribute("usuarios", usuarios);
        }catch(Exception e){
            modelo.put("error", e.getMessage());

        }finally {
            return "tablaClientes.html";
        }
    }
    @GetMapping("/tablaTrabajadores")
    public String listarTrabajadores(ModelMap modelo) {
        List<Usuario> listaUsuarios = usuarioServicio.buscarPorRol("TRABAJADOR");
        modelo.addAttribute("usuarios", listaUsuarios);
        return "tablaTrabajadores.html";
    }
    @PostMapping("/tablaTrabajadores/search")
    public String buscarTrabajadorPorNombre(@RequestParam String nombre, ModelMap modelo) throws Exception {
        try{
            List<Usuario> usuarios = new ArrayList<Usuario>();
            if(nombre.isEmpty() || nombre == null){
                usuarios = usuarioServicio.buscarPorRol("TRABAJADOR");
            }else{
                usuarios = usuarioServicio.usuariosPorNombreYRol(nombre.toUpperCase(), "TRABAJADOR");
            }
            modelo.addAttribute("usuarios", usuarios);
        }catch(Exception e){
            modelo.put("error", e.getMessage());

        }finally {
            return "tablaTrabajadores.html";
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
            }else if (rol.equals("TRABAJADOR")){
                usuarioServicio.cambiarRol(id, Rol.TRABAJADOR);
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
