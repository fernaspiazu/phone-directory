angular.module('phoneDirectory', []);

angular.module('phoneDirectory')
	.controller('MainCtrl', [function() {
		var self = this;
		self.now = Date.now();
	}]);