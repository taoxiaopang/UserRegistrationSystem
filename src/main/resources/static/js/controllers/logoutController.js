app.controller('LogoutController', function($rootScope, $scope, $http, $location, $route) {
    $http.post('logout', {}).finally(() => {
        $rootScope.authenticated = false;
        $location.path("/");
    });
});