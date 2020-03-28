package hu.sule.administration.exceptions;

import hu.sule.administration.xsdGenerator.XMLIsNotValidException;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    public ResponseEntity<CustomErrorResponse> customExceptionHandle(Exception e, HttpServletRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),"Exception", e.getMessage(),request.getRequestURL().toString());
        logger.error("Exception: " + errors.toString());
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({UtilException.class})
    public ResponseEntity<CustomErrorResponse> customExceptionHandle(UtilException e, HttpServletRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),"UtilException", e.getSubType(),e.getLocation(),e.getMessage(),request.getRequestURL().toString());
        logger.error("UtilException: " + errors.toString());
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({IOException.class})
    public ResponseEntity<CustomErrorResponse> customIOExceptionHandle(IOException e, HttpServletRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),"IOException", e.getMessage(),request.getRequestURL().toString());
        logger.error("IOException: " + errors.toString());
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({XMLDBException.class})
    public ResponseEntity<CustomErrorResponse> customXMLDBExceptionHandle(XMLDBException e, HttpServletRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),"XMLDBException", e.getMessage(), request.getRequestURL().toString());
        logger.error("XMLDBException: " + errors.toString());
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({JDOMException.class})
    public ResponseEntity<CustomErrorResponse> customJDOMExceptionHandle(JDOMException e, HttpServletRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),"JDOMException", e.getMessage(), request.getRequestURL().toString());
        logger.error("JDOMException: " + errors.toString());
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({SAXException.class})
    public ResponseEntity<CustomErrorResponse> customSAXExceptionHandle(SAXException e, HttpServletRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),"SAXException", e.getMessage(), request.getRequestURL().toString());
        logger.error("SAXException: " + errors.toString());
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({XMLIsNotValidException.class})
    public ResponseEntity<CustomErrorResponse> customXMLIsNotValidExceptionHandle(XMLIsNotValidException e, HttpServletRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),"XMLIsNotValidException", e.getMessage(), request.getRequestURL().toString());
        logger.error("XMLIsNotValidException: " + errors.toString());
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }






}
