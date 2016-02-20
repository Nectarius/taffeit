angular.module('eventlist', ['ngResource'])
    .controller('eventlist', function ($scope, $resource, $location) {

        $scope.currentPage = 0;
        $scope.pageSize = 10;
        $scope.asc = "asc";
        $scope.sortColumn = "name";
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

        var eventListData = $resource("/eventlist/:currentPage/:pageSize/:asc/sortBy:sortColumn.data");

        $scope.next = function () {
            $scope.currentPage += 1;
            $scope.update();
        }

        $scope.last = function () {
            $scope.currentPage -= 1;
            $scope.update();
        }

        $scope.update = function () {
            var response = eventListData.get({currentPage: $scope.currentPage, pageSize: $scope.pageSize, asc: $scope.asc, sortColumn: $scope.sortColumn }, function (response) {
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

        $scope.newevent = function () {
            $location.path('/newevent');
        }

        $scope.removeevent = function (id) {

            var eventResource = $resource('/event/remove/:id.data');

            eventResource.remove({id: id}, function (response) {

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
