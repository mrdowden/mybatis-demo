/*global window, angular, console, alert, $*/

angular.module('bourbon.confirmation', ['ngRoute', 'ui.bootstrap'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/confirmation/:orderNumber', {
    templateUrl: 'views/confirmation/confirmation.html',
    controller: 'confirmationCtrl'
  });
}])

.controller('confirmationCtrl', function ($scope, $http, $routeParams) {

  $http.get('/svc/order/' + $routeParams.orderNumber).then(function(response) {
    $scope.order = response.data;
  });

})
;
