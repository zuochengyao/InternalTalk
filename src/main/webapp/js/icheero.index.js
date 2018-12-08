$(function () {
    $('#fullpage').fullpage({
        // 导航
        menu: '#menu',
        anchors: ['cheese', 'zero', 'blog'],
        // navigationPosition: 'right',
        // navigation: true,blog
        // 设计
        sectionsColor: ['#FFFFFF', '#D4C191', '#7E8F7C'],
        // 滚动
        css3: true,
        scrollBar: true,
        // loopBottom: true,
        // 定义是否在最后一个区段向下滚动，或是否应该向下滚动到第一个区段，或如果在第一个区段向上滚动时是否应该滚动到最后一个区段。
        // 不兼容loopTop，loopBottom或站点中存在的任何滚动条（scrollBar：true或autoScrolling：false）。
        // continuousVertical: true,
        //定义水平滑块是否在到达上一张或上一张幻灯片后循环
        loopHorizontal: false
    });
});
