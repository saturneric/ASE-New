package com.codesdream.ase.component.datamanager;

import com.codesdream.ase.exception.innerservererror.HandlingErrorsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.stereotype.Controller;

@Controller
public class JsonPathParameter {
    public <T> T parsePathToObject(JsonPatch patch, T object){
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode patched = patch.apply(mapper.convertValue(object, JsonNode.class));
            return (T) mapper.treeToValue(patched, object.getClass());
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new HandlingErrorsException(e.getMessage());
        }

    }
}
