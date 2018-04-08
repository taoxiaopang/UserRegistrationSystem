/**
 *
 */
var app = angular.module('userregistrationsystem', ['ngRoute', 'ngResource']);

app.config(function($locationProvider, $routeProvider) {
	$locationProvider.hashPrefix('');
	$routeProvider
	.when('/', {
		controller: 'HomeController',
		templateUrl: '/views/home.html'
	})
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
	.when('/login', {
		controller: 'LoginController',
		templateUrl: '/views/login.html'
	})
	.when('/logout', {
		controller: 'LogoutController',
		templateUrl: '/views/login.html'
	})
	.otherwise({
		redirectTo: '/login'
	});

});

app.config(['$httpProvider', function($httpProvider) {
	//$httpProvider.interceptors.push('AuthInterceptor');
	$httpProvider.defaults.headers.common["X-Requested-Width"] = 'XMLHttpRequest';
}]);