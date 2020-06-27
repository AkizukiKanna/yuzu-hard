$(function () {

    // 当滚动条不在最顶端时，显示顶部导航条
    $(window).scroll(function () {
        var winScrollHeight = $(window).scrollTop();// 获取滚动条滚动的距离(移动距离)
        var docHeight = $(document).height(); // 获取整个页面的高度(不只是窗口,还包括为显示的页面)
        // 滚动离开顶部时
        if (winScrollHeight != 0 && $("nav .top-bar").hasClass("scroll-top") == true) {
            $("nav .top-bar").removeClass("scroll-top").addClass("scroll-medium");
        }
        // 滚动到顶部时
        if (winScrollHeight == 0 && $("nav .top-bar").hasClass("scroll-medium") == true) {
            $("nav .top-bar").removeClass("scroll-medium").addClass("scroll-top");
        }
    });


    // 手机端点击菜单按钮
    $(".menu-icon").click(function () {
        unScroll();
        $("body").addClass("body-selected");
        $(".menu-icon").addClass("menu-icon-select");//.addClass("select");
        // $(".m-side-bar").addClass("select");
        $("._menu-right-wrapper").removeClass("m-display-none").addClass("m-display-block");

    });


    // 菜单打开后点击空白区域 复原
    $("._menu-right-wrapper").click(function () {
        $("body").removeClass("body-selected");
        $("._menu-right-wrapper").removeClass("m-display-block").addClass("m-display-none");
        // $(".m-side-bar").removeClass("select");
        $(".menu-icon").removeClass("menu-icon-select");//.removeClass("select");
        removeUnScroll();

    });








    //  禁用滚动条
    function unScroll() {
        var top = $(document).scrollTop();
        $(document).on('scroll.unable', function (e) {
            $(document).scrollTop(top);
        })
    }



    //  解除禁用滚动条
    function removeUnScroll() {
        $(document).unbind("scroll.unable");
    }



})
