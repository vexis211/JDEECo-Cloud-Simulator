package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.converters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeConverterImpl implements TypeConverter {

	private final Map<Class<?>, Entry> from2Convertor = new HashMap<>();

	public TypeConverterImpl(List<ConcreteTypeConverter<?, ?>> concreteTypeConvertors) {

		for (ConcreteTypeConverter<?, ?> converter : concreteTypeConvertors) {
			Class<?> fromClass = converter.getFromClass();
			if (this.from2Convertor.containsKey(fromClass)) {
				this.from2Convertor.get(fromClass).addConverter(converter);
			} else {
				Entry entry = new Entry();
				this.from2Convertor.put(fromClass, entry);
				entry.addConverter(converter);
			}
		}
	}

	@Override
	public <TFrom, TTo> TTo convert(TFrom value, Class<TTo> toClass) {
		@SuppressWarnings("unchecked")
		ConcreteTypeConverter<TFrom, TTo> converter = (ConcreteTypeConverter<TFrom, TTo>) from2Convertor
				.get(value.getClass()).get(toClass);

		TTo result = converter.convert(value);
		return result;
	}

	private class Entry {
		private Map<Class<?>, ConcreteTypeConverter<?, ?>> to2Converter = new HashMap<>();

		void addConverter(ConcreteTypeConverter<?, ?> converter) {
			if (!to2Converter.containsKey(converter.getToClass())) {
				to2Converter.put(converter.getToClass(), converter);
			} else {
				throw new RuntimeException(
						String.format("Two convertors for same types. From: '%s', To: '%s', Convertor Type: '%s'.",
								converter.getFromClass(), converter.getToClass(), converter.getClass()));
			}
		}

		ConcreteTypeConverter<?, ?> get(Class<?> toClass) {
			return to2Converter.get(toClass);
		}
	}
}
