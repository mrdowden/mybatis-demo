/*global window, angular, console, alert, $*/

angular.module('bourbon.cart', ['ngRoute', 'ui.bootstrap'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/cart', {
    templateUrl: 'views/cart/cart.html',
    controller: 'cartCtrl',
    resolve: {
      shoppingCart: function($http) {
        return $http.get('/svc/cart').then(function(response) {
          return response.data;
        });
      }
    }
  });
}])

.controller('cartCtrl', function ($scope, $http, shoppingCart) {
  var refresh = function() {
    $http.get('/svc/cart').then(function(response) {
      $scope.shoppingCart = response.data;
    });
  };

  $scope.shoppingCart = shoppingCart;

  $scope.remove = function(itemId) {
    $http.delete('/svc/cart/'+itemId).then(refresh);
  };

  $scope.updateQty = function(item) {
    if(item && item.qty) {
      //Spring can't see the proper POST parameter method
      $http.post('/svc/cart/'+item.id, 'qty='+item.qty, {
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
      }).then(refresh);
    }
  };

})
;
