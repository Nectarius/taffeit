angular.module('editnote', ['ngResource','note'])
    .controller('editnote', function ($scope, $routeParams, $resource, $location) {

        var note_id = $routeParams.id;
        var noteResource = $resource('/note/:id.data');

        $scope.note = {};

        $scope.noteId = note_id;

        $scope.fill = function (event) {

            noteResource.get({id: note_id }, function (response) {
                event.theme = response.theme;
                //event.date = response.date;
                event.text = response.text;

            }, function (error) {
                alert(error.data);
            });

        }

        $scope.fill($scope.note);


        $scope.save = function (note_) {

            var note = {};
            note.id = note_id;
            note.theme = note_.theme;
            //note.date = note_.date;
            note.text = note_.text;


            noteResource = $resource('/note/save.data', note);

            noteResource.save(note, function (response) {
                alert('Данные сохранены');
                $location.path("/notelist");
            }, function (error) {
                if (error.status == 400) {
                    alert('Данные указаны неверно');
                }
                else
                    alert('Произошла неизвестная ошибка');
            });
        }

    });