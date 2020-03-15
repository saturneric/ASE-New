package com.codesdream.ase.component.auth;

import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

// SHA1算法不可逆加密 主要用于JSON签名
@Component
public class SHA1Encoder {
    String encode(CharSequence charSequence){
        return DigestUtils.sha1Hex(charSequence.toString());
    }

    boolean match(CharSequence charSequence, String s){
        return s.equals(encode(charSequence));
    }
}
