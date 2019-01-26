package script.demo;

import script.dictionary;
import script.library.create;
import script.location;
import script.obj_id;

public class grievous_demoer extends script.base_script
{
    public grievous_demoer()
    {
    }
    public int OnSpeaking(obj_id self, String strText) throws InterruptedException
    {
        String[] strCommands = split(strText, ' ');
        if (strCommands[0].equalsIgnoreCase("setupDemo1"))
        {
            location jabbaPalace = new location(2591, 0, 4423);
            obj_id palace1 = createObject("object/building/tatooine/palace_tatooine_jabba.iff", jabbaPalace);
            setObjVar(self, "palace1", palace1);
            dictionary dctParams = new dictionary();
            dctParams.put("objBuilding", palace1);
            String[] strScripts = getScriptList(palace1);
            detachScriptList(strScripts, palace1);
            LOG("test", "Messaging self with " + dctParams.toString());
            messageTo(self, "cleanoutBuilding", dctParams, 15, false);
        }
        if (strCommands[0].equalsIgnoreCase("setupDemo2"))
        {
            location jabbaPalace = new location(-2480, 0, -5109);
            obj_id palace2 = createObject("object/building/tatooine/palace_tatooine_jabba.iff", jabbaPalace);
            setObjVar(self, "palace2", palace2);
            dictionary dctParams = new dictionary();
            dctParams.put("objBuilding", palace2);
            String[] strScripts = getScriptList(palace2);
            detachScriptList(strScripts, palace2);
            LOG("test", "Messaging self with " + dctParams.toString());
            messageTo(self, "cleanoutBuilding", dctParams, 15, false);
        }
        if (strCommands[0].equalsIgnoreCase("demoLoc1"))
        {
            warpPlayer(self, "tatooine", 2601, 0, 4598, null, 0, 0, 0, "", false);
        }
        if (strCommands[0].equalsIgnoreCase("demoLoc2"))
        {
            warpPlayer(self, "tatooine", -2471, 0, -4929, null, 0, 0, 0, "", false);
        }
        if (strCommands[0].equalsIgnoreCase("startDemo1"))
        {
            sendSystemMessageTestingOnly(self, "Demo is starting now!  Proceed into the palace!");
            obj_id palace1 = getObjIdObjVar(self, "palace1");
            obj_id cell = getCellId(palace1, "foyer");
            location grievousSpawn = new location(-1.2f, 0.3f, 116.9f, "thm_tato_jabbas_palace", cell);
            obj_id grievous = create.object("demo_general_grievous", grievousSpawn);
            attachScript(grievous, "demo.grievous");
            setObjVar(grievous, "player", self);
            setObjVar(grievous, "demoNumber", 1);
        }
        if (strCommands[0].equalsIgnoreCase("startDemo2"))
        {
            sendSystemMessageTestingOnly(self, "Demo is starting now!  Proceed into the palace!");
            obj_id palace2 = getObjIdObjVar(self, "palace2");
            obj_id cell = getCellId(palace2, "foyer");
            location grievousSpawn = new location(-1.2f, 0.3f, 116.9f, "thm_tato_jabbas_palace", cell);
            obj_id grievous = create.object("demo_general_grievous", grievousSpawn);
            attachScript(grievous, "demo.grievous");
            setObjVar(grievous, "player", self);
            setObjVar(grievous, "demoNumber", 2);
        }
        if (strCommands[0].equalsIgnoreCase("cleanUpDemo1"))
        {
            obj_id palace1 = getObjIdObjVar(self, "palace1");
            destroyObject(palace1);
        }
        if (strCommands[0].equalsIgnoreCase("cleanUpDemo2"))
        {
            obj_id palace2 = getObjIdObjVar(self, "palace2");
            destroyObject(palace2);
        }
        return SCRIPT_CONTINUE;
    }
    public int resetDemo1(obj_id self, dictionary params) throws InterruptedException
    {
        warpPlayer(self, "tatooine", 2601, 0, 4598, null, 0, 0, 0, "", false);
        messageTo(self, "setUpDemo1", null, 1.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int resetDemo2(obj_id self, dictionary params) throws InterruptedException
    {
        warpPlayer(self, "tatooine", -2471, 0, -4929, null, 0, 0, 0, "", false);
        messageTo(self, "setUpDemo2", null, 1.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int setUpDemo1(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id palace1 = getObjIdObjVar(self, "palace1");
        obj_id cell = getCellId(palace1, "foyer");
        location grievousSpawn = new location(-1.2f, 0.3f, 116.9f, "thm_tato_jabbas_palace", cell);
        obj_id grievous = create.object("demo_general_grievous", grievousSpawn);
        attachScript(grievous, "demo.grievous");
        setObjVar(grievous, "player", self);
        setObjVar(grievous, "demoNumber", 1);
        return SCRIPT_CONTINUE;
    }
    public int setUpDemo2(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id palace2 = getObjIdObjVar(self, "palace2");
        obj_id cell = getCellId(palace2, "foyer");
        location grievousSpawn = new location(-1.2f, 0.3f, 116.9f, "thm_tato_jabbas_palace", cell);
        obj_id grievous = create.object("demo_general_grievous", grievousSpawn);
        attachScript(grievous, "demo.grievous");
        setObjVar(grievous, "player", self);
        setObjVar(grievous, "demoNumber", 2);
        return SCRIPT_CONTINUE;
    }
    public int cleanoutBuilding(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("test", "Cleaningout bulidng");
        obj_id objBuilding = params.getObjId("objBuilding");
        obj_id[] objContents = getContents(objBuilding);
        if (objContents != null)
        {
            for (obj_id objContent : objContents) {
                String strTemplate = getTemplateName(objContent);
                int intIndex = strTemplate.indexOf("cell.iff");
                LOG("test", "intIndex is " + intIndex);
                if (intIndex == -1) {
                    destroyObject(objContent);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void detachScriptList(String[] strScriptList, obj_id objObject) throws InterruptedException
    {
        for (String s : strScriptList) {
            String script = s;
            if (script.contains("script.")) {
                script = script.substring(7);
            }
            if (!script.equals("")) {
                detachScript(objObject, script);
            }
        }
    }
}
