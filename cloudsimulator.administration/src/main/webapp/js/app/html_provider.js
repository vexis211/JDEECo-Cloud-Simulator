var HtmlProvider = function(HtmlProvider) {

	HtmlProvider.getUserPanel = function() {
		return document.getElementById('user-panel');
	};
	
	return HtmlProvider;
}(HtmlProvider || {});