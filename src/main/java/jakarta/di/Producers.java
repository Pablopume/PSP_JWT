package jakarta.di;

import jakarta.enterprise.inject.Produces;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;


public class Producers {
    @Produces
    public Jsonb producesJsonb()
    {
        return JsonbBuilder.create();
    }



}
