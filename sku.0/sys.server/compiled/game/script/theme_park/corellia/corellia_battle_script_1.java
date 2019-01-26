package script.theme_park.corellia;

import script.dictionary;
import script.library.advanced_turret;
import script.library.ai_lib;
import script.library.create;
import script.location;
import script.obj_id;

public class corellia_battle_script_1 extends script.base_script
{
    public corellia_battle_script_1()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        spawnEveryone(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "Spawning Everyone");
        spawnCelebs(self);
        setObjVar(self, "people", 1);
        return;
    }
    public void spawnCelebs(obj_id self) throws InterruptedException
    {
        spawnImperial01(self);
        spawnImperial02(self);
        spawnImperial03(self);
        spawnImperial04(self);
        spawnImperial05(self);
        spawnImperial06(self);
        spawnImperial07(self);
        spawnImperial08(self);
        spawnImperial09(self);
        spawnImperial10(self);
        spawnImperial11(self);
        spawnImperial12(self);
        spawnImperial_atst(self);
        spawnRebel_01(self);
        spawnRebel_02(self);
        spawnRebel_03(self);
        spawnRebel_04(self);
        spawnRebel_05(self);
        spawnRebel_06(self);
        spawnRebel_07(self);
        spawnRebel_08(self);
        spawnRebel_09(self);
        spawnRebel_10(self);
        spawnRebel_11(self);
        spawnRebel_12(self);
        spawnRebel_13(self);
        spawnRebel_14(self);
        spawnRebel_15(self);
        spawnRebel_16(self);
        spawnTurret01(self);
        spawnTurret2(self);
        spawnTurret3(self);
    }
    public void spawnImperial01(obj_id self) throws InterruptedException
    {
        location imp01 = new location(3953.0f, 26.0f, -1165, "corellia", null);
        obj_id stormtrooper01 = create.object("stormtrooper_squad_leader", imp01);
        ai_lib.setDefaultCalmBehavior(stormtrooper01, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(stormtrooper01, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.stormtrooper01", stormtrooper01);
        setObjVar(stormtrooper01, "hideout", self);
        location elseWhere = new location(4088, 24, -1269, "corellia", null);
        ai_lib.aiPathTo(stormtrooper01, elseWhere);
        setHomeLocation(stormtrooper01, elseWhere);
        return;
    }
    public void spawnImperial02(obj_id self) throws InterruptedException
    {
        location imp02 = new location(3956.0f, 26.0f, -1165, "corellia", null);
        obj_id stormtrooper02 = create.object("stormtrooper", imp02);
        ai_lib.setDefaultCalmBehavior(stormtrooper02, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(stormtrooper02, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.stormtrooper02", stormtrooper02);
        setObjVar(stormtrooper02, "hideout", self);
        location elseWhere = new location(4088, 24, -1269, "corellia", null);
        ai_lib.aiPathTo(stormtrooper02, elseWhere);
        setHomeLocation(stormtrooper02, elseWhere);
        return;
    }
    public void spawnImperial03(obj_id self) throws InterruptedException
    {
        location imp03 = new location(3950.0f, 26.0f, -1165, "corellia", null);
        obj_id stormtrooper03 = create.object("stormtrooper", imp03);
        ai_lib.setDefaultCalmBehavior(stormtrooper03, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(stormtrooper03, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.stormtrooper03", stormtrooper03);
        setObjVar(stormtrooper03, "hideout", self);
        location elseWhere = new location(4090, 24, -1272, "corellia", null);
        ai_lib.aiPathTo(stormtrooper03, elseWhere);
        setHomeLocation(stormtrooper03, elseWhere);
        return;
    }
    public void spawnImperial04(obj_id self) throws InterruptedException
    {
        location imp04 = new location(3953.0f, 26.0f, -1162, "corellia", null);
        obj_id stormtrooper04 = create.object("stormtrooper", imp04);
        ai_lib.setDefaultCalmBehavior(stormtrooper04, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(stormtrooper04, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.stormtrooper04", stormtrooper04);
        setObjVar(stormtrooper04, "hideout", self);
        location elseWhere = new location(4090, 24, -1272, "corellia", null);
        ai_lib.aiPathTo(stormtrooper04, elseWhere);
        setHomeLocation(stormtrooper04, elseWhere);
        return;
    }
    public void spawnImperial05(obj_id self) throws InterruptedException
    {
        location imp05 = new location(4100.0f, 22.0f, -1440, "corellia", null);
        obj_id stormtrooper05 = create.object("stormtrooper_squad_leader", imp05);
        ai_lib.setDefaultCalmBehavior(stormtrooper05, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(stormtrooper05, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.stormtrooper05", stormtrooper05);
        setObjVar(stormtrooper05, "hideout", self);
        location elseWhere = new location(4093, 24, -1277, "corellia", null);
        ai_lib.aiPathTo(stormtrooper05, elseWhere);
        setHomeLocation(stormtrooper05, elseWhere);
        return;
    }
    public void spawnImperial06(obj_id self) throws InterruptedException
    {
        location imp06 = new location(4102.0f, 22.0f, -1440, "corellia", null);
        obj_id stormtrooper06 = create.object("stormtrooper", imp06);
        ai_lib.setDefaultCalmBehavior(stormtrooper06, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(stormtrooper06, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.stormtrooper06", stormtrooper06);
        setObjVar(stormtrooper06, "hideout", self);
        location elseWhere = new location(4093, 24, -1277, "corellia", null);
        ai_lib.aiPathTo(stormtrooper06, elseWhere);
        setHomeLocation(stormtrooper06, elseWhere);
        return;
    }
    public void spawnImperial07(obj_id self) throws InterruptedException
    {
        location imp07 = new location(4104.0f, 22.0f, -1440, "corellia", null);
        obj_id stormtrooper07 = create.object("stormtrooper", imp07);
        ai_lib.setDefaultCalmBehavior(stormtrooper07, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(stormtrooper07, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.stormtrooper07", stormtrooper07);
        setObjVar(stormtrooper07, "hideout", self);
        location elseWhere = new location(4096, 24, -1279, "corellia", null);
        ai_lib.aiPathTo(stormtrooper07, elseWhere);
        setHomeLocation(stormtrooper07, elseWhere);
        return;
    }
    public void spawnImperial08(obj_id self) throws InterruptedException
    {
        location imp08 = new location(4106.0f, 22.0f, -1440, "corellia", null);
        obj_id stormtrooper08 = create.object("stormtrooper", imp08);
        ai_lib.setDefaultCalmBehavior(stormtrooper08, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(stormtrooper08, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.stormtrooper08", stormtrooper08);
        setObjVar(stormtrooper08, "hideout", self);
        location elseWhere = new location(4096, 24, -1279, "corellia", null);
        ai_lib.aiPathTo(stormtrooper08, elseWhere);
        setHomeLocation(stormtrooper08, elseWhere);
        return;
    }
    public void spawnImperial09(obj_id self) throws InterruptedException
    {
        location imp09 = new location(4294.0f, 26.0f, -1332, "corellia", null);
        obj_id stormtrooper09 = create.object("stormtrooper_squad_leader", imp09);
        ai_lib.setDefaultCalmBehavior(stormtrooper09, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(stormtrooper09, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.stormtrooper09", stormtrooper09);
        setObjVar(stormtrooper09, "hideout", self);
        location elseWhere = new location(4101, 24, -1283, "corellia", null);
        ai_lib.aiPathTo(stormtrooper09, elseWhere);
        setHomeLocation(stormtrooper09, elseWhere);
        return;
    }
    public void spawnImperial10(obj_id self) throws InterruptedException
    {
        location imp10 = new location(4288.0f, 26.0f, -1332, "corellia", null);
        obj_id stormtrooper10 = create.object("stormtrooper_squad_leader", imp10);
        ai_lib.setDefaultCalmBehavior(stormtrooper10, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(stormtrooper10, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.stormtrooper10", stormtrooper10);
        setObjVar(stormtrooper10, "hideout", self);
        location elseWhere = new location(4101, 24, -1283, "corellia", null);
        ai_lib.aiPathTo(stormtrooper10, elseWhere);
        setHomeLocation(stormtrooper10, elseWhere);
        return;
    }
    public void spawnImperial11(obj_id self) throws InterruptedException
    {
        location imp11 = new location(4291.0f, 26.0f, -1332, "corellia", null);
        obj_id stormtrooper11 = create.object("stormtrooper", imp11);
        ai_lib.setDefaultCalmBehavior(stormtrooper11, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(stormtrooper11, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.stormtrooper11", stormtrooper11);
        setObjVar(stormtrooper11, "hideout", self);
        location elseWhere = new location(4105, 24, -1284, "corellia", null);
        ai_lib.aiPathTo(stormtrooper11, elseWhere);
        setHomeLocation(stormtrooper11, elseWhere);
        return;
    }
    public void spawnImperial12(obj_id self) throws InterruptedException
    {
        location imp12 = new location(4291.0f, 26.0f, -1335, "corellia", null);
        obj_id stormtrooper12 = create.object("stormtrooper", imp12);
        ai_lib.setDefaultCalmBehavior(stormtrooper12, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(stormtrooper12, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.stormtrooper12", stormtrooper12);
        setObjVar(stormtrooper12, "hideout", self);
        location elseWhere = new location(4105, 24, -1284, "corellia", null);
        ai_lib.aiPathTo(stormtrooper12, elseWhere);
        setHomeLocation(stormtrooper12, elseWhere);
        return;
    }
    public void spawnImperial_atst(obj_id self) throws InterruptedException
    {
        location imp13 = new location(4103.0f, 22.0f, -1450, "corellia", null);
        obj_id atst = create.object("at_st", imp13);
        ai_lib.setDefaultCalmBehavior(atst, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(atst, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.atst", atst);
        setObjVar(atst, "hideout", self);
        location elseWhere = new location(4093, 24, -1280, "corellia", null);
        ai_lib.aiPathTo(atst, elseWhere);
        setHomeLocation(atst, elseWhere);
        return;
    }
    public void spawnRebel_01(obj_id self) throws InterruptedException
    {
        location reb_01 = new location(4082.0f, 26.0f, -1253, "corellia", null);
        obj_id rebel_01 = create.object("rebel_corporal", reb_01);
        ai_lib.setDefaultCalmBehavior(rebel_01, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(rebel_01, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.rebel_01", rebel_01);
        setObjVar(rebel_01, "hideout", self);
        return;
    }
    public void spawnRebel_02(obj_id self) throws InterruptedException
    {
        location reb_02 = new location(4088.0f, 26.0f, -1253, "corellia", null);
        obj_id rebel_02 = create.object("rebel_corporal", reb_02);
        ai_lib.setDefaultCalmBehavior(rebel_02, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(rebel_02, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.rebel_02", rebel_02);
        setObjVar(rebel_02, "hideout", self);
        return;
    }
    public void spawnRebel_03(obj_id self) throws InterruptedException
    {
        location reb_03 = new location(4085.0f, 26.0f, -1250, "corellia", null);
        obj_id rebel_03 = create.object("rebel_trooper", reb_03);
        ai_lib.setDefaultCalmBehavior(rebel_03, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(rebel_03, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.rebel_03", rebel_03);
        setObjVar(rebel_03, "hideout", self);
        return;
    }
    public void spawnRebel_04(obj_id self) throws InterruptedException
    {
        location reb_04 = new location(4085.0f, 26.0f, -1256, "corellia", null);
        obj_id rebel_04 = create.object("rebel_first_lieutenant", reb_04);
        ai_lib.setDefaultCalmBehavior(rebel_04, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(rebel_04, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.rebel_04", rebel_04);
        setObjVar(rebel_04, "hideout", self);
        return;
    }
    public void spawnRebel_05(obj_id self) throws InterruptedException
    {
        location reb_05 = new location(4122.0f, 26.0f, -1254, "corellia", null);
        obj_id rebel_05 = create.object("rebel_corporal", reb_05);
        ai_lib.setDefaultCalmBehavior(rebel_05, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(rebel_05, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.rebel_05", rebel_05);
        setObjVar(rebel_05, "hideout", self);
        return;
    }
    public void spawnRebel_06(obj_id self) throws InterruptedException
    {
        location reb_06 = new location(4128.0f, 26.0f, -1254, "corellia", null);
        obj_id rebel_06 = create.object("rebel_corporal", reb_06);
        ai_lib.setDefaultCalmBehavior(rebel_06, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(rebel_06, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.rebel_06", rebel_06);
        setObjVar(rebel_06, "hideout", self);
        return;
    }
    public void spawnRebel_07(obj_id self) throws InterruptedException
    {
        location reb_07 = new location(4125.0f, 26.0f, -1251, "corellia", null);
        obj_id rebel_07 = create.object("rebel_trooper", reb_07);
        ai_lib.setDefaultCalmBehavior(rebel_07, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(rebel_07, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.rebel_07", rebel_07);
        setObjVar(rebel_07, "hideout", self);
        return;
    }
    public void spawnRebel_08(obj_id self) throws InterruptedException
    {
        location reb_08 = new location(4125.0f, 26.0f, -1257, "corellia", null);
        obj_id rebel_08 = create.object("rebel_first_lieutenant", reb_08);
        ai_lib.setDefaultCalmBehavior(rebel_08, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(rebel_08, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.rebel_08", rebel_08);
        setObjVar(rebel_08, "hideout", self);
        return;
    }
    public void spawnRebel_09(obj_id self) throws InterruptedException
    {
        location reb_09 = new location(4113.0f, 26.0f, -1282, "corellia", null);
        obj_id rebel_09 = create.object("rebel_corporal", reb_09);
        ai_lib.setDefaultCalmBehavior(rebel_09, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(rebel_09, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.rebel_09", rebel_09);
        setObjVar(rebel_09, "hideout", self);
        return;
    }
    public void spawnRebel_10(obj_id self) throws InterruptedException
    {
        location reb_10 = new location(4107.0f, 26.0f, -1282, "corellia", null);
        obj_id rebel_10 = create.object("rebel_corporal", reb_10);
        ai_lib.setDefaultCalmBehavior(rebel_10, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(rebel_10, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.rebel_10", rebel_10);
        setObjVar(rebel_10, "hideout", self);
        return;
    }
    public void spawnRebel_11(obj_id self) throws InterruptedException
    {
        location reb_11 = new location(4110.0f, 26.0f, -1279, "corellia", null);
        obj_id rebel_11 = create.object("rebel_trooper", reb_11);
        ai_lib.setDefaultCalmBehavior(rebel_11, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(rebel_11, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.rebel_11", rebel_11);
        setObjVar(rebel_11, "hideout", self);
        return;
    }
    public void spawnRebel_12(obj_id self) throws InterruptedException
    {
        location reb_12 = new location(4110.0f, 26.0f, -1285, "corellia", null);
        obj_id rebel_12 = create.object("rebel_first_lieutenant", reb_12);
        ai_lib.setDefaultCalmBehavior(rebel_12, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(rebel_12, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.rebel_12", rebel_12);
        setObjVar(rebel_12, "hideout", self);
        return;
    }
    public void spawnRebel_13(obj_id self) throws InterruptedException
    {
        location reb_13 = new location(4113.0f, 26.0f, -1264, "corellia", null);
        obj_id rebel_13 = create.object("rebel_commando", reb_13);
        ai_lib.setDefaultCalmBehavior(rebel_13, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(rebel_13, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.rebel_13", rebel_13);
        setObjVar(rebel_13, "hideout", self);
        return;
    }
    public void spawnRebel_14(obj_id self) throws InterruptedException
    {
        location reb_14 = new location(4117.0f, 26.0f, -1264, "corellia", null);
        obj_id rebel_14 = create.object("rebel_commando", reb_14);
        ai_lib.setDefaultCalmBehavior(rebel_14, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(rebel_14, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.rebel_14", rebel_14);
        setObjVar(rebel_14, "hideout", self);
        return;
    }
    public void spawnRebel_15(obj_id self) throws InterruptedException
    {
        location reb_15 = new location(4115.0f, 26.0f, -1262, "corellia", null);
        obj_id rebel_15 = create.object("rebel_commando", reb_15);
        ai_lib.setDefaultCalmBehavior(rebel_15, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(rebel_15, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.rebel_15", rebel_15);
        setObjVar(rebel_15, "hideout", self);
        return;
    }
    public void spawnRebel_16(obj_id self) throws InterruptedException
    {
        location reb_16 = new location(4115.0f, 26.0f, -1266, "corellia", null);
        obj_id rebel_16 = create.object("rebel_commando", reb_16);
        ai_lib.setDefaultCalmBehavior(rebel_16, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(rebel_16, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.rebel_16", rebel_16);
        setObjVar(rebel_16, "hideout", self);
        return;
    }
    public void spawnTurret01(obj_id self) throws InterruptedException
    {
        location loc = new location(4085.0f, 26.0f, -1253, "corellia", null);
        int T_yaw = -36;
        obj_id turret01 = advanced_turret.createTurret(loc, T_yaw, advanced_turret.TYPE_TOWER, advanced_turret.SIZE_SMALL, advanced_turret.DAMAGE_ELEMENTAL_HEAT, 1000, 3000, 10000, 30.0f, 2.0f, "Rebel");
        attachScript(turret01, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.turret01", turret01);
        setObjVar(turret01, "hideout", self);
        return;
    }
    public void spawnTurret2(obj_id self) throws InterruptedException
    {
        location loc = new location(4125.0f, 24.0f, -1254.0f, "corellia", null);
        int T_yaw = 56;
        obj_id turret2 = advanced_turret.createTurret(loc, T_yaw, advanced_turret.TYPE_TOWER, advanced_turret.SIZE_SMALL, advanced_turret.DAMAGE_ELEMENTAL_HEAT, 1000, 3000, 10000, 30.0f, 2.0f, "Rebel");
        attachScript(turret2, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.turret2", turret2);
        setObjVar(turret2, "hideout", self);
        return;
    }
    public void spawnTurret3(obj_id self) throws InterruptedException
    {
        location loc = new location(4110.0f, 24.0f, -1282.0f, "corellia", null);
        int T_yaw = 164;
        obj_id turret3 = advanced_turret.createTurret(loc, T_yaw, advanced_turret.TYPE_DISH, advanced_turret.SIZE_SMALL, advanced_turret.DAMAGE_ELEMENTAL_HEAT, 1000, 3000, 10000, 30.0f, 2.0f, "Rebel");
        attachScript(turret3, "theme_park.corellia.patrol.turret");
        setObjVar(self, "BattlePatron.turret3", turret3);
        setObjVar(turret3, "hideout", self);
        return;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!hasObjVar(speaker, "gm"))
        {
            return SCRIPT_CONTINUE;
        }
        if (text.equals("celebs"))
        {
            spawnCelebs(self);
        }
        if (text.equals("kill celebs"))
        {
            killCelebs(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int turretDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id existing = getObjIdObjVar(self, "BattlePatron.turret01");
        if (!existing.isLoaded())
        {
            int timeOut = rand(3600, 4800);
            spawnTurret01(self);
        }
        obj_id existing02 = getObjIdObjVar(self, "BattlePatron.turret2");
        if (!existing02.isLoaded())
        {
            int timeOut = rand(3600, 4800);
            spawnTurret2(self);
        }
        obj_id existing03 = getObjIdObjVar(self, "BattlePatron.turret3");
        if (!existing03.isLoaded())
        {
            int timeOut = rand(3600, 4800);
            spawnTurret3(self);
        }
        obj_id existing4 = getObjIdObjVar(self, "BattlePatron.stormtrooper01");
        if (!existing4.isLoaded())
        {
            int timeOut = rand(4800, 6000);
            spawnImperial01(self);
        }
        obj_id existing5 = getObjIdObjVar(self, "BattlePatron.stormtrooper02");
        if (!existing5.isLoaded())
        {
            int timeOut = rand(4800, 6000);
            spawnImperial02(self);
        }
        obj_id existing6 = getObjIdObjVar(self, "BattlePatron.stormtrooper03");
        if (!existing6.isLoaded())
        {
            int timeOut = rand(4800, 6000);
            spawnImperial03(self);
        }
        obj_id existing7 = getObjIdObjVar(self, "BattlePatron.stormtrooper04");
        if (!existing7.isLoaded())
        {
            int timeOut = rand(4800, 6000);
            spawnImperial04(self);
        }
        obj_id existing8 = getObjIdObjVar(self, "BattlePatron.stormtrooper05");
        if (!existing8.isLoaded())
        {
            int timeOut = rand(4800, 6000);
            spawnImperial05(self);
        }
        obj_id existing9 = getObjIdObjVar(self, "BattlePatron.stormtrooper06");
        if (!existing9.isLoaded())
        {
            int timeOut = rand(4800, 6000);
            spawnImperial06(self);
        }
        obj_id existing10 = getObjIdObjVar(self, "BattlePatron.stormtrooper07");
        if (!existing10.isLoaded())
        {
            int timeOut = rand(4800, 6000);
            spawnImperial07(self);
        }
        obj_id existing11 = getObjIdObjVar(self, "BattlePatron.stormtrooper08");
        if (!existing11.isLoaded())
        {
            int timeOut = rand(4800, 6000);
            spawnImperial08(self);
        }
        obj_id existing12 = getObjIdObjVar(self, "BattlePatron.stormtrooper09");
        if (!existing12.isLoaded())
        {
            int timeOut = rand(4800, 6000);
            spawnImperial09(self);
        }
        obj_id existing13 = getObjIdObjVar(self, "BattlePatron.stormtrooper10");
        if (!existing13.isLoaded())
        {
            int timeOut = rand(4800, 6000);
            spawnImperial10(self);
        }
        obj_id existing14 = getObjIdObjVar(self, "BattlePatron.stormtrooper11");
        if (!existing14.isLoaded())
        {
            int timeOut = rand(4800, 6000);
            spawnImperial11(self);
        }
        obj_id existing15 = getObjIdObjVar(self, "BattlePatron.stormtrooper12");
        if (!existing15.isLoaded())
        {
            int timeOut = rand(4800, 6000);
            spawnImperial12(self);
        }
        obj_id existing16 = getObjIdObjVar(self, "BattlePatron.atst");
        if (!existing16.isLoaded())
        {
            int timeOut = rand(7200, 10800);
            spawnImperial_atst(self);
        }
        obj_id existing17 = getObjIdObjVar(self, "BattlePatron.rebel_01");
        if (!existing17.isLoaded())
        {
            int timeOut = rand(3600, 4800);
            spawnRebel_01(self);
        }
        obj_id existing18 = getObjIdObjVar(self, "BattlePatron.rebel_02");
        if (!existing18.isLoaded())
        {
            int timeOut = rand(3600, 4800);
            spawnRebel_02(self);
        }
        obj_id existing19 = getObjIdObjVar(self, "BattlePatron.rebel_03");
        if (!existing19.isLoaded())
        {
            int timeOut = rand(3600, 4800);
            spawnRebel_03(self);
        }
        obj_id existing20 = getObjIdObjVar(self, "BattlePatron.rebel_04");
        if (!existing20.isLoaded())
        {
            int timeOut = rand(3600, 4800);
            spawnRebel_04(self);
        }
        obj_id existing21 = getObjIdObjVar(self, "BattlePatron.rebel_05");
        if (!existing21.isLoaded())
        {
            int timeOut = rand(3600, 4800);
            spawnRebel_05(self);
        }
        obj_id existing22 = getObjIdObjVar(self, "BattlePatron.rebel_06");
        if (!existing22.isLoaded())
        {
            int timeOut = rand(3600, 4800);
            spawnRebel_06(self);
        }
        obj_id existing23 = getObjIdObjVar(self, "BattlePatron.rebel_07");
        if (!existing23.isLoaded())
        {
            int timeOut = rand(3600, 4800);
            spawnRebel_07(self);
        }
        obj_id existing24 = getObjIdObjVar(self, "BattlePatron.rebel_08");
        if (!existing24.isLoaded())
        {
            int timeOut = rand(3600, 4800);
            spawnRebel_08(self);
        }
        obj_id existing25 = getObjIdObjVar(self, "BattlePatron.rebel_09");
        if (!existing25.isLoaded())
        {
            int timeOut = rand(3600, 4800);
            spawnRebel_09(self);
        }
        obj_id existing26 = getObjIdObjVar(self, "BattlePatron.rebel_10");
        if (!existing26.isLoaded())
        {
            int timeOut = rand(3600, 4800);
            spawnRebel_10(self);
        }
        obj_id existing27 = getObjIdObjVar(self, "BattlePatron.rebel_11");
        if (!existing27.isLoaded())
        {
            int timeOut = rand(3600, 4800);
            spawnRebel_11(self);
        }
        obj_id existing28 = getObjIdObjVar(self, "BattlePatron.rebel_12");
        if (!existing28.isLoaded())
        {
            int timeOut = rand(3600, 4800);
            spawnRebel_12(self);
        }
        obj_id existing29 = getObjIdObjVar(self, "BattlePatron.rebel_13");
        if (!existing29.isLoaded())
        {
            int timeOut = rand(3600, 4800);
            spawnRebel_13(self);
        }
        obj_id existing30 = getObjIdObjVar(self, "BattlePatron.rebel_14");
        if (!existing30.isLoaded())
        {
            int timeOut = rand(3600, 4800);
            spawnRebel_14(self);
        }
        obj_id existing31 = getObjIdObjVar(self, "BattlePatron.rebel_15");
        if (!existing31.isLoaded())
        {
            int timeOut = rand(3600, 4800);
            spawnRebel_15(self);
        }
        obj_id existing32 = getObjIdObjVar(self, "BattlePatron.rebel_16");
        if (!existing32.isLoaded())
        {
            int timeOut = rand(3600, 4800);
            spawnRebel_16(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void killCelebs(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "BattlePatron.turret01"));
        removeObjVar(self, "BattlePatron.turret01");
        destroyObject(getObjIdObjVar(self, "BattlePatron.turret2"));
        removeObjVar(self, "BattlePatron.turret2");
        destroyObject(getObjIdObjVar(self, "BattlePatron.turret3"));
        removeObjVar(self, "BattlePatron.turret3");
        destroyObject(getObjIdObjVar(self, "BattlePatron.rebel_01"));
        removeObjVar(self, "BattlePatron.rebel_01");
        destroyObject(getObjIdObjVar(self, "BattlePatron.rebel_02"));
        removeObjVar(self, "BattlePatron.rebel_02");
        destroyObject(getObjIdObjVar(self, "BattlePatron.rebel_03"));
        removeObjVar(self, "BattlePatron.rebel_03");
        destroyObject(getObjIdObjVar(self, "BattlePatron.rebel_04"));
        removeObjVar(self, "BattlePatron.rebel_04");
        destroyObject(getObjIdObjVar(self, "BattlePatron.rebel_05"));
        removeObjVar(self, "BattlePatron.rebel_05");
        destroyObject(getObjIdObjVar(self, "BattlePatron.rebel_06"));
        removeObjVar(self, "BattlePatron.rebel_06");
        destroyObject(getObjIdObjVar(self, "BattlePatron.rebel_07"));
        removeObjVar(self, "BattlePatron.rebel_07");
        destroyObject(getObjIdObjVar(self, "BattlePatron.rebel_08"));
        removeObjVar(self, "BattlePatron.rebel_08");
        destroyObject(getObjIdObjVar(self, "BattlePatron.rebel_09"));
        removeObjVar(self, "BattlePatron.rebel_09");
        destroyObject(getObjIdObjVar(self, "BattlePatron.rebel_10"));
        removeObjVar(self, "BattlePatron.rebel_10");
        destroyObject(getObjIdObjVar(self, "BattlePatron.rebel_11"));
        removeObjVar(self, "BattlePatron.rebel_11");
        destroyObject(getObjIdObjVar(self, "BattlePatron.rebel_12"));
        removeObjVar(self, "BattlePatron.rebel_12");
        destroyObject(getObjIdObjVar(self, "BattlePatron.rebel_13"));
        removeObjVar(self, "BattlePatron.rebel_13");
        destroyObject(getObjIdObjVar(self, "BattlePatron.rebel_14"));
        removeObjVar(self, "BattlePatron.rebel_14");
        destroyObject(getObjIdObjVar(self, "BattlePatron.rebel_15"));
        removeObjVar(self, "BattlePatron.rebel_15");
        destroyObject(getObjIdObjVar(self, "BattlePatron.rebel_16"));
        removeObjVar(self, "BattlePatron.rebel_16");
        destroyObject(getObjIdObjVar(self, "BattlePatron.atst"));
        removeObjVar(self, "BattlePatron.atst");
        destroyObject(getObjIdObjVar(self, "BattlePatron.stormtrooper01"));
        removeObjVar(self, "BattlePatron.stormtrooper01");
        destroyObject(getObjIdObjVar(self, "BattlePatron.stormtrooper02"));
        removeObjVar(self, "BattlePatron.stormtrooper02");
        destroyObject(getObjIdObjVar(self, "BattlePatron.stormtrooper03"));
        removeObjVar(self, "BattlePatron.stormtrooper03");
        destroyObject(getObjIdObjVar(self, "BattlePatron.stormtrooper04"));
        removeObjVar(self, "BattlePatron.stormtrooper04");
        destroyObject(getObjIdObjVar(self, "BattlePatron.stormtrooper05"));
        removeObjVar(self, "BattlePatron.stormtrooper05");
        destroyObject(getObjIdObjVar(self, "BattlePatron.stormtrooper06"));
        removeObjVar(self, "BattlePatron.stormtrooper06");
        destroyObject(getObjIdObjVar(self, "BattlePatron.stormtrooper07"));
        removeObjVar(self, "BattlePatron.stormtrooper07");
        destroyObject(getObjIdObjVar(self, "BattlePatron.stormtrooper08"));
        removeObjVar(self, "BattlePatron.stormtrooper08");
        destroyObject(getObjIdObjVar(self, "BattlePatron.stormtrooper09"));
        removeObjVar(self, "BattlePatron.stormtrooper09");
        destroyObject(getObjIdObjVar(self, "BattlePatron.stormtrooper10"));
        removeObjVar(self, "BattlePatron.stormtrooper10");
        destroyObject(getObjIdObjVar(self, "BattlePatron.stormtrooper11"));
        removeObjVar(self, "BattlePatron.stormtrooper11");
        destroyObject(getObjIdObjVar(self, "BattlePatron.stormtrooper12"));
        removeObjVar(self, "BattlePatron.stormtrooper12");
        return;
    }
}
