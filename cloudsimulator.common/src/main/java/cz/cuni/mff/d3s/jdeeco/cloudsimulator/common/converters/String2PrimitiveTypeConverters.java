package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.converters;

class String2PrimitiveTypeConverters {

	public abstract class String2ConcreteTypeConverter<TTo> implements ConcreteTypeConverter<String, TTo> {

		@Override
		public Class<String> getFromClass() {
			return String.class;
		}
	}

	public class String2BooleanTypeConverter extends String2ConcreteTypeConverter<Boolean> {

		@Override
		public Boolean convert(String value) {
			return Boolean.valueOf(value);
		}
		
		@Override
		public Class<Boolean> getToClass() {
			return Boolean.class;
		}
	}

	public class String2ByteTypeConverter extends String2ConcreteTypeConverter<Byte> {

		@Override
		public Byte convert(String value) {
			return Byte.valueOf(value);
		}

		@Override
		public Class<Byte> getToClass() {
			return Byte.class;
		}
	}

	public class String2ShortTypeConverter extends String2ConcreteTypeConverter<Short> {

		@Override
		public Short convert(String value) {
			return Short.valueOf(value);
		}

		@Override
		public Class<Short> getToClass() {
			return Short.class;
		}
	}

	public class String2IntTypeConverter extends String2ConcreteTypeConverter<Integer> {

		@Override
		public Integer convert(String value) {
			return Integer.valueOf(value);
		}

		@Override
		public Class<Integer> getToClass() {
			return Integer.class;
		}
	}

	public class String2LongTypeConverter extends String2ConcreteTypeConverter<Long> {

		@Override
		public Long convert(String value) {
			return Long.valueOf(value);
		}

		@Override
		public Class<Long> getToClass() {
			return Long.class;
		}
	}

	public class String2FloatTypeConverter extends String2ConcreteTypeConverter<Float> {

		@Override
		public Float convert(String value) {
			return Float.valueOf(value);
		}

		@Override
		public Class<Float> getToClass() {
			return Float.class;
		}
	}

	public class String2DoubleTypeConverter extends String2ConcreteTypeConverter<Double> {

		@Override
		public Double convert(String value) {
			return Double.valueOf(value);
		}

		@Override
		public Class<Double> getToClass() {
			return Double.class;
		}
	}
}