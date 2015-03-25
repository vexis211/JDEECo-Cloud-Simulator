/**
 * Minimal load interface.
 */
var Init = function(Init) {
	Init._ready_stack = [];
	Init._js_async_stack = [];

	/**
	 * Calls function when every script element is loaded.
	 *
	 * @param {callback} fn Function reference or global function name.
	 */
	Init.call_me_when_ready = function(fn) {
		Init._ready_stack.push(fn);
	},

	/**
	 * Asynchronous load of script when document is ready.
	 *
	 * @param {string} js Script uri.
	 */
	Init.js_async_load = function(js) {
		Init._js_async_stack.push(js);
	};

	return Init;
}(Init || {});
