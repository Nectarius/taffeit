angular.module('guest', ['ngResource'])

    .directive('guestWidget', function () {
        return {
            restrict: 'E',
            priority: 5,
            transclude: true,
            link: function(scope, element, attrs, ngModel){
                function isInvalid(){
                    // it's something to think about ...
                    return scope.form.$invalid;
                }
                scope.$watch(isInvalid, function(newValue, oldValue){
                    scope.model.invalid = newValue;
                });
            },
            scope: {
                model: '=model'

            },
            templateUrl: "assets/js/components/guest/guestwidget.html"
        };
    });
