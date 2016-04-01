'use strict';

(function () {
  angular.module('phoneDirectory')
    .controller('HomeCtrl', [
      'PhoneDirectoryAPI', 'firstPageInTable', 'isEmptyContent',
      function (PhoneDirectoryAPI, firstPageInTable, isEmptyContent) {
        var self = this;
        self.empty = isEmptyContent.result;
        self.content = firstPageInTable.content;

        var fetchRecords = function () {
          PhoneDirectoryAPI.find({"page": 0, "size": 10}).$promise
            .then(function (data) {
              self.content = data.content;
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
      }
    ]);
})();