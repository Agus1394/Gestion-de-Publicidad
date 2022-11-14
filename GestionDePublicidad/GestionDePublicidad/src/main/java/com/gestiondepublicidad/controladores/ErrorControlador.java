package com.gestiondepublicidad.controladores;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

public class ErrorControlador implements ErrorController{
    
     @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView renderizarPaginaDeError(HttpServletRequest httpRequest) {  //o renderErrorPage 

        ModelAndView errorPage = new ModelAndView("error");

        String msjError = "";

        int httpErrorCode = getErrorCode(httpRequest);
        
        switch(httpErrorCode){
            case 400:
                msjError="El recurso solicitado es inexistente.";
                break;
            case 401:
                msjError="No se encuentra autorizado.";
                break;
            case 403:
                msjError="Carece de permisos para acceder a los recursos.";
                break;
            case 404:
                msjError= "Recurso no encontrado.";
                break;
            case 500:
                msjError="Problemas internos.";
                break;
        }
        errorPage.addObject("codigo", httpErrorCode);
        errorPage.addObject("error", msjError);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }

    public String getErrorPath() {
        return "/error.html";
    }
    
}
