package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

//    @PostMapping("/registrar_usuario/nuevo")
//    public String registrarUsuario(@RequestParam String nombreUsuario,
//            @RequestParam String email,
//            ModelMap archivo) {
//        try {
//            usuarioServicio.registrar((MultipartFile) archivo, nombreUsuario, email, contrasenia, nombreUsuario);
//        } catch (Exception e) {
//        }
//        return null;
//    }

    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "usuario_lista.html";
    }

    @GetMapping("/dashboard")
    public String panelAdministrador() {
        return "panel.html";
    }

    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id) {
        usuarioServicio.cambiarRol(id);
        return "redirect:/admin/usuario_lista.html";
    }

    @GetMapping("/devolver_usuarios")
    public String devolverUsuarioPorNombre(ModelMap modelo, String nombre){
        List <Usuario> usuario = usuarioServicio.usuariosPorNombre(nombre);
        modelo.put("usuario", usuario);
        return "devolver_usuario.html";   
    }
}
