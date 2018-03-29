app.controller('RegisterUserController', function($scope, $http, $location, $route) {
    $scope.submitUserForm = () => {
        $http
          .post('http://localhost:8080/api/user/', $scope.user)
          .then((response) => {
              $location.path("/list-all-users");
              $route.reload();
          }, (errResponse) => {
              $scope.errorMessage = errResponse.data.errorMessage;
          });
    };

    $scope.resetForm = () => {
        $scope.user = null;
    }
});