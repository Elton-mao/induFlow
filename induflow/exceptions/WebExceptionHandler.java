package com.compoldata.induflow.exceptions;

import java.util.Map;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class WebExceptionHandler {
    
    @ExceptionHandler(ModelNotfoundException.class)
    public ModelAndView handlerModelNotFoundExeption(ModelNotfoundException e){
        var model = Map.of(
            "message", e.getMessage(),
            "status", 404
          
        );
        return new ModelAndView("danger",model);
     
    }
}
