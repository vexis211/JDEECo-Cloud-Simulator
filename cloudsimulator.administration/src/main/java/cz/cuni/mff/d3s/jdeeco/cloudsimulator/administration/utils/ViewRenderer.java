package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.utils;

import java.util.Map;

public interface ViewRenderer {

	String render(String viewName, Map<String, Object> params) throws Exception;
}
