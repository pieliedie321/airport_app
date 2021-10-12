package com.app.airport.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.request.WebRequest;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Component
public class ExtendedErrorAttributes extends DefaultErrorAttributes {

  private static final String CAUSE_ERROR_FIELD_NAME = "cause";
  private static final Class[] SUPPORTED_EXCEPTIONS = {
    PersistenceException.class,
    ConstraintViolationException.class,
    IllegalArgumentException.class,
    EntityNotFoundException.class
  };

  @Override
  public Map<String, Object> getErrorAttributes(
      WebRequest webRequest, ErrorAttributeOptions options) {
    final Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
    final Throwable error = super.getError(webRequest);
    Optional<Class> supportException = findSupportException(error);
    String errorMessage = setErrorMessage(error, supportException);
    errorAttributes.put(CAUSE_ERROR_FIELD_NAME, errorMessage);
    return errorAttributes;
  }

  private Optional<Class> findSupportException(Throwable error) {
    return Arrays.stream(SUPPORTED_EXCEPTIONS)
        .filter(cl -> ClassUtils.isAssignableValue(cl, error))
        .findFirst();
  }

  private String setErrorMessage(Throwable error, Optional<Class> supportException) {
    return supportException.isPresent() && nonNull(error.getMessage())
        ? error.getMessage()
        : "No message available";
  }
}
