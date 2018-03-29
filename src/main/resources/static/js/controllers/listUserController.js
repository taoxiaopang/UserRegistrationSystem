app.controller('ListUserController', function($scope, $http, $location, $route) {
    $http
      .get('http://localhost:8080/api/user/')
      .then((response) => {
          $scope.users = response.data;
      }, (errorResponse) => {
          console.log("Failed to get user: " + errorResponse.data.errorMessage);
      }
      );

      $scope.editUser = (userId) => {
          $location.path('/update-user/' + userId);
      };

      $scope.deleteUser = (userId) => {
          $http.
            delete('http://localhost:8080/api/user/' + userId)
            .then((response) => {
                $location.path('/list-all-users');
                $route.reload();
            });
      };

});