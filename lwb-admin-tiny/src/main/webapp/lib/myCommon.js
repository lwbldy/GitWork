$(function () {

})

/*弹出层*/
/*
    参数解释：
    title   标题
    url     请求的url
    id      需要操作的数据id
    w       弹出层宽度（缺省调默认值）
    h       弹出层高度（缺省调默认值）
*/
function admin_show(title,url,w,h){
    if (title == null || title == '') {
        title=false;
    };
    if (url == null || url == '') {
        url="404.html";
    };
    if (w == null || w == '') {
        w=($(window).width()*0.9);
    };
    if (h == null || h == '') {
        h=($(window).height() - 50);
    };
    layer.open({
        type: 2,
        area: [w+'px', h +'px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: true,
        shade:0.4,
        title: title,
        content: url

    });
}

/**
 * 时间戳转换成时间
 * @param timestamp
 * @returns {*}
 */
function timestampToTime(timestamp) {
    var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = (date.getDate()< 10 ? '0'+ date.getDate() : date.getDate()) + ' ';
    h = (date.getHours()< 10 ? '0'+ date.getHours() : date.getHours()) + ':';
    m = (date.getMinutes()< 10 ? '0'+ date.getMinutes() : date.getMinutes()) + ':';
    s = date.getSeconds()< 10 ? '0'+ date.getSeconds() : date.getSeconds();
    return Y+M+D+h+m+s;
}