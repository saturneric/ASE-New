$(document).ready(function () {
    // 当自动查询用户名是否有效
    $("input#username").on("input",function () {
        let studentId =  $("input#username").val();
        if(studentId.length === 10) {
            check_username();
        }
        else if(studentId.length > 10){

        }
    });
});

function notify_check() {
    ase_notification("danger", "提示", "账号或密码错误");
}

function login() {
    ase_form_post("/login/process","login-form", {
        success : function (result) {
            console.log(result);
        },
        error : function (result) {
          console.log("FAIL!!!");
        }
    })
}

function check_username(){
    const checker = {
        "checkType": "UsernameExistChecker",
        "username": $("input#username").val(),
        "password":"",
    };

    ase_post_object("/login/check", checker, {
        success: function (result) {
            console.log(result);
            const usernameGroup = $("div#username-group");
            if(result.userExist === true) {
                ase_set_input_success(usernameGroup);
            }
            else{
                ase_set_input_error(usernameGroup);
            }
        }
    });
}