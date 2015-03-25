(function (namespace) {
	var UserPanelWidget = function() {
		var _self = this;
		var _$dropdown = null;
		var _hidden = true;

		this.show = function() {
			/*$(_self.getContainer()).toggleClass('with-details');*/
			_$dropdown.stop(true, true).slideDown();

			$('body').on('click.user_panel', _self.hide_callback);

			_hidden = false;
		};

		this.hide = function() {
			/*$(_self.getContainer()).toggleClass('with-details');*/
			_$dropdown.stop(true, true).slideUp('fast');

			$('body').off('.user_panel');

			_hidden = true;
		};
		
		this.init = function () {
			
			// Prepare internal widget api.
			var $widgetElement = $(_self.getContainer());
			_$dropdown = $widgetElement.find('.panel-dropdown');

			// hide with jquery
			_$dropdown.hide().prop('hidden', false);
			_hidden = true;

			// Dropdown button.
			$widgetElement.find('.panel-header').on('click.user_panel', function(ev) {
				ev.stopPropagation();

				_self.toggle();
			});

			// don't propagate because of body.onclick
			_$dropdown.on('click', function(ev) {
				ev.stopPropagation();
			});
		};
		
		/**
		 * Disconnects events.
		 */
		this.destroy = function () {
			_self.disconnectAllEvents();
		};


		// body.onclick event handler to hide opened details 
		this.hide_callback = function(ev) {
			_self.hide();
		};
		
		this.toggle = function() {
			if (_hidden) {
				_self.show();
			} else {
				_self.hide();
			}
		};
	};

	var widgetsNs = getWidgetsNs();
	UserPanelWidget.prototype = new widgetsNs.Widget();
	namespace.UserPanelWidget = UserPanelWidget;
}(getMainWidgetsNs()));
