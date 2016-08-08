/*global window, angular, console, alert, $*/

angular.module('bourbon.admin', ['ngRoute', 'ui.bootstrap'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/admin', {
    templateUrl: 'views/admin/admin.html',
    controller: 'adminCtrl'
  });
}])

.controller('adminCtrl', function ($scope, $http, $log, $uibModal) {

  $http.get('/svc/admin/items').success(function (data){

    console.log(data);

    $scope.bottles = data;
    $scope.totalItems = data.length;
    console.log($scope.bottles);

    $scope.bottleList = angular.copy($scope.bottles).slice(0, 10);

  });

  $scope.pageChanged = function () {
    var calc, firstItem, lastItem, items;

    items = angular.copy($scope.bottles);
    firstItem = ($scope.currentPage - 1) * 10;
    lastItem = firstItem + 10;

    $scope.bottleList = items.slice(firstItem, lastItem);
  };

  $scope.currentPage = 1;
  $scope.maxSize = 5;

  // modal

  $scope.open = function (size, item) {

   var modalInstance = $uibModal.open({
     animation: $scope.animationsEnabled,
     templateUrl: 'adminModal',
     controller: 'adminAddItemModalCtrl',
     size: size,
     resolve: {
       item: function () {
         return item;
       }
     }
   });

   modalInstance.result.then(function (item) {
     $http.post('/svc/admin/items', item).success(function (data) {
       console.log('returned data = : ' + data);
     });

   });
 };

})

.controller('adminAddItemModalCtrl', function ($scope, $uibModalInstance, item, $scope) {

  if (item) {
    $scope.item = item;
    $scope.title = item.name;
  } else {
    $scope.title = "New Item";
  }

  $scope.ok = function () {
    $uibModalInstance.close($scope.item);
  };

  $scope.cancel = function () {
    $uibModalInstance.dismiss('cancel');
  };

});
