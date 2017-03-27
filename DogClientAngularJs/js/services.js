(function (module) {

    module.service('FormDataService', function () {
        return function (dog, file) {
            var formData = new FormData();
            if (dog.name != undefined) {
                formData.append('dog', new Blob([angular.toJson(dog)], {
                    type: "application/json"
                }));
            }
            formData.append("file", file);
            return formData;
        }
    });

    module.service("DogPictureService", function (Picture, $q, Dog) {
        return function (idDog) {
            var dog = Dog.get({id : idDog});
            var deferred = $q.defer();
            Picture.get({id:idDog}, function (response) {
                dog.picture = response.data;
            }, function (errorData) {
                deferred.reject();
            });
            deferred.resolve(dog);
            return deferred.promise;
        }
    });

}(angular.module("app")));/**
 * Created by Pukho on 27.03.2017.
 */
