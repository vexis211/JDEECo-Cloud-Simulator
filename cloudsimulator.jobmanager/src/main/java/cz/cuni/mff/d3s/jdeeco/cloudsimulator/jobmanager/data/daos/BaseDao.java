package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos;

import java.util.List;

public interface BaseDao<T> {

	public void persist(T transientInstance);

	public void delete(T persistentInstance);

	public T findById(int id);

	public List<T> findAll();
}
