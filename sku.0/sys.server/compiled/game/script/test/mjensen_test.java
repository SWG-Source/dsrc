package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.armor;
import script.library.skill;
import script.library.utils;
import script.library.create;
import script.library.respec;
import script.library.static_item;
import script.library.skill_template;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Arrays;
import script.library.qa;
import script.library.sui;
import script.library.utils;
import script.library.pclib;
import script.library.weapons;
import script.library.skill;
import script.library.gm;
import script.library.respec;
import script.library.buff;
import script.library.performance;
import script.library.space_transition;
import script.library.space_utils;
import script.library.space_create;
import script.library.ship_ai;

public class mjensen_test extends script.base_script
{
    public mjensen_test()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 50 || !isPlayer(self)) {
            detachScript(self, "test.mjensen_test");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        java.util.StringTokenizer tok = new java.util.StringTokenizer(text);
        obj_id pInv = utils.getInventoryContainer(self);
        if (text.equals("space-generateSpaceParts"))
        {
            obj_id itemArmor = createObject("object/tangible/ship/components/armor/arm_corellian_reinforced_light_durasteel.iff", pInv, "");
            setObjVar(itemArmor, "ship_comp.hitpoints_current", 5000.000f);
            setObjVar(itemArmor, "ship_comp.hitpoints_maximum", 5000.000f);
            setObjVar(itemArmor, "ship_comp.mass", 5000.000f);
            obj_id itemArmor2 = createObject("object/tangible/ship/components/armor/arm_corellian_reinforced_light_durasteel.iff", pInv, "");
            setObjVar(itemArmor2, "ship_comp.hitpoints_current", 5000.000f);
            setObjVar(itemArmor2, "ship_comp.hitpoints_maximum", 5000.000f);
            setObjVar(itemArmor2, "ship_comp.mass", 5000.000f);
            obj_id itemEngine = createObject("object/tangible/ship/components/engine/eng_cygnus_hdx.iff", pInv, "");
            setObjVar(itemEngine, "ship_comp.mass", 100.000f);
            setObjVar(itemEngine, "ship_comp.engine.pitch_rate_maximum", 150.000f);
            setObjVar(itemEngine, "ship_comp.engine.yaw_rate_maximum", 150.000f);
            setObjVar(itemEngine, "ship_comp.engine.roll_rate_maximum", 150.000f);
            setObjVar(itemEngine, "ship_comp.engine.speed_maximum", 250.000f);
            obj_id itemShield = createObject("object/tangible/ship/components/shield_generator/shd_koensayr_deflector_m3.iff", pInv, "");
            setObjVar(itemShield, "ship_comp.mass", 100.000f);
            setObjVar(itemShield, "ship_comp.shield.hitpoints_back_current", 5000.000f);
            setObjVar(itemShield, "ship_comp.shield.hitpoints_back_maximum", 5000.000f);
            setObjVar(itemShield, "ship_comp.shield.hitpoints_front_current", 5000.000f);
            setObjVar(itemShield, "ship_comp.shield.hitpoints_front_maximum", 5000.000f);
            setObjVar(itemShield, "ship_comp.shield.recharge_rate", 500.000f);
            obj_id itemBooster = createObject("object/tangible/ship/components/booster/bst_corellian_experimental_tjh3.iff", pInv, "");
            setObjVar(itemBooster, "ship_comp.booster.acceleration", 80.000f);
            setObjVar(itemBooster, "ship_comp.booster.energy_consumption_rate", 150.000f);
            setObjVar(itemBooster, "ship_comp.booster.energy_current", 3000.000f);
            setObjVar(itemBooster, "ship_comp.booster.energy_maximum", 3000.000f);
            setObjVar(itemBooster, "ship_comp.booster.energy_recharge_rate", 500.00f);
            setObjVar(itemBooster, "ship_comp.booster.speed_maximum", 400.000f);
            setObjVar(itemBooster, "ship_comp.mass", 100.000f);
            obj_id itemRactor = createObject("object/tangible/ship/components/reactor/rct_freitek_improved_powerhouse_mk1.iff", pInv, "");
            setObjVar(itemRactor, "ship_comp.mass", 100.000f);
            setObjVar(itemRactor, "ship_comp.reactor.energy_generation_rate", 80000.000f);
            obj_id itemCap = createObject("object/tangible/ship/components/weapon_capacitor/cap_corellian_cruiser_grade_cap9.iff", pInv, "");
            setObjVar(itemCap, "ship_comp.mass", 100.000f);
            setObjVar(itemCap, "ship_comp.capacitor.energy_current", 5000.000f);
            setObjVar(itemCap, "ship_comp.capacitor.energy_maximum", 5000.000f);
            setObjVar(itemCap, "ship_comp.capacitor.energy_recharge_rate", 250.000f);
            obj_id itemWeapon = createObject("object/tangible/ship/components/weapon/wpn_mission_reward_rebel_incom_tricannon.iff", pInv, "");
            setObjVar(itemWeapon, "ship_comp.mass", 100.000f);
            setObjVar(itemWeapon, "ship_comp.weapon.damage_maximum", 5000.000f);
            setObjVar(itemWeapon, "ship_comp.weapon.damage_minimum", 3000.000f);
            setObjVar(itemWeapon, "ship_comp.weapon.effectiveness_armor", 0.950f);
            setObjVar(itemWeapon, "ship_comp.weapon.effectiveness_shields", 0.950f);
            setObjVar(itemWeapon, "ship_comp.weapon.energy_per_shot", 15.000f);
            setObjVar(itemWeapon, "ship_comp.weapon.refire_rate", 0.3900f);
        }
        if (text.equals("makeZoneToChips"))
        {
            obj_id moduleImpDS = createObject("object/tangible/droid/droid_space_memory_module_1.iff", pInv, "");
            setObjVar(moduleImpDS, "programSize", 1);
            setObjVar(moduleImpDS, "strDroidCommand", "droidcommand_zonetoimperialdeepspace");
            setName(moduleImpDS, "");
            setName(moduleImpDS, new string_id("space/droid_commands", "droidcommand_zonetoimperialdeepspace_chipname"));
            obj_id moduleRebDS = createObject("object/tangible/droid/droid_space_memory_module_1.iff", pInv, "");
            setObjVar(moduleRebDS, "programSize", 1);
            setObjVar(moduleRebDS, "strDroidCommand", "droidcommand_zonetorebeldeepspace");
            setName(moduleRebDS, "");
            setName(moduleRebDS, new string_id("space/droid_commands", "droidcommand_zonetorebeldeepspace_chipname"));
            obj_id moduleKessel = createObject("object/tangible/droid/droid_space_memory_module_1.iff", pInv, "");
            setObjVar(moduleKessel, "programSize", 1);
            setObjVar(moduleKessel, "strDroidCommand", "droidcommand_zonetokessel");
            setName(moduleKessel, "");
            setName(moduleKessel, new string_id("space/droid_commands", "droidcommand_zonetokessel_chipname"));
        }
        if (text.equals("spawnShip"))
        {
            transform gloc = getTransform_o2w(space_transition.getContainingShip(self));
            float dist = rand(50.f, 100.f);
            vector n = ((gloc.getLocalFrameK_p()).normalize()).multiply(dist);
            gloc = gloc.move_p(n);
            String targetShipType = "experimental_ship";
            obj_id targetShip = space_create.createShipHyperspace(targetShipType, gloc);
            sendSystemMessage(self, "Spawned ship - OID: " + targetShip, null);
        }
        if (text.equals("spawnShip2"))
        {
            transform gloc = getTransform_o2w(space_transition.getContainingShip(self));
            float dist = rand(50.f, 100.f);
            vector n = ((gloc.getLocalFrameK_p()).normalize()).multiply(dist);
            gloc = gloc.move_p(n);
            String targetShipType = "reb_gunboat_kessel_tier5";
            obj_id targetShip = space_create.createShipHyperspace(targetShipType, gloc);
            sendSystemMessage(self, "Spawned ship - OID: " + targetShip, null);
        }
        if (text.equals("cleanupPiracyEvent"))
        {
            obj_id beacon = utils.getObjIdScriptVar(self, "beacon");
            messageTo(beacon, "cleanupPiracyEvent", null, 3.f, false);
        }
        if (text.equals("rebelHelper"))
        {
            obj_id ship = space_transition.getContainingShip(self);
            setObjVar(ship, "spaceFaction.FactionOverride", (370444368));
            space_utils.notifyObject(ship, "checkSpacePVPStatus", null);
            sendSystemMessage(self, "Starting rebel fac helper", null);
        }
        if (text.equals("imperialHelper"))
        {
            obj_id ship = space_transition.getContainingShip(self);
            setObjVar(ship, "spaceFaction.FactionOverride", (-615855020));
            space_utils.notifyObject(ship, "checkSpacePVPStatus", null);
            sendSystemMessage(self, "Starting imperial fac helper", null);
        }
        if (text.equals("cleanHyper"))
        {
            obj_id ship = space_transition.getContainingShip(self);
            utils.removeScriptVar(self, "space.zoneDestination");
            sendSystemMessage(self, "clean scriptVars", null);
        }
        if (text.equals("scriptVarCleanup"))
        {
            obj_id ship = space_transition.getContainingShip(self);
            utils.removeScriptVar(ship, "space.goOvertTimer");
            utils.removeScriptVar(ship, "space.goOvert");
            utils.removeScriptVar(ship, "space.goOvertLoc");
        }
        if (text.equals("stopBlink"))
        {
            obj_id ship = space_transition.getContainingShip(self);
            pvpPrepareToBeNeutral(ship);
        }
        if (text.equals("startBlink"))
        {
            obj_id ship = space_transition.getContainingShip(self);
            pvpPrepareToBeDeclared(ship);
        }
        if (text.equals("spawnExpShip"))
        {
            int numships = 4;
            obj_id[] escortIdArray = new obj_id[4];
            for (int i = 0; i < numships; i++)
            {
                int escortSquad = ship_ai.squadCreateSquadId();
                obj_id ship = space_transition.getContainingShip(self);
                transform t = getTransform_o2w(ship);
                transform spawnLoc = t.move_l(new vector(rand(-200, 200), rand(-200, 200), rand(-200, 200)));
                sendSystemMessage(self, "spawning", null);
                escortIdArray[i] = space_create.createShipHyperspace("experimental_ship", spawnLoc);
                addMissionCriticalObject(self, escortIdArray[i]);
                ship_ai.unitSetLeashDistance(escortIdArray[i], 16000);
                ship_ai.unitSetSquadId(escortIdArray[i], escortSquad);
                ship_ai.unitAddExclusiveAggro(escortIdArray[i], self);
                ship_ai.squadSetLeader(escortSquad, escortIdArray[i]);
                ship_ai.squadSetFormation(escortSquad, 5);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void logIt(String logText) throws InterruptedException
    {
        LOG("mikkel", logText);
    }
    public void sysMes(String sysMessage) throws InterruptedException
    {
        obj_id self = getSelf();
        sendSystemMessageTestingOnly(self, sysMessage);
    }
}
