'use strict';

(function() {
	angular.module('phoneDirectory')
		.controller('HomeCtrl', ['PhoneDirectoryAPI', function(PhoneDirectoryAPI) {
			var self = this;
			self.content = [];

			PhoneDirectoryAPI.find({"page": 0, "size": 10}).$promise.then(function(data) {
				self.content = data.content;
			});
		}]);
})();