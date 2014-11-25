Init.call_me_when_ready(function () {
	/*	// Set ajax parameters
	$.ajaxSetup({
		// Check ajax responses for session timeout
		dataFilter: function (data, type) {
	       	if (data.indexOf('login-form-page') != -1) {
	       		// TODO: use variable here
	    		location.href=getAppNs().config.siteRoot + "/client/login";
	    		return false;
	        }
	        return data;
        },
        // Disable cache on AJAX requests
        cache: false
    });

	// Track AJAX requests for busy cursor while waiting for server
	$(document).ajaxSend(function(event, jqXHR, ajaxOptions) {
		if(!ajaxOptions['noprogress']) {
			getAppNs().trigger('u:request:new');
		}
		 Set as action ajax request.
		 * This is identified by backend so it can correctly return 403
		 * when there is authentication problem.
		 
		jqXHR.setRequestHeader("X-AjaxRequest", "1");
	});
	$(document).ajaxComplete(function(event, XMLHttpRequest, ajaxOptions) {
		if(!ajaxOptions['noprogress']) {
			getAppNs().trigger('u:request:completed');
		}
		if (XMLHttpRequest.status == 403) {
			 Request failed due to permission denied reason -> authorization failed.
			 * This can happen when opened in multiple windows and gets logged out in one.
			 * Or when the session expires.
			 
			getAppNs().trigger('u:logout_detected');
		}
	});*/
	
	var appNs = getAppNs();
	var mainNs = getMainNs();
	var widgetsNs = getWidgetsNs();
	var mainWidgetsNs = getMainWidgetsNs();

	// Extend app namespace with events
	new widgetsNs.EventManager(appNs);


	// Autologout widget currently does not support container.
	// It will after dialog design changes.
	// TODO If design wont change rename to AutologoutManager.
	var autologoutWidget = new mainWidgetsNs.AutologoutWidget();
	autologoutWidget.setEventManager(getAppNs());
	autologoutWidget.init();

	mainNs.widgetMgr = new widgetsNs.WidgetManager(appNs);

	// user panel
	mainNs.widgetMgr.registerWidget(new mainWidgetsNs.UserPanelWidget(), HtmlProvider.getUserPanel());
});
