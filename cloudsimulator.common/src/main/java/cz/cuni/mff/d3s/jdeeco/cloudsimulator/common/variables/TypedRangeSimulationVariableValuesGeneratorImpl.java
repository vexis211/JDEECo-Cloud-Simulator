package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.variables;

import java.util.ArrayList;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.Func;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.Pred;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.converters.TypeConverter;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.RangeVariableSetting;

public class TypedRangeSimulationVariableValuesGeneratorImpl<TValue>
		implements TypedRangeSimulationVariableValuesGenerator<TValue> {
	
	private final TypeConverter typeConverter;
	private final Class<TValue> valueClass;
	private final Pred<TValue, TValue> isLessOrEqualPred;
	private final Func<TValue, TValue, TValue> sumFunc;

	public TypedRangeSimulationVariableValuesGeneratorImpl(TypeConverter typeConverter, Class<TValue> valueClass,
			Pred<TValue, TValue> isLessOrEqualPred, Func<TValue, TValue, TValue> sumFunc) {
		this.typeConverter = typeConverter;
		this.valueClass = valueClass;
		this.isLessOrEqualPred = isLessOrEqualPred;
		this.sumFunc = sumFunc;
	}
	
	@Override
	public List<TValue> generate(RangeVariableSetting data) {
		TValue from = typeConverter.convert(data.getFrom(), valueClass);
		TValue to = typeConverter.convert(data.getTo(), valueClass);
		TValue step = typeConverter.convert(data.getStep(), valueClass);
		
		List<TValue> values = new ArrayList<TValue>();
		
		for (TValue i = from; isLessOrEqualPred.test(i, to); i = sumFunc.invoke(i, step)) {
			values.add(i);
		}
		
		return values;
	}

	@Override
	public Class<TValue> getValueType() {
		return valueClass;
	}
}
