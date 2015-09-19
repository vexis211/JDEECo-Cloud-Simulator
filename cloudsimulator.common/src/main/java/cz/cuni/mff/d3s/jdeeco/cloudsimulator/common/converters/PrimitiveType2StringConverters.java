package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.converters;

class PrimitiveType2StringConverters {

	public abstract class ConcreteType2StringConverter<TFrom> implements ConcreteTypeConverter<TFrom, String> {

		@Override
		public Class<String> getToClass() {
			return String.class;
		}
	}

	public class Boolean2StringTypeConverter extends ConcreteType2StringConverter<Boolean> {

		@Override
		public String convert(Boolean value) {
			return String.valueOf(value);
		}

		@Override
		public Class<Boolean> getFromClass() {
			return Boolean.class;
		}
	}

	public class Byte2StringTypeConverter extends ConcreteType2StringConverter<Byte> {

		@Override
		public String convert(Byte value) {
			return String.valueOf(value);
		}

		@Override
		public Class<Byte> getFromClass() {
			return Byte.class;
		}
	}

	public class Short2StringTypeConverter extends ConcreteType2StringConverter<Short> {

		@Override
		public String convert(Short value) {
			return String.valueOf(value);
		}

		@Override
		public Class<Short> getFromClass() {
			return Short.class;
		}
	}

	public class Int2StringTypeConverter extends ConcreteType2StringConverter<Integer> {

		@Override
		public String convert(Integer value) {
			return String.valueOf(value);
		}

		@Override
		public Class<Integer> getFromClass() {
			return Integer.class;
		}
	}

	public class Long2StringTypeConverter extends ConcreteType2StringConverter<Long> {

		@Override
		public String convert(Long value) {
			return String.valueOf(value);
		}

		@Override
		public Class<Long> getFromClass() {
			return Long.class;
		}
	}

	public class Float2StringTypeConverter extends ConcreteType2StringConverter<Float> {

		@Override
		public String convert(Float value) {
			return String.valueOf(value);
		}

		@Override
		public Class<Float> getFromClass() {
			return Float.class;
		}
	}

	public class Double2StringTypeConverter extends ConcreteType2StringConverter<Double> {

		@Override
		public String convert(Double value) {
			return String.valueOf(value);
		}

		@Override
		public Class<Double> getFromClass() {
			return Double.class;
		}
	}
}