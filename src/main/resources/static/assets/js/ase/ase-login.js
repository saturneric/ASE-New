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
                if(usernameGroup.hasClass("has-error")){
                    usernameGroup.removeClass("has-error");
                }
                if(!usernameGroup.hasClass("has-success")){
                    usernameGroup.addClass("has-success");
                }
            }
            else{
                if(usernameGroup.hasClass("has-success")){
                    usernameGroup.removeClass("has-success");
                }
                if(!usernameGroup.hasClass("has-error")){
                    usernameGroup.addClass("has-error");
                }
            }
        }
    });
}