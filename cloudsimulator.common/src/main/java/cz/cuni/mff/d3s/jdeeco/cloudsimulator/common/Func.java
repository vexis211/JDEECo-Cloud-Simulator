package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common;

@FunctionalInterface
public interface Func<TResult, TArg1, TArg2> {
	TResult invoke(TArg1 arg1, TArg2 arg2);
}
