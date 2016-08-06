/*global window, angular, console, alert, $*/

angular.module('bourbon.checkout', ['ngRoute', 'ui.bootstrap'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/checkout', {
    templateUrl: 'views/checkout/checkout.html',
    controller: 'checkoutCtrl',
    resolve: {
      address: function($http) {
        return $http.get('/svc/order/shipping').then(function(response) {
          return response.data;
        });
      }
    }
  });
}])

.controller('checkoutCtrl', function ($scope, $http, address) {
  var saveAddress = function(address) {
    console.log(address);
    return $http.put('/svc/order/shipping', address);
  };


  $scope.form = address;

  $scope.continueShopping = function() {
    console.log("Continue Shopping");
    saveAddress($scope.form).then(function() {
      location.href = '#/store';
    });
  };

  $scope.placeOrder = function() {
    console.log("Place Order");
    saveAddress($scope.form).then(function() {
      console.log("Checkout");
      return $http.post('/svc/order/checkout');
    }).then(function(response) {
      console.log("Order Confirmation");
      console.log(response.data);
      location.href = '#/confirmation/' + response.data;
    });
  };
  
})
;
