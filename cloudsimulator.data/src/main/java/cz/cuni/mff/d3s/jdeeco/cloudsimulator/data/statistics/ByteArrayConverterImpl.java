package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.statistics;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ByteArrayConverterImpl<T> implements ByteArrayConverter<T> {

	private final Class<T> clazz;

	public ByteArrayConverterImpl(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public byte[] convertVector(T[] valuesVector) {
		return convert(valuesVector);
	}

	@Override
	public byte[] convertScalar(T value) {
		return convert(value);
	}

	private byte[] convert(Object obj) {
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
			objectOutputStream.writeObject(obj);
			return byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			throw new ConversionException(
					String.format("Cannot convert %s of type %s to byte array!", obj, clazz.getName()), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] convertBackVector(byte[] bytes) {
		return (T[]) convertBack(bytes);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T convertBackScalar(byte[] bytes) {
		return (T) convertBack(bytes);
	}

	public Object convertBack(byte[] bytes) {
		try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
				ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
			return objectInputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new ConversionException(
					String.format("Cannot read object from bytes. Converter for type: %s!", clazz.getName()), e);
		}
	}
}
