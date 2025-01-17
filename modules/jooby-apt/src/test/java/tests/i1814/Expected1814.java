package tests.i1814;

import io.jooby.Extension;
import io.jooby.MvcFactory;
import io.jooby.Route;

import edu.umd.cs.findbugs.annotations.NonNull;
import jakarta.inject.Provider;

public class Expected1814 implements MvcFactory {

  @Override public boolean supports(@NonNull Class type) {
    return false;
  }

  @NonNull @Override public Extension create(@NonNull Provider provider) {
    return application -> {
      Route route = application.get("/1814", ctx -> {
        C1814 controller = (C1814) provider.get();
        String type = ctx.query("type").value();
        return controller.getUsers(type, ctx.getRoute());
      });
      route.setReturnType(U1814.class);
    };
  }

}
