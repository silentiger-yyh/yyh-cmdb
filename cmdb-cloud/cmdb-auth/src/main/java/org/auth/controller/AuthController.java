package org.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.silentiger.api.CommonResult;
import org.silentiger.constant.AuthConstant;
import org.silentiger.dto.oauth2.Oauth2TokenDto;
import org.silentiger.enumeration.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


@RestController
@RequestMapping("/oauth")
@Api(tags = "认证中心", value = "AuthController")
public class AuthController {

    @Autowired
    private TokenEndpoint tokenEndpoint;
    @Autowired
    private TokenStore tokenStore;


    @PostMapping("/token")
    @ApiOperation("认证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名"),
            @ApiImplicitParam(name = "password", value = "密码"),
            @ApiImplicitParam(name = "grant_type", value = "授权类型"),
            @ApiImplicitParam(name = "scopes", value = "授权范围"),
    })
    public CommonResult<Oauth2TokenDto> postAccessToken(HttpServletRequest request,
                                                @RequestBody HashMap<String, String> paramsMap) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(request.getUserPrincipal(), paramsMap).getBody();
        assert oAuth2AccessToken != null;
        String tokenValue = oAuth2AccessToken.getValue();
        String username = (String) oAuth2AccessToken.getAdditionalInformation().get("username");
        if (StringUtils.isBlank(tokenValue) || StringUtils.isBlank(username)) {
            return CommonResult.failed(ResultCodeEnum.FAILED.getMessage());
        }
        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                .jti(oAuth2AccessToken.getAdditionalInformation().get("jti").toString())
                .token(tokenValue)
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead(AuthConstant.JWT_TOKEN_PREFIX)
                .username(username)
                .build();
        return CommonResult.success(oauth2TokenDto);
    }

    @GetMapping(value = "/logout")
    @ApiOperation("登出")
    public CommonResult logOut(HttpServletRequest request) {
        String tokenWithPrefix = request.getHeader(AuthConstant.AUTHORIZATION);
        if (StringUtils.isBlank(tokenWithPrefix)) {
            return CommonResult.failed("无效TOKEN");
        }
        String accessToken = tokenWithPrefix.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(accessToken);
        if (oAuth2AccessToken != null) {
            OAuth2RefreshToken oAuth2RefreshToken = oAuth2AccessToken.getRefreshToken();
            //从tokenStore中移除token
            tokenStore.removeAccessToken(oAuth2AccessToken);
            tokenStore.removeRefreshToken(oAuth2RefreshToken);
            tokenStore.removeAccessTokenUsingRefreshToken(oAuth2RefreshToken);
            return CommonResult.success(null, "登出成功");
        } else {
            return CommonResult.forbidden("token已失效，请勿重复登出");
        }
    }

}

