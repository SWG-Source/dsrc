package script.city;

import script.dictionary;
import script.library.gcw;
import script.obj_id;

public class ship_spawner extends script.base_script
{
    public ship_spawner()
    {
    }

    public int OnInitialize(obj_id self) throws InterruptedException {
        messageTo(self, "evaluateSpawn", null, 0.0f, false);
        return SCRIPT_CONTINUE;
    }

    public int evaluateSpawn(obj_id self, dictionary params) throws InterruptedException{
        // get the current imperial GCW score for the location.
        int impScore = gcw.getGcwImperialScorePercentile(gcw.getGcwRegion(self));

        String template = "object/static/structure/general/distant_ship_controller2.iff";
        if(impScore < 50) {
            template = "object/static/structure/general/distant_ship_controller_rebel.iff";
        } else if(impScore > 50){
            template = "object/static/structure/general/distant_ship_controller_imperial.iff";
        }

        if(null != params) {
            obj_id controller = params.getObjId("currentController");
            String currentTemplate = getTemplateName(controller);
            if (currentTemplate != null && currentTemplate.equals(template)) {
                messageTo(self, "evaluateSpawn", params, 60.0f, false);
                return SCRIPT_CONTINUE;
            }
            // our template is different, let's get rid of the old one before we create a new one.
            destroyObject(params.getObjId("currentController"));
        } else {
            params = new dictionary();
        }

        obj_id controller = createObject(template, getLocation(self));

        params.put("currentController", controller);
        params.put("lastImperialScore", impScore);

        // check the spawn point again in 60 seconds.
        messageTo(self, "evaluateSpawn", params, 60.0f, false);

        return SCRIPT_CONTINUE;
    }
}
