(function () {
    'use strict';

    angular
        .module('amandaApp')
        .controller('BootswatchController', BootswatchController);

    BootswatchController.$inject = ['$scope', 'BootSwatchService'];

    function BootswatchController($scope, BootSwatchService) {
        /*Get the list of availabel bootswatch themes*/
        BootSwatchService.get().then(function (themes) {
            $scope.themes = themes;
            $scope.themes.unshift({name: 'Default', css: ''});
        });
    }
})();
