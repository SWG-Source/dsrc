package script.working.ahunter;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.colors;
import script.library.hue;
import script.library.prose;
import script.library.pclib;
import script.library.player_structure;
import script.library.skill;
import script.library.utils;
import script.library.fs_quests_sad;
import script.library.chat;
import script.library.respec;

public class my_script extends script.base_script
{
    public my_script()
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
    public void whackamoleRandomize(obj_id self, int pid) throws InterruptedException
    {
        int button = rand(0, 2);
        utils.setScriptVar(self, "whackamolebutton", button);
        setSUIProperty(pid, "%button0%", "BackgroundTint", "#FF0000");
        setSUIProperty(pid, "%button1%", "BackgroundTint", "#FF0000");
        setSUIProperty(pid, "%button2%", "BackgroundTint", "#FF0000");
        setSUIProperty(pid, "%button" + button + "%", "BackgroundTint", "#00FF00");
        flushSUIPage(pid);
        utils.setScriptVar(self, "whackamolebutton", button);
    }
    public int whackamoleTimerCallback(obj_id self, dictionary params) throws InterruptedException
    {
        int pid = utils.getIntScriptVar(self, "whackamolepid");
        if (pid > 0)
        {
            whackamoleRandomize(self, pid);
            messageTo(self, "whackamoleTimerCallback", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int whackamoleCallback(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "whackamoleCallback");
        String widgetName = params.getString("eventWidgetName");
        debugSpeakMsg(self, "eventWidgetName = " + widgetName);
        int buttonPressed = -1;
        if (widgetName.equalsIgnoreCase("%button0%"))
        {
            buttonPressed = 0;
        }
        else if (widgetName.equalsIgnoreCase("%button1%"))
        {
            buttonPressed = 1;
        }
        else if (widgetName.equalsIgnoreCase("%button2%"))
        {
            buttonPressed = 2;
        }
        int targetButton = utils.getIntScriptVar(self, "whackamolebutton");
        int currentScore = utils.getIntScriptVar(self, "whackamolescore");
        int pid = utils.getIntScriptVar(self, "whackamolepid");
        if (targetButton == buttonPressed)
        {
            currentScore += 1;
        }
        else 
        {
            currentScore = 0;
        }
        setSUIProperty(pid, "score.numberBox", "Text", "" + currentScore);
        utils.setScriptVar(self, "whackamolescore", currentScore);
        whackamoleRandomize(self, pid);
        return SCRIPT_CONTINUE;
    }
    public int OnTheaterCreated(obj_id self, obj_id[] objects, obj_id player, obj_id creator) throws InterruptedException
    {
        debugSpeakMsg(player, "my id is " + self);
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        debugServerConsoleMsg(self, "From myscript.script OnSpeaking: " + text);
        java.util.StringTokenizer st = new java.util.StringTokenizer(text);
        String arg = st.nextToken();
        String arg2 = st.nextToken();
        if (arg.equals("gotoDungeon") && st.countTokens() == 1)
        {
            obj_id dungeon = utils.stringToObjId(st.nextToken());
            obj_id cell = getCellId(dungeon, "cave_entrance");
            location dest = new location(2.4f, 10.5f, 9.3f, getCurrentSceneName(), cell);
            setLocation(self, dest);
        }
        else if (arg.equals("gotoStation") && st.countTokens() == 1)
        {
            obj_id station = utils.stringToObjId(st.nextToken());
            obj_id cell = getCellId(station, "hangarbay2");
            location dest = new location(62.7f, 0.9f, -33.5f, getCurrentSceneName(), cell);
            setLocation(self, dest);
        }
        if (text.equals("newdewd"))
        {
            location mylocation = getLocation(self);
            createObject("creature/fodder", mylocation);
        }
        else if (arg.equals("turnon"))
        {
            debugSpeakMsg(self, ("turning on"));
            setCondition(obj_id.getObjId(Integer.parseInt(arg2)), CONDITION_ON);
        }
        else if (arg.equals("turnoff"))
        {
            debugSpeakMsg(self, ("turning off"));
            clearCondition(obj_id.getObjId(Integer.parseInt(arg2)), CONDITION_ON);
        }
        else if (arg.equals("oc"))
        {
            debugSpeakMsg(self, "opening customization window with " + Integer.parseInt(arg2));
            obj_id oid = obj_id.getObjId(Integer.parseInt(arg2));
            custom_var[] allVars = getAllCustomVars(oid);
            if (allVars.length == 1)
            {
                openCustomizationWindow(self, oid, allVars[0].getVarName(), -1, -1, "", -1, -1, "", -1, -1, "", -1, -1);
            }
            else if (allVars.length == 2)
            {
                openCustomizationWindow(self, oid, allVars[0].getVarName(), 0, 10, allVars[1].getVarName(), -1, -1, "", -1, -1, "", -1, -1);
            }
            else if (allVars.length == 3)
            {
                openCustomizationWindow(self, oid, allVars[0].getVarName(), -1, -1, allVars[1].getVarName(), -1, -1, allVars[2].getVarName(), -1, -1, "", -1, -1);
            }
        }
        else if (text.equals("sendcombat1"))
        {
            string_id critSpam = new string_id("combat_effects", "action_too_tired");
            prose_package ppc = new prose_package();
            ppc = prose.setStringId(ppc, critSpam);
            showFlyTextPrivateProseWithFlags(self, self, ppc, 1.5f, colors.DARKORANGE, FLY_TEXT_FLAG_IS_BLEED);
        }
        else if (text.equals("sendcombat2"))
        {
            string_id critSpam = new string_id("combat_effects", "action_too_tired");
            prose_package ppc = new prose_package();
            ppc = prose.setStringId(ppc, critSpam);
            showFlyTextPrivateProseWithFlags(self, self, ppc, 1.5f, colors.DARKORANGE, FLY_TEXT_FLAG_IS_HEAL);
        }
        else if (text.equals("rae"))
        {
            resetExpertises(self);
        }
        else if (text.equals("rcs"))
        {
            recomputeCommandSeries(self);
        }
        else if (text.equals("scs"))
        {
            debugSpeakMsg(self, "sending fake combat spam");
            hit_result hit = new hit_result();
            hit.damage = 1000;
            hit.hitLocation = 0;
            string_id sid = new string_id("combat_effects", "action_too_tired");
            sendCombatSpam(self, self, getCurrentWeapon(self), hit, sid, true, true, true, COMBAT_RESULT_HIT);
        }
        else if (text.equals("sit"))
        {
        }
        else if (text.equals("git"))
        {
            debugSpeakMsg(self, "getting intended target: " + getIntendedTarget(self));
        }
        else if (text.equals("cit"))
        {
        }
        else if (text.equals("gt"))
        {
            debugSpeakMsg(self, "getting template: '" + getSkillTemplate(self) + "'");
        }
        else if (text.equals("ct"))
        {
            debugSpeakMsg(self, "setting template to nothing");
            setSkillTemplate(self, "");
        }
        else if (text.equals("sti"))
        {
            debugSpeakMsg(self, "setting template to something invalid");
            setSkillTemplate(self, "foo");
        }
        else if (text.equals("st"))
        {
            debugSpeakMsg(self, "setting template to teras kasi smuggler");
            setSkillTemplate(self, "smuggler_2a");
        }
        else if (text.equals("st2"))
        {
            debugSpeakMsg(self, "setting template to pistoleer smuggler");
            setSkillTemplate(self, "smuggler_2b");
        }
        else if (text.equals("st3"))
        {
            debugSpeakMsg(self, "setting template to commando b");
            setSkillTemplate(self, "commando_2b");
        }
        else if (text.equals("st4"))
        {
            debugSpeakMsg(self, "setting template to commando a");
            setSkillTemplate(self, "commando_2a");
        }
        else if (text.equals("sws"))
        {
            debugSpeakMsg(self, "setting working skill to combat_marksman_pistol_01");
            setWorkingSkill(self, "combat_marksman_pistol_01");
        }
        else if (text.equals("advance"))
        {
            debugSpeakMsg(self, "advancing along template");
            grantSkill(self, "combat_marksman_pistol_01");
            grantSkill(self, "combat_brawler_unarmed_01");
            grantSkill(self, "combat_marksman_pistol_02");
            grantSkill(self, "combat_brawler_unarmed_02");
            grantSkill(self, "combat_marksman_pistol_03");
            grantSkill(self, "combat_brawler_unarmed_03");
            grantSkill(self, "combat_marksman_pistol_04");
            grantSkill(self, "combat_brawler_unarmed_04");
            grantSkill(self, "combat_smuggler_novice");
            grantSkill(self, "science_medic_novice");
            grantSkill(self, "outdoors_scout_novice");
            setWorkingSkill(self, "combat_brawler_2handmelee_01");
        }
        else if (text.equals("demo"))
        {
            debugSpeakMsg(self, "setting up template demo");
            setSkillTemplate(self, "smuggler_2a");
            grantSkill(self, "combat_marksman_novice");
            grantSkill(self, "combat_brawler_novice");
            setWorkingSkill(self, "combat_marksman_pistol_01");
        }
        else if (text.equals("gt"))
        {
            debugSpeakMsg(self, "getting template: " + getSkillTemplate(self));
        }
        else if (text.equals("gws"))
        {
            debugSpeakMsg(self, "getting working skill: " + getWorkingSkill(self));
        }
        else if (text.equals("ht"))
        {
            debugSpeakMsg(self, "highlighting toolbar widget");
            newbieTutorialHighlightUIElement(self, "/GroundHUD.Toolbar.volume.1", 5.0f);
        }
        else if (text.equals("hm"))
        {
            debugSpeakMsg(self, "highlighting toolbar widget");
            newbieTutorialHighlightUIElement(self, "/GroundHUD.ButtonBar.buttonsComposite.buttonInventoryComposite.inventory", 5.0f);
        }
        else if (text.equals("hm2"))
        {
            debugSpeakMsg(self, "highlighting toolbar widget");
            newbieTutorialHighlightUIElement(self, "/GroundHUD.ButtonBar.bigMenuPage.bigMenu", 5.0f);
        }
        else if (text.equals("hy"))
        {
            debugSpeakMsg(self, "highlighting widget");
            newbieTutorialHighlightUIElement(self, "/GroundHUD.radar", true);
        }
        else if (text.equals("hn"))
        {
            debugSpeakMsg(self, "turning off highlighting widget");
            newbieTutorialHighlightUIElement(self, "/GroundHUD.radar", false);
        }
        else if (text.equals("hw"))
        {
            debugSpeakMsg(self, "highlighting widget");
            newbieTutorialHighlightUIElement(self, "/GroundHUD.radar", 6.0f);
        }
        else if (text.equals("sb"))
        {
            debugSpeakMsg(self, "setting bar");
            newbieTutorialSetToolbarElement(self, 8, "/meleeHit");
        }
        else if (text.equals("sp"))
        {
            debugSpeakMsg(self, "setting pants in bar");
            newbieTutorialSetToolbarElement(self, 8, obj_id.getObjId(new Long(10000720)));
        }
        else if (text.equals("cb"))
        {
            debugSpeakMsg(self, "setting bar");
            newbieTutorialSetToolbarElement(self, 8, "");
        }
        else if (text.equals("no"))
        {
            debugSpeakMsg(self, "sending notification");
            addNotification(self, "foo", false, 0, 0.0f, 0, "sound/tut_15_opencontainer.snd");
        }
        else if (text.equals("no2"))
        {
            debugSpeakMsg(self, "sending notification");
            int handle = addNotification(self, "foo2", false, NOTE_ICON_STYLE_EXCLAMATION, 0.0f, 0, "");
            utils.setScriptVar(self, "notifyhandle", handle);
            debugSpeakMsg(self, "handle " + handle);
        }
        else if (text.equals("no3"))
        {
            debugSpeakMsg(self, "sending notification");
            addNotification(self, "foo3", true, NOTE_ICON_STYLE_QUESTION, 0.0f, 0, "");
        }
        else if (text.equals("no4"))
        {
            debugSpeakMsg(self, "sending notification");
            addNotification(self, "foo4", true, 0, 6.0f, 0, "");
        }
        else if (text.equals("cl2"))
        {
            int handle = utils.getIntScriptVar(self, "notifyhandle");
            debugSpeakMsg(self, "sending cancel " + handle);
            cancelNotification(self, handle);
        }
        else if (text.equals("tt"))
        {
            String duration = getConfigSetting("GameServer", "respecDurationAllowedInSeconds");
            debugSpeakMsg(self, "current config time = " + duration);
            int now = getGameTime();
            debugSpeakMsg(self, "now = " + now);
            int timestamp = getIntObjVar(self, respec.OBJVAR_TIMESTAMP);
            debugSpeakMsg(self, "timestamp = " + timestamp);
            String duration2 = getConfigSetting("GameServer", "minRespecIntervalInSeconds");
            debugSpeakMsg(self, "duration2 = " + duration2);
            int lastTimestamp = getIntObjVar(self, respec.OBJVAR_LAST_TIMESTAMP);
            debugSpeakMsg(self, "lastTimestamp = " + lastTimestamp);
        }
        else if (text.equals("ab"))
        {
            String s = new String("testMedBuff");
            _addBuff(self, getStringCrc(s));
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
        else if (text.equals("nt3"))
        {
            debugSpeakMsg(self, "creating theater");
            location theaterLoc = getLocation(self);
            theaterLoc.z -= 10.0;
            boolean created = assignTheaterToPlayer(self, "datatables/theater/fs_quest_sad/theater3.iff", theaterLoc, "ahunter.my_script");
            debugSpeakMsg(self, "result = " + created);
        }
        else if (text.equals("foo"))
        {
            chat.chat(self, "bar");
        }
        else if (text.equals("nqc"))
        {
            debugSpeakMsg(self, "num quests completed = " + fs_quests_sad.getNumberTasksCompleted(self));
        }
        else if (text.equals("tu"))
        {
            debugSpeakMsg(self, "unassigning theater");
            unassignTheaterFromPlayer(self);
        }
        else if (text.equals("launchcalibpuzzle2"))
        {
            detachScript(self, "systems.fs_quest.calibrationpuzzle.puzzle2");
            attachScript(self, "systems.fs_quest.calibrationpuzzle.puzzle2");
        }
        else if (text.equals("whackamole"))
        {
            debugSpeakMsg(self, "whackamole processing");
            int pid = createSUIPage("/Script.whackamole", self, self, "whackamoleCallback");
            utils.setScriptVar(self, "whackamolepid", pid);
            debugSpeakMsg(self, "whackamole pid = " + pid);
            if (pid < 0)
            {
                return SCRIPT_CONTINUE;
            }
            subscribeToSUIEvent(pid, sui_event_type.SET_onButton, "%button0%", "whackamoleCallback");
            subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onButton, "%button0%", "score", "Text");
            subscribeToSUIEvent(pid, sui_event_type.SET_onButton, "%button1%", "whackamoleCallback");
            subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onButton, "%button1%", "score", "Text");
            subscribeToSUIEvent(pid, sui_event_type.SET_onButton, "%button2%", "whackamoleCallback");
            subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onButton, "%button2%", "score", "Text");
            setSUIProperty(pid, "bg.caption.lbltitle", "Text", "WHACK-A-MOLE");
            utils.setScriptVar(self, "whackamolescore", 0);
            setSUIProperty(pid, "score.numberBox", "Text", "0");
            setSUIAssociatedObject(pid, self);
            showSUIPage(pid);
            whackamoleRandomize(self, pid);
            messageTo(self, "whackamoleTimerCallback", null, 1, false);
        }
        else if (text.equals("testlib"))
        {
        }
        else if (text.equals("testweapon"))
        {
            obj_id weapon = getCurrentWeapon(self);
            debugSpeakMsg(self, "My weapon damage = " + getAverageDamage(weapon));
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
                    int hp = current - (int)(maxhp * .1f);
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
            String[] skills = getStringArrayObjVar(self, pclib.OBJVAR_JEDI_SKILL_REQUIREMENTS);
            if (skills != null)
            {
                for (int i = 0; i < skills.length; ++i)
                {
                    skill.grantSkillToPlayer(self, skills[i]);
                }
            }
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
            debugSpeakMsg(self, "jedi state = " + getJediState(target));
        }
        else if (text.equals("setjediforcesensitive"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            setJediState(target, JEDI_STATE_FORCE_SENSITIVE);
            debugSpeakMsg(self, "setting jedi state to JEDI_STATE_FORCE_SENSITIVE");
        }
        else if (text.equals("setjedijedi"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            setJediState(target, JEDI_STATE_JEDI);
            debugSpeakMsg(self, "setting jedi state to JEDI_STATE_JEDI");
        }
        else if (text.equals("setjediforcerankedlight"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            setJediState(target, JEDI_STATE_FORCE_RANKED_LIGHT);
            debugSpeakMsg(self, "setting jedi state to JEDI_STATE_FORCE_RANKED_LIGHT");
        }
        else if (text.equals("setjediforcerankeddark"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            setJediState(target, JEDI_STATE_FORCE_RANKED_DARK);
            debugSpeakMsg(self, "setting jedi state to JEDI_STATE_FORCE_RANKED_DARK");
        }
        else if (text.equals("setjedinone"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            setJediState(target, JEDI_STATE_NONE);
            debugSpeakMsg(self, "setting jedi state to JEDI_STATE_NONE");
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
    public int OnCustomizeFinished(obj_id self, obj_id object, String params) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        String var = st.nextToken();
        int i = Integer.parseInt(st.nextToken());
        debugSpeakMsg(self, "OnCustomizeFinished: " + object + " , '" + var + "' " + i);
        hue.setColor(object, var, i);
        if (st.hasMoreTokens())
        {
            var = st.nextToken();
            i = Integer.parseInt(st.nextToken());
            debugSpeakMsg(self, "OnCustomizeFinished: " + object + " , '" + var + "' " + i);
            hue.setColor(object, var, i);
        }
        if (st.hasMoreTokens())
        {
            var = st.nextToken();
            i = Integer.parseInt(st.nextToken());
            debugSpeakMsg(self, "OnCustomizeFinished: " + object + " , '" + var + "' " + i);
            hue.setColor(object, var, i);
        }
        return SCRIPT_CONTINUE;
    }
}
