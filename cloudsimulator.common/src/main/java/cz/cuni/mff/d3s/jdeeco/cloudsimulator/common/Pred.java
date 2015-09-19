package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common;

@FunctionalInterface
public interface Pred<TArg1, TArg2> {
	boolean test(TArg1 arg1, TArg2 arg2);
}
