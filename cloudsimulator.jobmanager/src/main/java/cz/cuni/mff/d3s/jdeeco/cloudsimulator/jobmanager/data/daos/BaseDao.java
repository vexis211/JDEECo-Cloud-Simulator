package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos;

import java.util.List;

public interface BaseDao<T> {

	public void saveOrUpdate(T item);

	public void delete(T item);

	public T findById(int id);

	public List<T> findAll();
}
