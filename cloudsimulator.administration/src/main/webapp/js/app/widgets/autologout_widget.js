(function (namespace) {
	var AutologoutWidget = function () {
		var _self = this;
		var _logoutInProgress = false;
		
		var _showLogoutDialog = function () {
			utildialog.error('Logout timeout.', function () {
				// Let back end handle non AJAX request for unauthorized users.
				window.location = getConfigNs().siteRoot;
			});
		};
		
		var _logoutDetectedHandler = function() {
			if (_logoutInProgress) {
				// Do not show multiple dialogs.
				return;
			}
			_logoutInProgress = true;
			_showLogoutDialog();
		};
		
		this.init = function () {
			_self.connectEvent('u:logout_detected', _logoutDetectedHandler);
		};
		
		this.destroy = function () {
			_self.disconnectAllEvents();
		};
	};
	
	var widgetsNs = getWidgetsNs();
	AutologoutWidget.prototype = new widgetsNs.Widget();
	namespace.AutologoutWidget = AutologoutWidget;
}(getMainWidgetsNs()));