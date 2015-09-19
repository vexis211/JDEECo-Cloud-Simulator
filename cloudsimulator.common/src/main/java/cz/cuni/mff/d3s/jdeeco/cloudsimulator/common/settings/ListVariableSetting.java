package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("ListVariable")
public class ListVariableSetting extends VariableSetting {
	
	public static final char ITEM_SEPARATOR = ',';
	
	
	@XStreamAlias("List")
	@XStreamAsAttribute
	private final String list;
	
	public ListVariableSetting(String name, String type, String list) {
		super(name, type);
		this.list = list;
	}

	public String getList() {
		return list;
	}
}
