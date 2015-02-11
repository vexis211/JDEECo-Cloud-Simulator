package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos;

import java.util.List;

public interface BaseDao<T> {

	void saveOrUpdate(T item);
	void merge(T item);

	void delete(T item);

	T findById(int id);
	
	T findLastAdded();

	List<T> findAll();
}
