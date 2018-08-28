package com.ogel.common.error;

import com.ogel.common.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

public class ErrorControllerAdviceBase {

    private static final String MISSING_URI_VARIABLE = "Missing URI Variable";
    private final static Logger LOGGER = LoggerFactory.getLogger(ErrorControllerAdviceBase.class);

    /*
        Authorization is not yet implemented but when it would be this method will handle errors. I assume each API should
        have some protection.
    */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ResponseBody
    public AuthorizationFailed handle(AuthorizationException exception) {
        LOGGER.error("Authorization error", exception);
        return new AuthorizationFailed(exception.getMessage());
    }

    /*
        Authentication is not yet implemented but when it would be this method will handle errors. I assume each API should
        have some protection.
    */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public AuthenticationFailed handle(AuthenticationFailed exception) {
        LOGGER.error("Authentication error", exception);
        return new AuthenticationFailed(exception.getMessage());
    }

    /*
        This method handle error when `ResourceNotFoundException` is thrown, for now there is no such a case but is is very
        common issue and I'm sure it will be needed in next iterations of coding
    */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResourceNotFound handle(ResourceNotFoundException exception) {
        LOGGER.warn("Resource not found", exception);
        return new ResourceNotFound(exception.getMessage());
    }

    @ExceptionHandler({ValidationFailedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationFailed handle(ValidationFailedException exception) {
        LOGGER.warn("Validation failed");
        ValidationFailed error = new ValidationFailed(exception.getMessage());
        exception.getErrors().forEach(error::addError);
        return error;
    }

    /*
       All custom exceptions in application should extend `ApiException`, thanks to that they will be caught here
       and some logs will be stored. Of course it will not protect against http request ended with 500 error.
     */
    @ExceptionHandler({ApiException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handle(ApiException exception) {
        LOGGER.error("ApiException was thrown. Code = '{}', message = '{}'", exception.getCode(), exception.getMessage());
    }

    /*
        Handlers for internal Spring exceptions to make response has custom body:

            - MethodArgumentNotValidException
            - ConstraintViolationException
            - MissingPathVariableException
            - MissingServletRequestParameterException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationFailed handle(MethodArgumentNotValidException exception) {
        LOGGER.warn("Validation error", exception);
        ValidationFailed error = new ValidationFailed("Method argument validation failed");
        exception.getBindingResult().getFieldErrors()
                .forEach(e -> error.addError(new RestValidationError(e.getField(), e.getDefaultMessage())));
        return error;
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationFailed handle(ConstraintViolationException exception) {
        LOGGER.warn("Validation error", exception);
        ValidationFailed error = new ValidationFailed("Request parameters constraints violation");
        exception.getConstraintViolations()
                .forEach(e -> error.addError(new RestValidationError(e.getPropertyPath().toString(), e.getMessage())));
        return error;
    }

    @ExceptionHandler(value = {MissingPathVariableException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ValidationFailed handle(MissingPathVariableException exception) {
        LOGGER.warn("Validation error", exception);
        ValidationFailed error = new ValidationFailed("Path variables validation failed");
        error.addError(new RestValidationError(exception.getVariableName(), MISSING_URI_VARIABLE));
        return error;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationFailed handle(MissingServletRequestParameterException exception) {
        LOGGER.warn("Validation failed");
        ValidationFailed error = new ValidationFailed("Request parameters validation failed");
        error.addError(RestValidationError.of(exception.getParameterName(), exception.getMessage()));
        return error;
    }
}
