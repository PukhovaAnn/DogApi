angular.module('app', ['ui.bootstrap', 'ngRoute', 'ngResource'])

    .config(function($locationProvider) {
        $locationProvider.hashPrefix('');
    })

    .config(function($routeProvider) {
        $routeProvider
            .when('/', {
                controller: 'DogController as cntrl',
                templateUrl:'dog_list.html'
            })
            .otherwise({
                redirectTo:'/'
            });
    })


    .controller('DogController', function ($scope, $window, Dog, $uibModal) {
        var self = this;
        self.dogs = Dog.query();

        self.modalOpenEdit = function (index) {
            var modalInstance = $uibModal.open({
                controller: 'PopupEditDog',
                controllerAs: 'cntrl',
                templateUrl: 'popup.html',
                resolve: {
                    dogWithPicture: ['DogPictureService', function(dogPictureService){
                        return dogPictureService(self.dogs[index].id);
                    }]
                }
            });
            modalInstance.result.then(function (dog) {
                if (dog != undefined) {
                    self.dogs[index] = dog;
                } else {
                    self.dogs.splice(index, 1)
                }
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };
        self.modalOpenAdd = function () {
            var modalInstance = $uibModal.open({
                controller: 'PopupAddDog',
                controllerAs: 'cntrl',
                templateUrl: 'popup.html'
            });
            modalInstance.result.then(function (dog) {
                self.dogs.push(dog)
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        }
    })

    .controller('PopupAddDog', function ($scope, Dog, fileReader, $modalInstance, FormDataService) {
        var self = this;
        self.dog = new Dog();

        $scope.getFile = function () {
            fileReader.readAsDataUrl($scope.file, $scope)
                .then(function (readResult) {
                    self.dog.picture = readResult;
                });
        };

        self.close = function () {
            $modalInstance.dismiss("close")
        };

        self.save = function () {
            var formData =  FormDataService(self.dog, $scope.file);
            Dog.create(formData, function (result) {
                $modalInstance.close(result)
            });
        };
    })

    .controller('PopupEditDog', function ($resource, fileReader, FormDataService, dogWithPicture, $sce, $window, $scope, Dog, $modalInstance) {
        var self = this;
        self.dog = dogWithPicture;

        $scope.getFile = function () {
            fileReader.readAsDataUrl($scope.file, $scope)
                .then(function (readResult) {
                    self.dog.picture = readResult;
                });
        };

        self.save = function () {
            var formData =  FormDataService(self.dog, $scope.file);
            Dog.update({id : self.dog.id }, formData, function (result) {
                   $modalInstance.close(result)
            });
        };

        self.close = function () {
            $modalInstance.dismiss("close")
        };

        self.delete = function () {
                var params = {id: self.dog.id};
                Dog.delete(params, function () {
                    $modalInstance.close()
                });
        };
    });