'use strict';

(function() {
	angular.module('phoneDirectory')
		.factory('PhoneDirectoryAPI', ['$resource', function($resource) {
			return $resource('/api/:action', {},
				{
					"find": {
						"method": "GET",
						"headers": {"Content-Type": "application/json"},
						"params": {"action": "find"}
					}
				}
			);
		}])
})();