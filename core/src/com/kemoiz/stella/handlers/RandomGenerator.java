package com.kemoiz.stella.handlers;

public class RandomGenerator {

	public static double getFloat() {

		return Math.random();
	}

	public static double getFloat(int range) {

		return Math.random() * range;
	}

}
