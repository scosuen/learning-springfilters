package com.nobodyknows.learning_springfilters;

import java.util.UUID;

public class GUIDUtils {
	
	public static String generateNewGUID() {
		return UUID.randomUUID().toString();
	}

	public static void main(String[] args) {
		System.out.println(generateNewGUID());
	}
}
