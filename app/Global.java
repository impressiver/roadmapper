import com.fasterxml.jackson.databind.node.ObjectNode;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.libs.F.*;

import play.libs.Json;

import static play.mvc.Results.*;

public class Global extends GlobalSettings {

    public Promise<SimpleResult> onError(RequestHeader request, Throwable t) {
        // todo: this seems weird
        ObjectNode json = Json.newObject();
        json.put("globalError", "unexpectedError");

        return Promise.<SimpleResult>pure(internalServerError(
            json
        ));
    }

}
