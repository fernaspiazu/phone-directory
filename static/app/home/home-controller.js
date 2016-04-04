'use strict';

(function () {
  angular.module('phoneDirectory')
    .controller('HomeCtrl', [
      '$scope', 'PhoneDirectoryAPI', 'firstPageInTable', 'isEmptyContent',
      function ($scope, PhoneDirectoryAPI, firstPageInTable, isEmptyContent) {
        var self = this;
        self.empty = isEmptyContent.result;
        self.page = firstPageInTable;
        self.current = 0;

        var fetchRecords = function (page) {
          self.current = page;
          PhoneDirectoryAPI.find({"page": self.current, "size": 10}).$promise
            .then(function (data) {
              self.page = data;
              self.empty = data.content.length === 0;
            });
        };

        self.delete = function (id) {
          PhoneDirectoryAPI.delete({"id": id}).$promise
            .then(function (resp) {
              console.log('Delete status: ' + resp.isDeleted);
            }, function (err) {
              console.error('Error when I delete: ', err);
            })
            .then(fetchRecords);
        };

        self.find = function (page) {
          fetchRecords(page);
        };

        $scope.$watch('formCtrl.criteria', function (newVal, oldVal) {
          var data = {"page": self.current, "size": 10, "q": newVal || ""};
          PhoneDirectoryAPI.search(data).$promise
            .then(function (data) {
              self.page = data;
              self.empty = data.content.length === 0;
            });
        });

        self.range = function (min, max, step) {
          min = min || 0;
          step = step || 1;
          var input = [];
          for (var i = min; i <= max; i += step) {
            input.push(i);
          }
          return input;
        };
      }
    ]);
})();