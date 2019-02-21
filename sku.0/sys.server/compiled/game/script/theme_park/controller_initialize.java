package script.theme_park;

import script.dictionary;
import script.library.utils;
import script.location;
import script.obj_id;

public class controller_initialize extends script.base_script
{
    public controller_initialize()
    {
    }
    public static final String CONTROLLER = "object/tangible/ground_spawning/patrol_waypoint.iff";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        location selfLoc = getLocation(self);
        obj_id objects[] = getObjectsInRange(selfLoc, 0.1f);
        boolean exists = false;
        if (objects != null || objects.length > 0)
        {
            for (obj_id object : objects) {
                if ((getTemplateName(object)).equals(CONTROLLER)) {
                    exists = true;
                }
            }
        }
        if (!exists)
        {
            createController(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int createController(obj_id self, dictionary params) throws InterruptedException
    {
        createController(self);
        return SCRIPT_CONTINUE;
    }
    public void createController(obj_id self) throws InterruptedException
    {
        obj_id object = createObject(CONTROLLER, getLocation(self));
        // why are we persisting??
        // persistObject(object);
        String objVarString = null;
        if (hasObjVar(self, "objVarString"))
        {
            objVarString = getStringObjVar(self, "objVarString");
        }
        String scriptString = null;
        if (hasObjVar(self, "scriptString"))
        {
            scriptString = getStringObjVar(self, "scriptString");
        }
        setObjVarString(object, objVarString);
        setScriptString(object, scriptString);
    }
    public void setScriptString(obj_id subject, String scriptString) throws InterruptedException
    {
        if (scriptString == null)
        {
            return;
        }
        String[] scripts = split(scriptString, ';');
        for (String script : scripts) {
            attachScript(subject, script);
        }
    }
    public void setObjVarString(obj_id newObject, String objVarString) throws InterruptedException
    {
        if (objVarString == null || objVarString.equals("none"))
        {
            return;
        }
        String[] parse = split(objVarString, ';');
        if (parse == null || parse.length == 0)
        {
            return;
        }
        setObjVar(newObject, "spawnedBy", getSelf());
        for (int i = 0; i < parse.length; i++)
        {
            String[] typeDataSplit = split(parse[i], ':');
            String type = typeDataSplit[0];
            String data = typeDataSplit[1];
            String[] nameValueSplit = split(data, '=');
            String name = nameValueSplit[0];
            String value = nameValueSplit[1];
            if (type.equals("int")) {
                setObjVar(newObject, name, utils.stringToInt(value));
            }
            if (type.equals("float")) {
                setObjVar(newObject, name, utils.stringToFloat(value));
            }
            if (type.equals("string")) {
                setObjVar(newObject, name, value);
            }
            if (type.equals("boolean") && (value.equals("true") || value.equals("1"))) {
                setObjVar(newObject, name, true);
            }
            if (type.equals("boolean") && (value.equals("false") || value.equals("0"))) {
                setObjVar(newObject, name, false);
            }
        }
    }
}
