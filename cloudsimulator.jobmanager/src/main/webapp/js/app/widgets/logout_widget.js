(function (namespace) {
	function LogoutWidget() {
		var _self = this;
		
		var _isLogout = false;
		
		this._logoutUser = function(event, data) {
			_isLogout = true;
			window.location.href = getConfigNs().siteRoot + "/client/logout";
		};
					
		/** Initialize settings widget */
		this.init = function () {
			// Connect events
			_self.connectEvent('u:user_panel_logout', _self._logoutUser);
		};
		
		this.destroy = function () {
		
		};
	}
	LogoutWidget.prototype = new getWidgetsNs().Widget();
	namespace.LogoutWidget = LogoutWidget;
}(getMainWidgetsNs()));
