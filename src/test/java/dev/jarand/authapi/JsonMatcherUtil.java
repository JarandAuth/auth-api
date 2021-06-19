package dev.jarand.authapi;

import org.json.JSONObject;
import org.skyscreamer.jsonassert.Customization;

public final class JsonMatcherUtil {

    public static Customization isPresent(String jsonPath) {
        return Customization.customization(jsonPath, (o1, o2) -> !JSONObject.NULL.equals(o1) && !JSONObject.NULL.equals(o2));
    }
}
