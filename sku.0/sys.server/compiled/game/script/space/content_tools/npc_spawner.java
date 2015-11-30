package script.space.content_tools;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_utils;
import script.library.planetary_map;
import script.library.objvar_mangle;
import script.library.space_quest;
import script.library.space_create;
import script.library.ship_ai;
import script.library.utils;
import script.library.space_battlefield;
import java.lang.Math;
import script.library.hue;

public class npc_spawner extends script.base_script
{
    public npc_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        LOG("space", "ONINTIAILIZE");
        String strTest = "" + self;
        dictionary dctSpawnInfo = dataTableGetRow("datatables/space_content/npc_spawners.iff", strTest);
        if (dctSpawnInfo == null)
        {
            LOG("space", "NO ENTRY FOR " + self);
            obj_id objTest = createObject("object/tangible/gravestone/gravestone01.iff", getLocation(self));
            setName(objTest, "BAD NPC SPAWNER IS HERE!, NO ENTRY IN NPC SPAWNERS DATATABLE");
            return SCRIPT_CONTINUE;
        }
        String strTemplate = dctSpawnInfo.getString("strTemplate");
        if (strTemplate.equals("unused"))
        {
            return SCRIPT_CONTINUE;
        }
        location locTest = getLocation(self);
        obj_id objNPC = null;
        try
        {
            objNPC = createObject(strTemplate, getTransform_o2p(self), locTest.cell);
        }
        catch(Throwable err)
        {
            LOG("space", "BAD TEMPLATE FOR " + self);
            locTest.x = locTest.x + 1;
            obj_id objTest = createObject("object/tangible/gravestone/gravestone01.iff", locTest);
            setName(objTest, "BAD NPC SPAWNER IS HERE!, BAD TEMPLATE IN NPC SPAWNERS DATATABLE FOR ENTRY " + self + "TEMPLATE IS " + strTemplate);
            return SCRIPT_CONTINUE;
        }
        for (int intI = 1; intI < 5; intI++)
        {
            String strScript = dctSpawnInfo.getString("strScript" + intI);
            if (!strScript.equals(""))
            {
                if (!hasScript(objNPC, strScript))
                {
                    int intReturn = attachScript(objNPC, strScript);
                    if (intReturn != SCRIPT_CONTINUE)
                    {
                        if (!hasScript(objNPC, strScript))
                        {
                            setName(objNPC, "BAD SCRIPT IN TABLE FOR " + self + " SCRIPT IS " + strScript);
                        }
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        String sidName = dctSpawnInfo.getString("sidName");
        if (!sidName.equals(""))
        {
            setName(objNPC, "");
            setName(objNPC, new string_id("npc_spawner_n", sidName));
        }
        setInvulnerable(objNPC, true);
        if (isMob(objNPC))
        {
            setCondition(objNPC, CONDITION_SPACE_INTERESTING);
            setCondition(objNPC, CONDITION_CONVERSABLE);
        }
        String strPrimaryCategory = dctSpawnInfo.getString("strPrimaryCategory");
        if (!strPrimaryCategory.equals(""))
        {
            String strSecondaryCategory = dctSpawnInfo.getString("strSecondaryCategory");
            LOG("space", "strLocationName [npc_spawner_n]:" + sidName);
            string_id strMapNameId = new string_id("npc_spawner_n", sidName);
            obj_id objContainer = getTopMostContainer(self);
            locTest = getLocation(self);
            if (isIdValid(objContainer))
            {
                locTest = getLocation(objContainer);
            }
            addPlanetaryMapLocation(self, utils.packStringId(strMapNameId), (int)locTest.x, (int)locTest.z, strPrimaryCategory, strSecondaryCategory, MLT_STATIC, planetary_map.NO_FLAG);
        }
        return SCRIPT_CONTINUE;
    }
}
