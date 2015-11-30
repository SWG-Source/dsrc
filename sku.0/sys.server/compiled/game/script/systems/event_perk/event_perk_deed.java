package script.systems.event_perk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.dressup;
import script.library.sui;
import script.library.create;
import script.library.event_perk;
import script.library.prose;

public class event_perk_deed extends script.base_script
{
    public event_perk_deed()
    {
    }
    public static final String TBL_PLAYER_TYPES = "datatables/vendor/vendor_player_types.iff";
    public static final String TBL_ALLNPC_TYPES = "datatables/vendor/vendor_allnpc_types.iff";
    public static final String DATATABLE = "datatables/event_perk/perk_data.iff";
    public static final String STF_FILE = "event_perk";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String name = getStringObjVar(self, "event_perk.deedName");
        int numEntries = dataTableGetNumRows(DATATABLE);
        float terminalRegistration = 999999;
        setObjVar(self, "event_perk.terminal_registration", terminalRegistration);
        if (!hasObjVar(self, "event_perk.deedNumber"))
        {
            for (int i = 0; i < numEntries; i++)
            {
                String currentName = dataTableGetString(DATATABLE, i, "NAME");
                if (currentName.equals(name))
                {
                    setObjVar(self, "event_perk.deedNumber", i);
                }
            }
        }
        int deedNumber = getIntObjVar(self, "event_perk.deedNumber");
        if (!hasObjVar(self, "event_perk.redeeded"))
        {
            float lifeSpan = dataTableGetFloat(DATATABLE, deedNumber, "LIFESPAN");
            setObjVar(self, "event_perk.lifeSpan", lifeSpan);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        checkTimeLimit(self, player);
        string_id useEventPerk = new string_id(STF_FILE, "use_event_perk");
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, useEventPerk);
            if (hasObjVar(self, "event_perk.timeStamp"))
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("event_perk", "mnu_show_exp_time"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            useEventPerk(self, player);
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (hasObjVar(self, "event_perk.timeStamp"))
            {
                float lifeSpan = getFloatObjVar(self, "event_perk.lifeSpan");
                float timeStamp = getFloatObjVar(self, "event_perk.timeStamp");
                float rightNow = getGameTime();
                float expirationTimeMinutesFloat = ((lifeSpan + timeStamp) - rightNow) / 60;
                int expirationTimeMinutes = (int)expirationTimeMinutesFloat;
                prose_package showExpiration = new prose_package();
                showExpiration = prose.getPackage(new string_id("event_perk", "show_exp_time"));
                prose.setDI(showExpiration, expirationTimeMinutes);
                sendSystemMessageProse(player, showExpiration);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        checkTimeLimit(self, player);
        return SCRIPT_CONTINUE;
    }
    public void useEventPerk(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id inventory = getObjectInSlot(player, "inventory");
        if (!utils.isNestedWithin(self, player))
        {
            sendSystemMessage(player, new string_id(STF_FILE, "from_inventory_only"));
            return;
        }
        if (ai_lib.isInCombat(player))
        {
            sendSystemMessage(player, new string_id(STF_FILE, "not_in_combat"));
            return;
        }
        if (1 == getState(player, STATE_SWIMMING))
        {
            sendSystemMessage(player, new string_id(STF_FILE, "not_while_swimming"));
            return;
        }
        int deedNumber = getIntObjVar(self, "event_perk.deedNumber");
        String skillRequired = dataTableGetString(DATATABLE, deedNumber, "SKILL");
        if (!skillRequired.equals("none"))
        {
            if (!hasSkill(player, skillRequired))
            {
                sendSystemMessage(player, new string_id(STF_FILE, "lack_the_skill"));
                sendSystemMessage(player, "Skill required is " + skillRequired + ". I am deed number " + deedNumber, null);
                return;
            }
        }
        location here = getLocation(player);
        String scene = getCurrentSceneName();
        String sceneRestriction = dataTableGetString(DATATABLE, deedNumber, "SCENE");
        if (!sceneRestriction.equals("none"))
        {
            if (!scene.equals(sceneRestriction))
            {
                sendSystemMessage(player, new string_id(STF_FILE, "not_on_this_planet"));
                return;
            }
        }
        if (event_perk.canPlaceEventPerkHere(self, player, here))
        {
            spawnEventPerk(self, player, here);
        }
    }
    public void spawnEventPerk(obj_id self, obj_id player, location here) throws InterruptedException
    {
        if (!hasObjVar(self, "event_perk.timeStamp"))
        {
            float timeStamp = getGameTime();
            setObjVar(self, "event_perk.timeStamp", timeStamp);
        }
        int deedNumber = getIntObjVar(self, "event_perk.deedNumber");
        String type = dataTableGetString(DATATABLE, deedNumber, "TYPE");
        String spawn = dataTableGetString(DATATABLE, deedNumber, "SPAWN");
        String script = dataTableGetString(DATATABLE, deedNumber, "SCRIPT");
        float lifeSpan = getFloatObjVar(self, "event_perk.lifeSpan");
        float heading = getYaw(player);
        float timeStamp = getFloatObjVar(self, "event_perk.timeStamp");
        String ownerName = getFirstName(player);
        float terminalRegistration = getFloatObjVar(self, "event_perk.terminal_registration");
        String stringData1 = dataTableGetString(DATATABLE, deedNumber, "SDATA1");
        float floatData1 = dataTableGetFloat(DATATABLE, deedNumber, "FDATA1");
        float floatData2 = dataTableGetFloat(DATATABLE, deedNumber, "FDATA2");
        if (type.equals("static") || type.equals("recruiter") || type.equals("gamedroid"))
        {
            obj_id eventPerk = create.object(spawn, here);
            setObjVar(eventPerk, "event_perk.lifeSpan", lifeSpan);
            setObjVar(eventPerk, "event_perk.timeStamp", timeStamp);
            setObjVar(eventPerk, "event_perk.owner", player);
            setObjVar(eventPerk, "event_perk.ownerName", ownerName);
            setObjVar(eventPerk, "event_perk.terminal_registration", terminalRegistration);
            if (type.equals("recruiter"))
            {
                ai_lib.setDefaultCalmBehavior(eventPerk, ai_lib.BEHAVIOR_SENTINEL);
                ai_lib.stop(eventPerk);
                attachScript(eventPerk, "npc.faction_recruiter.recruiter_setup");
                attachScript(eventPerk, "npc.faction_recruiter.faction_recruiter");
                setInvulnerable(eventPerk, true);
                persistObject(eventPerk);
            }
            if (type.equals("gamedroid"))
            {
                string_id droidNameSID = new string_id("event_perk", stringData1);
                String droidName = getString(droidNameSID);
                setName(eventPerk, droidName);
                setInvulnerable(eventPerk, true);
                persistObject(eventPerk);
            }
            if (!script.equals("none"))
            {
                attachScript(eventPerk, script);
            }
            if (!stringData1.equals("none"))
            {
                setObjVar(eventPerk, "event_perk.stringData1", stringData1);
            }
            setYaw(eventPerk, heading);
            attachScript(eventPerk, "systems.event_perk.event_perk");
            persistObject(eventPerk);
            if (eventPerk != null)
            {
                setObjVar(eventPerk, "event_perk.perk_type", type);
                setObjVar(eventPerk, "event_perk.source_deed", getTemplateName(self));
                destroyObject(self);
                CustomerServiceLog("EventPerk", "(Perk Created) Type [" + type + "] Location [" + here + "] OID + [" + eventPerk + "].");
            }
            else 
            {
                sendSystemMessage(player, new string_id(STF_FILE, "bad_area"));
            }
        }
        if (type.equals("theater"))
        {
            if (script.equals("none"))
            {
                script = "";
            }
            obj_id eventPerk = createTheater(spawn, here, script, TLT_none);
            setObjVar(eventPerk, "event_perk.lifeSpan", lifeSpan);
            setObjVar(eventPerk, "event_perk.timeStamp", timeStamp);
            setObjVar(eventPerk, "event_perk.owner", player);
            setObjVar(eventPerk, "event_perk.ownerName", ownerName);
            setObjVar(eventPerk, "event_perk.terminal_registration", terminalRegistration);
            if (!stringData1.equals("none"))
            {
                setObjVar(eventPerk, "event_perk.stringData1", stringData1);
            }
            attachScript(eventPerk, "systems.event_perk.event_perk");
            if (eventPerk != null)
            {
                location termLoc = here;
                termLoc.x = termLoc.x + floatData1;
                termLoc.y = termLoc.y + floatData2;
                obj_id venueTerm = create.object("object/tangible/terminal/terminal_event_venue.iff", termLoc);
                setObjVar(venueTerm, "event_perk.lifeSpan", lifeSpan);
                setObjVar(venueTerm, "event_perk.timeStamp", timeStamp);
                setObjVar(venueTerm, "event_perk.owner", player);
                setObjVar(venueTerm, "event_perk.ownerName", ownerName);
                setObjVar(venueTerm, "event_perk.perk_type", type);
                setObjVar(venueTerm, "event_perk.event_perk", eventPerk);
                setObjVar(venueTerm, "event_perk.source_deed", getTemplateName(self));
                attachScript(venueTerm, "systems.event_perk.event_perk");
                CustomerServiceLog("EventPerk", "(Perk Created) Type [" + type + "] Location [" + here + "] OID + [" + eventPerk + "].");
                destroyObject(self);
            }
            else 
            {
                sendSystemMessage(player, new string_id(STF_FILE, "bad_area"));
            }
        }
        if (type.equals("honorguard"))
        {
            int headCount = 0;
            int numPerLine = (int)floatData1;
            int numRows = (int)floatData2;
            float offset = 2.0f;
            float xLoc = here.x;
            float yLoc = here.y;
            float zLoc = here.z;
            for (int i = 0; i < numPerLine; i++)
            {
                double xRowOffsetDbl = Math.sin(Math.toRadians(heading + 90)) * (i * offset);
                float xRowOffset = (float)xRowOffsetDbl;
                double zRowOffsetDbl = Math.cos(Math.toRadians(heading + 90)) * (i * offset);
                float zRowOffset = (float)zRowOffsetDbl;
                for (int j = 0; j < numRows; j++)
                {
                    double xSpawnDbl = Math.sin(Math.toRadians(heading)) * (j * offset) + xLoc + xRowOffset;
                    double zSpawnDbl = Math.cos(Math.toRadians(heading)) * (j * offset) + zLoc + zRowOffset;
                    float xSpawn = (float)xSpawnDbl;
                    float zSpawn = (float)zSpawnDbl;
                    location spawnPoint = new location();
                    spawnPoint.x = xSpawn;
                    spawnPoint.y = yLoc;
                    spawnPoint.z = zSpawn;
                    obj_id eventPerk = create.object(spawn, spawnPoint);
                    setYaw(eventPerk, heading);
                    if (!script.equals("none"))
                    {
                        attachScript(eventPerk, script);
                    }
                    setObjVar(eventPerk, "event_perk.lifeSpan", lifeSpan);
                    setObjVar(eventPerk, "event_perk.timeStamp", timeStamp);
                    setObjVar(eventPerk, "event_perk.owner", player);
                    setObjVar(eventPerk, "event_perk.ownerName", ownerName);
                    setObjVar(eventPerk, "event_perk.terminal_registration", terminalRegistration);
                    ai_lib.setDefaultCalmBehavior(eventPerk, ai_lib.BEHAVIOR_SENTINEL);
                    ai_lib.stop(eventPerk);
                    setInvulnerable(eventPerk, true);
                    setCreatureStatic(eventPerk, true);
                    persistObject(eventPerk);
                    attachScript(eventPerk, "systems.event_perk.event_perk");
                    if (!utils.hasScriptVar(self, "honorguard_leader"))
                    {
                        utils.setScriptVar(self, "honorguard_leader", eventPerk);
                        String gottenName = getName(eventPerk);
                        String actualName = getString(utils.unpackString(gottenName));
                        String leaderName = actualName + " ***";
                        setName(eventPerk, leaderName);
                        setObjVar(eventPerk, "event_perk.perk_type", type);
                        setObjVar(eventPerk, "event_perk.source_deed", getTemplateName(self));
                    }
                    obj_id leader = utils.getObjIdScriptVar(self, "honorguard_leader");
                    setObjVar(leader, "event_perk.honorguard_num_" + headCount, eventPerk);
                    headCount++;
                }
            }
            obj_id leader = utils.getObjIdScriptVar(self, "honorguard_leader");
            setObjVar(leader, "event_perk.honorguard_headCount", headCount);
            destroyObject(self);
            CustomerServiceLog("EventPerk", "(Perk Created) Type [honorguard] Location [" + here + "].");
        }
        if (type.equals("npc"))
        {
            utils.setScriptVar(self, "event_perk.lifeSpan", lifeSpan);
            utils.setScriptVar(self, "event_perk.timeStamp", timeStamp);
            utils.setScriptVar(self, "event_perk.heading", heading);
            createNpcActor(self, player, here);
        }
    }
    public void checkTimeLimit(obj_id self, obj_id player) throws InterruptedException
    {
        if (hasObjVar(self, "event_perk.timeStamp"))
        {
            float lifeSpan = getFloatObjVar(self, "event_perk.lifeSpan");
            float timeStamp = getFloatObjVar(self, "event_perk.timeStamp");
            float rightNow = getGameTime();
            float delta = rightNow - timeStamp;
            if (delta > lifeSpan)
            {
                destroyObject(self);
                sendSystemMessage(player, new string_id(STF_FILE, "deed_expired"));
            }
        }
        return;
    }
    public void createNpcActor(obj_id self, obj_id player, location here) throws InterruptedException
    {
        String[] randomOnly = 
        {
            "@player_structure:random"
        };
        utils.setScriptVar(self, "event_perk.checkGender", 0);
        String[] rawRaceTypes = dataTableGetStringColumn(TBL_ALLNPC_TYPES, 0);
        String[] raceTypes = new String[rawRaceTypes.length];
        for (int i = 0; i < rawRaceTypes.length; i++)
        {
            raceTypes[i] = "@player_structure:race_" + rawRaceTypes[i];
        }
        utils.setScriptVar(self, "event_perk.races", rawRaceTypes);
        utils.setScriptVar(self, "event_perk.checkGender", 1);
        sui.listbox(self, player, "@event_perk_npc_actor:race_type_d", sui.OK_CANCEL, "@event_perk_npc_actor:race_type_t", raceTypes, "handleNpcActorRaceSelect", true);
        return;
    }
    public int destroyYourself(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handleNpcActorRaceSelect(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "event_perk.raceIndex", idx);
        int checkGender = utils.getIntScriptVar(self, "event_perk.checkGender");
        if (checkGender == 1)
        {
            int[] hasFemale = dataTableGetIntColumn(TBL_ALLNPC_TYPES, 1);
            if (hasFemale[idx] == 0)
            {
                utils.setScriptVar(self, "event_perk.genderIndex", 0);
                sui.inputbox(self, player, "@event_perk_npc_actor:name_d", sui.OK_CANCEL, "@event_perk_npc_actor:name_t", sui.INPUT_NORMAL, null, "handleSetNpcActorName", null);
                return SCRIPT_CONTINUE;
            }
        }
        String[] possibleNPCGender = 
        {
            "@player_structure:male",
            "@player_structure:female"
        };
        sui.listbox(self, player, "@event_perk_npc_actor:gender_d", sui.OK_CANCEL, "@event_perk_npc_actor:gender_t", possibleNPCGender, "handleNpcActorGenderSelect", true);
        return SCRIPT_CONTINUE;
    }
    public int handleNpcActorGenderSelect(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "event_perk.genderIndex", idx);
        sui.inputbox(self, player, "@event_perk_npc_actor:name_d", sui.OK_CANCEL, "@event_perk_npc_actor:name_t", sui.INPUT_NORMAL, null, "handleSetNpcActorName", null);
        return SCRIPT_CONTINUE;
    }
    public int handleSetNpcActorName(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        String vendorName = sui.getInputBoxText(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if ((vendorName.equals("")) || isNameReserved(vendorName))
        {
            sendSystemMessage(player, new string_id(STF_FILE, "no_obscene_stuff"));
            sui.inputbox(self, player, "@event_perk_npc_actor:name_d", sui.OK_CANCEL, "@event_perk_npc_actor:name_t", sui.INPUT_NORMAL, null, "handleSetNpcActorName", null);
            return SCRIPT_CONTINUE;
        }
        if (vendorName.length() > 40)
        {
            vendorName = vendorName.substring(0, 39);
        }
        utils.setScriptVar(self, "event_perk.vendorName", vendorName);
        params.put("player", player);
        messageTo(self, "buildNpcActor", params, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int buildNpcActor(obj_id self, dictionary params) throws InterruptedException
    {
        int deedNumber = getIntObjVar(self, "event_perk.deedNumber");
        String type = dataTableGetString(DATATABLE, deedNumber, "TYPE");
        String spawn = dataTableGetString(DATATABLE, deedNumber, "SPAWN");
        obj_id player = params.getObjId("player");
        location here = getLocation(player);
        if (!event_perk.canPlaceEventPerkHere(self, player, here))
        {
            return SCRIPT_CONTINUE;
        }
        String ownerName = getFirstName(player);
        String terminalSuffix = "NPC";
        String vendorName = utils.getStringScriptVar(self, "event_perk.vendorName");
        string_id titleAppend = new string_id("event_perk_npc_actor", "title_append");
        String titleAppendStr = getString(titleAppend);
        vendorName += titleAppendStr + ownerName + ")";
        float lifeSpan = utils.getFloatScriptVar(self, "event_perk.lifeSpan");
        float timeStamp = utils.getFloatScriptVar(self, "event_perk.timeStamp");
        float heading = utils.getFloatScriptVar(self, "event_perk.heading");
        String vendorTemplate = "object/tangible/vendor/vendor_" + terminalSuffix;
        int raceIndex = utils.getIntScriptVar(self, "event_perk.raceIndex");
        int genderIndex = utils.getIntScriptVar(self, "event_perk.genderIndex");
        String[] raceTypes = utils.getStringArrayScriptVar(self, "event_perk.races");
        String[] playerTypes = dataTableGetStringColumn(TBL_PLAYER_TYPES, 0);
        String[] genderList = 
        {
            "male",
            "female"
        };
        String creatureName = "vendor";
        String templateName;
        if (raceIndex == 0)
        {
            templateName = playerTypes[rand(1, playerTypes.length - 1)] + "_" + genderList[genderIndex] + ".iff";
        }
        else 
        {
            templateName = raceTypes[raceIndex] + "_" + genderList[genderIndex] + ".iff";
        }
        obj_id vendor = create.object(create.TEMPLATE_PREFIX + "vendor/" + templateName, here);
        if ((vendor != null))
        {
            setInvulnerable(vendor, true);
            ai_lib.setDefaultCalmBehavior(vendor, ai_lib.BEHAVIOR_SENTINEL);
            setObjVar(vendor, "event_perk.lifeSpan", lifeSpan);
            setObjVar(vendor, "event_perk.timeStamp", timeStamp);
            setObjVar(vendor, "event_perk.owner", player);
            setObjVar(vendor, "event_perk.ownerName", ownerName);
            if (templateName.indexOf("ithorian") > -1)
            {
                dressup.dressNpc(vendor, "random_ithorian", true);
            }
            else 
            {
                dressup.dressNpc(vendor, "rich_no_jacket");
            }
        }
        setName(vendor, vendorName);
        attachScript(vendor, "systems.event_perk.event_perk_npc");
        attachScript(vendor, "systems.event_perk.event_perk");
        if (isIdValid(vendor))
        {
            persistObject(vendor);
            setObjVar(vendor, "event_perk.perk_type", type);
            setObjVar(vendor, "event_perk.source_deed", getTemplateName(self));
            destroyObject(self);
            CustomerServiceLog("EventPerk", "(Perk Created) Type [NPC Actor] Location [" + here + "] OID + [" + vendor + "].");
        }
        else 
        {
            sendSystemMessage(player, new string_id("event_perk", "npc_creation_error"));
        }
        return SCRIPT_CONTINUE;
    }
}
