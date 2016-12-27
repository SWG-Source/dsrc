package script.theme_park.tatooine.mos_eisley;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.ai_lib;
import script.library.performance;

public class masterspawner extends script.base_script
{
    public masterspawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "Initialized Mos Eisley Cantina spawner script");
        messageTo(self, "spawnThings", null, 2, true);
        return SCRIPT_CONTINUE;
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        spawnPadawanTrialsNpc(self);
        spawnBarPatronsRight(self);
        spawnWuher(self);
        spawnSittingPatrons(self);
        spawnBithBand(self);
        spawnAudience(self);
        spawnStandingConvoGroup(self);
        spawnClosePatrons(self);
        spawnFoyer(self);
        spawnChadraFans(self);
        spawnCheatedGambler(self);
        spawnJunkDealer(self);
    }
    public void spawnHanandChewie(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "cantina");
        obj_id han = create.object("han_solo", new location(10.50f, 1.0f, 11.98f, "tatooine", room));
        obj_id chewie = create.object("chewbacca", new location(10.22f, 1.0f, 9.78f, "tatooine", room));
        setYaw(han, -177);
        setYaw(chewie, 0);
        setInvulnerable(han, true);
        setInvulnerable(chewie, true);
        setCreatureStatic(han, true);
        setCreatureStatic(chewie, true);
    }
    public void spawnPadawanTrialsNpc(obj_id self) throws InterruptedException
    {
        obj_id npc = create.object("patron_human_male_01", new location(-9.34f, 1.0f, 5.66f, "tatooine", getCellId(self, "cantina")));
        setYaw(npc, 105);
        setInvulnerable(npc, true);
        setCreatureStatic(npc, true);
        ai_lib.setDefaultCalmMood(npc, "npc_standing_drinking");
        equip(createObject("object/tangible/item/con_drinking_glass_01.iff", npc, ""), npc);
        setName(npc, "Marco Vahn (a booking agent)");
        attachScript(npc, "conversation.padawan_old_musician_02");
        setObjVar(self, "cantinaInhabitants.padawanTrialsNpc", npc);
    }
    public void spawnChadraFans(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "cantina");
        obj_id c1 = create.object("chadra_fan_male", new location(10.70f, 1.0f, -0.23f, "tatooine", room));
        obj_id c2 = create.object("chadra_fan_female", new location(10.43f, 1.0f, -1.47f, "tatooine", room));
        setYaw(c1, -158);
        setYaw(c2, 25);
        setCreatureStatic(c1, true);
        setCreatureStatic(c2, true);
        setInvulnerable(c1, true);
        setInvulnerable(c2, true);
        ai_lib.setDefaultCalmMood(c1, "conversation");
        ai_lib.setDefaultCalmMood(c2, "conversation");
    }
    public void spawnFoyer(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "foyer2");
        obj_id f1 = create.object("commoner", new location(29.51f, 1.0f, -7.38f, "tatooine", room));
        obj_id f2 = create.object("commoner", new location(31.01f, 1.0f, -8.83f, "tatooine", room));
        obj_id f3 = create.object("commoner", new location(29.54f, 1.0f, -6.18f, "tatooine", room));
        obj_id f4 = create.object("commoner", new location(35.96f, 1.0f, 0.74f, "tatooine", room));
        obj_id f5 = create.object("commoner", new location(35.72f, 1.0f, 3.19f, "tatooine", room));
        setYaw(f1, 47);
        setYaw(f2, -12);
        setYaw(f3, 171);
        setYaw(f4, 1);
        setYaw(f5, 158);
        setCreatureStatic(f1, true);
        setInvulnerable(f1, true);
        setCreatureStatic(f2, true);
        setInvulnerable(f2, true);
        setCreatureStatic(f3, true);
        setInvulnerable(f3, true);
        setCreatureStatic(f4, true);
        setInvulnerable(f4, true);
        setCreatureStatic(f5, true);
        setInvulnerable(f5, true);
        ai_lib.setDefaultCalmMood(f1, "npc_sitting_chair");
        ai_lib.setDefaultCalmMood(f2, "npc_sitting_chair");
        ai_lib.setDefaultCalmMood(f3, "npc_sitting_chair");
        ai_lib.setDefaultCalmMood(f4, "npc_sitting_chair");
        ai_lib.setDefaultCalmMood(f5, "npc_sitting_chair");
    }
    public void spawnClosePatrons(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "cantina");
        obj_id s1 = create.object("patron_ishitib_male", new location(22.31f, 1.0f, 3.00f, "tatooine", room));
        obj_id s2 = create.object("muftak", new location(20.52f, 1.0f, 4.10f, "tatooine", room));
        obj_id s3 = create.object("patron_devaronian_male", new location(21.19f, 1.0f, 5.65f, "tatooine", room));
        obj_id s4 = create.object("patron_nikto", new location(23.37f, 1.0f, 4.88f, "tatooine", room));
        obj_id s5 = create.object("patron_quarren", new location(16.96f, 1.0f, 6.62f, "tatooine", room));
        obj_id s6 = create.object("patron_klaatu", new location(14.97f, 1.0f, 6.77f, "tatooine", room));
        setYaw(s1, -19);
        setYaw(s2, 73);
        setYaw(s3, 157);
        setYaw(s4, -103);
        setYaw(s5, -114);
        setYaw(s6, 88);
        setCreatureStatic(s1, true);
        setCreatureStatic(s2, true);
        setCreatureStatic(s3, true);
        setCreatureStatic(s4, true);
        setCreatureStatic(s5, true);
        setCreatureStatic(s6, true);
        setInvulnerable(s1, true);
        setInvulnerable(s2, true);
        setInvulnerable(s3, true);
        setInvulnerable(s4, true);
        setInvulnerable(s5, true);
        setInvulnerable(s6, true);
        ai_lib.setDefaultCalmMood(s1, "npc_sitting_chair");
        ai_lib.setDefaultCalmMood(s2, "npc_sitting_chair");
        ai_lib.setDefaultCalmMood(s3, "npc_sitting_chair");
        ai_lib.setDefaultCalmMood(s4, "npc_sitting_chair");
        ai_lib.setDefaultCalmMood(s5, "npc_sitting_chair");
        ai_lib.setDefaultCalmMood(s6, "npc_sitting_chair");
    }
    public void spawnStandingConvoGroup(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "cantina");
        obj_id itho = create.object("patron_ithorian_male", new location(15.89f, 1.0f, 2.48f, "tatooine", room));
        obj_id greeata = create.object("commoner", new location(16.46f, 1.0f, 2.98f, "tatooine", room));
        setYaw(itho, 36);
        setYaw(greeata, -129);
        setCreatureStatic(itho, true);
        setCreatureStatic(greeata, true);
        setInvulnerable(itho, true);
        setInvulnerable(greeata, true);
        ai_lib.setDefaultCalmMood(itho, "nervous");
        ai_lib.setDefaultCalmMood(greeata, "conversation");
    }
    public void spawnAudience(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "cantina");
        obj_id a1 = create.object("patron_human_female_01", new location(8.68f, 1.0f, -6.08f, "tatooine", room));
        obj_id a2 = create.object("patron_human_male_01", new location(6.76f, 1.0f, -6.48f, "tatooine", room));
        obj_id a3 = create.object("commoner", new location(1.99f, 1.0f, -8.44f, "tatooine", room));
        obj_id a4 = create.object("commoner", new location(1.19f, 1.0f, -7.63f, "tatooine", room));
        obj_id a5 = create.object("stormtrooper_squad_leader", new location(3.62f, 1.0f, -6.78f, "tatooine", room));
        obj_id a6 = create.object("stormtrooper", new location(2.84f, 1.0f, -6.30f, "tatooine", room));
        obj_id a7 = create.object("patron_chiss_female", new location(3.62f, 1.0f, -4.77f, "tatooine", room));
        obj_id a8 = create.object("patron_chiss_male", new location(1.74f, 1.0f, -4.91f, "tatooine", room));
        setYaw(a1, -148);
        setYaw(a2, 177);
        setYaw(a3, -35);
        setYaw(a4, 152);
        setYaw(a5, 0);
        setYaw(a6, 16);
        setYaw(a7, -176);
        setYaw(a8, 95);
        setCreatureStatic(a1, true);
        setCreatureStatic(a2, true);
        setCreatureStatic(a3, true);
        setCreatureStatic(a4, true);
        setCreatureStatic(a5, true);
        setCreatureStatic(a6, true);
        setCreatureStatic(a7, true);
        setCreatureStatic(a8, true);
        setInvulnerable(a1, true);
        setInvulnerable(a2, true);
        setInvulnerable(a3, true);
        setInvulnerable(a4, true);
        setInvulnerable(a5, true);
        setInvulnerable(a6, true);
        setInvulnerable(a7, true);
        setInvulnerable(a8, true);
        ai_lib.setDefaultCalmMood(a1, "entertained");
        ai_lib.setDefaultCalmMood(a2, "entertained");
        ai_lib.setDefaultCalmMood(a3, "conversation");
        ai_lib.setDefaultCalmMood(a4, "conversation");
        ai_lib.setDefaultCalmMood(a5, "npc_accusing");
        ai_lib.setDefaultCalmMood(a6, "npc_imperial");
        ai_lib.setDefaultCalmMood(a7, "npc_sad");
        ai_lib.setDefaultCalmMood(a8, "npc_consoling");
    }
    public void spawnBarPatronsRight(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "cantina");
        obj_id p1 = create.object("businessman", new location(10.65f, 1.0f, 1.91f, "tatooine", room));
        obj_id p2 = create.object("commoner", new location(10.17f, 1.0f, 2.74f, "tatooine", room));
        obj_id p3 = create.object("entertainer", new location(9.08f, 1.0f, 4.15f, "tatooine", room));
        obj_id p4 = create.object("noble", new location(8.49f, 1.0f, 4.64f, "tatooine", room));
        obj_id p7 = create.object("commoner", new location(4.11f, 1.0f, 5.40f, "tatooine", room));
        obj_id p8 = create.object("commoner", new location(3.11f, 1.0f, 5.40f, "tatooine", room));
        obj_id p9 = create.object("commoner", new location(2.11f, 1.0f, 5.40f, "tatooine", room));
        obj_id p10 = create.object("commoner", new location(1.11f, 1.0f, 5.40f, "tatooine", room));
        obj_id p13 = create.object("commoner", new location(-3.11f, 1.0f, 5.40f, "tatooine", room));
        obj_id p14 = create.object("businessman", new location(-4.11f, 1.0f, 5.40f, "tatooine", room));
        setCreatureStatic(p1, true);
        setCreatureStatic(p2, true);
        setCreatureStatic(p3, true);
        setCreatureStatic(p4, true);
        setCreatureStatic(p7, true);
        setCreatureStatic(p8, true);
        setCreatureStatic(p9, true);
        setCreatureStatic(p10, true);
        setCreatureStatic(p13, true);
        setCreatureStatic(p14, true);
        setInvulnerable(p1, true);
        setInvulnerable(p2, true);
        setInvulnerable(p3, true);
        setInvulnerable(p4, true);
        setInvulnerable(p7, true);
        setInvulnerable(p8, true);
        setInvulnerable(p9, true);
        setInvulnerable(p10, true);
        setInvulnerable(p13, true);
        setInvulnerable(p14, true);
        setYaw(p1, -82);
        setYaw(p2, -133);
        setYaw(p3, -92);
        setYaw(p4, 159);
        setYaw(p8, 161);
        setYaw(p10, 155);
        setYaw(p13, 105);
        setYaw(p14, -106);
        ai_lib.setDefaultCalmMood(p1, "npc_standing_drinking");
        ai_lib.setDefaultCalmMood(p2, "conversation");
        ai_lib.setDefaultCalmMood(p3, "npc_standing_drinking");
        ai_lib.setDefaultCalmMood(p4, "conversation");
        ai_lib.setDefaultCalmMood(p7, "npc_standing_drinking");
        ai_lib.setDefaultCalmMood(p8, "npc_standing_drinking");
        ai_lib.setDefaultCalmMood(p9, "conversation");
        ai_lib.setDefaultCalmMood(p10, "npc_standing_drinking");
        ai_lib.setDefaultCalmMood(p13, "npc_standing_drinking");
        ai_lib.setDefaultCalmMood(p14, "npc_standing_drinking");
        obj_id cup1 = createObject("object/tangible/item/con_drinking_glass_01.iff", p1, "");
        equip(cup1, p1);
        obj_id cup3 = createObject("object/tangible/item/con_drinking_glass_01.iff", p3, "");
        equip(cup3, p3);
        obj_id cup7 = createObject("object/tangible/item/con_drinking_glass_01.iff", p7, "");
        equip(cup7, p7);
        obj_id cup8 = createObject("object/tangible/item/con_drinking_glass_01.iff", p8, "");
        equip(cup8, p8);
        obj_id cup10 = createObject("object/tangible/item/con_drinking_glass_01.iff", p10, "");
        equip(cup10, p10);
        obj_id cup13 = createObject("object/tangible/item/con_drinking_glass_01.iff", p13, "");
        equip(cup13, p13);
        obj_id cup14 = createObject("object/tangible/item/con_drinking_glass_01.iff", p14, "");
        equip(cup14, p14);
    }
    public void spawnSittingPatrons(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "cantina");
        obj_id p2 = create.object("patron_human_female_02", new location(14.57f, 1.0f, -2.85f, "tatooine", room));
        obj_id p3 = create.object("patron_human_male_03", new location(14.21f, 1.0f, -4.89f, "tatooine", room));
        obj_id p4 = create.object("patron_human_female_03", new location(16.49f, 1.0f, -4.86f, "tatooine", room));
        setCreatureStatic(p2, true);
        setCreatureStatic(p3, true);
        setCreatureStatic(p4, true);
        setInvulnerable(p2, true);
        setInvulnerable(p3, true);
        setInvulnerable(p4, true);
        setYaw(p2, 127);
        setYaw(p3, 46);
        setYaw(p4, -48);
        ai_lib.setDefaultCalmMood(p2, "npc_sitting_table");
        ai_lib.setDefaultCalmMood(p3, "npc_sitting_table_eating");
        ai_lib.setDefaultCalmMood(p4, "npc_sitting_chair");
        obj_id p7 = create.object("patron_human_female_04", new location(24.34f, 1.0f, -8.23f, "tatooine", room));
        obj_id p8 = create.object("patron_human_male_04", new location(26.20f, 1.0f, -8.33f, "tatooine", room));
        setCreatureStatic(p7, true);
        setCreatureStatic(p8, true);
        setInvulnerable(p7, true);
        setInvulnerable(p8, true);
        setYaw(p7, 49);
        setYaw(p8, -43);
        ai_lib.setDefaultCalmMood(p7, "npc_sitting_table_eating");
        ai_lib.setDefaultCalmMood(p8, "npc_sitting_chair");
    }
    public void spawnWuher(obj_id self) throws InterruptedException
    {
        obj_id wuher = create.object("wuher", new location(8.46f, 1.0f, 0.35f, "tatooine", getCellId(self, "cantina")));
        create.addDestroyMessage(wuher, "wuherDied", 10f, self);
        // all bartenders should move around and do their job... nobody gets away with just standing around.
        // (removing setCreatureStatic to allow Wuher to move around.)
        // setCreatureStatic(wuher, true);
        setInvulnerable(wuher, true);
        setYaw(wuher, 47);
        ai_lib.setDefaultCalmMood(wuher, "npc_imperial");
        attachScript(wuher, "npc.bartender.base");
        attachScript(wuher, "npc.bartender.listen");
    }
    public void spawnBithBand(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "alcove3");
        obj_id figrin = create.object("figrin_dan", new location(3.69f, 1.0f, -14.40f, "tatooine", room));
        obj_id box = create.object("bith_musician", new location(4.11f, 1.0f, -17.07f, "tatooine", room));
        obj_id horn = create.object("bith_musician", new location(1.29f, 1.0f, -15.18f, "tatooine", room));
        obj_id fizz = create.object("bith_musician", new location(2.32f, 1.0f, -16.47f, "tatooine", room));
        obj_id bandfill = create.object("bith_musician", new location(0.54f, 1.0f, -17.13f, "tatooine", room));
        setObjVar(figrin, performance.NPC_ENTERTAINMENT_NO_ENTERTAIN, 1);
        setObjVar(box, performance.NPC_ENTERTAINMENT_NO_ENTERTAIN, 1);
        setObjVar(horn, performance.NPC_ENTERTAINMENT_NO_ENTERTAIN, 1);
        setObjVar(fizz, performance.NPC_ENTERTAINMENT_NO_ENTERTAIN, 1);
        setObjVar(bandfill, performance.NPC_ENTERTAINMENT_NO_ENTERTAIN, 1);
        ai_lib.setDefaultCalmMood(figrin, "themepark_music_3");
        ai_lib.setDefaultCalmMood(box, "themepark_music_2");
        ai_lib.setDefaultCalmMood(horn, "themepark_music_3");
        ai_lib.setDefaultCalmMood(fizz, "themepark_music_3");
        ai_lib.setDefaultCalmMood(bandfill, "themepark_music_3");
        setYaw(figrin, 50);
        setYaw(box, 45);
        setYaw(horn, 70);
        setYaw(fizz, 44);
        setYaw(bandfill, 38);
        setCreatureStatic(figrin, true);
        setInvulnerable(figrin, true);
        setCreatureStatic(box, true);
        setInvulnerable(box, true);
        setCreatureStatic(horn, true);
        setInvulnerable(horn, true);
        setCreatureStatic(fizz, true);
        setInvulnerable(fizz, true);
        setCreatureStatic(bandfill, true);
        setInvulnerable(bandfill, true);
        setName(figrin, "Figrin D'an");
        setName(box, "Tech Mo'r");
        setName(horn, "Tedn Dahai");
        setName(fizz, "Doikk Na'ts");
        setName(bandfill, "Nalan Cheel");
        equip(createObject("object/tangible/instrument/kloo_horn.iff", figrin, ""), figrin);
        equip(createObject("object/tangible/instrument/kloo_horn.iff", horn, ""), horn);
        equip(createObject("object/tangible/instrument/fizz.iff", fizz, ""), fizz);
        equip(createObject("object/tangible/instrument/bandfill.iff", bandfill, ""), bandfill);
    }
    public int spawnThings(obj_id self, dictionary params) throws InterruptedException
    {
        spawnEveryone(self);
        return SCRIPT_CONTINUE;
    }
    public int peopleDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnEveryone(self);
        return SCRIPT_CONTINUE;
    }
    public int wuherDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnWuher(self);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!hasObjVar(speaker, "gm_testing"))
        {
            return SCRIPT_CONTINUE;
        }
        if (text.equals("padawan_trials"))
        {
            spawnPadawanTrialsNpc(self);
        }
        if (text.equals("kill_padawan"))
        {
            obj_id npc = getObjIdObjVar(self, "cantinaInhabitants.padawanTrialsNpc");
            if (isIdValid(npc))
            {
                destroyObject(npc);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void spawnCheatedGambler(obj_id self) throws InterruptedException
    {
        obj_id gambler = create.object("ep3_cheated_gambler", new location(-6.5f, -.9f, -20.7f, "tatooine", getCellId(self, "stage")));
        setYaw(gambler, 60);
        ai_lib.setDefaultCalmMood(gambler, "npc_sad");
    }
    public void spawnJunkDealer(obj_id self) throws InterruptedException
    {
        obj_id junkDealer = create.object("junk_dealer", new location(-30.9152f, -0.52f, 6.8631f, "tatooine", getCellId(self, "private_room2")));
        setYaw(junkDealer, 25);
        setInvulnerable(junkDealer, true);
        ai_lib.setDefaultCalmMood(junkDealer, "npc_imperial");
    }
}
