function layuiIframe(title, url) {
    layer.open({
        type: 2,
        title: title,
        closeBtn: 1,
        shade: [0],
        area: ['1000px', '600px'],
        offset: 'auto',
        content: [url, 'yes']
    });
}