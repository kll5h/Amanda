(function () {
    'use strict';

    angular
        .module('amandaApp')
        .factory('BootSwatchService', BootSwatchService);

    BootSwatchService.$inject = ['$http'];

    function BootSwatchService($http) {
        return {
            get: function () {
                return $http.get('/content/themes/3.json').then(function (response) {
                    return response.data.themes;
                });
            }
        };
    }
})();
