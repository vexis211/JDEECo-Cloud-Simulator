package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.variables;

import java.util.List;

public interface SimulationVariableSettingVisitor {

	<T> void visit(String name, Class<T> valueClass, List<T> values);
}
