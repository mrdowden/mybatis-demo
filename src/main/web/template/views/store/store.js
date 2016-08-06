/*global window, angular, console, alert, $*/

angular.module('bourbon.store', ['ngRoute', 'ui.bootstrap'])

  .config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/store', {
      templateUrl: 'views/store/store.html',
      controller: 'storeCtrl'
    });
  }])

  .controller('storeCtrl', function ($scope, $http, $uibModal) {
  
    $http.get('/svc/items').success(function (data){
      $scope.bottles = data;
    });

    $scope.add = function(itemId) {
      $http.put('/svc/cart/'+itemId).then(function() {
        location.href = '#/cart';
      });
    };
  
    // Modal
  
    $scope.open = function (size, bottle) {
      var modalInstance = $uibModal.open({
        animation: $scope.animationsEnabled,
        templateUrl: 'description',
        controller: 'modalInstanceCtrl',
        size: size,
        resolve: {
          bottle: function () {
            return bottle;
          }
        }
      })
    };
  
  
  })

.controller('modalInstanceCtrl', function($scope, $uibModalInstance, bottle) {
  
  $scope.bottle = bottle;

  $scope.close = function () {
    $uibModalInstance.close();
  };
  
});
