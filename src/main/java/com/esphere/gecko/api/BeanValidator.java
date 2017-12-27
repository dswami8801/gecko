package com.esphere.gecko.api;

import com.esphere.gecko.entity.BeanNotValidException;

public interface BeanValidator {
	
	public void doValidate() throws BeanNotValidException;

}
