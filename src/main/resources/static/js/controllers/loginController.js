app.controller('LoginController', function($rootScope, $scope,
    $http, $location, $route) {
    $scope.credentials = {};

    var authenticate = (credentials, callback) => {

        var headers = $scope.credentials ? {
            authentiaction: "Basic "
            + btoa($scope.credentials.username + ":"
                + $scope.credentials.password)
                      } : {};

        console.log($scope.credentials.username);
        console.log(btoa($scope.credentials.username + ":"
                + $scope.credentials.password));

        $http.get('user', {headers: headers})
        .then((response) => {
            if(response.data.name) {
                $rootScope.authenticated = true;
            } else {
                $rootScope.authenticated = false;
            }
            callback && callback();
        }, () => {
            $rootScope.authenticated = false;
            callback && callback();
        });
    }

    authenticate();

    $scope.resetForm = () => {
        $scope.credentials = null;
    }

    $scope.loginUser = () => {
        authenticate($scope.credentials, () => {
            if($rootScope.authenticated) {
                $location.path("/");
                $scope.loginerror = false;
            } else {
                $location.path("/login");
                $scope.loginerror = true;
            }
        });
    }

});