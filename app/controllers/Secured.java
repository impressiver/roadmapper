package controllers;

import com.newrelic.api.agent.NewRelic;
import play.Configuration;
import play.Play;
import play.libs.WS;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

public class Secured extends Security.Authenticator {
    public final static String OAUTH_BASE = "https://dev--auth-newrelic-com-rzk4pe3f3jxn.runscope.net";
    public final static long OAUTH_TIMEOUT = 15000;

    @Override
    public String getUsername(Http.Context ctx) {
        Configuration config = Play.application().configuration();
        String email;

        // Allow oauth override in dev
        if (Play.isDev()) {
            email = config.getString("auth.impersonate.email");

            if (email != null && !email.isEmpty()) {
                return email;
            }
        }

        String accessToken = ctx.session().get("oauth-access-token");
        if (accessToken == null) {
            return null;
        }

        email = ctx.session().get("oauth-email");
        long lastCheck = Long.parseLong(ctx.session().get("oauth-last-check"));

        // check again if we've timed out
        if (System.currentTimeMillis() > lastCheck + OAUTH_TIMEOUT) {
            WS.Response response = WS.url(config.getString("oauth.userDetailUrl"))
                    .setHeader("Authorization", "Bearer " + accessToken)
                    .get().get();

            if (response.getStatus() != 200) {
                ctx.session().remove("oauth-access-token");
                return null;
            } else {
                email = response.asJson().get("email").asText();
                ctx.session().put("oauth-email", email);
                ctx.session().put("oauth-last-check", String.valueOf(System.currentTimeMillis()));
            }
        }

        NewRelic.addCustomParameter("username", email);

        return email;
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return redirect(Play.application().configuration().getString("oauth.authorizeUrl"));
    }
}
