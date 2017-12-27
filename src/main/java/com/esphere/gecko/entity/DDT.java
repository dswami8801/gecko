package com.esphere.gecko.entity;

import java.util.function.Function;
import java.util.function.Predicate;

public interface DDT<T> {

	public String getId();

	public DDT<T> clone();

	public Integer count();
	
	public DDT<T> union(DDT<T> another);
	
	public DDT<T> intersect(DDT<T> another);
	
	public DDT<T> distinct(DDT<T> another);
	
	public DDT<T> filter(Predicate<T> predicate);
	
	public DDT<T> map(Function<T, T> function);

}
