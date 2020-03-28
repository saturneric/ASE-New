package com.codesdream.ase.component.json.respond;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class PermissionJSONRespond {
    private Boolean tagExist = null;
    private Boolean actionSuccess = null;
    private Integer tagId = null;
    private String tagName = null;
    private Set<Integer> users = null;
}
