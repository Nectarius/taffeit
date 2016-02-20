angular.module('editevent', ['ngResource','event'])
    .controller('editevent', function ($scope, $routeParams, $resource, $location) {

        var event_id = $routeParams.id;
        var eventResource = $resource('/event/:id.data');

        $scope.event = {};

        $scope.eventId = event_id;

        $scope.fill = function (event) {

            eventResource.get({id: event_id }, function (response) {
                event.name = response.name;
                event.description = response.description;
                event.webSite = response.webSite;
                event.path = response.path;

            }, function (error) {
                alert(error.data);
            });

        }

        $scope.fill($scope.event);


        $scope.save = function (event_) {

            var event = {};
            event.id = event_id;
            event.name = event_.name;
            event.description = event_.description;
            event.webSite = event_.webSite;
            event.path = event_.path;
            event.status = true;

            //var data = JSON.stringify(event);

            var eventResource = $resource('/event/save.data', event);

            eventResource.save(event, function (response) {
                alert('Данные сохранены');
                $location.path("/eventlist");
            }, function (error) {
                if (error.status == 400) {
                    alert('Неверно указано имя или фамилия пользователя');
                }
                else
                    alert('Произошла неизвестная ошибка');
            });
        }

    });