app.controller('HomeController', function($rootScope, $scope,
    $http, $location, $route) {
    if($rootScope.authenticated) {
        $location.path("/");
        $scope.loginerror = false;
    } else {
        $location.path("/login");
        $scope.loginerror = true;
    }
});