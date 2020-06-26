$(function () {

    // 当滚动条不在最顶端时，显示顶部导航条
    $(window).scroll(function () {
        var winScrollHeight = $(window).scrollTop();// 获取滚动条滚动的距离(移动距离)
        var docHeight = $(document).height(); // 获取整个页面的高度(不只是窗口,还包括为显示的页面)
        // console.log(winScrollHeight+" "+docHeight);
        // 滚动离开顶部时
        if (winScrollHeight != 0 && $("nav.top-bar").hasClass("scroll-top") == true) {
            $("nav.top-bar").removeClass("scroll-top").addClass("scroll-medium");
        }
        // 滚动到顶部时
        if (winScrollHeight == 0 && $("nav.top-bar").hasClass("scroll-medium") == true) {
            $("nav.top-bar").removeClass("scroll-medium").addClass("scroll-top");
        }
    });
})
