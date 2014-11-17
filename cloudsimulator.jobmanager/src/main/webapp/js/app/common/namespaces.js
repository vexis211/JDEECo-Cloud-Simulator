function getOrCreateSubNs(parentNs, wantedNsName) {
	var splittedNsName = wantedNsName.split('.');
	for (var index in splittedNsName) {
		var currentSubNsName = splittedNsName[index];
		if (typeof(parentNs[currentSubNsName]) === 'undefined') {
			parentNs[currentSubNsName] = {};
		}
		parentNs = parentNs[currentSubNsName];
	}
	return parentNs;
}

function getOrCreateRootNs(wantedNsName) {
	if (typeof(window[wantedNsName]) === 'undefined') {
		window[wantedNsName] = {};
	}
	return window[wantedNsName];
}

function getAppNs() {
	return getOrCreateRootNs('jdeecosimulator');
}

function getEventsNs() {
	return getOrCreateSubNs(getAppNs(), 'events');
}

function getConfigNs() {
	return getOrCreateSubNs(getAppNs(), 'config');
}

function getWidgetsNs() {
	return getOrCreateSubNs(getAppNs(), 'widgets');
}