package script.working.steve;

import script.*;
import script.library.*;

import java.util.Vector;

public class myscript extends script.base_script
{
    public myscript()
    {
    }
    public int[] anArrayFunc() throws InterruptedException
    {
        return new int[5];
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        boolean result;
        int intArrayIndex;
        obj_id other = null;
        if (other == self)
        {
        }
        else 
        {
            other = self;
        }
        debugServerConsoleMsg(self, "about to crash in C!");
        debugServerConsoleMsg(null, "crashing in C!");
        debugServerConsoleMsg(self, "after crash in C!");
        messageTo(self, "myMessageHandler", null, 0, true);
        
        debugServerConsoleMsg(null, "in debug mode");

        debugConsoleMsg(self, "myscript reload attached!");
        debugConsoleMsg(self, "current objvars:");
        listObjvars(self, self);
        debugConsoleMsg(self, "adding objvars");
        debugConsoleMsg(self, "integer");
        result = setObjVar(self, "idata", 111);
        if (result)
        {
            debugConsoleMsg(self, "ok");
        }
        else 
        {
            debugConsoleMsg(self, "fail");
        }
        debugConsoleMsg(self, "float");
        result = setObjVar(self, "fdata", 666.999f);
        if (result)
        {
            debugConsoleMsg(self, "ok");
        }
        else 
        {
            debugConsoleMsg(self, "fail");
        }
        debugConsoleMsg(self, "string");
        result = setObjVar(self, "sdata", "winona");
        if (result)
        {
            debugConsoleMsg(self, "ok");
        }
        else 
        {
            debugConsoleMsg(self, "fail");
        }
        debugConsoleMsg(self, "obj_id");
        result = setObjVar(self, "oid_data", self);
        if (result)
        {
            debugConsoleMsg(self, "ok");
        }
        else 
        {
            debugConsoleMsg(self, "fail");
        }
        debugConsoleMsg(self, "obj_id array");
        obj_id[] oidaData = new obj_id[3];
        oidaData[0] = self;
        oidaData[1] = self;
        oidaData[2] = self;
        result = setObjVar(self, "oidArray_data", oidaData);
        if (result)
        {
            debugConsoleMsg(self, "ok");
        }
        else 
        {
            debugConsoleMsg(self, "fail");
        }
        debugConsoleMsg(self, "sublist");
        result = setObjVar(self, "item1", 123);
        result = setObjVar(self, "item2", 123.456f);
        result = setObjVar(self, "sublist.sub item", "yadayada");
        if (result)
        {
            debugConsoleMsg(self, "result:");
            listObjvars(self, self);
        }
        else 
        {
            debugConsoleMsg(self, "setObjVar failed!");
        }
        debugConsoleMsg(self, "leaving OnAttach");
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "Bye bye!");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "I'm initialized!");
        return SCRIPT_CONTINUE;
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "I'm in the world!");
        return SCRIPT_CONTINUE;
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "I'm logging out!");
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        debugServerConsoleMsg(self, "From myscript.script OnSpeaking: " + text);
        if (text.equals("newdewd"))
        {
            location mylocation = getLocation(self);
            createObject("creature/fodder", mylocation);
        }
        else if (text.equals("testlib"))
        {
        }
        else if (text.equals("testweapon"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            obj_id weapon = getCurrentWeapon(target);
            debugSpeakMsg(self, "Weapon " + weapon + " damage = " + getAverageDamage(weapon));
        }
        else if (text.equals("testattach"))
        {
            attachScript(self, "steve.dummy");
        }
        else if (text.equals("testdetach"))
        {
            detachScript(self, "steve.myscript");
            setObjVar(self, "item1", 0);
            removeObjVar(self, "item2");
        }
        else if (text.equals("quest"))
        {
            messageTo(self, "spawnBoxDestroyed", null, 0, true);
        }
        else if (text.equals("broadcast"))
        {
            debugServerConsoleMsg(self, "broadcasting message");
            dictionary params = new dictionary();
        }
        else if (text.equals("messageTo"))
        {
            debugServerConsoleMsg(self, "testing messageTo");
            dictionary params = new dictionary();
            params.put("slef", self);
            messageTo(self, "myMessageHandler", params, 0.5f, false);
        }
        else if (text.equals("damagehouse"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                deltadictionary scriptvars = target.getScriptVars();
                obj_id house = scriptvars.getObjId("player_structure.parent");
                if (isIdValid(house))
                {
                    int maxhp = getMaxHitpoints(house);
                    int current = getHitpoints(house);
                    int hp = current - (int)(maxhp * 0.1f);
                    if (hp < 0)
                    {
                        hp = 0;
                    }
                    debugSpeakMsg(self, "setting house hp from " + current + " to " + hp);
                    player_structure.damageStructure(house, current - hp);
                }
                else 
                {
                    debugSpeakMsg(self, "Can't find scriptvar player_structure.parent");
                }
            }
            else 
            {
                debugSpeakMsg(self, "No lookat target");
            }
        }
        else if (text.equals("makemejedi"))
        {
            setJediState(self, JEDI_STATE_JEDI);
            setMaxForcePower(self, 100);
        }
        else if (text.equals("makemeoldjedi"))
        {
            setJediState(self, JEDI_STATE_JEDI);
            setMaxForcePower(self, 100);
            removeObjVar(self, "jedi.skillsNeeded");
            grantSkill(self, "jedi_padawan_novice");
            grantSkill(self, "jedi_padawan_saber_01");
            grantSkill(self, "jedi_padawan_saber_02");
            grantSkill(self, "jedi_padawan_saber_03");
            grantSkill(self, "jedi_padawan_saber_04");
            grantSkill(self, "jedi_padawan_force_power_01");
            grantSkill(self, "jedi_padawan_force_power_02");
            grantSkill(self, "jedi_padawan_force_power_03");
            removeObjVar(self, "jedi.state");
        }
        else if (text.equals("isjedi"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            if (isJedi(target))
            {
                debugSpeakMsg(self, "jedi");
            }
            else 
            {
                debugSpeakMsg(self, "not jedi");
            }
        }
        else if (text.equals("getjedistate"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
        }
        else if (text.equals("getjedi"))
        {
            dictionary result = requestJedi(IGNORE_JEDI_STAT, IGNORE_JEDI_STAT, 1, 1000, IGNORE_JEDI_STAT, IGNORE_JEDI_STAT, IGNORE_JEDI_STAT);
            if (result != null)
            {
                obj_id[] id = result.getObjIdArray("id");
                String[] name = result.getStringArray("name");
                location[] loc = result.getLocationArray("location");
                String[] scene = result.getStringArray("scene");
                int[] visibility = result.getIntArray("visibility");
                int[] bountyValue = result.getIntArray("bountyValue");
                int[] level = result.getIntArray("level");
                int[] hoursAlive = result.getIntArray("hoursAlive");
                int[] state = result.getIntArray("state");
                obj_id[][] bounties = (obj_id[][])(result.get("bounties"));
                boolean[] online = result.getBooleanArray("online");
                if (id != null && id.length > 0)
                {
                    for (int i = 0; i < id.length; ++i)
                    {
                        debugConsoleMsg(self, "Player " + name[i]);
                        debugConsoleMsg(self, "\tid = " + id[i]);
                        debugConsoleMsg(self, "\tloc = " + loc[i]);
                        debugConsoleMsg(self, "\tscene = " + scene[i]);
                        debugConsoleMsg(self, "\tvisibility = " + visibility[i]);
                        debugConsoleMsg(self, "\tbounty value = " + bountyValue[i]);
                        debugConsoleMsg(self, "\tlevel = " + level[i]);
                        debugConsoleMsg(self, "\thours alive = " + hoursAlive[i]);
                        debugConsoleMsg(self, "\tstate = " + state[i]);
                        debugConsoleMsg(self, "\tonline = " + online[i]);
                    }
                }
                else 
                {
                    debugConsoleMsg(self, "No jedi");
                }
            }
            else 
            {
                debugConsoleMsg(self, "Error getting jedi");
            }
        }
        else if (text.equals("testjedivisibility"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            if (isJedi(target))
            {
                setJediVisibility(target, 5000);
                debugSpeakMsg(self, "visiblity set to " + getJediVisibility(target));
            }
            else 
            {
                debugSpeakMsg(self, "not jedi");
            }
        }
        else if (text.equals("testlocation"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            debugSpeakMsg(self, "Target location = " + getLocation(target));
        }
        else if (text.equals("testdistance"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                location targetPos = getLocation(target);
                location myPos = getLocation(self);
                float distance = getDistance(myPos, targetPos);
                debugSpeakMsg(self, "distance = " + distance);
                float dx = myPos.x - targetPos.x;
                float dz = myPos.z - targetPos.z;
                distance = (float)(Math.sqrt(dx * dx + dz * dz));
                debugSpeakMsg(self, "2d distance = " + distance);
            }
            else 
            {
                debugSpeakMsg(self, "no target");
            }
        }
        else if (text.equals("isgod"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            if (isGod(target))
            {
                debugConsoleMsg(self, "yes, level = " + getGodLevel(self));
            }
            else 
            {
                debugConsoleMsg(self, "no");
            }
        }
        else if (text.equals("testskillmodmod"))
        {
            addSkillModModifier(self, "themodmodmod", "winona", 50, 120, false, false);
            messageTo(self, "abortSkillModMod", null, 30, false);
        }
        else if (text.equals("testmakecrafted"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                setCraftedId(target, target);
            }
        }
        else if (text.equals("testmakeuncrafted"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                setCraftedId(target, null);
            }
        }
        else if (text.equals("testjedislot"))
        {
            addJediSlot(self);
        }
        else if (text.equals("testhasjedi"))
        {
            debugSpeakMsg(self, "hasJediSlot = " + hasJediSlot(self));
        }
        else if (text.equals("damageme"))
        {
            damage(self, DAMAGE_KINETIC, HIT_LOCATION_BODY, 100);
        }
        else if (text.equals("hurt"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            damage(target, DAMAGE_KINETIC, HIT_LOCATION_BODY, 100);
        }
        else if (text.equals("testincap"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            int health = getMaxAttrib(target, HEALTH);
            damage(target, DAMAGE_KINETIC, HIT_LOCATION_BODY, health * 4);
        }
        else if (text.equals("whackhealth"))
        {
            int health = getMaxAttrib(self, HEALTH) + 100;
            setAttrib(self, HEALTH, -health);
        }
        else if (text.equals("whackaction"))
        {
            int action = getMaxAttrib(self, ACTION) + 100;
            setAttrib(self, ACTION, -action);
        }
        else if (text.equals("whackmind"))
        {
            int mind = getMaxAttrib(self, MIND) + 100;
            setAttrib(self, MIND, -mind);
        }
        else if (text.equals("killme"))
        {
            setObjVar(self, "killme", true);
            int health = getMaxAttrib(self, HEALTH);
            damage(self, DAMAGE_KINETIC, HIT_LOCATION_BODY, health * 4);
        }
        else if (text.equals("maxstats"))
        {
            for (int i = 0; i < NUM_ATTRIBUTES; ++i)
            {
                setAttrib(self, i, getMaxAttrib(self, i));
            }
            int force = getMaxForcePower(self);
            if (force > 0)
            {
                setForcePower(self, force);
            }
            setShockWound(self, 0);
        }
        else if (text.equals("testxp"))
        {
            dictionary params = new dictionary();
            params.put("xp_type", "scout");
            params.put("amt", 13);
            params.put("verbose", true);
            messageTo(self, "grantUnmodifiedExperienceOnSelf", params, 0.1f, false);
        }
        else if (text.equals("makemerebel"))
        {
            factions.setFactionStanding(self, factions.FACTION_REBEL, 5000);
        }
        else if (text.equals("makemeimperial"))
        {
            factions.setFactionStanding(self, factions.FACTION_IMPERIAL, 500);
        }
        else if (text.equals("clearallmods"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            removeAllAttribModifiers(target);
        }
        else if (text.equals("testforceregen"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target) && getMaxForcePower(target) > 0)
            {
                int maxforce = getForcePower(target);
                int force = getMaxForcePower(target);
                float regen = getForcePowerRegenRate(target);
                int skill = getSkillStatisticModifier(target, "jedi_force_power_regen");
                int penalty = utils.getIntScriptVar(target, "jedi.regenPenalty");
                setObjVar(self, "testregenresult.maxforce", maxforce);
                setObjVar(self, "testregenresult.force", force);
                setObjVar(self, "testregenresult.regen", regen);
                setObjVar(self, "testregenresult.skill", skill);
                setObjVar(self, "testregenresult.penalty", penalty);
                setObjVar(self, "testregenresult.notajedi", false);
            }
            else 
            {
                setObjVar(self, "testregenresult.notajedi", true);
            }
        }
        else if (text.equals("resetforceregen"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target) && getMaxForcePower(target) > 0)
            {
                jedi.recalculateForcePower(target);
            }
        }
        else if (text.equals("testgetname"))
        {
            String myname = getPlayerName(self);
            debugSpeakMsg(self, "my name = " + myname);
            String otherName = getPlayerName(obj_id.getObjId(28000016));
            debugSpeakMsg(self, "other name = " + otherName);
        }
        else if (text.equals("testobjvar"))
        {
            boolean result = hasObjVar(self, "");
            result = hasObjVar(self, "winona");
            debugSpeakMsg(self, "winona = " + result);
            result = hasObjVar(self, "sdata");
            debugSpeakMsg(self, "sdata = " + result);
        }
        else if (text.equals("testcreate"))
        {
            location myLocation = getLocation(self);
            obj_id result = createObject("asdasfasdfa", myLocation);
            result = createObject("object/tangible/terminal/terminal_mission_scout.iff", myLocation);
        }
        else if (text.equals("testtheater"))
        {
            location mypos = getLocation(self);
            String[] templates = new String[100];
            location[] positions = new location[100];
            float x, z;
            double arc = Math.PI * 2.0 / 100.0;
            for (int i = 0; i < 100; ++i)
            {
                templates[i] = "object/tangible/wearables/dress/nightsister_dress.iff";
                x = (float)(StrictMath.cos(arc * i)) * 20.0f;
                z = (float)(StrictMath.sin(arc * i)) * 20.0f;
                positions[i] = new location(x, 0, z);
            }
            obj_id theater = createTheater("datatables/theater/test.iff", mypos, "steve.theater_test");
            if (isIdValid(theater))
            {
                (theater.getScriptVars()).put("creator", self);
                (getScriptVars()).put("theater", theater);
            }
            else 
            {
                debugSpeakMsg(self, "Error creating theater");
            }
        }
        else if (text.equals("testfindtheater"))
        {
            debugSpeakMsg(self, "Theater fred = " + findTheater("fred"));
            debugSpeakMsg(self, "Theater alpha = " + findTheater("alpha"));
            debugSpeakMsg(self, "Theater beta = " + findTheater("beta"));
            debugSpeakMsg(self, "Theater gamma = " + findTheater("gamma"));
            debugSpeakMsg(self, "Theater delta = " + findTheater("delta"));
            debugSpeakMsg(self, "Theater epsilon = " + findTheater("epsilon"));
        }
        else if (text.equals("destroytheater"))
        {
            obj_id theater = getObjIdObjVar(self, "player_theater");
            if (isIdValid(theater))
            {
                destroyObject(theater);
                removeObjVar(self, "player_theater");
            }
        }
        else if (text.equals("testplayertheater"))
        {
            boolean result = assignTheaterToPlayer(self, "datatables/theater/test.iff", getLocation(self), null, TLT_none);
            debugSpeakMsg(self, "assign theater result = " + result);
        }
        else if (text.equals("testdestroyplayertheater"))
        {
            obj_id theater = getLastSpawnedTheater(self);
            if (isIdValid(theater))
            {
                destroyObject(theater);
                debugSpeakMsg(self, "destroyed theater " + theater);
            }
            else 
            {
                debugSpeakMsg(self, "No theater");
            }
        }
        else if (text.equals("testdietheater"))
        {
            setObjVar(self, "testdietheater", true);
            setObjVar(self, "killme", true);
            int health = getMaxAttrib(self, HEALTH);
            damage(self, DAMAGE_KINETIC, HIT_LOCATION_BODY, health * 4);
        }
        else if (text.equals("getspawnedtheater"))
        {
            debugSpeakMsg(self, "my last spawned theater = " + getLastSpawnedTheater(self));
        }
        else if (text.equals("testspeed"))
        {
            debugSpeakMsg(self, "My run speed = " + getRunSpeed(self));
        }
        else if (text.equals("testschematic"))
        {
            draft_schematic schematicData = getSchematicData("object/draft_schematic/community_crafting/village_shields.iff");
            String[] scripts = schematicData.getScripts();
            if (scripts != null)
            {
                for (String script : scripts) {
                    debugSpeakMsg(self, "Script = " + script);
                }
            }
            else 
            {
                debugSpeakMsg(self, "No scripts!");
            }
        }
        else if (text.equals("makecommunitycrafting"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                boolean result = community_crafting.setMasterSchematic(target, "object/draft_schematic/community_crafting/village_defenses.iff", true, true, true, null);
                debugSpeakMsg(self, "community crafting result = " + result);
            }
        }
        else if (text.equals("endcommunitycrafting"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                boolean result = community_crafting.finalizeItem(target, true);
                debugSpeakMsg(self, "finalize result = " + result);
            }
        }
        else if (text.equals("clearcommunitycrafting"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                boolean result = community_crafting.cleanup(target);
                debugSpeakMsg(self, "cleanup result = " + result);
            }
        }
        else if (text.equals("joincommunitycrafting"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                boolean result = community_crafting.grantSchematicToPlayer(target, self);
                debugSpeakMsg(self, "join community crafting result = " + result);
            }
        }
        else if (text.equals("logmasterobject"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                int schematic = community_crafting.getSchematic(target);
                if (schematic != 0)
                {
                    utils.setScriptVar(self, "master_community_crafter", target);
                    debugSpeakMsg(self, "OK");
                }
                else 
                {
                    debugSpeakMsg(self, "Target not master object");
                }
            }
        }
        else if (text.equals("makecommunitynpc"))
        {
            obj_id masterObject = utils.getObjIdScriptVar(self, "master_community_crafter");
            if (isIdValid(masterObject))
            {
                obj_id target = getLookAtTarget(self);
                if (isIdValid(target) && target != masterObject)
                {
                    setObjVar(target, community_crafting.OBJVAR_COMMUNITY_CRAFTING_TRACKER, masterObject);
                    debugSpeakMsg(self, "OK, reset the servers");
                }
                else 
                {
                    debugSpeakMsg(self, "No target");
                }
            }
            else 
            {
                debugSpeakMsg(self, "No master object");
            }
        }
        else if (text.equals("givecommunityingredient"))
        {
            obj_id masterObject = utils.getObjIdScriptVar(self, "master_community_crafter");
            if (isIdValid(masterObject))
            {
                obj_id target = getLookAtTarget(self);
                if (isIdValid(target))
                {
                    boolean result = community_crafting.addIngredient(masterObject, self, target);
                    debugSpeakMsg(self, "Ingredient result = " + result);
                }
                else 
                {
                    debugSpeakMsg(self, "No target");
                }
            }
            else 
            {
                debugSpeakMsg(self, "No master object");
            }
        }
        else if (text.equals("getgot"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                int type = getGameObjectType(target);
                debugSpeakMsg(self, "GOT = " + Integer.toHexString(type));
            }
        }
        else if (text.equals("testdoublesort"))
        {
            int[] values = new int[50];
            obj_id[] ids = new obj_id[50];
            for (int i = 0; i < values.length; ++i)
            {
                values[i] = rand(1, 999999);
            }
            community_crafting.doubleSort(values, ids);
            int j;
            for (j = 0; j < values.length - 1; ++j)
            {
                if (values[j] < values[j + 1])
                {
                    break;
                }
            }
            if (j == values.length - 1)
            {
                debugSpeakMsg(self, "OK");
            }
            else 
            {
                debugSpeakMsg(self, "FAIL!");
            }
        }
        else if (text.equals("testvillageaccess"))
        {
            fs_quests.makeVillageEligible(self);
            debugSpeakMsg(self, "I should be able to enter the village now");
        }
        else if (text.startsWith("setvillagephase"))
        {
            String phaseString = text.substring(("setvillagephase").length());
            phaseString = phaseString.trim();
            int phase = Integer.parseInt(phaseString);
            if (phase >= 1 && phase <= 4)
            {
                debugSpeakMsg(self, "Setting phase to " + phase);
                fs_dyn_village.setNextPhaseAuth(getLookAtTarget(self), phase);
            }
            else 
            {
                debugSpeakMsg(self, "Error, phaseString = " + phaseString);
            }
        }
        else if (text.equals("testdt"))
        {
            final String DATATABLE_CRAFTING_QUESTS = "datatables/quest/force_sensitive/community_crafting.iff";
            final String DATATABLE_CQ_PHASE = "phase";
            final String DATATABLE_CQ_SCHEMATIC = "schematic";
            final String DATATABLE_CQ_PRIZE = "prize";
            final String DATATABLE_CQ_TRACK_QUANTITY = "track_quantity";
            final String DATATABLE_CQ_TRACK_QUALTITY = "track_quality";
            final String DATATABLE_CQ_TRACK_SLOTS = "track_slots";
            final String DATATABLE_CQ_MIN_INGREDIENTS = "min_ingredients";
            int row = dataTableSearchColumnForInt(2, DATATABLE_CQ_PHASE, DATATABLE_CRAFTING_QUESTS);
            if (row >= 0)
            {
                String schematic = dataTableGetString(DATATABLE_CRAFTING_QUESTS, row, DATATABLE_CQ_SCHEMATIC);
                String prize = dataTableGetString(DATATABLE_CRAFTING_QUESTS, row, DATATABLE_CQ_PRIZE);
                boolean trackQuantity = dataTableGetInt(DATATABLE_CRAFTING_QUESTS, row, DATATABLE_CQ_TRACK_QUANTITY) != 0;
                boolean trackQuality = dataTableGetInt(DATATABLE_CRAFTING_QUESTS, row, DATATABLE_CQ_TRACK_QUALTITY) != 0;
                boolean trackSlots = dataTableGetInt(DATATABLE_CRAFTING_QUESTS, row, DATATABLE_CQ_TRACK_SLOTS) != 0;
                int minIngredients = dataTableGetInt(DATATABLE_CRAFTING_QUESTS, row, DATATABLE_CQ_MIN_INGREDIENTS);
                debugConsoleMsg(self, "schematic = " + schematic);
                debugConsoleMsg(self, "prize = " + prize);
                debugConsoleMsg(self, "trackQuantity = " + trackQuantity);
                debugConsoleMsg(self, "trackQuality = " + trackQuality);
                debugConsoleMsg(self, "trackSlots = " + trackSlots);
                debugConsoleMsg(self, "minIngredients = " + minIngredients);
            }
            else 
            {
                debugConsoleMsg(self, "No row 2");
            }
        }
        else if (text.equals("testgivequest"))
        {
            debugSpeakMsg(self, "Activating quest");
            quests.activate("fs_phase_2_craft_defenses_01", self, self);
        }
        else if (text.equals("testhasquest"))
        {
            debugSpeakMsg(self, "isActive = " + quests.isActive("fs_phase_2_craft_defenses_01", self));
        }
        else if (text.equals("clearccquests"))
        {
            clearCompletedQuest(self, quests.getQuestId("fs_phase_2_craft_defenses_01"));
            clearCompletedQuest(self, quests.getQuestId("fs_phase_3_craft_shields_01"));
        }
        else if (text.equals("completeccquest"))
        {
            messageTo(self, "handleCommunityCraftingMinimumQuantityMet", null, 0.1f, false);
        }
        else if (text.equals("testamicraftingorquesting"))
        {
            final String QUEST_NAME = "fs_phase_3_craft_shields_01";
            obj_id masterId = obj_id.getObjId(18000045);
            if (community_crafting.getNumPlayersCrafting(masterId) >= community_crafting.MAX_PLAYERS_PER_PROJECT)
            {
                debugSpeakMsg(self, "Yes, there are too many players crafting");
            }
            else if (community_crafting.isSessionActive(masterId) && community_crafting.isPlayerCrafting(masterId, self))
            {
                debugSpeakMsg(self, "Yes, I am crafting");
            }
            else if (quests.isActive(QUEST_NAME, self))
            {
                debugSpeakMsg(self, "Yes, I have the quest active");
            }
            else if (quests.isComplete(QUEST_NAME, self))
            {
                debugSpeakMsg(self, "Yes, I have the quest complete");
            }
            else if (fs_quests.hasQuestAccepted(self))
            {
                debugSpeakMsg(self, "Yes, I have a quest for this phase already");
            }
            else 
            {
                debugSpeakMsg(self, "No, I should be able to join the quest");
            }
        }
        else if (text.equals("makeendrine"))
        {
            obj_id inventory = utils.getInventoryContainer(self);
            obj_id endrine = createObject("object/tangible/loot/quest/endrine.iff", inventory, "");
            if (isValidId(endrine))
            {
                setObjVar(endrine, "crafting_components.res_malleability", rand(500, 1000));
                setObjVar(endrine, "crafting_components.res_quality", rand(500, 1000));
                setObjVar(endrine, "crafting_components.res_toughness", rand(500, 1000));
            }
        }
        else if (text.equals("makerudric"))
        {
            obj_id inventory = utils.getInventoryContainer(self);
            obj_id rudric = createObject("object/tangible/loot/quest/rudric.iff", inventory, "");
            if (isValidId(rudric))
            {
                setObjVar(rudric, "crafting_components.res_conductivity", rand(500, 1000));
                setObjVar(rudric, "crafting_components.res_decay_resist", rand(500, 1000));
                setObjVar(rudric, "crafting_components.res_quality", rand(500, 1000));
                setObjVar(rudric, "crafting_components.res_shock_resistance", rand(500, 1000));
            }
        }
        else if (text.equals("makeardanium"))
        {
            obj_id inventory = utils.getInventoryContainer(self);
            obj_id ardanium = createObject("object/tangible/loot/quest/ardanium_ii.iff", inventory, "");
            if (isValidId(ardanium))
            {
                setObjVar(ardanium, "crafting_components.res_potential_energy", rand(500, 1000));
                setObjVar(ardanium, "crafting_components.res_quality", rand(500, 1000));
            }
        }
        else if (text.equals("makeostrine"))
        {
            obj_id inventory = utils.getInventoryContainer(self);
            obj_id ostrine = createObject("object/tangible/loot/quest/ostrine.iff", inventory, "");
            if (isValidId(ostrine))
            {
                setObjVar(ostrine, "crafting_components.malleability", rand(500, 1000));
                setObjVar(ostrine, "crafting_components.res_quality", rand(500, 1000));
            }
        }
        else if (text.equals("testremotecreate"))
        {
            obj_id object = createObject("object/tangible/loot/quest/endrine.iff", new location(1000, 0, 1000));
            debugSpeakMsg(self, "Created object " + object);
        }
        else if (text.equals("testremotetheater"))
        {
            debugSpeakMsg(self, "Create remote theater result = " + createRemoteTheater("datatables/theater/test2.iff", "fred"));
        }
        else if (text.equals("destroy"))
        {
            obj_id object = getLookAtTarget(self);
            if (isIdValid(object))
            {
                destroyObject(object);
            }
        }
        else if (text.equals("testgetobjects"))
        {
            obj_id[] objects = getNonCreaturesInRange(getLocation(self), 100.0f);
            if (objects != null)
            {
                for (obj_id object : objects) {
                    debugConsoleMsg(self, "" + object);
                }
            }
        }
        else if (text.equals("testneedschematics"))
        {
            debugSpeakMsg(self, "isActive test = " + quests.isActive("fs_phase_3_craft_shields_01", self));
            debugSpeakMsg(self, "isCrafting test = " + community_crafting.isPlayerCrafting(obj_id.getObjId(71000022), self));
        }
        else if (text.equals("acceptquest2"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                quests.activate("fs_phase_2_craft_defenses_01", self, target);
                debugSpeakMsg(self, "Activated phase 2 crafting quest");
            }
        }
        else if (text.equals("acceptquest3"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                quests.activate("fs_phase_3_craft_shields_01", self, target);
                debugSpeakMsg(self, "Activated phase 3 crafting quest");
            }
        }
        else if (text.equals("testenemycamps"))
        {
            final String DT_TABLE_POI_NAME = "datatables/theater/fs_quest_combat3/fs_quest_combat3.iff";
            Vector camps = script.library.fs_counterstrike.getAllCampHintsFromDT();
            Vector phaseLocs = (Vector)(camps.get(0));
            Vector phaseNames = (Vector)(camps.get(1));
            for (int i = 0; i < phaseLocs.size() && i < 4; i++)
            {
                if (createRemoteTheater(DT_TABLE_POI_NAME, (location)phaseLocs.get(i), "steve.theater_test", self, (String)phaseNames.get(i), TLT_getGoodLocation))
                {
                    debugSpeakMsg(self, "OK for remote theater " + phaseNames.get(i));
                }
            }
        }
        else if (text.equals("testenemy"))
        {
            debugSpeakMsg(self, "faction # = " + factions.getFactionNumber("sith_shadow"));
            debugSpeakMsg(self, "isenemy = " + pvpIsEnemy(obj_id.getObjId(215000087), getLookAtTarget(self)));
        }
        else if (text.equals("testoverload"))
        {
            debugSpeakMsg(self, "created overload object " + createObjectInInventoryAllowOverload("object/tangible/parrot_cage/parrot_cage.iff", self));
        }
        else if (text.equals("nt1"))
        {
            debugSpeakMsg(self, "creating theater");
            location theaterLoc = getLocation(self);
            theaterLoc.z -= 10.0;
            boolean created = assignTheaterToPlayer(self, "datatables/theater/fs_quest_sad/theater1.iff", theaterLoc, "ahunter.my_script");
            debugSpeakMsg(self, "result = " + created);
        }
        else if (text.equals("nt2"))
        {
            debugSpeakMsg(self, "creating theater");
            location theaterLoc = getLocation(self);
            theaterLoc.z -= 10.0;
            boolean created = assignTheaterToPlayer(self, "datatables/theater/fs_quest_sad/theater2.iff", theaterLoc, "ahunter.my_script");
            debugSpeakMsg(self, "result = " + created);
        }
        else if (text.equals("testscriptvars"))
        {
            debugSpeakMsg(self, "testing script vars");
            utils.setScriptVar(self, "foo.sub1", "bar");
            utils.setScriptVar(self, "foo.sub2", "bar2");
            debugSpeakMsg(self, "hasfoo = " + utils.hasScriptVar(self, "foo"));
            messageTo(self, "handleTestScriptVars", null, 0.5f, false);
        }
        else if (text.equals("testrand"))
        {
            int[] counts = new int[100];
            for (int i = 0; i < 200; ++i)
            {
                ++counts[rand(0, 99)];
            }
            for (int i = 0; i < counts.length; ++i)
            {
                debugServerConsoleMsg(self, "counts[" + i + "] = " + counts[i]);
            }
        }
        else if (text.equals("getyaw"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                debugSpeakMsg(self, "Yaw = " + getYaw(target));
            }
        }
        else if (text.equals("testcontentsloaded"))
        {
            obj_id building = getTopMostContainer(self);
            if (!isIdValid(building) || !player_structure.isBuilding(building))
            {
                debugSpeakMsg(self, "My topmost container " + building + " is not a building");
            }
            else 
            {
                debugSpeakMsg(self, "Contents loaded result = " + areAllContentsLoaded(building));
            }
        }
        else if (text.equals("startphase23bug"))
        {
            final String QUEST_2_MAIN = "fs_phase_2_craft_defenses_main";
            final String QUEST_2_STEP_01 = "fs_phase_2_craft_defenses_01";
            final String QUEST_2_STEP_02 = "fs_phase_2_craft_defenses_02";
            final String QUEST_2_SCRIPT = "quest.force_sensitive.fs_craft_village_defenses";
            final String QUEST_3_MAIN = "fs_phase_3_craft_shields_main";
            final String QUEST_3_STEP_01 = "fs_phase_3_craft_shields_01";
            final String QUEST_3_STEP_02 = "fs_phase_3_craft_shields_02";
            final String QUEST_3_SCRIPT = "quest.force_sensitive.fs_craft_village_shields";
            final String[] QUESTS = 
            {
                QUEST_2_MAIN,
                QUEST_2_STEP_01,
                QUEST_2_STEP_02,
                QUEST_3_MAIN,
                QUEST_3_STEP_01,
                QUEST_3_STEP_02
            };
            String oldQuest = QUEST_2_STEP_02;
            String questScript = QUEST_2_SCRIPT;
            if (hasObjVar(self, "phase3"))
            {
                oldQuest = QUEST_3_STEP_02;
                questScript = QUEST_3_SCRIPT;
            }
            for (String quest : QUESTS) {
                int id = quests.getQuestId(quest);
                if (isQuestActive(self, id)) {
                    deactivateQuest(self, id);
                }
                if (isQuestComplete(self, id)) {
                    clearCompletedQuest(self, id);
                }
            }
        }
        else if (text.equals("testphase23bug"))
        {
            final String QUEST_2_MAIN = "fs_phase_2_craft_defenses_main";
            final String QUEST_2_STEP_01 = "fs_phase_2_craft_defenses_01";
            final String QUEST_2_STEP_02 = "fs_phase_2_craft_defenses_02";
            final String QUEST_2_SCRIPT = "quest.force_sensitive.fs_craft_village_defenses";
            final String QUEST_3_MAIN = "fs_phase_3_craft_shields_main";
            final String QUEST_3_STEP_01 = "fs_phase_3_craft_shields_01";
            final String QUEST_3_STEP_02 = "fs_phase_3_craft_shields_02";
            final String QUEST_3_SCRIPT = "quest.force_sensitive.fs_craft_village_shields";
            final String[] QUESTS = 
            {
                QUEST_2_MAIN,
                QUEST_2_STEP_01,
                QUEST_2_STEP_02,
                QUEST_3_MAIN,
                QUEST_3_STEP_01,
                QUEST_3_STEP_02
            };
            for (String quest : QUESTS) {
                int id = quests.getQuestId(quest);
                if (isQuestActive(self, id)) {
                    debugConsoleMsg(self, "quest " + quest + " active");
                }
                if (isQuestComplete(self, id)) {
                    debugConsoleMsg(self, "quest " + quest + " complete");
                }
            }
        }
        else if (text.equals("testccprize"))
        {
            dictionary params = new dictionary();
            params.addInt(community_crafting.REWARD_PRIZE, (1664865686));
            params.addInt(community_crafting.REWARD_PRIZE_SLOT, 0);
            params.addInt(community_crafting.REWARD_PRIZE_TYPE, 0);
            params.put(community_crafting.REWARD_PRIZE_SCRIPT, "quest.force_sensitive.fs_grant_assembly_5");
            messageTo(self, community_crafting.MSG_HANDLER_PRIZE_WON, params, 0.1f, true);
        }
        else if (text.equals("finishcraftingquest"))
        {
            messageTo(self, community_crafting.MSG_HANDLER_MIN_QUANTITY_MET, null, 0.1f, true);
        }
        else if (text.equals("testloot"))
        {
            obj_id target = getLookAtTarget(self);
            if (isValidId(target))
            {
                obj_id inventory = utils.getInventoryContainer(target);
                if (isIdValid(inventory))
                {
                    obj_id item = createObject("object/tangible/loot/quest/endrine.iff", inventory, "");
                    if (isIdValid(item))
                    {
                        debugSpeakMsg(self, "Put item in inventory");
                        setPosture(target, POSTURE_DEAD);
                        setObjVar(target, "readyToLoot", true);
                        attachScript(target, "corpse.ai_corpse");
                    }
                    else 
                    {
                        debugSpeakMsg(self, "Error 1");
                    }
                }
                else 
                {
                    debugSpeakMsg(self, "Error 2");
                }
            }
        }
        else if (text.equals("die"))
        {
            obj_id target = getLookAtTarget(self);
            if (isValidId(target) && (getGameObjectType(target) & GOT_creature) != 0)
            {
                damage(target, DAMAGE_ENERGY, HIT_LOCATION_BODY, Integer.MAX_VALUE - 500);
            }
        }
        else if (text.equals("testobjectsinrange"))
        {
            obj_id[] objects = getPlayerCreaturesInRange(getLocation(getLastSpawnedTheater(self)), 64);
            if (objects != null)
            {
                debugSpeakMsg(self, "Found objects");
                for (int i = 0; i < objects.length; ++i)
                {
                    debugConsoleMsg(self, "object " + i + " = " + objects[i]);
                }
            }
            else 
            {
                debugSpeakMsg(self, "No objects in range");
            }
        }
        else if (text.equals("testlos"))
        {
            messageTo(self, "handleTestLos", null, 0.1f, false);
        }
        else if (text.equals("testfx"))
        {
            obj_id[] clients = null;
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                clients = new obj_id[2];
                clients[0] = self;
                clients[1] = target;
            }
            else 
            {
                clients = new obj_id[1];
                clients[0] = self;
            }
            playClientEffectObj(clients, "clienteffect/pl_force_shield_hit.cef", self, "");
        }
        else if (text.equals("testattribbonus"))
        {
            obj_id target = obj_id.getObjId(55000058);
            if (exists(target) && getContainedBy(target) != self)
            {
                setAttributeBonus(target, HEALTH, 25);
                setAttributeBonus(target, ACTION, 32);
                debugSpeakMsg(self, "modded target");
            }
        }
        else if (text.equals("testweaponrange"))
        {
            obj_id weapon = getCurrentWeapon(self);
            range_info info = getWeaponRangeInfo(weapon);
            if (info != null)
            {
                info.minRange = 10.0f;
                info.maxRange = 80.0f;
                setWeaponRangeInfo(weapon, info);
            }
        }
        else if (text.equals("testgetcomplexity"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                debugSpeakMsg(self, "target complexity = " + getComplexity(target));
            }
        }
        else if (text.equals("testsetcomplexity"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                setComplexity(target, getComplexity(target) * 2.0f);
            }
        }
        else if (text.equals("getcell"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            location loc = getLocation(target);
            String cell = getCellName(loc.cell);
            debugSpeakMsg(self, "target in cell " + cell);
        }
        else if (text.equals("testmoveup"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            location loc = getLocation(target);
            loc.y += 10;
            setLocation(target, loc);
        }
        else if (text.equals("testmovedown"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            location loc = getLocation(target);
            loc.y -= 10;
            setLocation(target, loc);
        }
        else if (text.equals("testforceslope"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                addSlowDownEffect(self, target, 30.0f, 90.0f, 75.0f, 120.0f);
            }
        }
        else if (text.equals("testnpcmove"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                messageTo(target, "startMoving", null, 0.1f, false);
            }
        }
        else if (text.equals("testnpcstop"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                messageTo(target, "stopMoving", null, 0.1f, false);
            }
        }
        else if (text.equals("testarmorloot"))
        {
            obj_id shirt = createObject("object/tangible/wearables/shirt/shirt_s34.iff", getLocation(self));
            if (isIdValid(shirt))
            {
                armor.setArmorDataPercent(shirt, AL_basic, AC_battle, 0.5f, 0.7f);
                armor.setArmorSpecialProtectionPercent(shirt, 0, 0.3f);
            }
        }
        else if (text.equals("testarmorloot2"))
        {
            obj_id shirt = createObject("object/tangible/wearables/shirt/shirt_s34.iff", getLocation(self));
            if (isIdValid(shirt))
            {
                armor.setAbsoluteArmorData(shirt, AL_basic, AC_battle, 1000, 7500);
            }
        }
        else if (text.equals("testarmorloot3"))
        {
            obj_id target = createObject("object/tangible/component/armor/armor_layer_generic.iff", getLocation(self));
            if (isIdValid(target))
            {
                armor.initializeArmor(target, 0, 0);
                armor.setAbsoluteArmorSpecialProtection(target, 8, 75);
                debugSpeakMsg(self, "armor created");
            }
            else 
            {
                debugSpeakMsg(self, "armor NOT created");
            }
        }
        else if (text.equals("testbiolink"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                setBioLink(target, self);
            }
        }
        else if (text.equals("testbiolinkpending"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                setBioLink(target, null);
            }
        }
        else if (text.equals("makecrafted"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                setCraftedId(target, self);
                setCrafter(target, self);
            }
        }
        else if (text.equals("testcrafter"))
        {
            obj_id crafter = getCrafter(obj_id.getObjId(74000293));
            debugSpeakMsg(self, "crafter = " + crafter);
        }
        else if (text.equals("testbelt"))
        {
            obj_id psg = getObjectInSlot(self, combat.PSG_SLOT_LOCATION);
            debugSpeakMsg(self, "obj in belt slot = " + psg + ", GOT = " + getGameObjectType(psg));
        }
        else if (text.equals("testpsgfx"))
        {
            obj_id psg = combat.getPsgArmor(self);
            obj_id target = getLookAtTarget(self);
            if (isIdValid(psg) && isIdValid(target))
            {
                int psgLevel = armor.getArmorLevel(psg);
                if (psgLevel < 0 || psgLevel >= armor.PSG_HIT_EFFECTS.length)
                {
                    psgLevel = 0;
                }
                transform attackerTransform = getTransform_o2w(target);
                vector defenderVector = space_utils.getVector(self);
                transform finalTransform = space_utils.faceTransformToVector(attackerTransform, defenderVector);
                String fx = armor.PSG_STARTUP_EFFECTS[psgLevel];
                fx = armor.PSG_HIT_EFFECTS[psgLevel];
                debugSpeakMsg(self, "Playing psg fx " + fx);
                debugSpeakMsg(self, "Psg transform = " + finalTransform);
                playClientEffectObj(self, fx, self, "", finalTransform);
            }
            else 
            {
                debugSpeakMsg(self, "Not wearing psg");
            }
        }
        else if (text.equals("testdrainactionmind"))
        {
            drainAttributes(self, 50, 100);
        }
        else if (text.equals("testdatatable"))
        {
            debugSpeakMsg(self, "reading datatable row");
            dictionary actionData = dataTableGetRow("datatables/combat/non_combat_data.iff", "bactaToss");
            if (actionData != null)
            {
                debugSpeakMsg(self, "Success");
            }
            else 
            {
                debugSpeakMsg(self, "Fail");
            }
        }
        else if (text.equals("testnpcregen"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                int health = getHealth(target);
                setHealth(target, health / 2);
            }
        }
        else if (text.equals("testbuff"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            debugSpeakMsg(self, "Applying buff, result = " + buff.applyBuff(target, "frogBuff", 15));
        }
        else if (text.equals("testunbuff"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            debugSpeakMsg(self, "Removing buff, result = " + buff.removeBuff(target, "frogBuff"));
        }
        else if (text.equals("testgetbuffs"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            int[] buffs = buff.getAllBuffs(target);
            if (buffs != null && buffs.length > 0)
            {
                for (int b : buffs) {
                    debugSpeakMsg(self, "Buff = " + buff.getBuffNameFromCrc(b));
                }
            }
            else 
            {
                debugSpeakMsg(self, "No buffs");
            }
        }
        else if (text.equals("stopai"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                stop(target);
            }
        }
        else if (text.equals("testdodamage"))
        {
            combat_engine.hit_result hitResult = new combat_engine.hit_result();
            hitResult.hitLocation = HIT_LOCATION_BODY;
            hitResult.damage = 100;
            doDamage(self, self, getCurrentWeapon(self), hitResult);
        }
        else if (text.equals("testoldspawn"))
        {
            location loc = getLocation(self);
            obj_id id = create.object("gnort", loc);
            debugSpeakMsg(self, "Spawned id " + id);
        }
        else if (text.equals("spawngnorts"))
        {
            location base = getLocation(self);
            for (int i = 0; i < 100; ++i)
            {
                float x = rand(-50.0f, 50.0f);
                float z = rand(-50.0f, 50.0f);
                create.object("gnort", new location(base.x + x, base.y, base.z + z));
            }
        }
        else if (text.equals("testpatrol"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                String[] scripts = getScriptList(target);
                if (scripts != null)
                {
                }
                location targetLoc = getLocation(target);
                location[] path = new location[4];
                for (int i = 0; i < 4; ++i)
                {
                    path[i] = new location(targetLoc);
                }
                path[0].x += 25.0f;
                path[1].x += 25.0f;
                path[1].z += 25.0f;
                path[2].x += 50.0f;
                path[2].z += 25.0f;
                path[3].x += 50.0f;
                patrol(target, path);
            }
        }
        else if (text.equals("testcreatepathpoints"))
        {
            location pos = getLocation(self);
            pos.z += 10.0f;
            pos.x -= 25.0f;
            obj_id[] points = new obj_id[10];
            for (int i = 0; i < 10; ++i)
            {
                points[i] = createObject("object/path_waypoint/path_waypoint.iff", pos);
                if (isIdValid(points[i]))
                {
                    setObjVar(points[i], "pathfinding.waypoint.name", "pathpoint" + i);
                    setObjVar(points[i], "pathfinding.waypoint.type", 7);
                    persistObject(points[i]);
                }
                pos.x -= 25.0f;
                if (i % 2 == 0)
                {
                    pos.z -= 20.0f;
                }
                else 
                {
                    pos.z += 20.0f;
                }
            }
            setObjVar(points[0], "pathpoints", points);
        }
        else if (text.equals("testfixpathpoints"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                obj_id[] points = getObjIdArrayObjVar(target, "pathpoints");
                if (points != null)
                {
                    for (int i = 0; i < points.length; ++i)
                    {
                        setObjVar(points[i], "pathfinding.waypoint.name", "pathpoint" + i);
                        setObjVar(points[i], "pathfinding.waypoint.type", 7);
                    }
                }
            }
        }
        else if (text.equals("testdeletepathpoints"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                obj_id[] points = getObjIdArrayObjVar(target, "pathpoints");
                if (points != null)
                {
                    for (obj_id point : points) {
                        destroyObject(point);
                    }
                }
            }
        }
        else if (text.equals("testpatrolpathpoints"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                String[] path = new String[10];
                for (int i = 0; i < 10; ++i)
                {
                    path[i] = "pathpoint" + i;
                }
                patrol(target, path);
            }
        }
        else if (text.equals("removescripts"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                String[] scripts = getScriptList(target);
                if (scripts != null)
                {
                    for (int i = 0; i < scripts.length; ++i)
                    {
                        if (scripts[i].indexOf(obj_id.SCRIPT_PREFIX) == 0)
                        {
                            scripts[i] = scripts[i].substring(obj_id.SCRIPT_PREFIX.length());
                        }
                        detachScript(target, scripts[i]);
                    }
                }
            }
        }
        else if (text.equals("testobjvarlist"))
        {
            obj_var_list stomach = getObjVarList(self, "stomach");
            if (stomach != null)
            {
                obj_var ov = stomach.getObjVar("drink");
                if (ov != null)
                {
                    debugSpeakMsg(self, "Short - yes");
                }
                else 
                {
                    debugSpeakMsg(self, "Short - no");
                }
                ov = stomach.getObjVar("stomach.drink");
                if (ov != null)
                {
                    debugSpeakMsg(self, "Long - yes");
                }
                else 
                {
                    debugSpeakMsg(self, "Long - no");
                }
            }
        }
        else if (text.equals("testgetspeed"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                float walk = getWalkSpeed(target);
                float run = getRunSpeed(target);
                debugSpeakMsg(self, "walk = " + walk + ", run = " + run);
            }
        }
        else if (text.equals("getweaponinfo"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                obj_id weapon = getDefaultWeapon(target);
                if (isIdValid(weapon))
                {
                    int minDamage = getWeaponMinDamage(weapon);
                    int maxDamage = getWeaponMaxDamage(weapon);
                    debugSpeakMsg(self, "Weapon min damage = " + minDamage + ", max Damage = " + maxDamage);
                    String template = getTemplateName(weapon);
                    debugSpeakMsg(self, "Weapon template = " + template);
                }
                else 
                {
                    debugSpeakMsg(self, "no weapon?");
                }
            }
        }
        else if (text.equals("testhate"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            obj_id[] hatelist = getHateList(target);
            if (hatelist != null && hatelist.length > 0)
            {
                for (obj_id obj_id : hatelist) {
                    debugSpeakMsg(self, "hate " + obj_id);
                }
            }
            else 
            {
                debugSpeakMsg(self, "no hate");
            }
        }
        else if (text.equals("testnumai"))
        {
            debugSpeakMsg(self, "Num ai on server = " + getNumAI());
        }
        else if (text.equals("testmoveto"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                ai_lib.aiPathTo(target, getLocation(self));
            }
        }
        else if (text.equals("sethomelocation"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                setHomeLocation(target, getLocation(target));
            }
        }
        else if (text.equals("testtrial"))
        {
            obj_id target = getTopMostContainer(self);
            messageTo(target, "beginSpawn", null, 0, false);
        }
        else if (text.equals("cleartrial"))
        {
            obj_id target = getTopMostContainer(self);
            dictionary params = new dictionary();
            params.put("command", "clearEventArea");
            messageTo(target, "remoteCommand", params, 0, false);
        }
        else if (text.equals("teststartcombat"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target) && target != self)
            {
                startCombat(self, target);
            }
        }
        else if (text.equals("teststopcombat"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target) && target != self)
            {
                stopCombat(target);
                stopCombat(self);
            }
        }
        else if (text.equals("testsuspendmove"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                boolean suspended = hasSuspendedMovement(target);
                if (!suspended)
                {
                    debugSpeakMsg(self, "Suspended movement result = " + suspendMovement(target));
                }
                else 
                {
                    debugSpeakMsg(self, "Can't suspended movement, already suspended");
                }
            }
        }
        else if (text.equals("testresumemove"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                boolean suspended = hasSuspendedMovement(target);
                if (suspended)
                {
                    debugSpeakMsg(self, "Resume movement result = " + resumeMovement(target));
                }
                else 
                {
                    debugSpeakMsg(self, "Can't resume movement, none suspended");
                }
            }
        }
        else if (text.equals("getregenrates"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            int[] attribs = 
            {
                HEALTH,
                ACTION,
                MIND
            };
            float[] values = new float[3];
            for (int i = 0; i < attribs.length; ++i)
            {
                values[i] = getRegenRate(target, attribs[i]);
                debugSpeakMsg(self, "attrib " + i + " regen = " + values[i]);
            }
        }
        else if (text.equals("testregenrates"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            setRegenRate(target, HEALTH, 2.69f);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "ONLOGIN TRIGGERED");
        if (hasObjVar(self, "testdietheater"))
        {
            removeObjVar(self, "testdietheater");
            unassignTheaterFromPlayer(self);
            messageTo(self, "testdietheater", null, 6, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnImmediateLogout(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnUnloadedFromMemory(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int handleWarningShotTest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = getLookAtTarget(self);
        if (!isIdValid(target))
        {
            target = self;
        }
        debugSpeakMsg(self, "Skill mod = " + getEnhancedSkillStatisticModifier(target, "private_defense_penalty"));
        return SCRIPT_CONTINUE;
    }
    public int handleTestLos(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = getLookAtTarget(self);
        if (isValidId(target))
        {
            if (canSee(self, target))
            {
                debugSpeakMsg(self, "yes");
            }
            else 
            {
                debugSpeakMsg(self, "no");
            }
        }
        messageTo(self, "handleTestLos", null, 0.1f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleCloneRespawn(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "HANDLECLONERESPAWN TRIGGERED");
        return SCRIPT_CONTINUE;
    }
    public int testdietheater(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id theater = getLastSpawnedTheater(self);
        if (isIdValid(theater))
        {
            destroyObject(theater);
            debugSpeakMsg(self, "I've destroyed my theater " + theater);
        }
        else 
        {
            debugSpeakMsg(self, "testdietheater, unable to get last theater");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        if (hasObjVar(self, "killme"))
        {
            removeObjVar(self, "killme");
            pclib.killPlayer(self, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        for (int i = 0; i < 9; ++i)
        {
            setAttrib(self, i, getMaxAttrib(self, i));
        }
        int force = getMaxForcePower(self);
        if (force > 0)
        {
            setForcePower(self, force);
        }
        setShockWound(self, 0);
        return SCRIPT_CONTINUE;
    }
    public int OnSkillModsChanged(obj_id self, String[] modNames, int[] modValues) throws InterruptedException
    {
        if (modNames != null && modValues != null && modNames.length == modValues.length)
        {
            for (int i = 0; i < modNames.length; ++i)
            {
                debugSpeakMsg(self, "skillmods changed, " + modNames[i] + " = " + modValues[i]);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleTestScriptVars(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "deleting script vars");
        utils.removeScriptVar(self, "foo");
        debugSpeakMsg(self, "has foo = " + utils.hasScriptVar(self, "foo"));
        debugSpeakMsg(self, "has foo.sub1 = " + utils.hasScriptVar(self, "foo.sub1"));
        return SCRIPT_CONTINUE;
    }
    public int startSpeedTest(obj_id self, dictionary params) throws InterruptedException
    {
        location myPos = getLocation(self);
        (getScriptVars()).put("speedtest_start", myPos);
        messageTo(self, "stopSpeedTest", null, 30.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int stopSpeedTest(obj_id self, dictionary params) throws InterruptedException
    {
        location myPos = getLocation(self);
        location startPos = (getScriptVars()).getLocation("speedtest_start");
        float distance = myPos.distance(startPos);
        debugSpeakMsg(self, "My speed = " + distance / 30.0f);
        return SCRIPT_CONTINUE;
    }
    public int handleTheaterCreated(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id theater = params.getObjId("theater");
        String theaterName = getTheaterName(theater);
        debugSpeakMsg(self, "My theater " + theaterName + ", id " + theater + " has been created");
        return SCRIPT_CONTINUE;
    }
    public int destroyTheater(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id theater = params.getObjId("theater");
        if (isIdValid(theater))
        {
            debugSpeakMsg(self, "Destroying theater " + theater);
            destroyObject(theater);
        }
        else 
        {
            debugSpeakMsg(self, "destroyTheater got bad id");
        }
        return SCRIPT_CONTINUE;
    }
    public int abortSkillModMod(obj_id self, dictionary params) throws InterruptedException
    {
        removeAttribOrSkillModModifier(self, "themodmodmod");
        return SCRIPT_CONTINUE;
    }
    public void listObjvars(obj_id self, obj_id object) throws InterruptedException
    {
        obj_var_list list = getObjVarList(object, null);
        if (list != null)
        {
            debugConsoleMsg(self, list.toString());
        }
        else 
        {
            debugConsoleMsg(self, "no objvars on me");
        }
    }
    public String myFunc() throws InterruptedException
    {
        return new String("");
    }
    public dictionary myOtherFunc() throws InterruptedException
    {
        return null;
    }
    public int myMessageHandler(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "in my message handler");
        if (params != null)
        {
            debugServerConsoleMsg(self, "params = " + params);
            Object slef = params.get("slef");
            if (slef != null)
            {
                debugServerConsoleMsg(self, "slef class is " + slef.getClass());
                obj_id o_slef = (obj_id)slef;
                if (o_slef != null)
                {
                    debugServerConsoleMsg(self, "slef converted to obj_id");
                }
                else 
                {
                    debugServerConsoleMsg(self, "cannot convert slef");
                }
                obj_id d_slef = params.getObjId("slef");
                if (d_slef != null)
                {
                    debugServerConsoleMsg(self, "dictionary converted to obj_id");
                }
                else 
                {
                    debugServerConsoleMsg(self, "dictionary cannot convert to obj_id");
                }
            }
            else 
            {
                debugServerConsoleMsg(self, "can't find slef");
            }
        }
        else 
        {
            debugServerConsoleMsg(self, "null params");
        }
        return SCRIPT_CONTINUE;
    }
}
