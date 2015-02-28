package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common;

public class Out<T> {

	private boolean isAssigned;
	private T value;

	public Out() {
	}

	public T get() {
		if (!isAssigned) {
			throw new RuntimeException("Value is not assigned!");
		}
		return this.value;
	}

	public void set(T value) {
		this.isAssigned = true;
		this.value = value;
	}

	@Override
	public String toString() {
		return "Out [isAssigned=" + isAssigned + ", value=" + value + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isAssigned ? 1231 : 1237);
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		Out<T> other = (Out<T>) obj;
		if (isAssigned != other.isAssigned)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
