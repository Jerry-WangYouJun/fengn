/**
 * Created by Joo on 13-12-2.
 */

+function ($) {
    "use strict";
    var css3defs = ["transition", "transform", "box-shadow"],
        prefixes = ["webkit", "moz", "ms", "o"],
        baseCSS,//成功提示背景
        baseCSS2,//失败提示背景
        restoreCSS,
        slideDownCSS,
        isShowing = false,
        timerID = null, 
        $shower = $("<div></div>");

    //css defination.
    baseCSS = {
        "width": "100%",
        "height": "2.5em",
        "fontSize": "1.25em",
        "lineHeight": "2.5em",
        "textAlign": "center",
        "color": "#ffffff",
        "textShadow": "0 0 2px #000000",
        "position": "fixed",
        "zIndex": 9999, 
        "backgroundColor": "#32CD32",//  --绿色
        "transition": "all 0.5s"
    };

    baseCSS2 = {
        "width": "100%",
        "height": "2.5em",
        "fontSize": "1.25em",
        "lineHeight": "2.5em",
        "textAlign": "center",
        "color": "#ffffff",
        "textShadow": "0 0 2px #000000",
        "position": "fixed",
        "zIndex": 9999,
        "backgroundColor": "#DB7093",// --红色背景
        "transition": "all 0.5s"
    };

    restoreCSS = {
        "opacity": 0,
        "transform": "translateY(-2em)"
    };

    slideDownCSS = {
        "opacity": 1,
        "transform": "translateY(0)"
    };

    //private function(prefix)
    function fixPrefixes(css) {
        $.each(css3defs, function (i, prop) {
            if (prop in css) {
                $.each(prefixes, function (j, pf) {
                    css["-" + pf + "-" + prop] = css[prop];
                });
            }
        });
    }

    //fix the prefix of the div(shower).
    //$.each([baseCSS, restoreCSS, slideDownCSS], function (i, css) {
    //    fixPrefixes(css);
    //});

    //set base styles.
    //$shower.css($.extend(baseCSS, restoreCSS));

    //onload events register. first we must check if the $.moblie is supported.
    $(document).on($.mobile ? "pageinit" : "ready", function () {
        var divshowTip = document.getElementById("divshowTip");
        $shower.appendTo(divshowTip);
    });

    //export the easy API to the developer.
    $.extend({
        showTip: function (message, isbgcolor, delay) {
            if (isbgcolor == 1) {//绿色
                $shower.css($.extend(baseCSS, restoreCSS));
            } else
            {
                $shower.css($.extend(baseCSS2, restoreCSS));
            }

            $shower.text(message);
            if (!isShowing) {
                $shower.css(slideDownCSS);
                isShowing = true;
            } else {
                clearTimeout(timerID);
            }
            timerID = setTimeout(function () {
                $shower.css(restoreCSS);
                isShowing = false
            }, delay || 2000);

            return $shower;
        }
    });
}(jQuery);