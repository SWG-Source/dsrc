package script.theme_park.poi.tatooine.city;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;

public class poi_city_street_music extends script.base_script
{
    public poi_city_street_music()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int handlePlaying(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "handlePlaying", null, 600, true);
        obj_id musician = getObjIdObjVar(self, "musician");
        if (!exists(musician))
        {
            return SCRIPT_CONTINUE;
        }
        setAnimationMood(musician, "whatever");
        return SCRIPT_CONTINUE;
    }
    public void spawnMusician(obj_id baseObject) throws InterruptedException
    {
        obj_id musician = create.themeParkObject("noble", 1f, 0f, "objectDestroyed", 0f);
        createObject("object/tangible/instrument/kloo_horn.iff", musician, "");
        setObjVar(baseObject, "musician", musician);
    }
    public int objectDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        spawnMusician(self);
        return SCRIPT_CONTINUE;
    }
    public int checkForScripts(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasScript(self, "theme_park.poi.launch"))
        {
            detachScript(self, "theme_park.poi.launch");
        }
        return SCRIPT_CONTINUE;
    }
}
