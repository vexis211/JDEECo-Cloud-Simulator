/* Documented 30.1.2014 by Vladimír Matěna (vlada.matena@gmail.com) */
(function (namespace) {
	//require: app, jquery, app.main
	function SettingsWidget() {
		var _self = this;
		
		// INFO: Change this to alter package offer
		// Plans(durations) has to be configured in DB
		// Changing this without proper templates in DB do NOT let you buy anything new!
		// Sizes are in MB
		this._offerSizeMiddle = 50000;
		this._offerSizeMaxi = 100000;
		
		/// Package templates data (used to provide extra information on packages)
		this._packTemplates = [];
						
		/** Display the settings dialog width settings tab 
		 * 
		 * @param event Ignored
		 * @param data Ignored 
		 */
		this._displaySettings = function(event, data) {
			_self._displaySettingsDialog('settings');
		};
				
		/** Get package template by Id
		 * 
		 *  @param id package template Id
		 */
		var getPackTemplate = function(id) {
			return $(_self._packTemplates).filter(function(index) {
				return _self._packTemplates[index].id == id;
			})[0];
		};
		
		/** Perform package buy action
		 * 
		 * @param templateId ID of the template for new package
		 */
		var buyPack = function(id) {
			// Set busy cursor, to inform user that something is happening
			$('body').addClass('busy');
			
			// google analytics
			var packageSize = getPackTemplate(id).size;
			_gaq.push(['_trackEvent', 'Storage', 'Pack ordered', packageSize.toString(), packageSize]);
			// Redirect to payment gateway with template id to buy as parameter
			window.location.href = getAppNs().config.siteRoot + '/payment/buypack?templateId=' + id;
		};
		
		/** Update billing information
		 * 
		 * Get new billing information from the server and fill it in the UI.
		 * 
		 */
		var updateBilling = function() {
			var url = getAppNs().config.siteRoot + '/client/get-billing';
			$.ajax({
				url: url,
				headers: { "Accept": "application/json" }
			}).done(function(billing) {
				// Fill the input fields with downloaded data
				$('#settings-billing-name').val(billing.name);
				$('#settings-billing-street').val(billing.street);
				$('#settings-billing-city').val(billing.city);
				$('#settings-billing-zip').val(billing.zip);
			});
		};
		
		/** Format package size
		 * 
		 * Get human readable package size according to specification.
		 * 
		 * @param size Size in megabytes
		 * @return String describing the size
		 * 
		 */
		var formatSize = function(size) {
			var units = ['MB', 'GB', 'TB'];
			var div = 1;
			var unit = 0;
			
			while(size / div >= 1024 && unit < units.length - 1) {
				div *= 1000;
				unit++;
			}
			
			return Math.round(size / div) + units[unit];
		};
		
		/** Format package duration
		 * 
		 * Get human readable package duration according to specification.
		 * 
		 * @param days Package duration in days
		 * @return String describing package duration
		 */
		var formatDuration = function(days) {
			if(days < 30)
				return days + getWordForCount('day', days);
			var months = days / 30;
			if(months < 12)
				return Math.round(months) + getWordForCount('month', months);
			var years = months / 12;
			return Math.round(years) + getWordForCount('year', years);
		};
		
		/** Format date
		 * 
		 * Get human readable text describing the given date.
		 * 
		 * @param time Time-stamp to be formated
		 * @return String describing date
		 */
		var formatDate = function(time) {
			var date = new Date(time);
			return date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear();
		};
		
		/** Format price
		 * 
		 * Format price in cents to string without Euro symbol
		 * 
		 * @param price Price in Eurocents
		 * @return String describing the price as human readable value
		 */
		var formatPrice = function(price) {
			var euros = Math.floor(price / 100);
			var cents = price % 100;
			return euros + '.' + cents;
		};
		
		/** Update payments
		 * 
		 * This actually fetches purchased packs data.
		 * The user do not want to see actual payments, but the packages he has.
		 *
		 * Gets JSON data from server and update payment table.
		 */
		var updatePayments = function() {
			// Fetch the JSON data from server
			$.ajax({
				url: getAppNs().config.siteRoot + '/payment/listpacks',
				headers: { "Accept": "application/json" }
			}).done(function(result) {
				// Sort payments by ID in order to keep current payment up in the list
				result.packs = result.packs.sort(function(a, b) { return b.id - a.id; });
				
				// Add human values
				$(result.packs).each(function() {
					// Import data from pack template
					var pack = this;
					$(_self._packTemplates).each(function() {
						if(this.id == pack.storagePackTemplateId) {
							pack.size = this.size;
							pack.duration = this.duration;
						}
					});
					
					// Generate human readable values to be sued by Mustache
					this.humanSize = formatSize(this.size);
					this.humanDuration = formatDuration(this.duration);
					this.humanOrdered = formatDate(this.purchaseDate);
					this.humanExpiration = formatDate(this.expirationDate);
				});
				
				// Generate pack listing
				var template = $('#settings-userpayment-template').html();
				$('.user-payments > tbody', _self.getContainer()).html(Mustache.to_html(template, result));
				
				// Hide/show payment tab
				var $tab = $('ul.settings-tabs-panel > li.payments', _self.getContainer());
				if(result.packs.length > 0)
					$tab.show();
				else
					$tab.hide();
			});
		};
		
		/** Display the settings dialog
		 * 
		 *  @param tab Either 'settings' or 'storage', select initial tab'
		 * 
		 */
		this._displaySettingsDialog = function(tab) {
			// Display the dialog
			$(_self.getContainer()).dialog({
				resizable: false,
				// Fixed width and height to prevent dialog from resizing on tab change
				width: 1024,
				height: 550,
				modal: true,
				dialogClass: "settings-dialog",
				create: function(event, data) {
					// Activate settings tabs
					$(this).find("#settings-tabs").tabs();
					
					// Activate save button
					$(this).find("a.options-save-button").button().click(function(event) {
						_self._optionsSave();
					});
					
					// Activate package buttons
					$(this).find(".settings-packages-pane > div > a").button().click(function(event) {
						// Hide all offers
						$('.packages-offer').hide();
						
						// Show the selected offer
						var space = $(this).closest('div').attr('data-pack');
						$('.packages-offer[data-pack=' + space + ']').show();
					});
					
					// Allow package select by clicking on its image (redirect click to button)
					$(this).find(".settings-packages-pane > div > i").click(function(event) {
						$(this).siblings('a').click();
					});
					
					// Add scroll for payment listing
					$('.user-payments-wrapper', _self.getContainer()).jScrollPane({autoReinitialise:true}).data('jsp');
				},
				open: function(event, data) {
					// Select tab
					if(tab == 'settings')
						$(this).find("#settings-tabs").tabs({ active: 2 });
					else {
						$(this).find("#settings-tabs").tabs({ active: 0 });
						// google analytics
						_gaq.push(['_trackEvent', 'Storage', 'Dialog opened']);
					}
					
					// Erase input fields
					$(_self.getContainer()).find('#settings-tabs-options input').val("");
					
					// Prefill account details input fields
					updateBilling();
					
					// Load package templates
					updatePackTemplates();
					
					// Hide the error
					$("#settings-options-error").css('visibility', 'hidden');
				}
			});
		};
		
		/** Validate options values and perform save action */
		this._optionsSave = function() {
			// Save user info (both billing and password)
			// Ignore blank fields, backend will handle it (we do not know what are the user intentions)
			
			var billingName = $('#settings-billing-name').val();
			var billingStreet = $('#settings-billing-street').val();
			var billingCity = $('#settings-billing-city').val();
			var billingZip = $('#settings-billing-zip').val();
			
			var oldPass = $("#settings-password-old").val();
			var newPass = $("#settings-password-input").val();
			var newPassVer = $("#settings-password-verify").val();
			
			
			// Send the data to the server
			$.post(getAppNs().config.siteRoot + "/client/change-userinfo", {
				name: billingName,
				street: billingStreet,
				city: billingCity,
				zip: billingZip,
				
				oldPassword: oldPass,
				newPassword: newPass,
				newPasswordVerify: newPassVer
			},
			function (result) {
				if(result == "Success") {
					$("#settings-options-error").text(result).css('visibility', 'hidden');
					utildialog.info('Údaje boli zmenené');
					$(_self.getContainer()).dialog("close");
				}
				else {
					$("#settings-options-error").text(result).css('visibility', 'visible');
				}
			});
		};
		
		/** Initialize settings widget */
		this.init = function () {
			// Connect events
			_self.connectEvent('u:user_bar_settings', _self._displaySettings);
			_self.connectEvent('u:user_bar_storage', _self._displayStorage);
		};
		
		/** Handle the widget destruction
		 * 
		 * This widget is not supposed to be destructed. 
		 */ 
		this.destroy = function () {
			_self.disconnectAllEvents();
		};
	}
	SettingsWidget.prototype = new agetWidgetsNs().Widget();
	namespace.SettingsWidget = SettingsWidget;
}(getMainWidgetsNs()));
