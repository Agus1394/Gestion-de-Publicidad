package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.repositorios.UsuarioRepositorio;
import com.gestiondepublicidad.servicios.ProyectoServicio;
import com.gestiondepublicidad.servicios.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    ProyectoServicio proyectoServicio;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    //CREAR
    @GetMapping("/registro")
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registrar")
    public String registro(@RequestParam String nombre,
            @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, ModelMap modelo) {
        try {
            usuarioServicio.registrar(nombre, email, password, password2);
            modelo.put("exito", "Usuario registrado correctamente!");
            return "index.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registro.html";
        }
    }

    //LOGIN
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o contraseña invalidos");
        }
        return "login.html";
    }

    @Secured({"ROLE_USER","ROLE_ADMIN","ROLE_TRABAJADOR"})
    @GetMapping("/inicio")
    public String inicio(ModelMap modelo, HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        Usuario usuario = usuarioServicio.usuariosPorEmail(logueado.getEmail());
        if(usuario.getRol().toString().equals("ADMIN")) {
            return "dashboard.html";
        } else if (usuario.getRol().toString().equals("USER")) {
            modelo.addAttribute("proyectos", proyectoServicio.buscarPorUsuario(usuario.getId_usuario()));
            return "indexCliente.html";
        } else if (usuario.getRol().toString().equals("TRABAJADOR")){
            modelo.addAttribute("proyectos", proyectoServicio.buscarPorUsuario(usuario.getId_usuario()));
            return "indexTrabajador.html";
        }
            return "index.html";
    }

    @Secured({"ROLE_USER","ROLE_ADMIN","ROLE_TRABAJADOR"})
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        Usuario usuario1 = usuarioServicio.usuariosPorEmail(usuario.getEmail());
        modelo.put("usuario", usuario1);
        return "perfil.html";
    }

    //ACTUALIZAR
    @Secured({"ROLE_USER","ROLE_ADMIN","ROLE_TRABAJADOR"})
    @PostMapping("/perfil/{id}")
    public String actualizar(@RequestParam MultipartFile archivo, @PathVariable String id,
            @RequestParam String nombre, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2,
            ModelMap modelo) throws MiException {

        try {
            usuarioServicio.actualizar(archivo, id, nombre, email, password, password2);
            modelo.put("exito", "Usuario actualizado correctamente!");
            return "index.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "perfil.html";
        }

    }

}
