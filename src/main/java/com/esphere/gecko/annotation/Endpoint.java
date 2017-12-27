package com.esphere.gecko.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.esphere.gecko.constant.RequestMethod;

@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Endpoint {

	public abstract String value();

	public abstract RequestMethod method() default RequestMethod.GET;

	public abstract String[] headers() default "";

	public abstract String consumes() default "";

	public abstract String produces() default "";

}
