
angular.module('guestpage',
    ['ngRoute', 'ngResource', 'ngSanitize', 'textAngular','newguest', 'editguest' , 'guestlist' , 'newevent', 'editevent' , 'eventlist', 'newnote', 'editnote', 'notelist'])
    .config(['$routeProvider', function ($routeProvider) {

        $routeProvider.when('/guestlist/event:eventId', {templateUrl: 'assets/js/components/guestlist/guestlist.html', controller: 'guestlist'});
        $routeProvider.when('/newguest/:eventId', {templateUrl: 'assets/js/components/guest/newguest.html', controller: 'Newguest'});
        $routeProvider.when('/editguest/:id/event:eventId', {templateUrl: 'assets/js/components/guest/editguest.html', controller: 'Editguest'});

        $routeProvider.when('/eventlist', {templateUrl: 'assets/js/components/eventlist/eventlist.html', controller: 'eventlist'});
        $routeProvider.when('/newevent', {templateUrl: 'assets/js/components/event/newevent.html', controller: 'Newevent'});
        $routeProvider.when('/editevent/:id', {templateUrl: 'assets/js/components/event/editevent.html', controller: 'editevent'});

        $routeProvider.when('/notelist', {templateUrl: 'assets/js/components/notes/notelist.html', controller: 'notelist'});
        $routeProvider.when('/newnote', {templateUrl: 'assets/js/components/note/newnote.html', controller: 'newnote'});
        $routeProvider.when('/editnote/:id', {templateUrl: 'assets/js/components/note/editnote.html', controller: 'editnote'});


        $routeProvider.otherwise({redirectTo: '/guestlist/event0'});
    }]).config(['$provide', function($provide){
        // this demonstrates how to register a new tool and add it to the default toolbar
        $provide.decorator('taOptions', ['$delegate', function(taOptions){
            // $delegate is the taOptions we are decorating
            // here we override the default toolbars and classes specified in taOptions.
            taOptions.toolbar = [
                ['h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'p', 'pre', 'quote'],
                ['bold', 'italics', 'underline', 'ul', 'ol', 'redo', 'undo', 'clear'],
                ['justifyLeft','justifyCenter','justifyRight'],
                ['html', 'insertImage', 'insertLink']
            ];
            taOptions.classes = {
                focussed: 'focussed',
                toolbar: 'btn-toolbar',
                toolbarGroup: 'btn-group',
                toolbarButton: 'btn btn-default',
                toolbarButtonActive: 'active',
                disabled: 'disabled',
                textEditor: 'form-control',
                htmlEditor: 'form-control'
            };
            return taOptions; // whatever you return will be the taOptions
        }]);
        // this demonstrates changing the classes of the icons for the tools for font-awesome v3.x
        $provide.decorator('taTools', ['$delegate', function(taTools){
            taTools.bold.iconclass = 'glyphicon glyphicon-bold';
            taTools.italics.iconclass = 'glyphicon glyphicon-italic';
            taTools.underline.iconclass = 'glyphicon glyphicon-text-width';
            taTools.ul.iconclass = 'glyphicon glyphicon-th-list';
            taTools.ol.iconclass = 'glyphicon glyphicon-list';
            taTools.undo.iconclass = 'glyphicon glyphicon-chevron-left';
            taTools.redo.iconclass = 'glyphicon glyphicon-chevron-right';
            taTools.justifyLeft.iconclass = 'glyphicon glyphicon-align-left';
            taTools.justifyRight.iconclass = 'glyphicon glyphicon-align-right';
            taTools.justifyCenter.iconclass = 'glyphicon glyphicon-align-center';
            taTools.clear.iconclass = 'glyphicon glyphicon-off';
            taTools.insertLink.iconclass = 'glyphicon glyphicon-flash';
            //taTools.unlink.iconclass = 'icon-link red';
            taTools.insertImage.iconclass = 'glyphicon glyphicon-picture';
            // there is no quote icon in old font-awesome so we change to text as follows
            delete taTools.quote.iconclass;
            taTools.quote.buttontext = '.';
            return taTools;
        }]);
    }]);;

