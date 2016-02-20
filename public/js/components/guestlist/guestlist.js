angular.module('guestlist', ['ngResource'])
    .controller('guestlist', function ($scope, $routeParams, $resource, $location) {

        $scope.currentPage = 0;
        $scope.pageSize = 10;
        $scope.asc = "asc";
        $scope.sortColumn = "name";
        $scope.data = [];
        $scope.totalPages = 0;
        $scope.event = null;
        var eventId = $routeParams.eventId;
        $scope.eventId = eventId;

        $scope.setStatusIcon = function(value){

            switch(value){
                case  'PROMISED' :
                    return 'glyphicon glyphicon-ok';
                case 'NOT_YET_DECIDED' :
                    return 'glyphicon glyphicon-cloud';
                case 'UNLIKELY' :
                    return 'glyphicon glyphicon-thumbs-down';
                default :
                    return 'glyphicon glyphicon-glass';
            }

        }

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

        var guestListData = $resource("/guestlist/event:eventId/:currentPage/:pageSize/:asc/sortBy:sortColumn.data");

        $scope.next = function () {
            $scope.currentPage += 1;
            $scope.update();
        }

        $scope.last = function () {
            $scope.currentPage -= 1;
            $scope.update();
        }

        $scope.update = function () {
            if($scope.event !== null){
                eventId = $scope.event.id;
            }
            var response = guestListData.get({currentPage: $scope.currentPage, pageSize: $scope.pageSize, asc: $scope.asc, sortColumn: $scope.sortColumn, eventId : eventId }, function (response) {
                var length = response.content.length;
                $scope.totalPages = response.totalPages;
                $scope.event = response.event;
                var newData = [];
                for (var i = 0; i < length; i++) {
                    newData.push(response.content[i]);
                }
                $scope.data = newData;
            }, function (error) {
                alert(error.data);
            });

        }

        $scope.newguest = function () {
            if($scope.event !== null){
                eventId = $scope.event.id;
            }
            $location.path('/newguest/'+ eventId);
        }

        $scope.removeguest = function (id) {

            var guestResource = $resource('/guest/remove/:id.data');

            guestResource.remove({id: id}, function (response) {

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
