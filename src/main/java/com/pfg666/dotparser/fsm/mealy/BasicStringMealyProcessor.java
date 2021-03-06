package com.pfg666.dotparser.fsm.mealy;

import net.automatalib.commons.util.Pair;

/**
 * A basic processor for building Mealy machines whose inputs and outputs 
 * are equal to the i/o strings in graph edges.  
 */
public class BasicStringMealyProcessor extends AbstractMealyProcessor<String, String> {

	@Override
	public Pair<String,String> processMealyInputOutput(String input, String output) {
		return Pair.of(input.trim(), output.trim());
	}

}
