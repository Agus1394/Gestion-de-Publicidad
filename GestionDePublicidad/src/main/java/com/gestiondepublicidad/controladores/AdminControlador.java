package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.enumeraciones.PuestoEmpresa;
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

    //LISTAR TODOS LOS USUARIOS
    @GetMapping("/tablaUsuarios")
    public String listarUsuarios(ModelMap modelo) {
        List<Usuario> listaUsuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", listaUsuarios);
        return "tablaClientes.html";
    }

    //----------------------------------------CLIENTE--------------------------------------------
    //FILTRAR POR ROL: CLIENTE
    @GetMapping("/tablaClientes")
    public String listarClientes(ModelMap modelo) {
        List<Usuario> listaUsuarios = usuarioServicio.buscarPorRol(Rol.USER);
        modelo.addAttribute("usuarios", listaUsuarios);
        return "tablaClientes.html";
    }

    //FILTRAR CLIENTES POR NOMBRE
    @PostMapping("/tablaClientes/nombre")
    public String buscarClientePorNombre(@RequestParam String nombre, ModelMap modelo) throws Exception {
        try {
            List<Usuario> usuarios = new ArrayList<Usuario>();
            if (nombre.isEmpty() || nombre == null) {
                usuarios = usuarioServicio.buscarPorRol(Rol.USER);
            } else {
                usuarios = usuarioServicio.usuariosPorNombreYRol(nombre.toUpperCase(), "USER");
            }
            modelo.addAttribute("usuarios", usuarios);
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        }
        return "tablaClientes.html";

    }

    //LISTAR CLIENTES POR EMAIL
    @PostMapping("/tablaClientes/email")
    public String listarClientesEmail(@RequestParam String email, ModelMap modelo) {
        try {
            Usuario listaUsuarios = usuarioServicio.BusquedaPorEmail(email.toLowerCase(), Rol.USER);
            modelo.addAttribute("usuarios", listaUsuarios);
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        }
        return "tablaClientes.html";
    }

    //LISTAR CLIENTES POR PROYECTO
    @PostMapping("/tablaClientes/proyecto")
    public String listarClientesProyecto(@RequestParam String proyecto, ModelMap modelo) {
        try {
            List<Usuario> listaUsuarios = usuarioServicio.usuariosPorProyecto(proyecto.toUpperCase(), Rol.USER);
            modelo.addAttribute("usuarios", listaUsuarios);
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        }
        return "tablaClientes.html";
    }

    //----------------------------------------TRABAJADOR--------------------------------------------
    //LISTAR TRABAJADORES 
    @GetMapping("/tablaTrabajadores")
    public String listarTrabajadores(ModelMap modelo) {
        List<Usuario> listaUsuarios = usuarioServicio.buscarPorRol(Rol.TRABAJADOR);
        modelo.addAttribute("usuarios", listaUsuarios);
        return "tablaTrabajadores.html";
    }

    //LISTAR TRABAJADORES POR NOMBRE
    @PostMapping("/tablaTrabajadores/search")
    public String buscarTrabajadorPorNombre(@RequestParam String nombre, ModelMap modelo) throws Exception {
        try {
            List<Usuario> usuarios = new ArrayList<Usuario>();
            if (nombre.isEmpty() || nombre == null) {
                usuarios = usuarioServicio.buscarPorRol(Rol.TRABAJADOR);
            } else {
                usuarios = usuarioServicio.usuariosPorNombreYRol(nombre.toUpperCase(), "TRABAJADOR");
            }
            modelo.addAttribute("usuarios", usuarios);
        } catch (Exception e) {
            modelo.put("error", e.getMessage());

        } finally {
            return "tablaTrabajadores.html";
        }
    }

    //LISTAR TRABAJADORES POR EMAIL
    @PostMapping("/tablaTrabajadores/email")
    public String listarPorEmail(@RequestParam String email, ModelMap modelo) {
        Usuario usuario = usuarioServicio.BusquedaPorEmail(email.toLowerCase(), Rol.TRABAJADOR);
        modelo.addAttribute("usuario", usuario);
        return "tablaTrabajadores.html";
    }

    //LISTAR TRABAJADORES POR PUESTO EN LA EMPRESA
    @PostMapping("/tablaTrabajadores/puestoempresa")
    public String listarPorPuestoEmpresa(@RequestParam(required = false) PuestoEmpresa puestoempresa,
            ModelMap modelo) throws Exception {
        try {
            switch (puestoempresa) {
                case CEO:
                    List<Usuario> usuario = usuarioServicio.BusquedaPorPuesto(puestoempresa.CEO, Rol.TRABAJADOR);
                    modelo.addAttribute("usuario", usuario);
                    break;
                case CTO:
                    List<Usuario> usuario1 = usuarioServicio.BusquedaPorPuesto(puestoempresa.CTO, Rol.TRABAJADOR);
                    modelo.addAttribute("usuario", usuario1);
                    break;
                case EMPRESARIO:
                    List<Usuario> usuario2 = usuarioServicio.BusquedaPorPuesto(puestoempresa.EMPRESARIO, Rol.TRABAJADOR);
                    modelo.addAttribute("usuario", usuario2);

                    break;
                case GERENTE:
                    List<Usuario> usuario3 = usuarioServicio.BusquedaPorPuesto(puestoempresa.GERENTE, Rol.TRABAJADOR);
                    modelo.addAttribute("usuario", usuario3);

                    break;
                case MANAGER:
                    List<Usuario> usuario4 = usuarioServicio.BusquedaPorPuesto(puestoempresa.MANAGER, Rol.TRABAJADOR);
                    modelo.addAttribute("usuario", usuario4);
                    break;
                default:
                    return "tablaTrabajadores.html";
            }

        } catch (Exception e) {
            modelo.put("error", e.getMessage());

        }
        return "tablaTrabajadores.html";
    }

    //LISTAR TRABAJADORES POR PROYECTO
    @PostMapping("/tablaTrabajadores/proyecto")
    public String listarPorProyecto(@RequestParam(required = false) String proyecto, ModelMap modelo) {
        try {
            List<Usuario> usuarios = new ArrayList<Usuario>();
            if (proyecto.isEmpty() || proyecto == null) {
                usuarios = usuarioServicio.buscarPorRol(Rol.TRABAJADOR);
            } else {
                List<Usuario> usuario = usuarioServicio.usuariosPorProyecto(proyecto, Rol.TRABAJADOR);
                modelo.addAttribute("usuario", usuario);
            }
        } catch (Exception e) {
            modelo.put("error", e.getMessage());

        }
        return "tablaTrabajadores.html";
    }
    
    //----------------------------------------PROYECTO--------------------------------------------

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
            if (usuario.getRol().toString().equals(rol)) {
                throw new MiException("El usuario ya tiene este rol");
            } else if (rol.equals("TRABAJADOR")) {
                usuarioServicio.cambiarRol(id, Rol.TRABAJADOR);
            } else if (rol.equals("USER")) {
                usuarioServicio.cambiarRol(id, Rol.USER);
            } else if (rol.equals("ADMIN")) {
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
