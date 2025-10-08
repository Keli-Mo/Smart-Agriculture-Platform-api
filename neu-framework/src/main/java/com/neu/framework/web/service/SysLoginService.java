package com.neu.framework.web.service;

import javax.annotation.Resource;

import com.neu.common.utils.SecurityUtils;
import com.neu.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.neu.common.constant.Constants;
import com.neu.common.core.domain.model.LoginUser;
import com.neu.common.core.redis.RedisCache;
import com.neu.common.exception.CustomException;
import com.neu.common.exception.user.CaptchaException;
import com.neu.common.exception.user.CaptchaExpireException;
import com.neu.common.exception.user.UserPasswordNotMatchException;
import com.neu.common.utils.MessageUtils;
import com.neu.framework.manager.AsyncManager;
import com.neu.framework.manager.factory.AsyncFactory;

import java.util.Collection;

/**
 * 登录校验方法
 * 
 * @author neu
 */
@Component
public class SysLoginService
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysUserService userService;

    /**
     * 登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid)
    {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
        // 用户验证
        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        // 单点登录
//        Collection<String> keys = redisCache.keys(Constants.LOGIN_TOKEN_KEY + "*");
//        for (String key : keys) {
//            LoginUser user = redisCache.getCacheObject(key);
//            if(username.equals(user.getUsername())){
//                redisCache.deleteObject(key);
//            }
//        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    public String loginWithoutCode(String username, String password)
    {
        // 用户验证
        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                System.out.println(e.getMessage());
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        // 单点登录
//        Collection<String> keys = redisCache.keys(Constants.LOGIN_TOKEN_KEY + "*");
//        for (String key : keys) {
//            LoginUser user = redisCache.getCacheObject(key);
//            if(username.equals(user.getUsername())){
//                redisCache.deleteObject(key);
//            }
//        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 手机登录验证
     *
     * @param username 用户名
     * @param otp 验证码
     * @return 结果
     */
    public String mobileLogin(String username, String otp, String password)
    {
        userService.resetUserPwd(username, SecurityUtils.encryptPassword(password));

        // 用户验证
        Authentication authentication = null;

        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        // 单点登录
//        Collection<String> keys = redisCache.keys(Constants.LOGIN_TOKEN_KEY + "*");
//        for (String key : keys) {
//            LoginUser user = redisCache.getCacheObject(key);
//            if(username.equals(user.getUsername())){
//                redisCache.deleteObject(key);
//            }
//        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 登录验证
     *
     * @param username 用户名
     * @return 结果
     */
    public String smsLogin(String username)
    {
        // 用户验证
        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new SmsCodeAuthenticationToken(username));
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        // 单点登录
//        Collection<String> keys = redisCache.keys(Constants.LOGIN_TOKEN_KEY + "*");
//        for (String key : keys) {
//            LoginUser user = redisCache.getCacheObject(key);
//            if(username.equals(user.getUsername())){
//                redisCache.deleteObject(key);
//            }
//        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }
}
