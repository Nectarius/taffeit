angular.module('newevent', ['ngResource','event'])
    .controller('Newevent', function ($scope, $resource, $resource, $location) {

        $scope.event = {};

        $scope.fill = function (event) {
            event.name = "";
        }

        $scope.fill($scope.event);


        $scope.save = function (event_) {

            var event = {};
            event.name = event_.name;
            event.status = true;
            event.description = event_.description;
            event.webSite = event_.webSite;
            event.path = event_.path;

            var eventResource = $resource('/event/save.data', event);

            eventResource.save(event, function (response) {
                alert('Данные сохранены');
                $location.path("/eventlist");
            }, function (error) {
                if (error.status == 400) {
                    alert('Введены некорректные данные');
                }
                else
                    alert('Произошла неизвестная ошибка');
            });
        }

    });
