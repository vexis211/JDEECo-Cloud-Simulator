/** @file dialog.js
*
* Dialog is a simple helper that is used to display various general
* warning and error messages in styled fashion.
*
*/

utildialog = {};

/** Display error message
*
* @param template Dialog to be used
* @param title Message title
* @param text Message text
* @param closeCallback Called when dialog is closed. Optional.
*/
utildialog.common = function(template, text, title, closeCallback) {
	// Set dialog title
	if(title != null && typeof title != 'undefined')
		$('#' + template).attr('title', title);

	// Set dialog text
	$('#' + template + ' > .text').html(text);

	// Show dialog
	var dialog = $('#' + template).dialog({
		resizable: false,
		height:'auto',
		width:'auto',
		modal: true,
		dialogClass: template,
		buttons:{
			Close:{
				text:'',
				'class':'close',
				click: function()	{
					$(this).dialog("close");
					if (typeof(closeCallback) === 'function') {
						closeCallback();
					}
				}
			}
		}
	});
};

/** Display error message
*
* @param text Message text
* @param closeCallback Called when dialog is closed. Optional.
* @param title Message title. Optional.
*/
utildialog.error = function(text, closeCallback, title) {
	utildialog.common('utildialog-error-dialog', text, title, closeCallback);
};

/** Display info message
*
* @param text Message text
* @param closeCallback Called when dialog is closed. Optional.
* @param title Message title. Optional.
*/
utildialog.info = function(text, closeCallback, title) {
	utildialog.common('utildialog-info-dialog', text, title, closeCallback);
};

/** Display neutral message
*
* @param text Message text
* @param closeCallback Called when dialog is closed. Optional.
* @param title Message title. Optional.
*/
utildialog.message = function(text, closeCallback, title) {
	utildialog.common('utildialog-message-dialog', text, title, closeCallback);
};
