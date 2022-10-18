package com.douyin.domain.to;

import com.douyin.valid.GenCode;
import com.douyin.valid.LoginWithCode;
import com.douyin.valid.LoginWithPwd;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

/**
 * @author Cra2iTeT
 * @date 2022/10/14 10:41
 */
@Data
public class LoginFormTo {

    @NotBlank(message = "邮箱号不能为空", groups = {LoginWithCode.class, LoginWithPwd.class, GenCode.class})
    @Email(message = "邮箱号格式不正确", groups = {LoginWithCode.class, LoginWithPwd.class, GenCode.class})
    private String email;

    @NotBlank(message = "密码不能为空", groups = {LoginWithPwd.class})
    @Null(message = "密码必须为空", groups = {LoginWithCode.class, GenCode.class})
    @Length(min = 6, message = "密码不能少于6位", groups = {LoginWithPwd.class})
    private String pwd;

    @NotBlank(message = "验证码不能为空", groups = {LoginWithCode.class})
    @Null(message = "验证码必须为空", groups = {LoginWithPwd.class, GenCode.class})
    @Length(min = 6, max = 6, message = "验证码必须为6位", groups = {LoginWithCode.class})
    private String code;
}
