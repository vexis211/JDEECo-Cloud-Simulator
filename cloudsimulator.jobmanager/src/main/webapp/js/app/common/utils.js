var Utils = function(Utils) {

	Utils.html_unescape = function(text) {
		return text.replace(/&amp;/g, '&')
				.replace(/&lt;/g, '<')
				.replace(/&gt;/g, '>')
				.replace(/&quot;/g, '"');
	};

	Utils.post_json = function(url, data, settings) {
		settings = _.extend({
			data : JSON.stringify(data),
			contentType : 'application/json',
			type : 'POST',
			dataType : 'json'
		}, settings);

		return $.ajax(url, settings);
	};

	/**
	 * Calculates an object with the x and y coordinates of the given object
	 *
	 * @param the object of which the coordinates to be calculated
	 * @return an object with x and y coordinates
	 */
	Utils.get_object_position = function(object) {
		var left = 0;
		var top = 0;

		while (object.offsetParent) {
			left += object.offsetLeft;
			top += object.offsetTop;

			object = object.offsetParent;
		}

		left += object.offsetLeft;
		top += object.offsetTop;

		return {
			left: left,
			top: top
		};
	};


	function s4() {
		return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
	};

	Utils.guid = function() {
		return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4();
	};


	Utils.isArrayEmpty = function(arr) {
		return typeof (arr) === 'undefined' || arr == null || arr.length == 0;
	};

	Utils.isStringEmptyOrWhitespace = function(value) {
		return typeof (value) === 'undefined' || value == null || value == '' || value.match(/^\s*$/);
	};

	Utils.isStringEmpty = function(value) {
		return typeof (value) === 'undefined' || value == null || value == '';
	};

	Utils.isNullOrUndef = function(value) {
		return typeof (value) === 'undefined' || value == null;
	};

	Utils.inArray = function(key, array) {
		var is = false;
		$(array).each(function() {
			is |= this == key;
		});
		return is;
	};

	return Utils;
}(Utils || {});