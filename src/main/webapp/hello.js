(function () {
    var as = angular.module('angularspring');

    as.controller('Hello', function ($scope, $http, i18n) {
        var actionUrl = 'http://rest-service.guides.spring.io/greeting',
            load = function () {
                $http.get(actionUrl).success(function (data) {
                    $scope.greeting = data;
                });
            };

        load();
    });

}());