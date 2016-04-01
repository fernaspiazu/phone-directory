'use strict';

(function () {
  angular.module('phoneDirectory')
    .controller('FormCtrl', [
      '$scope', 'PhoneDirectoryAPI',
      function ($scope, PhoneDirectoryAPI) {
        var self = this;
        self.isSuccessful = false;
        self.isFailed = false;

        var cleanForm = function () {
          self.model = {};
          $scope.entryForm.$setPristine();
          $scope.entryForm.$setUntouched();
        };

        self.save = function () {
          PhoneDirectoryAPI.save(self.model).$promise
            .then(function (d) {
              console.log('Saved record: ', d.entity);
              self.isSuccessful = true;
              cleanForm();
            }, function (err) {
              console.error('Error when saving record: ', err);
              self.isFailed = true;
            });
        };

        self.reset = function () {
          self.isSuccessful = false;
          self.isFailed = false;
          cleanForm();
        }
      }
    ]);
})();