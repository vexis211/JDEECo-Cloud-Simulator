var Init = (function(Init) {
	/**
	 * Calls any function by its reference or global function by its name.
	 *
	 * @param {function|string} func Function or global function name.
	 */
	Init.call_function = function(func) {
		if ($.isFunction(func)) {
			func();
		} else if(window[func] && $.isFunction(window[func])) {
			window[func]();
		}
	};

	/**
	 * Loads JS.
	 *
	 * @param {string} js Javascript file uri.
	 * @param {object} attrs Script element attributes.
	 * @param {Function} callback
	 */
	Init.load_js = function(js, attrs, callback) {
		"use strict";

		var defaults = {
			src: js
		};

		// the jQuery way
		var head = document.head || document.getElementsByTagName("head")[0] || document.documentElement;

		attrs = _.extend(defaults, attrs);

		var script = document.createElement("script");
		_.extend(script, attrs);

		if (callback && _.isFunction(callback)) {
			// Attach handlers for all browsers
			script.onload = script.onreadystatechange = function(_, isAbort) {
				if (isAbort || !script.readyState || /loaded|complete/.test(script.readyState) ) {

					// Handle memory leak in IE
					script.onload = script.onreadystatechange = null;

					$(script).remove();
					if (head && script.parentNode) {
						head.removeChild(script);
					}

					if (!isAbort) {
						callback();
					}
				}
			};
		}

		head.insertBefore(script, head.firstChild);
	};

	/**
	 * Downloads scripts for all elements of array Init.js_async_load.
	 */
	Init.load_js_from_stack  = function() {
		"use strict";

		function async_load(js) {
			var attrs = {
				async: 'true'
			};
			var callback = undefined;

			if (_.isObject(js)) {
				if (js['async'] === false) {
					delete js['async'];
				}

				callback = js['callback'];
				_.extend(attrs, js['attrs']);
				js = js['src'];
			}

			Init.load_js(js, attrs, callback);
		}

		_.each(Init._js_async_stack, async_load);

		// page is ready -> call immediately, don't add to stack
		Init.js_async_load = async_load;
	};

	/**
	 * Loads everything in ready and async stacks (see. Init/init.js).
	 * Has to be run as last script on page.
	 */
	Init.after_we_have_all_the_scripts = function() {
		$.support.cors = true;

		Init.load_js_from_stack();

		_.each(Init._ready_stack, function(fn) {
			Init.call_function(fn);
		});

		// page is ready -> call immediately, don't add to stack
		Init.call_me_when_ready = Init.call_function;
	};

	try {
		if (!String.format) {
			String.format = function(format) {
				var args = Array.prototype.slice.call(arguments, 1);
				return format.replace(/{(\d+)}/g, function(match, number) {
					return typeof args[number] != 'undefined' ? args[number] : match;
				});
			};
		}
	} catch (e) {
	}

	return Init;
	
}(Init || {}));
