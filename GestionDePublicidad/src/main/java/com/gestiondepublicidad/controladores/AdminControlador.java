package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Proyecto;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.enumeraciones.PuestoEmpresa;
import com.gestiondepublicidad.enumeraciones.Rol;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.servicios.ProyectoServicio;
import com.gestiondepublicidad.servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private ProyectoServicio proyectoServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "dashboard.html";
    }


    //LISTAR ---TODOS--- LOS USUARIOS
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
        System.out.println(listaUsuarios);
        modelo.addAttribute("clientes", listaUsuarios);
        return "tablaClientes.html";
    }

    //FILTRAR CLIENTES POR NOMBRE
    @PostMapping("/tablaClientes/nombre")
    public String buscarClientePorNombre(@RequestParam String nombre, ModelMap modelo) throws Exception {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        try {
            if (nombre.isEmpty() || nombre == null) {
                usuarios = usuarioServicio.buscarPorRol(Rol.USER);
            } else {
                usuarios = usuarioServicio.usuariosPorNombreYRol(nombre.toUpperCase(), "USER");
            }
            modelo.addAttribute("clientes", usuarios);
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
            modelo.addAttribute("clientes", listaUsuarios);
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        }
        return "tablaClientes.html";
    }

    //LISTAR CLIENTES POR PROYECTO
    @PostMapping("/tablaClientes/proyecto")
    public String listarClientesProyecto(@RequestParam String proyecto, ModelMap modelo) {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        try {
            if (proyecto.isEmpty() || proyecto == null) {
                usuarios = usuarioServicio.buscarPorRol(Rol.USER);
            } else {
                usuarios = usuarioServicio.nombreProyectoUsuarios(proyecto, "USER");
            }
            modelo.addAttribute("clientes", usuarios);
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        }
        return "tablaClientes.html";
    }

    //----------------------------------------TRABAJADOR--------------------------------------------
    //LISTAR TRABAJADORES
    //NO FUNCIONAN LOS FILTROS
    @GetMapping("/tablaTrabajadores")
    public String listarTrabajadores(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.buscarPorRol(Rol.TRABAJADOR);
        modelo.addAttribute("trabajadores", usuarios);
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
            modelo.addAttribute("trabajadores", usuarios);
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
        modelo.addAttribute("trabajadores", usuario);
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
                    modelo.addAttribute("trabajadores", usuario);
                    break;
                case CTO:
                    List<Usuario> usuario1 = usuarioServicio.BusquedaPorPuesto(puestoempresa.CTO, Rol.TRABAJADOR);
                    modelo.addAttribute("trabajadores", usuario1);
                    break;
                case EMPRESARIO:
                    List<Usuario> usuario2 = usuarioServicio.BusquedaPorPuesto(puestoempresa.EMPRESARIO, Rol.TRABAJADOR);
                    modelo.addAttribute("trabajadores", usuario2);

                    break;
                case GERENTE:
                    List<Usuario> usuario3 = usuarioServicio.BusquedaPorPuesto(puestoempresa.GERENTE, Rol.TRABAJADOR);
                    modelo.addAttribute("trabajadores", usuario3);

                    break;
                case MANAGER:
                    List<Usuario> usuario4 = usuarioServicio.BusquedaPorPuesto(puestoempresa.MANAGER, Rol.TRABAJADOR);
                    modelo.addAttribute("trabajadores", usuario4);
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
                usuarios = usuarioServicio.nombreProyectoUsuarios(proyecto, "TRABAJADOR");
            }
            modelo.addAttribute("trabajadores", usuarios);
        } catch (Exception e) {
            modelo.put("error", e.getMessage());

        }
        return "tablaTrabajadores.html";
    }
    
        //ELIMINAR USUARIO
        @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
        @PostMapping("/eliminar/{id}")
        public String eliminar
        (@PathVariable
        String id, ModelMap modelo
        
            ) {
        usuarioServicio.eliminarUsuario(id);
            return "redirect:/admin/usuarios";

        }
    //----------------------------------------PROYECTO--------------------------------------------
    //Listar TODOS
    @GetMapping("/tablaProyectos")
    public String listarProyectos(ModelMap modelo) {
        List<Proyecto> proyectos = proyectoServicio.listarTodos();
        modelo.addAttribute("proyectos", proyectos);
        return "tablaProyectos.html";
    }

    //filtrar por nombre
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/tablaProyectos/nombre")
    public String filtrarPorNombre(@RequestParam String nombre, ModelMap modelo) {
        List<Proyecto> proyectos = new ArrayList<Proyecto>();
        if (nombre.isEmpty() || nombre == null) {
            proyectos = proyectoServicio.listarTodos();
        } else {
            proyectos = proyectoServicio.buscarPorNombre(nombre.toUpperCase());
        }
        modelo.addAttribute("proyectos", proyectos);
        return "tablaProyectos.html ";
    }

    //FILTRAR POR ESTADO DEL PROYECTO
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/tablaProyectos/estado")
    public String filtrarPorEstadoProyecto(@RequestParam String estado, ModelMap modelo) throws MiException {

        List<Proyecto> proyectos = new ArrayList<Proyecto>();

        if (estado.isEmpty() || estado == null) {
            proyectos = proyectoServicio.listarTodos();
        } else {
            proyectos = proyectoServicio.filtrarProyectoPorEstado(estado);
        }
        modelo.addAttribute("proyectos", proyectos);
        return "tablaProyectos.html";
    }

    //FILTRAR POR FECHA DE INICIO
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/tablaProyectos/fechaInicio")
    public String ordenarProyectosPorFechaInicio(@RequestParam String fechaInicio, ModelMap modelo) {
        List<Proyecto> proyectos = new ArrayList<Proyecto>();

        if (fechaInicio.isEmpty() || fechaInicio == null) {
            proyectos = proyectoServicio.listarTodos();
        } else {
            proyectos = proyectoServicio.ordenarProyectosPorFechaInicio(fechaInicio);
        }

        modelo.addAttribute("proyectos", proyectos);
        return "tablaProyectos.html";
    }

    //FILTRAR POR FECHA DE FIN
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/tablaProyectos/fechaFin")
    public String ordenarProyectosPorFechaFin(@RequestParam String fechaFin, ModelMap modelo) {
        List<Proyecto> proyectos = new ArrayList<Proyecto>();

        if (fechaFin.isEmpty() || fechaFin == null) {
            proyectos = proyectoServicio.listarTodos();
        } else {
            proyectos = proyectoServicio.ordenarProyectosPorFechaFin(fechaFin);
        }

        modelo.addAttribute("proyectos", proyectos);
        return "tablaProyectos.html";
    }

    //----------------------------------------MODIFICAR PROYECTO ADMIN--------------------------------------------
    //MODIFICAR
    @GetMapping("/tablaProyectos/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) throws MiException {
        modelo.put("proyecto", proyectoServicio.getOne(id));
        List<Usuario> usuarios = usuarioServicio.listarUsuarios(); //LISTAR USUARIO POR PROYECTO Y ROL. ESTE METODO INCLUYE TODOS LOS USUARIOS.
        modelo.addAttribute("usuarios", usuarios);

        return "modificarProyecto.html";
    }

    @PostMapping("/tablaProyectos/modificar/{id}")
    public String modificar(@RequestParam String idProyecto,
            @RequestParam String nombreProyecto,
            @RequestParam String descripcion,
            @RequestParam String idUsuario,
            @RequestParam(required = false) Date fechaInicio,
            @RequestParam(required = false) Date fechaFin,
            ModelMap modelo) {
        try {
            proyectoServicio.actualizar(idProyecto, nombreProyecto, descripcion, fechaFin,
                    fechaInicio, idUsuario);

            return "redirect:../tablaProyectos";
        } catch (MiException ex) {
            modelo.put("Error", ex.getMessage());
            return "modificarProyecto.html";
        }
        //ESTA DEVOLVIENDO UNA LISTA DE USUARIO Y NO SOLO UN ID.

    }
//MODIFICAR ROL USUARIO
        @GetMapping("/modificarRol/{id}")
        public String modificarRol
        (ModelMap modelo, 
        @PathVariable String id
        
            ) {
        Usuario usuario = usuarioServicio.getOne(id);
            modelo.put("usuario", usuario);
            return "editar_usuario.html";
        }

        @PostMapping("/modificarRol/{id}")
        public String modificarRol
        (@RequestParam
        String id, String rol
        , ModelMap modelo) throws MiException {

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
        //FALTA VISTA EN FRONT

    }
