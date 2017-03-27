(function (module) {

    module.constant('urlConfig', {
        baseUrl : "http://192.168.2.103:8082/"
    });

    module.factory('Dog', function ($resource, urlConfig) {
        return $resource(urlConfig.baseUrl+'dog/:id', {}, {
            query : {
                method : 'Get',
                isArray : true
            },
            get : {
                method: 'GET',
                params: {id: '@_id'}
            },
            create : {
                method: 'POST',
                headers: {
                    "Content-Type": undefined
                },
                tranformRequest: angular.identity
            },
            update : {
                method: 'POST',
                params : {id : '@_id'},
                headers: {
                    "Content-Type": undefined
                },
                tranformRequest: angular.identity
            },
            delete : {
                method: 'DELETE', params: {id: '@_id'}
            }
        })
    });

    module.factory('Picture', function ($resource, $window, urlConfig) {
        return $resource(urlConfig.baseUrl+'picture/:id', {id: '@id'}, {
                get : {
                    responseType: 'arraybuffer',
                    method: 'GET',
                    transformResponse : function (response) {
                        response.data =  $window.URL.createObjectURL(new Blob([response], {type: 'image/jpg'}));
                        return response;
                    }
                }
            }
        )
    });
}(angular.module("app")));