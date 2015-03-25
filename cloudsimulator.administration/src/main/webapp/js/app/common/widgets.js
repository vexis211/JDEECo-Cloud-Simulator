(function(namespace) {

	function EventManager(wrapper) {
		// Extend app namespace with events
		var grand_central = $({});

		if (typeof (wrapper) === 'undefined') {
			wrapper = this;
		}

		_.extend(wrapper, {
			on : _.bind(grand_central.on, grand_central),
			one : _.bind(grand_central.one, grand_central),
			off : _.bind(grand_central.off, grand_central),
			trigger : _.bind(grand_central.trigger, grand_central)
		});
	}
	namespace.EventManager = EventManager;

	function WidgetManager(eventManager) {
		var _widgets = [];
		var _eventManager = eventManager;

		_registerWidget = function(widget, container) {
			var previousWidget = this.findWidgetWithContainer(container);
			if (previousWidget != null) {
				this.unregisterWidget(previousWidget);
			}
			widget.setEventManager(_eventManager);
			widget.setContainer(container);
			_widgets.push(widget);
			widget.init();
		};
		this.registerWidget = _registerWidget;

		_findWidget = function(widget) {
			for (var index in _widgets) {
				if (_widgets[index] == widget) {
					return index;
				}
			}
			return null;
		};

		_findWidgetWithContainer = function(container) {
			for ( var index in _widgets) {
				if (_widgets[index].getContainer() == container) {
					return _widgets[index];
				}
			}
			return null;
		};
		this.findWidgetWithContainer = _findWidgetWithContainer;

		_unregisterWidget = function(widget) {
			var widgetIndex = _findWidget(widget);
			if (widgetIndex == null) {
				return;
			}
			_widgets.splice(widgetIndex, 1);
			widget.destroy();
		};
		this.unregisterWidget = _unregisterWidget;
	}
	namespace.WidgetManager = WidgetManager;

	function Widget() {
		var _container = null;
		var _eventManager = null;
		var _localEventManager = new EventManager();
		var _connectedEvents = [];

		this.connectEvent = function(event, handler, manager) {
			if (typeof (manager) === 'undefined') {
				manager = _eventManager;
			}
			manager.on(event, handler);
			_connectedEvents.push({
				'event' : event,
				'handler' : handler,
				'manager' : manager
			});
		};

		this.disconnectEvent = function(event, handler, manager) {
			if (typeof (manager) === 'undefined') {
				manager = _eventManager;
			}
			for ( var index in _connectedEvents) {
				var connection = _connectedEvents[index];
				if (connection.event == event && connection.handler == handler && connection.manager == manager) {
					connection.manager.off(connection.event, connection.handler);
					return;
				}
			}
			throw new Exception("Connection does not exists (" + [ event, handler, manager ].join(", ") + ")");
		};

		this.disconnectAllEvents = function() {
			for ( var index in _connectedEvents) {
				var connection = _connectedEvents[index];
				connection.manager.off(connection.event, connection.handler);
			}
		};

		_setContainer = function(container) {
			_container = container;
		};
		this.setContainer = _setContainer;

		_getContainer = function() {
			return _container;
		};
		this.getContainer = _getContainer;

		_getEventManager = function() {
			return _eventManager;
		};
		this.getEventManager = _getEventManager;

		_setEventManager = function(eventManager) {
			_eventManager = eventManager;
		};
		this.setEventManager = _setEventManager;

		_getLocalEventManager = function() {
			return _localEventManager;
		};
		this.getLocalEventManager = _getLocalEventManager;
	}
	namespace.Widget = Widget;

}(getWidgetsNs()));