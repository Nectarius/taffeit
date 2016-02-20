angular.module('notelist', ['ngResource'])
    .controller('notelist', function ($scope, $resource, $location) {

        $scope.currentPage = 0;
        $scope.pageSize = 10;
        $scope.asc = "asc";
        $scope.sortColumn = "theme";
        $scope.data = [];
        $scope.totalPages = 0;


        $scope.isActiveColumn = function (column) {
            if (column == $scope.sortColumn) {
                if ($scope.asc == "asc") {
                    return 'active sorting_asc';
                } else {
                    return 'active sorting_desc';
                }

            } else
                return '';
        }


        $scope.changeSorting = function (column) {
            if ($scope.sortColumn != column) {
                $scope.sortColumn = column;
            }
            if ($scope.asc == "asc") {
                $scope.asc = "desc";
            } else {
                $scope.asc = "asc";
            }
            $scope.update();
        };

        var noteListData = $resource("/notelist/:currentPage/:pageSize/:asc/sortBy:sortColumn.data");

        $scope.next = function () {
            $scope.currentPage += 1;
            $scope.update();
        }

        $scope.last = function () {
            $scope.currentPage -= 1;
            $scope.update();
        }

        $scope.update = function () {
            var response = noteListData.get({currentPage: $scope.currentPage, pageSize: $scope.pageSize, asc: $scope.asc, sortColumn: $scope.sortColumn }, function (response) {
                var length = response.content.length;
                $scope.totalPages = response.totalPages;
                var newData = [];
                for (var i = 0; i < length; i++) {
                    newData.push(response.content[i]);
                }
                $scope.data = newData;
            }, function (error) {
                alert(error.data);
            });

        }

        $scope.newnote = function () {
            $location.path('/newnote');
        }

        $scope.removenote= function (id) {

            var noteResource = $resource('/note/remove/:id.data');

            noteResource.remove({id: id}, function (response) {

                $scope.update();

            }, function (error) {
                alert('Произошла неизвестная ошибка');
            });

        }


        $scope.numberOfPages = function () {
            return $scope.totalPages;
        }


        $scope.update();


    });
