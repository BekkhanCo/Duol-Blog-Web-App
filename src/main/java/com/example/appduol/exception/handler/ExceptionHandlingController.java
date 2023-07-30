package com.example.appduol.exception.handler;

import com.example.appduol.exception.DataNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlingController {

  @ExceptionHandler(DataNotFoundException.class)
  public ModelAndView handleDataNotFoundException(HttpServletRequest req, DataNotFoundException e) {
    ModelAndView mav = new ModelAndView("error");
    mav.addObject("error", e.getMessage());
    mav.addObject("url", req.getRequestURL());
    mav.addObject("status", HttpStatus.NOT_FOUND);
    return mav;
  }
}
