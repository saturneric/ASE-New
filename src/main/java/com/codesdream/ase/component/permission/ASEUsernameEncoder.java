package com.codesdream.ase.component.permission;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class ASEUsernameEncoder {
    public String encode(CharSequence charSequence){
        return "u_id_" + DigestUtils.sha256Hex(charSequence.toString());
    }

    public boolean matches(CharSequence charSequence, String s){
        return s.equals(encode(charSequence.toString()));
    }
}
