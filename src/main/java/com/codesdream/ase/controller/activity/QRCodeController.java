package com.codesdream.ase.controller.activity;

import com.codesdream.ase.component.activity.QrCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@Controller
public class QRCodeController {

    /**
     * 二维码
     *
     * @param request
     * @param response
     */
    @GetMapping("/qrcode")
    public void qrCode(HttpServletRequest request, HttpServletResponse response) {
        StringBuffer url = request.getRequestURL();
        // 域名
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();

        // 再加上请求链接
        String requestUrl = tempContextUrl + "/index";
        try {
            OutputStream os = response.getOutputStream();
            QrCodeUtils.encode(requestUrl, "", os, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
