
// 快速打印信息
function ase_debug_log_info(message){
    const head = "[ASE JQuery Framework] INFO: ";
    console.log(head + message)
}

// 快速打印错误信息
function ase_debug_log_error(message){
    const head = "[ASE JQuery Framework] Error ";
    console.error(head + message)
}

function ase_create_random_id() {
    return (Math.random()*10000000).toString(16).substr(0,4)
        +'-'+(new Date()).getTime()
        +'-'+Math.random().toString().substr(2,5);
}

// 获得一个HTML标签
function ase_tag_getter(tag, id){
    return $(tag+"#"+id);
}

// 获得一个input标签的内容
function ase_input_content_getter(id){
    return  ase_tag_getter("input", id).val();
}

// 在某个标签的内部追加一个标签
function ase_tag_append(tag, id, tag_append) {
    ase_tag_getter(tag, id).append(tag_append);
}

// 在某个标签的后面追加一个标签
function ase_tag_after(tag, id, tag_append) {
    ase_tag_getter(tag, id).after(tag_append);
}

// 获得一个div标签
function ase_div_getter(id){
    return ase_tag_getter("div", id);
}

// 在某个div内部追加一个标签
function ase_div_append(id, tag_append) {
    ase_tag_append("div", id, tag_append);
}

// 修改标签的某个属性
function ase_modify_attr(tag, attr_name, attr_value) {
    tag.attr(attr_name, attr_value);
}

// 修改标签的data-notify属性
function ase_add_attr_data_notify(tag, value) {
    ase_modify_attr(tag, 'data-notify', value);
}

// 修改标签的data-notify-position属性
function ase_add_attr_data_notify_position(tag, position) {
    ase_modify_attr(tag, 'data-notify-position', position);
}

// 给标签添加显示值
function ase_add_show_text(tag, text) {
    tag.html(text);

}

// 创建一个标签
function ase_create_tag(tag, id) {
    const new_tag = $("<" + tag + ">" + "</" + tag + ">");
    if(id !== undefined) {
        ase_modify_attr(new_tag, 'id', id);
    }
    return new_tag;
}

// 给标签指定css属性
function ase_add_attr_style(tag, value) {
    ase_modify_attr(tag, 'style', value);
}

// 给标签指定class属性
function ase_add_attr_class(tag, value) {
    ase_modify_attr(tag, 'class', value);
}

// 给标签指定class属性
function ase_add_attr_role(tag, value) {
    ase_modify_attr(tag, 'role', value);
}

// 获得一个提示气泡
function ase_notification_getter() {
    const notification = ase_create_tag("div", ase_create_random_id());
    const close_button = ase_create_tag('button');
    const icon_span = ase_create_tag('span');
    const title_span = ase_create_tag('span');
    const message_span = ase_create_tag('span');

    notification.append(close_button);
    notification.append(icon_span);
    notification.append(title_span);
    notification.append(message_span);

    ase_add_attr_data_notify(notification, 'container');
    ase_add_attr_data_notify_position(notification, 'top-right');
    ase_add_attr_role(notification, 'alert');
    ase_add_attr_class(notification, 'col-10 col-xs-11 col-sm-4 alert alert-danger');
    ase_add_attr_style(notification, 'display: inline-block; ' +
        'margin: 0px auto; ' +
        'padding-left: 65px; ' +
        'position: fixed; ' +
        'transition: all 0.5s ease-in-out 0s; ' +
        'z-index: 1031; ' +
        'top: 254px; ' +
        'right: 20px;');


    ase_add_attr_data_notify(close_button, 'dismiss');
    ase_add_attr_data_notify(icon_span, 'icon');
    ase_add_attr_data_notify(title_span, 'title');
    ase_add_attr_data_notify(message_span, 'message');
}


// 快速给服务器以POST方法传递对象
function ase_post_object(url, object, callback){
    if(!callback.hasOwnProperty("success")){
        ase_debug_log_info("Function Callback NULL")
        callback.success = function (result) {

        }
    }

    if(!callback.hasOwnProperty("error")){
        ase_debug_log_info("the error callback is null")
        callback.error = function (result) {
            ase_debug_log_info("Post Request Error Occurred");
        };
    }
    // 对成功调用返回进行装饰
    callback.success = request_success_callback_decorator(callback.success);

    $.ajax({
        type: "POST",
        dataType: "json",
        url: url ,
        data: JSON.stringify(object),
        success: callback.success,
        error : callback.error,
    });
}

// 对成功返回的回调函数进行装饰
function request_success_callback_decorator(callback){
    return function (result) {
        if(result.status === "failed"){
            ase_system_status_checker(result)
        }
        else callback(result);
    }
}

// 对服务器返回的状态进行检查
function ase_system_status_checker(result) {
    ase_debug_log_error("Request Failed.");
    ase_debug_log_error(result);
}