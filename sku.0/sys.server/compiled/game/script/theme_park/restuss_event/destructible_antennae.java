package script.theme_park.restuss_event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.sui;
import script.library.groundquests;
import script.library.factions;

public class destructible_antennae extends script.base_script
{
    public destructible_antennae()
    {
    }
    public static final string_id SID_MNU_EXPLOSIVE = new string_id("restuss_event/object", "antenna_explosive");
    public static final string_id SID_MNU_ARM_EXPLOSIVE = new string_id("restuss_event/object", "antenna_arm_explosive");
    public static final string_id SID_MNU_WRONG_FCT = new string_id("restuss_event/object", "incorrect_faction");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setMaxHitpoints(self, 90000);
        setHitpoints(self, 90000);
        setInvulnerable(self, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setMaxHitpoints(self, 90000);
        setHitpoints(self, 90000);
        setInvulnerable(self, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        String myTemplate = getTemplateName(self);
        int initialHitPoints = getHitpoints(self);
        if (myTemplate.equals("object/tangible/dungeon/restuss_event/restuss_rebel_antennae.iff"))
        {
            if (factions.isRebel(attacker))
            {
                setHitpoints(self, (initialHitPoints + damage));
                return SCRIPT_CONTINUE;
            }
        }
        else if (myTemplate.equals("object/tangible/dungeon/restuss_event/restuss_imperial_antennae.iff"))
        {
            if (factions.isImperial(attacker))
            {
                setHitpoints(self, (initialHitPoints + damage));
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        location loc = getLocation(self);
        obj_id[] playersInRange = getPlayerCreaturesInRange(self, 64.0f);
        String myTemplate = getTemplateName(self);
        playClientEffectLoc(killer, "clienteffect/combat_explosion_lair_large.cef", loc, 0);
        if (myTemplate.equals("object/tangible/dungeon/restuss_event/restuss_rebel_antennae.iff"))
        {
            groundquests.sendSignal(playersInRange, "blewAntennaImperial");
        }
        else if (myTemplate.equals("object/tangible/dungeon/restuss_event/restuss_imperial_antennae.iff"))
        {
            groundquests.sendSignal(playersInRange, "blewAntennaRebel");
        }
        messageTo(self, "antennaDestroyed", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        String myTemplate = getTemplateName(self);
        if (groundquests.isTaskActive(player, "restuss_imperial_st3_destroy_installation_1_commando", "blowAntennaImperialCommando1") && myTemplate.equals("object/tangible/dungeon/restuss_event/restuss_rebel_antennae.iff"))
        {
            if (factions.isImperial(player))
            {
                mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_EXPLOSIVE);
            }
            else 
            {
                sendSystemMessage(player, SID_MNU_WRONG_FCT);
            }
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.isTaskActive(player, "restuss_rebel_st3_destroy_installation_1_commando", "blowAntennaRebelCommando1") && myTemplate.equals("object/tangible/dungeon/restuss_event/restuss_imperial_antennae.iff"))
        {
            if (factions.isRebel(player))
            {
                mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_EXPLOSIVE);
            }
            else 
            {
                sendSystemMessage(player, SID_MNU_WRONG_FCT);
            }
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.isTaskActive(player, "restuss_imperial_st3_destroy_installation_1_commando", "blowAntennaImperialCommando2") && myTemplate.equals("object/tangible/dungeon/restuss_event/restuss_rebel_antennae.iff"))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_ARM_EXPLOSIVE);
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.isTaskActive(player, "restuss_rebel_st3_destroy_installation_1_commando", "blowAntennaRebelCommando2") && myTemplate.equals("object/tangible/dungeon/restuss_event/restuss_imperial_antennae.iff"))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_ARM_EXPLOSIVE);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            String myTemplate = getTemplateName(self);
            if (groundquests.isTaskActive(player, "restuss_imperial_st3_destroy_installation_1_commando", "blowAntennaImperialCommando1") && myTemplate.equals("object/tangible/dungeon/restuss_event/restuss_rebel_antennae.iff"))
            {
                groundquests.sendSignal(player, "blewAntennaImperialCommando1");
                return SCRIPT_CONTINUE;
            }
            if (groundquests.isTaskActive(player, "restuss_rebel_st3_destroy_installation_1_commando", "blowAntennaRebelCommando1") && myTemplate.equals("object/tangible/dungeon/restuss_event/restuss_imperial_antennae.iff"))
            {
                groundquests.sendSignal(player, "blewAntennaRebelCommando1");
                return SCRIPT_CONTINUE;
            }
            if (groundquests.isTaskActive(player, "restuss_imperial_st3_destroy_installation_1_commando", "blowAntennaImperialCommando2") && myTemplate.equals("object/tangible/dungeon/restuss_event/restuss_rebel_antennae.iff"))
            {
                obj_id[] playerInRange = getPlayerCreaturesInRange(self, 40.0f);
                groundquests.sendSignal(playerInRange, "blewAntennaImperial");
                messageTo(self, "antennaDestroyedCommando", null, 6.0f, false);
                return SCRIPT_CONTINUE;
            }
            if (groundquests.isTaskActive(player, "restuss_rebel_st3_destroy_installation_1_commando", "blowAntennaRebelCommando2") && myTemplate.equals("object/tangible/dungeon/restuss_event/restuss_imperial_antennae.iff"))
            {
                obj_id[] playerInRange = getPlayerCreaturesInRange(self, 40.0f);
                groundquests.sendSignal(player, "blewAntennaRebel");
                messageTo(self, "antennaDestroyedCommando", null, 6.0f, false);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int antennaDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int antennaDestroyedCommando(obj_id self, dictionary params) throws InterruptedException
    {
        location loc = getLocation(self);
        obj_id[] playersInRange = getPlayerCreaturesInRange(self, 40.0f);
        playClientEffectLoc(playersInRange, "clienteffect/combat_explosion_lair_large.cef", loc, 0);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
