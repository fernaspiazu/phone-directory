'use strict';

(function() {
	angular.module('phoneDirectory')
		.controller('HomeCtrl', ['PhoneDirectoryAPI', function(PhoneDirectoryAPI) {
			var self = this;
			self.content = [];

			var fetchRecords = function() {
				PhoneDirectoryAPI.find({"page": 0, "size": 10}).$promise
					.then(function(data) {
						self.content = data.content;
					});
			};

			fetchRecords();

			self.delete = function(id) {
				PhoneDirectoryAPI.delete({"id": id}).$promise
					.then(function(response) {
						console.log('Delete status: ' + response.deleted);
					}, function(err) {
						console.error('Error when I delete: ', err);
					})
					.then(fetchRecords);
			}
		}]);
})();