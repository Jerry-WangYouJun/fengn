(function ($) {
    var hostNames = ['uiot-wx.jdcloud.com', '117.48.206.228'];
  $.fn.extend({
    CheckHostName: function () {
      var $window = $(window);
      var currHostName = location.hostname;
      var checked = function (type) {
        setTimeout($window.trigger.bind($window, 'host-name-checked'), 0);
      }.bind(this);
      var always = function (type) {

        setTimeout(function () {
          $('body').fadeIn('fast');
          $window.trigger('host-name-always', [type])
        }, 0);
      }.bind(this);
      var isChecked = function (currHostName) {
        return hostNames.indexOf(currHostName) !== -1;
      }
      var appendLink = function (other) {
        var base = '/Html/styles/JD/'
        var css = location.pathname;
        var name = css.split('/');
        name = name[name.length - 1].split('.')[0];
        var $link = $('<link>').attr('rel', "stylesheet").attr('href', base + name + other + '.min.css');
        $('head').append($link);
        return $link[0];
      }
      if (isChecked(currHostName)) {
        var other = '_JD';
        this.cssOnload(appendLink(other), function () {
          checked();
          always('JD');
        });
      } else {
        always('MLB');
      }
    },
    cssOnload: function (node, callback) {
      var poll = function (node, callback) {
        if (callback.isCalled) {
          return;
        }
        var isLoaded = false;
        if (/webkit/i.test(navigator.userAgent)) {
          if (node['sheet']) {
            isLoaded = true;
          }
        } else if (node['sheet']) {
          try {
            if (node['sheet'].cssRules) {
              isLoaded = true;
            }
          } catch (ex) {
            if (ex.code === 1000) {
              isLoaded = true;
            }
          }
        }
        if (isLoaded) {
          setTimeout(function () {
            callback();
          }, 1);
        } else {
          setTimeout(function () {
            poll(node, callback);
          }, 1);
        }
      }
      if (node.attachEvent) {
        node.attachEvent('onload', callback);
      } else {
        setTimeout(function () {
          poll(node, callback);
        }, 0);
      }
    }
  })
  $(window).CheckHostName();
})(jQuery);