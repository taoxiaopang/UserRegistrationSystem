/**
 *
 */
var app = angular.module('userregistrationsystem', ['ngRoute']);

app.config(function($locationProvider, $routeProvider) {
	$locationProvider.hashPrefix('');
	$routeProvider
	.when('/list-all-users', {
		controller: 'ListUserController',
		templateUrl: '/views/listUser.html'
	})
	.when('/register-new-user', {
		controller: 'RegisterUserController',
		templateUrl: 'views/userRegistration.html'
	})
	.when('/update-user/:id', {
		controller: 'UpdateUserController',
		templateUrl: 'views/userUpdate.html'
	})
	.otherwise({
		redirectTo: '/home',
		templateUrl: 'views/home.html'
	});

});