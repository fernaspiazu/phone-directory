'use strict';

(function () {
  angular.module('phoneDirectory')
    .factory('PhoneDirectoryAPI', ['$resource', function ($resource) {
      return $resource('/api/:action', {},
        {
          "isEmpty": {
            "method": "GET",
            "headers": {"Content-Type": "application/json"},
            "params": {"action": "is-empty"}
          },
          "find": {
            "method": "GET",
            "headers": {"Content-Type": "application/json"},
            "params": {"action": "find"}
          },
          "search": {
            "method": "GET",
            "headers": {"Content-Type": "application/json"},
            "params": {"action": "search"}
          },
          "save": {
            "method": "POST",
            "headers": {"Content-Type": "application/json"},
            "params": {"action": "save"}
          },
          "delete": {
            "method": "DELETE",
            "headers": {"Content-Type": "application/json"},
            "params": {"action": "delete"}
          }
        }
      );
    }])
})();