package es.iespuertodelacruz.mp.canarytrails.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepcion personalizada que devuelve un err 400 bad request
 * Se usa para verificar que el correo cumple la expresión regular
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidMailException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public InvalidMailException(String message){
        super(message);
    }

}
