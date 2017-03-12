var app = angular.module('emrApp', [ 'ngCookies', 'ngResource', 'ngSanitize',
		'ngRoute' ]);

app.config(function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'views/list.html',
		controller : 'ListCtrl'
	}).otherwise({
		redirectTo : '/'
	})
});

app.controller('ListCtrl', function($scope, $http) {
	
	$scope.case1='';
	$scope.case2='hidden';
	$scope.case3='hidden';
	$scope.case4='hidden';
	$scope.case5='hidden';
	$scope.number = 10;
	$scope.getN = function() {
		$("#spinner").show();
		$scope.case1='';
		$scope.case2='hidden';
		$scope.case3='hidden';
		$scope.case4='hidden';
		$scope.case5='hidden';
		$scope.emrs = null;
		$http.get('/api/v1/showTopN/' + $scope.number).success(function(data) {
			$scope.emrs = data;
			$("#spinner").hide();
		}).error(function(data, status) {
			console.log('Error ' + data)
		})
	}
	$scope.getwdCount = function() {
		$("#spinner").show();
		$scope.case1='hidden';
		$scope.case2='';
		$scope.case3='hidden';
		$scope.case4='hidden';
		$scope.case5='hidden';
		$scope.results = null;
		$http.get('/api/v1/wdcount/' + $scope.number).success(function(data) {
			$scope.emrs = null;
			$scope.results = data;
			$("#spinner").hide();
		}).error(function(data, status) {
			console.log('Error ' + data)
		})
	}
	$scope.getcityCount = function() {
		$("#spinner").show();
		$scope.case1='hidden';
		$scope.case2='hidden';
		$scope.case3='';
		$scope.case4='hidden';
		$scope.case5='hidden';
		$scope.results = null;
		$http.get('/api/v1/citycount/' + $scope.number).success(function(data) {
			$scope.emrs = null;
			$scope.results = data;
			$("#spinner").hide();
		}).error(function(data, status) {
			console.log('Error ' + data)
		})
	}
	$scope.getyearCount = function() {
		$("#spinner").show();
		$scope.case1='hidden';
		$scope.case2='hidden';
		$scope.case3='hidden';
		$scope.case4='';
		$scope.case5='hidden';
		$scope.results = null;
		$http.get('/api/v1/yearcount/' + $scope.number).success(function(data) {
			$scope.emrs = null;
			$scope.results = data;
			$("#spinner").hide();
		}).error(function(data, status) {
			console.log('Error ' + data)
		})
	}
	$scope.gettodCount = function() {
		$("#spinner").show();
		$scope.case1='hidden';
		$scope.case2='hidden';
		$scope.case3='hidden';
		$scope.case4='hidden';
		$scope.case5='';
		$scope.results = null;
		$http.get('/api/v1/gettodcount').success(function(data) {
			$scope.emrs = null;
			$scope.results = data;
			$("#spinner").hide();
		}).error(function(data, status) {
			console.log('Error ' + data)
		})
	}


});
