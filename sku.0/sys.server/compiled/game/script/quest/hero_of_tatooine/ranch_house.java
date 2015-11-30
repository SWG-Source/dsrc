package script.quest.hero_of_tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.ai_lib;
import script.library.chat;

public class ranch_house extends script.base_script
{
    public ranch_house()
    {
    }
    public static final String RUG = "object/tangible/furniture/all/frn_all_rug_rectangle_large_style_04.iff";
    public static final String STATUE01 = "object/tangible/furniture/all/frn_all_decorative_lg_s1.iff";
    public static final String STATUE02 = "object/tangible/furniture/all/frn_all_decorative_lg_s2.iff";
    public static final String BOX = "object/tangible/terminal/terminal_elevator_up.iff";
    public static final string_id SUCCESS = new string_id("quest/hero_of_tatooine/system_messages", "success");
    public static final string_id FAILED = new string_id("quest/hero_of_tatooine/system_messages", "failed");
    public static final string_id RESTART = new string_id("quest/hero_of_tatooine/system_messages", "restart");
    public static final string_id ENTER = new string_id("quest/hero_of_tatooine/system_messages", "enter");
    public static final string_id RESET = new string_id("quest/hero_of_tatooine/system_messages", "reset");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "spawnDudes", null, 1, false);
        String[] cell = getCellNames(self);
        permissionsMakePrivate(getCellId(self, "bedroom1"));
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (text.equals("reset"))
        {
            messageTo(self, "reset", null, 1f, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "cleanUp", null, 0.0f, false);
        permissionsMakePrivate(getCellId(self, "bedroom1"));
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "quest.hero_of_tatooine.progress"))
        {
            if (!hasObjVar(item, "quest.hero_of_tatooine.honor"))
            {
                if (hasObjVar(item, "quest.hero_of_tatooine.rancherquest"))
                {
                    obj_id hallcell01 = getCellId(self, "hall1");
                    if (destinationCell == hallcell01)
                    {
                        setObjVar(self, "quest.hero_of_tatooine.progress", item);
                        setObjVar(self, "quest.hero_of_tatooine.timer", getGameTime());
                        messageTo(self, "restart", null, 300f, false);
                        sendSystemMessage(item, ENTER);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnDudes(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "quest.hero_of_tatooine.spawned"))
        {
            location wifeLocation = new location(-6.53f, 0.03f, -3.21f, "tatooine", getCellId(self, "livingroom"));
            obj_id wife = create.object("commoner_human_female", wifeLocation);
            setInvulnerable(wife, true);
            attachScript(wife, "conversation.quest_hero_of_tatooine_wife");
            setObjVar(self, "quest.hero_of_tatooine.spawned", true);
            setObjVar(self, "wife", wife);
            location boxLocation = new location(-6.33f, -3.57f, -7.27f, "tatooine", getCellId(self, "stair1"));
            obj_id box = create.object(BOX, boxLocation);
            setYaw(box, 175f);
            setName(box, "Intercom");
            setCondition(self, CONDITION_CONVERSABLE);
            detachScript(box, "systems.elevator.elevator_up");
            attachScript(box, "conversation.quest_hero_of_tatooine_box");
            setObjVar(self, "box", box);
            location statue01Location = new location(2.36f, 0.49f, 0.67f, "tatooine", getCellId(self, "foyer"));
            obj_id statue01 = create.object(STATUE01, statue01Location);
            setObjVar(self, "statue01", statue01);
            location statue02Location = new location(-10.62f, 0.30f, -2.10f, "tatooine", getCellId(self, "livingroom"));
            obj_id statue02 = create.object(STATUE02, statue02Location);
            setYaw(statue02, 90f);
            setObjVar(self, "statue02", statue02);
            location rugLocation = new location(-8.07f, 0.31f, -3.29f, "tatooine", getCellId(self, "livingroom"));
            obj_id rug = create.object(RUG, rugLocation);
            setObjVar(self, "rug", rug);
        }
        return SCRIPT_CONTINUE;
    }
    public int piratesEscape(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id guy = getObjIdObjVar(self, "quest.hero_of_tatooine.progress");
        location explode = new location(-4.78f, 0.31f, -2.30f, "tatooine", getCellId(self, "livingroom"));
        playClientEffectLoc(guy, "clienteffect/combat_grenade_thermal_detonator.cef", explode, 0);
        playClientEffectLoc(guy, "clienteffect/cr_bodyfall_huge.cef", explode, 0);
        location pirate01Location = new location(-9.03f, -3.97f, -8.63f, "tatooine", getCellId(self, "stair1"));
        obj_id pirate01 = create.object("fugitive", pirate01Location);
        attachScript(pirate01, "quest.hero_of_tatooine.pirate_01");
        setObjVar(self, "pirate01", pirate01);
        location pirate02Location = new location(-7.26f, -3.97f, -9.66f, "tatooine", getCellId(self, "stair1"));
        obj_id pirate02 = create.object("fugitive", pirate02Location);
        attachScript(pirate02, "quest.hero_of_tatooine.pirate_02");
        setObjVar(self, "pirate02", pirate02);
        location pirate03Location = new location(-5.96f, -3.97f, -7.76f, "tatooine", getCellId(self, "stair1"));
        obj_id pirate03 = create.object("fugitive", pirate03Location);
        attachScript(pirate03, "quest.hero_of_tatooine.pirate_03");
        setObjVar(self, "pirate03", pirate03);
        location pirate04Location = new location(4.48f, -3.41f, -9.66f, "tatooine", getCellId(self, "stair1"));
        obj_id pirate04 = create.object("fugitive", pirate04Location);
        attachScript(pirate04, "quest.hero_of_tatooine.pirate_04");
        setObjVar(self, "pirate04", pirate04);
        messageTo(self, "cleanUp", null, 120f, false);
        messageTo(self, "failed", null, 3f, false);
        return SCRIPT_CONTINUE;
    }
    public int piratesLose(obj_id self, dictionary params) throws InterruptedException
    {
        location rancherLocation = new location(-9.03f, -3.97f, -8.63f, "tatooine", getCellId(self, "hall1"));
        obj_id rancher = create.object("commoner_human_male", rancherLocation);
        attachScript(rancher, "quest.hero_of_tatooine.rancher");
        setObjVar(self, "rancher", rancher);
        location auth01Location = new location(-9.03f, -3.97f, -8.63f, "tatooine", getCellId(self, "hall1"));
        obj_id auth01 = create.object("crackdown_elite_dark_trooper", auth01Location);
        attachScript(auth01, "quest.hero_of_tatooine.auth_01");
        setObjVar(self, "auth01", auth01);
        location auth02Location = new location(-9.03f, -3.97f, -8.63f, "tatooine", getCellId(self, "hall1"));
        obj_id auth02 = create.object("crackdown_elite_dark_trooper", auth02Location);
        attachScript(auth02, "quest.hero_of_tatooine.auth_02");
        setObjVar(self, "auth02", auth02);
        messageTo(self, "cleanUp", null, 120f, false);
        messageTo(self, "success", null, 10f, false);
        return SCRIPT_CONTINUE;
    }
    public int piratesCaptured(obj_id self, dictionary params) throws InterruptedException
    {
        location pirate01Location = new location(-9.03f, -3.97f, -8.63f, "tatooine", getCellId(self, "stair1"));
        obj_id pirate01 = create.object("fugitive", pirate01Location);
        attachScript(pirate01, "quest.hero_of_tatooine.pirate_captured_01");
        setObjVar(self, "pirate01", pirate01);
        location pirate02Location = new location(-7.26f, -3.97f, -9.66f, "tatooine", getCellId(self, "stair1"));
        obj_id pirate02 = create.object("fugitive", pirate02Location);
        attachScript(pirate02, "quest.hero_of_tatooine.pirate_captured_02");
        setObjVar(self, "pirate02", pirate02);
        return SCRIPT_CONTINUE;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id wife = getObjIdObjVar(self, "wife");
        if (isIdValid(wife) && exists(wife))
        {
            destroyObject(wife);
        }
        removeObjVar(self, "wife");
        obj_id box = getObjIdObjVar(self, "box");
        if (isIdValid(box) && exists(box))
        {
            destroyObject(box);
        }
        removeObjVar(self, "box");
        obj_id rug = getObjIdObjVar(self, "rug");
        if (isIdValid(rug) && exists(rug))
        {
            destroyObject(rug);
        }
        removeObjVar(self, "rug");
        obj_id statue01 = getObjIdObjVar(self, "statue01");
        if (isIdValid(statue01) && exists(statue01))
        {
            destroyObject(statue01);
        }
        removeObjVar(self, "statue01");
        obj_id statue02 = getObjIdObjVar(self, "statue02");
        if (isIdValid(statue02) && exists(statue02))
        {
            destroyObject(statue02);
        }
        removeObjVar(self, "statue02");
        obj_id pirate01 = getObjIdObjVar(self, "pirate01");
        if (isIdValid(pirate01) && exists(pirate01))
        {
            destroyObject(pirate01);
        }
        removeObjVar(self, "pirate01");
        obj_id pirate02 = getObjIdObjVar(self, "pirate02");
        if (isIdValid(pirate02) && exists(pirate02))
        {
            destroyObject(pirate02);
        }
        removeObjVar(self, "pirate02");
        obj_id pirate03 = getObjIdObjVar(self, "pirate03");
        if (isIdValid(pirate03) && exists(pirate03))
        {
            destroyObject(pirate03);
        }
        removeObjVar(self, "pirate03");
        obj_id pirate04 = getObjIdObjVar(self, "pirate04");
        if (isIdValid(pirate04) && exists(pirate04))
        {
            destroyObject(pirate04);
        }
        removeObjVar(self, "pirate04");
        obj_id rancher = getObjIdObjVar(self, "rancher");
        if (isIdValid(rancher) && exists(rancher))
        {
            destroyObject(rancher);
        }
        removeObjVar(self, "rancher");
        obj_id auth01 = getObjIdObjVar(self, "auth01");
        if (isIdValid(auth01) && exists(auth01))
        {
            destroyObject(auth01);
        }
        removeObjVar(self, "auth01");
        obj_id auth02 = getObjIdObjVar(self, "auth02");
        if (isIdValid(auth02) && exists(auth02))
        {
            destroyObject(auth02);
        }
        removeObjVar(self, "auth02");
        removeObjVar(self, "quest.hero_of_tatooine.spawned");
        removeObjVar(self, "quest.hero_of_tatooine.progress");
        removeObjVar(self, "quest.hero_of_tatooine.timer");
        messageTo(self, "spawnDudes", null, 5f, false);
        return SCRIPT_CONTINUE;
    }
    public int failed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player01 = getObjIdObjVar(self, "quest.hero_of_tatooine.progress");
        sendSystemMessage(player01, FAILED);
        removeObjVar(player01, "quest.hero_of_tatooine.rancherquest");
        removeObjVar(player01, "quest.hero_of_tatooine.distract");
        if (hasObjVar(player01, "quest.hero_of_tatooine.honor_waypoint"))
        {
            obj_id waypoint = getObjIdObjVar(player01, "quest.hero_of_tatooine.honor_waypoint");
            destroyObject(waypoint);
        }
        return SCRIPT_CONTINUE;
    }
    public int success(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player01 = getObjIdObjVar(self, "quest.hero_of_tatooine.progress");
        sendSystemMessage(player01, SUCCESS);
        if (hasObjVar(player01, "quest.hero_of_tatooine.honor_waypoint"))
        {
            obj_id waypoint = getObjIdObjVar(player01, "quest.hero_of_tatooine.honor_waypoint");
            destroyObject(waypoint);
        }
        return SCRIPT_CONTINUE;
    }
    public int restart(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "quest.hero_of_tatooine.timer"))
        {
            int died = getIntObjVar(self, "quest.hero_of_tatooine.timer");
            int gametime = getGameTime();
            if ((died + 420) > gametime)
            {
                messageTo(self, "restart", null, 5f, false);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                obj_id wife = getObjIdObjVar(self, "wife");
                destroyObject(wife);
                removeObjVar(self, "wife");
                obj_id box = getObjIdObjVar(self, "box");
                destroyObject(box);
                removeObjVar(self, "box");
                obj_id rug = getObjIdObjVar(self, "rug");
                destroyObject(rug);
                removeObjVar(self, "rug");
                obj_id statue01 = getObjIdObjVar(self, "statue01");
                destroyObject(statue01);
                removeObjVar(self, "statue01");
                obj_id statue02 = getObjIdObjVar(self, "statue02");
                destroyObject(statue02);
                removeObjVar(self, "statue02");
                obj_id pirate01 = getObjIdObjVar(self, "pirate01");
                destroyObject(pirate01);
                removeObjVar(self, "pirate01");
                obj_id pirate02 = getObjIdObjVar(self, "pirate02");
                destroyObject(pirate02);
                removeObjVar(self, "pirate02");
                obj_id pirate03 = getObjIdObjVar(self, "pirate03");
                destroyObject(pirate03);
                removeObjVar(self, "pirate03");
                obj_id pirate04 = getObjIdObjVar(self, "pirate04");
                destroyObject(pirate04);
                removeObjVar(self, "pirate04");
                obj_id rancher = getObjIdObjVar(self, "rancher");
                destroyObject(rancher);
                removeObjVar(self, "rancher");
                obj_id auth01 = getObjIdObjVar(self, "auth01");
                destroyObject(auth01);
                removeObjVar(self, "auth01");
                obj_id auth02 = getObjIdObjVar(self, "auth02");
                destroyObject(auth02);
                removeObjVar(self, "auth02");
                obj_id player = getObjIdObjVar(self, "quest.hero_of_tatooine.progress");
                removeObjVar(self, "quest.hero_of_tatooine.spawned");
                removeObjVar(self, "quest.hero_of_tatooine.progress");
                removeObjVar(self, "quest.hero_of_tatooine.timer");
                messageTo(self, "spawnDudes", null, 5f, false);
                sendSystemMessage(player, RESTART);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int reset(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id wife = getObjIdObjVar(self, "wife");
        destroyObject(wife);
        removeObjVar(self, "wife");
        obj_id box = getObjIdObjVar(self, "box");
        destroyObject(box);
        removeObjVar(self, "box");
        obj_id rug = getObjIdObjVar(self, "rug");
        destroyObject(rug);
        removeObjVar(self, "rug");
        obj_id statue01 = getObjIdObjVar(self, "statue01");
        destroyObject(statue01);
        removeObjVar(self, "statue01");
        obj_id statue02 = getObjIdObjVar(self, "statue02");
        destroyObject(statue02);
        removeObjVar(self, "statue02");
        obj_id pirate01 = getObjIdObjVar(self, "pirate01");
        destroyObject(pirate01);
        removeObjVar(self, "pirate01");
        obj_id pirate02 = getObjIdObjVar(self, "pirate02");
        destroyObject(pirate02);
        removeObjVar(self, "pirate02");
        obj_id pirate03 = getObjIdObjVar(self, "pirate03");
        destroyObject(pirate03);
        removeObjVar(self, "pirate03");
        obj_id pirate04 = getObjIdObjVar(self, "pirate04");
        destroyObject(pirate04);
        removeObjVar(self, "pirate04");
        obj_id rancher = getObjIdObjVar(self, "rancher");
        destroyObject(rancher);
        removeObjVar(self, "rancher");
        obj_id auth01 = getObjIdObjVar(self, "auth01");
        destroyObject(auth01);
        removeObjVar(self, "auth01");
        obj_id auth02 = getObjIdObjVar(self, "auth02");
        destroyObject(auth02);
        removeObjVar(self, "auth02");
        obj_id player = getObjIdObjVar(self, "quest.hero_of_tatooine.progress");
        removeObjVar(self, "quest.hero_of_tatooine.spawned");
        removeObjVar(self, "quest.hero_of_tatooine.progress");
        removeObjVar(self, "quest.hero_of_tatooine.timer");
        messageTo(self, "spawnDudes", null, 5f, false);
        sendSystemMessage(player, RESTART);
        return SCRIPT_CONTINUE;
    }
}
