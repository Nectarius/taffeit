angular.module('newnote', ['ngResource','note'])
    .controller('newnote', function ($scope, $resource, $resource, $location) {

        $scope.note = {};

        $scope.fill = function (note) {
            note.name = "";
        }

        $scope.fill($scope.note);


        $scope.save = function (note_) {

            var note = {};
            note.id = note_.id;
            note.theme = note_.theme;
            //note.date = note_.date;
            note.text = note_.text;

            var noteResource = $resource('/note/save.data', note);

            noteResource.save(note, function (response) {
                alert('Данные сохранены');
                $location.path("/notelist");
            }, function (error) {
                if (error.status == 400) {
                    alert('Введены некорректные данные');
                }
                else
                    alert('Произошла неизвестная ошибка');
            });
        }

    });
