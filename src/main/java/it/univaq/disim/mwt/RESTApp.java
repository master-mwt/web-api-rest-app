package it.univaq.disim.mwt;

import it.univaq.disim.mwt.resources.*;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rest")
public class RESTApp extends Application {

    private final Set<Class<?>> classes;

    public RESTApp() {
        HashSet<Class<?>> c = new HashSet<Class<?>>();

        c.add(AnniAccademiciRes.class);
        c.add(ClassiRes.class);
        c.add(FacoltaRes.class);
        c.add(AteneiRes.class);
        c.add(CorsiDiStudioRes.class);

        classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}
