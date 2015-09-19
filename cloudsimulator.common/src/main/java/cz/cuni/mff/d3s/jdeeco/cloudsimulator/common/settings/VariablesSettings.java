package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("SimulationVariables")
public class VariablesSettings {

	@XStreamImplicit
	private final ArrayList<VariableSetting> variables;

	public VariablesSettings(List<VariableSetting> variables) {
		this.variables = variables != null ? new ArrayList<VariableSetting>(variables) : null;
	}

	public List<VariableSetting> getProfileImports() {
		return variables;
	}
}
