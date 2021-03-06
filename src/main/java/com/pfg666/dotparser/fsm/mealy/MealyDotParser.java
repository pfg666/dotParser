package com.pfg666.dotparser.fsm.mealy;

import java.util.List;
import java.util.Map;

import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;
import com.pfg666.dotparser.fsm.FSMDotParser;

import net.automatalib.automata.transducers.impl.FastMealy;
import net.automatalib.automata.transducers.impl.FastMealyState;
import net.automatalib.automata.transducers.impl.MealyTransition;
import net.automatalib.commons.util.Pair;
import net.automatalib.words.Alphabet;
import net.automatalib.words.impl.SimpleAlphabet;

public class MealyDotParser<I,O> extends FSMDotParser<MealyProcessor<I,O>, FastMealyState<O>, I, MealyTransition<FastMealyState<O>,O>, FastMealy<I,O>>{
	

	public MealyDotParser(MealyProcessor<I,O> processor) {
		super(processor);
	}
	
	protected FastMealy<I, O> processGraph(Graph g) {
		Alphabet<I> inputAlphabet = new SimpleAlphabet<I>();
		FastMealy<I,O> muttable = new FastMealy<I,O>(inputAlphabet);
		Map<Node, FastMealyState<O>> nodeToState = super.addStates(g, muttable);
		List<Edge> edges = g.getEdges();
		
		for (Edge edge : edges) {
			Pair<I,O> ioPair = getProcessor().processInputOutput(edge);
			if (ioPair != null) {
				I input = ioPair.getFirst();
				O output = ioPair.getSecond();
				if (!muttable.getInputAlphabet().contains(input)) {
					muttable.addAlphabetSymbol(input);
				}
				FastMealyState<O> sourceState = nodeToState.get(edge.getSource().getNode());
				FastMealyState<O> targetState = nodeToState.get(edge.getTarget().getNode()); 
				MealyTransition<FastMealyState<O>, O> transition = new MealyTransition<FastMealyState<O>,O>(targetState, output);
				muttable.setTransition(sourceState, input, transition);
			}
		}

		return muttable;
	}
}
