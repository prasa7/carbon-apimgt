'use strict';

var _ = require('lodash');
var angular = require('angular');

SwaggerEditor.service('Backend', function Backend($http, $q, defaults,
  $rootScope, Builder, ExternalHooks, YAML) {
  var changeListeners = {};
  /* eslint no-useless-escape: "off"*/
  var absoluteRegex = /^(\/|http(s)?\:\/\/)/; // starts with slash or http|https
  var buffer = {};
  var throttleTimeout = defaults.backendThrottle || 200;
  var commit = _.throttle(commitNow, throttleTimeout, {
    leading: false,
    trailing: true
  });

  var backendEndpoint = defaults.backendEndpoint;

  // if backendEndpoint is not absolute append it to location.pathname
  if (!absoluteRegex.test(backendEndpoint)) {
    var pathname = _.endsWith(location.pathname, '/') ? location.pathname :
      location.pathname + '/';
    backendEndpoint = pathname + defaults.backendEndpoint;

    // avoid double slash that might generated by appending location.href to
    // backendEndpoint
    backendEndpoint = backendEndpoint.replace('//', '/');
  }

  /**
   * @param {object} data - data
  */
  function commitNow(data) {
    var result = Builder.buildDocs(data, {
      resolve: true
    });

    save('progress', 'progress-saving');

    var httpConfig = {
      headers: {
        'content-type': defaults.useYamlBackend ?
          'application/yaml; charset=utf-8' : 'application/json; charset=utf-8'
      }
    };

    if (!result.error) {
      $http.put(backendEndpoint, data, httpConfig)
        .then(function success() {
          ExternalHooks.trigger('put-success', [].slice.call(arguments));
          $rootScope.progressStatus = 'success-saved';
        }, function failure() {
          ExternalHooks.trigger('put-failure', [].slice.call(arguments));
          $rootScope.progressStatus = 'error-connection';
        });
    }
  }

  /**
   * @param {string} key - key
   * @param {string} value - value
  */
  function save(key, value) {
    // Save values in a buffer
    buffer[key] = value;

    if (Array.isArray(changeListeners[key])) {
      changeListeners[key].forEach(function(fn) {
        fn(value);
      });
    }

    if (key === 'yaml' && value) {
      if (defaults.useYamlBackend) {
        commit(value);
      } else {
        YAML.load(value, function(err, json) {
          if (!err) {
            commit(json);
          }
        });
      }
    }
  }

  /**
   * @param {string} key - key
   * @return {Function} if key is not 'yaml'
  */
  function load(key) {
    if (key !== 'yaml') {
      return new Promise(function(resolve, reject) {
        if (key) {
          resolve(buffer[key]);
        } else {
          reject();
        }
      });
    }

    var httpConfig = {
      headers: {
        accept: defaults.useYamlBackend ?
          'application/yaml; charset=utf-8' : 'application/json; charset=utf-8'
      }
    };

    return $http.get(backendEndpoint, httpConfig)
      .then(function(res) {
        if (defaults.useYamlBackend) {
          buffer.yaml = res.data;
          return buffer.yaml;
        }
        return res.data;
      });
  }

  /**
   * @param {string} key - key
   * @param {function} fn - function
  */
  function addChangeListener(key, fn) {
    if (angular.isFunction(fn)) {
      if (!changeListeners[key]) {
        changeListeners[key] = [];
      }
      changeListeners[key].push(fn);
    }
  }

  /** */
  function noop() {}

  this.save = save;
  this.reset = noop;
  this.load = load;
  this.addChangeListener = addChangeListener;
});
