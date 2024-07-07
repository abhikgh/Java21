package com.src.stream;

import java.util.Objects;

public class SingletonEx {

	private static volatile SingletonEx singletonEx = null;

	// prevent Object creation by Constructor
	private SingletonEx() {
		//do nothing
	}

	// prevent Object creation by Cloning
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	// get Object instance by Double-check locking
	public static SingletonEx getInstance() {
		if (Objects.isNull(singletonEx)) {
			synchronized (SingletonEx.class) {
				if (Objects.isNull(singletonEx)) {
					singletonEx = new SingletonEx();
				}
			}
		}
		return singletonEx;
	}
}
