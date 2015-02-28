package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common;

public class Ref<T> {

    private T value;

    public Ref(T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
    	this.value = value;
    }

	@Override
	public String toString() {
		return "Ref [value=" + value + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Ref<T> other = (Ref<T>) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
