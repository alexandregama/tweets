package com.twitter.profile;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.LocalDate;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Constraint(validatedBy = PastLocalDate.PastDateValidator.class)
@Retention(RUNTIME)
@Target({ElementType.FIELD})
public @interface PastLocalDate {
	
	String message() default "{javax.validation.constraints.Past.message}";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};

	class PastDateValidator implements ConstraintValidator<PastLocalDate, LocalDate> {

		@Override
		public void initialize(PastLocalDate constraintAnnotation) {
		}

		@Override
		public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
			boolean isValid = value != null && value.isBefore(LocalDate.now());
			return isValid;
		}
	}
	
}
