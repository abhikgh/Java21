package com.src.hackerRank;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.lang.reflect.*;
import java.util.Objects;

class Singleton {

	public static String str;

	private static volatile Singleton singletonEx = null;

	// prevent Object creation by Constructor
	private Singleton() {
		// do nothing
	}

	// prevent Object creation by Cloning
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	// get Object instance by Double-check locking
	public static Singleton getSingleInstance() {
		if (Objects.isNull(singletonEx)) {
			synchronized (Singleton.class) {
				if (Objects.isNull(singletonEx)) {
					singletonEx = new Singleton();
				}
			}
		}
		return singletonEx;
	}

}