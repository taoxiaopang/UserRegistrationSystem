app.controller('UpdateUserController', function($scope, $http, $location, $route, $routeParams) {
    $scope.userId = $routeParams.id;

    $http
      .get('http://localhost:8080/api/user/' + $scope.userId)
      .then((response) => {
         $scope.user = response.data;
      });

    $scope.submitUserForm = () => {
        $http
        .put('http://localhost:8080/api/user/' + $scope.userId, $scope.user)
        .then((response) => {
            $location.path('/list-all-users');
            $route.reload();
        }, (errorResponse) => {
            $scope.errorMessage = errorResponse.data.errorMessage;
        });
    };


    $scope.resetForm = () => {
        $scope.user = null;
    }
});