package com.lwbldy.system.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.lwbldy.common.shiro.ShiroUtils;
import com.lwbldy.common.util.R;
import com.lwbldy.common.util.SessionNames;
import com.lwbldy.mbg.model.SysUser;
import com.lwbldy.system.service.SysMenuService;
import com.lwbldy.system.vo.MenuVO;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.List;

@Api(tags = "系统后台公共登录退出，获取验证码")
@Controller
public class SysCommonController {

    @Autowired
    private Producer captchaProducer = null;

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value="后台首页")
    @GetMapping({"","/sys/index"})
    public String index(){
        return "system/index";
    }

    @ApiOperation(value="欢迎界面")
    @GetMapping("sys/welcome")
    public String welcome(){
        return "system/welcome";
    }

    @ApiOperation(value="用户登录界面")
    @GetMapping("/sys/login")
    public String login(){
        return "system/login";
    }

    @ApiOperation(value="用户登录验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名"),
            @ApiImplicitParam(name = "password", value = "密码"),
            @ApiImplicitParam(name = "vercode", value = "验证码"),
    })
    @PostMapping("/sys/login")
    @ResponseBody
    public R loginin(String username, String password, HttpSession session, String vercode) {
        try {
            if (StringUtils.isNotEmpty(vercode)) {
                String original =(String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
                // 验证验证码是否成功
                if (vercode.equalsIgnoreCase(original)) {
                    Subject subject = ShiroUtils.getSubject();
                    UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                    subject.login(token);
                    SysUser sysUserEntity = (SysUser) SecurityUtils.getSubject().getPrincipal();
                    sysUserEntity.setSalt(null);
                    sysUserEntity.setPassword(null);
                    session.setAttribute(SessionNames.ADMIN_SESSION,sysUserEntity);
                    return R.ok("登录成功！").put("user", sysUserEntity);
                }
            }
            return R.error("验证码错误！");

        } catch (UnknownAccountException e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return R.error("账号或密码不正确");
        } catch (LockedAccountException e) {
            return R.error("账号已被锁定,请联系管理员");
        } catch (AuthenticationException e) {
            return R.error("账户验证失败");
        }
    }

    /**
     * 生成验证码
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value="获取图形验证码")
    @GetMapping(value = "/sys/captcha")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //生成验证码
        String capText = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        //向客户端写出
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    @ApiOperation(value="退出登录")
    @GetMapping(value = "/sys/logout")
    public String logout(HttpSession session) {
        ShiroUtils.logout();
        return "redirect:/";
    }

    @ApiOperation(value="获取菜单")
    @GetMapping(value = "/sys/menu")
    @ResponseBody
    public MenuVO menuAll(){
        SysUser sysUser = ShiroUtils.getUserEntity();
        return sysMenuService.queryMenu(sysUser.getUserId());
    }

    @ApiOperation(value="获取菜单")
    @GetMapping(value = "/sys/icon")
    public String icon(){
        return "system/icon";
    }

}
