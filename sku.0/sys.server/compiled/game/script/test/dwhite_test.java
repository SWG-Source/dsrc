package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.badge;
import script.library.resource;
import script.library.create;
import script.library.bio_engineer;
import script.library.features;
import script.ai.ai_combat;
import script.library.jedi;
import script.library.skill_template;
import script.library.pclib;
import script.library.sui;
import script.library.cloninglib;

public class dwhite_test extends script.base_script
{
    public dwhite_test()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 50 || !isPlayer(self)) {
            detachScript(self, "test.dwhite_test");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        location loc = getLocation(self);
        if (text.startsWith("getWorkingSkill"))
        {
            sendSystemMessageTestingOnly(self, "Working Skill = " + getWorkingSkill(self));
        }
        if (text.startsWith("getSkillTemplate"))
        {
            sendSystemMessageTestingOnly(self, "Skill Template = " + getSkillTemplate(self));
        }
        if (text.startsWith("setWorkingSkill"))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            String cmd = st.nextToken();
            String arg = "";
            if (st.hasMoreTokens())
            {
                arg = st.nextToken();
            }
            setWorkingSkill(self, arg);
            sendSystemMessageTestingOnly(self, "Working Skill set to: " + arg);
        }
        if (text.startsWith("setSkillTemplate"))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            String cmd = st.nextToken();
            String arg = "";
            if (st.hasMoreTokens())
            {
                arg = st.nextToken();
            }
            String templateSkills = dataTableGetString(skill_template.TEMPLATE_TABLE, arg, "template");
            if (!arg.equals("") && (templateSkills == null || templateSkills.equals("")))
            {
                sendSystemMessageTestingOnly(self, "Not a Valid Skill Template");
            }
            else 
            {
                setSkillTemplate(self, arg);
                sendSystemMessageTestingOnly(self, "Skill Template set to: " + arg);
            }
        }
        if (text.startsWith("killme"))
        {
            obj_id target = self;
            int dam = -(getAttrib(target, HEALTH) + 50);
            addAttribModifier(target, HEALTH, dam, 0f, 0f, MOD_POOL);
            if (getPosture(target) != POSTURE_DEAD)
            {
                setPosture(target, POSTURE_DEAD);
            }
            if (!hasObjVar(target, pclib.VAR_BEEN_COUPDEGRACED))
            {
                setObjVar(target, pclib.VAR_DEATHBLOW_KILLER, target);
                setObjVar(target, pclib.VAR_DEATHBLOW_STAMP, getGameTime());
            }
            pclib.playerDeath(target, target, false);
        }
        if (text.equals("clonewarp"))
        {
            createCloneWarpSui(self);
        }
        if (text.startsWith("createResource"))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            String arg = st.nextToken();
            if (st.countTokens() != 2)
            {
                sendSystemMessageTestingOnly(self, "[debugger] SYNTAX: createResource <resource> <amt>");
                return SCRIPT_CONTINUE;
            }
            String type = st.nextToken();
            String sAmt = st.nextToken();
            int amt = utils.stringToInt(sAmt);
            if (amt < 1)
            {
                sendSystemMessageTestingOnly(self, "[debugger] SYNTAX: createResource <resource> <amt>");
                return SCRIPT_CONTINUE;
            }
            obj_id pInv = utils.getInventoryContainer(self);
            obj_id[] crates = resource.createRandom(type, amt, loc, pInv, self, 1);
        }
        if (text.startsWith("createDNA"))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            String arg = st.nextToken();
            if (st.countTokens() != 1)
            {
                sendSystemMessageTestingOnly(self, "[debugger] SYNTAX: createDNA <creature>");
                return SCRIPT_CONTINUE;
            }
            String creature = st.nextToken();
            bio_engineer.quickHarvest(self, creature);
        }
        if (text.equals("attack"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null && target != self)
            {
                startCombat(target, self);
            }
        }
        if (text.equals("frenzy"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null && target != self)
            {
                addToMentalStateToward(target, self, FEAR, 45.0f);
            }
        }
        if (text.startsWith("checkBadge"))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            String arg = st.nextToken();
            String badgeName = st.nextToken();
            obj_id target = getLookAtTarget(self);
            sendSystemMessageTestingOnly(self, "[Badge] Has Badge " + badgeName + " = " + badge.hasBadge(target, badgeName));
        }
        if (text.equals("enableJedi"))
        {
            addJediSlot(self);
        }
        if (text.equals("runTest"))
        {
            sendSystemMessageTestingOnly(self, "[test] Analyzing creatures level/stats...");
            checkCreatures();
        }
        if (text.equals("resetPower"))
        {
            jedi.recalculateForcePower(self);
        }
        if (text.startsWith("setScale"))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            String arg = st.nextToken();
            float scale = utils.stringToFloat(st.nextToken());
            obj_id target = getLookAtTarget(self);
            float baseScale = 1.0f;
            if (utils.hasScriptVar(target, "ai.baseScale"))
            {
                baseScale = utils.getFloatScriptVar(target, "ai.baseScale");
            }
            float finalScale = baseScale * scale;
            setScale(target, finalScale);
            sendSystemMessageTestingOnly(self, "[test] Creature scale set to " + scale);
        }
        if (text.startsWith("getScriptVars"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            deltadictionary dctScriptVars = target.getScriptVars();
            sendSystemMessageTestingOnly(self, "Scriptvars are " + dctScriptVars.toString());
        }
        if (text.startsWith("getPlanetScriptVars"))
        {
            String planetName = getCurrentSceneName();
            obj_id planet = getPlanetByName(planetName);
            deltadictionary dctScriptVars = planet.getScriptVars();
            sendSystemMessageTestingOnly(self, "Scriptvars are " + dctScriptVars.toString());
        }
        if (text.startsWith("getResourceId"))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            String arg = st.nextToken();
            String resource = st.nextToken();
            obj_id resId = getResourceTypeByName(resource);
            sendSystemMessageTestingOnly(self, "[test] " + resource + " = " + resId);
        }
        if (text.startsWith("testGoggles"))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            String arg = st.nextToken();
            String oidString = st.nextToken();
            Long temp = new Long(Long.parseLong(oidString));
            obj_id goggle = obj_id.getObjId(temp.longValue());
            boolean validGoggles = true;
            obj_id owner = getOwner(goggle);
            LOG("seGoggles", "Owner = " + owner);
            if (!isIdValid(owner))
            {
                validGoggles = false;
            }
            else 
            {
                obj_id inv = utils.getInventoryContainer(owner);
                obj_id bank = utils.getPlayerBank(owner);
                obj_id eyes = getObjectInSlot(owner, "eyes");
                LOG("seGoggles", "Inv = " + inv);
                LOG("seGoggles", "Bank = " + bank);
                LOG("seGoggles", "Eyes = " + eyes);
                if (!isIdValid(inv) || !isIdValid(bank))
                {
                    validGoggles = false;
                }
                if (!features.isCollectorEdition(owner))
                {
                    validGoggles = false;
                }
                else 
                {
                    obj_id container = getContainedBy(goggle);
                    LOG("seGoggles", "Container = " + container);
                    if (container == owner)
                    {
                        if (goggle != eyes)
                        {
                            validGoggles = false;
                        }
                    }
                    else if (!(container == inv || container == bank))
                    {
                        validGoggles = false;
                    }
                }
            }
            if (!validGoggles)
            {
                if (isIdValid(owner))
                {
                    sendSystemMessage(owner, new string_id("error_message", "destroy_goggle"));
                }
                destroyObject(goggle);
                return SCRIPT_CONTINUE;
            }
        }
        if (text.startsWith("saberStats"))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            String arg = st.nextToken();
            String oidString = st.nextToken();
            Long temp = new Long(Long.parseLong(oidString));
            obj_id saber = obj_id.getObjId(temp.longValue());
            int minDmg = getWeaponMinDamage(saber);
            int maxDmg = getWeaponMaxDamage(saber);
            float speed = getWeaponAttackSpeed(saber);
            range_info ri = getWeaponRangeInfo(saber);
            float wound = getWeaponWoundChance(saber);
            float radius = getWeaponDamageRadius(saber);
        }
        if (text.startsWith("decaySaber"))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            String arg = st.nextToken();
            String oidString = st.nextToken();
            Long temp = new Long(Long.parseLong(oidString));
            obj_id item = obj_id.getObjId(temp.longValue());
            dictionary data = new dictionary();
            data.put("amount", 100);
            data.put("owner", self);
            messageTo(item, "decaySaberCrystal", data, 0, false);
        }
        if (text.startsWith("decay "))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            String arg = st.nextToken();
            String oidString = st.nextToken();
            Long temp = new Long(Long.parseLong(oidString));
            obj_id item = obj_id.getObjId(temp.longValue());
            int hp = getHitpoints(item);
            if (hp > 0)
            {
                hp -= rand(25, 75);
                setInvulnerableHitpoints(item, hp);
            }
        }
        if (text.startsWith("jediState"))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            String arg = st.nextToken();
            String token = st.nextToken();
            int state = Integer.parseInt(token);
            switch (state)
            {
                case 0:
                state = JEDI_STATE_NONE;
                sendSystemMessageTestingOnly(self, "Setting Jedi State to JEDI_STATE_NONE");
                break;
                case 1:
                state = JEDI_STATE_FORCE_SENSITIVE;
                sendSystemMessageTestingOnly(self, "Setting Jedi State to JEDI_STATE_FORCE_SENSITIVE");
                break;
                case 2:
                state = JEDI_STATE_JEDI;
                sendSystemMessageTestingOnly(self, "Setting Jedi State to JEDI_STATE_JEDI");
                break;
                case 3:
                state = JEDI_STATE_FORCE_RANKED_LIGHT;
                sendSystemMessageTestingOnly(self, "Setting Jedi State to JEDI_STATE_FORCE_RANKED_LIGHT");
                break;
                case 4:
                state = JEDI_STATE_FORCE_RANKED_DARK;
                sendSystemMessageTestingOnly(self, "Setting Jedi State to JEDI_STATE_FORCE_RANKED_DARK");
                break;
            }
            setJediState(self, state);
        }
        if (text.startsWith("test"))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            String arg = st.nextToken();
            String lvlString = st.nextToken();
            int level = Integer.parseInt(lvlString);
            int count = 1;
            if (st.hasMoreTokens())
            {
                String countString = st.nextToken();
                count = Integer.parseInt(countString);
            }
            for (int i = 0; i < count; i++)
            {
                testTune(level);
            }
        }
        if (text.startsWith("fullTest"))
        {
            String output = "\tlevel\tdamage\tspeed\twound\tforce\t";
            LOG("saber_test", output);
            for (int i = 0; i < 65000; i++)
            {
                testTune(0);
            }
        }
        if (text.startsWith("crystalTest"))
        {
            obj_id inv = utils.getInventoryContainer(self);
            for (int i = 0; i < 32; i++)
            {
                obj_id crystal = createObject("object/tangible/component/weapon/lightsaber/lightsaber_module_force_crystal.iff", inv, "");
                custom_var myVar = getCustomVarByName(crystal, "private/index_color_1");
                if (myVar.isPalColor())
                {
                    palcolor_custom_var pcVar = (palcolor_custom_var)myVar;
                    pcVar.setValue(i);
                }
                setObjVar(crystal, jedi.VAR_CRYSTAL_STATS + "." + jedi.VAR_COLOR, i);
                setObjVar(crystal, jedi.VAR_CRYSTAL_OWNER_ID, self);
                setObjVar(crystal, jedi.VAR_CRYSTAL_OWNER_NAME, getName(self));
            }
        }
        if (text.equals("fuck"))
        {
            utils.removeScriptVar(self, "armor_count");
        }
        return SCRIPT_CONTINUE;
    }
    public int createCloneWarpSui(obj_id player) throws InterruptedException
    {
        location playerLoc = getLocation(player);
        String planetName = playerLoc.area;
        if (planetName == null)
        {
            return -1;
        }
        obj_id planet = getPlanetByName(planetName);
        if (!isIdValid(planet))
        {
            return -1;
        }
        Vector nameList = utils.getResizeableStringArrayScriptVar(planet, cloninglib.VAR_PLANET_CLONE_NAME);
        Vector areaList = utils.getResizeableStringArrayScriptVar(planet, cloninglib.VAR_PLANET_CLONE_AREA);
        Vector locList = utils.getResizeableLocationArrayScriptVar(planet, cloninglib.VAR_PLANET_CLONE_LOC);
        Vector list = new Vector();
        list.setSize(0);
        for (int i = 0; i < nameList.size(); i++)
        {
            String entry = ((String)nameList.get(i));
            entry += " \\>200 ";
            entry += ((String)areaList.get(i));
            entry += " (" + ((location)locList.get(i)).x + "," + ((location)locList.get(i)).z + ")";
            list = utils.addElement(list, entry);
        }
        String title = "Clone Warp";
        String prompt = "Choose a Clone Location";
        int pid = createSUIPage(sui.SUI_LISTBOX, player, player, "handleCloneWarp");
        setSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.LISTBOX_PROMPT, sui.PROP_TEXT, prompt);
        sui.listboxButtonSetup(pid, sui.OK_CANCEL);
        clearSUIDataSource(pid, sui.LISTBOX_DATASOURCE);
        for (int i = 0; i < list.size(); i++)
        {
            addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + i);
            setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + i, sui.PROP_TEXT, ((String)list.get(i)));
        }
        subscribeToSUIProperty(pid, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
        subscribeToSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT);
        showSUIPage(pid);
        flushSUIPage(pid);
        return pid;
    }
    public int handleCloneWarp(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL || idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        location playerLoc = getLocation(self);
        String planetName = playerLoc.area;
        if (planetName == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id planet = getPlanetByName(planetName);
        if (!isIdValid(planet))
        {
            return SCRIPT_CONTINUE;
        }
        Vector locList = utils.getResizeableLocationArrayScriptVar(planet, cloninglib.VAR_PLANET_CLONE_LOC);
        Vector respawnList = utils.getResizeableLocationArrayScriptVar(planet, cloninglib.VAR_PLANET_CLONE_RESPAWN);
        boolean warped = pclib.sendToAnyLocation(self, ((location)locList.get(idx)), ((location)respawnList.get(idx)));
        return SCRIPT_CONTINUE;
    }
    public void testTune(int level) throws InterruptedException
    {
        final String COMPONENT_DATATABLE = "datatables/jedi/saber_component_ranges.iff";
        obj_id player = getSelf();
        int quality = jedi.getCrystalQuality(level);
        dictionary dctWeaponInfo = dataTableGetRow(COMPONENT_DATATABLE, 2);
        int minDamage = (int)dctWeaponInfo.getFloat("fltMinDamage");
        int maxDamage = (int)dctWeaponInfo.getFloat("fltMaxDamage");
        float minSpeed = dctWeaponInfo.getFloat("fltMinSpeed");
        float maxSpeed = dctWeaponInfo.getFloat("fltMaxSpeed");
        float minForce = dctWeaponInfo.getFloat("fltMinForcePower");
        float maxForce = dctWeaponInfo.getFloat("fltMaxForcePower");
        float minWound = dctWeaponInfo.getFloat("fltMinWoundChance");
        float maxWound = dctWeaponInfo.getFloat("fltMaxWoundChance");
        String output = "";
        for (level = 10; level < 350; level += 10)
        {
            int damage = distributedRand(minDamage, maxDamage, level);
            float wound = distributedRand(minWound, maxWound, level);
            float force = distributedRand(minForce, maxForce, level);
            float speed = distributedRand(minSpeed, maxSpeed, level);
            output += "\t" + level + "\t" + damage + "\t" + speed + "\t" + wound + "\t" + force + "\t";
        }
        LOG("saber_test", output);
    }
    public int distributedRand(int min, int max, int level) throws InterruptedException
    {
        final int levelMin = 60;
        final int levelMax = 280;
        float rank = (float)(level - levelMin) / (float)(levelMax - levelMin);
        float mid = min + ((max - min) * rank);
        if (mid < min)
        {
            max += (mid - min);
            mid = min;
        }
        if (mid > max)
        {
            min += (mid - max);
            mid = max;
        }
        int minRand = rand(min, (int)(mid + 0.5f));
        int maxRand = rand((int)(mid + 0.5f), max);
        int randNum = rand(minRand, maxRand);
        return randNum;
    }
    public float distributedRand(float min, float max, int level) throws InterruptedException
    {
        final int levelMin = 60;
        final int levelMax = 280;
        float rank = (float)(level - levelMin) / (float)(levelMax - levelMin);
        float mid = min + ((max - min) * rank);
        if (mid < min)
        {
            max += (mid - min);
            mid = min;
        }
        if (mid > max)
        {
            min += (mid - max);
            mid = max;
        }
        float minRand = rand(min, mid);
        float maxRand = rand(mid, max);
        float randNum = rand(minRand, maxRand);
        return randNum;
    }
    public float round(float num, int decimal) throws InterruptedException
    {
        return ((int)(num * (10 ^ decimal))) / (float)(10 ^ decimal);
    }
    public void checkCreatures() throws InterruptedException
    {
        String[] creatureList = dataTableGetStringColumn(create.CREATURE_TABLE, "creatureName");
        for (int i = 0; i < creatureList.length; i++)
        {
            dictionary creatureDict = dataTableGetRow(create.CREATURE_TABLE, creatureList[i]);
            int level = creatureDict.getInt("BaseLevel");
            int statLevel = creatureDict.getInt("StatLevelModifier");
            int dmgLevel = creatureDict.getInt("Damagelevelmodifier");
            int newStatLevel = bio_engineer.calcStatLevel(creatureDict);
            int newDmgLevel = bio_engineer.calcDmgLevel(creatureDict);
            int newToHitLevel = bio_engineer.calcToHitLevel(creatureDict);
            int newArmorLevel = bio_engineer.calcArmorLevel(creatureDict);
            int oldToHitLevel = bio_engineer.calcOldToHitLevel(creatureDict);
            int oldArmorLevel = bio_engineer.calcOldArmorLevel(creatureDict);
            int newLevel = (int)((((newStatLevel * 2) + (newDmgLevel * 2) + (newToHitLevel * 2) + (newArmorLevel * 4)) / 10) + 0.5f);
            int newLevelOldArmor = (int)((((newStatLevel * 2) + (newDmgLevel * 2) + (newToHitLevel * 2) + (oldArmorLevel * 4)) / 10) + 0.5f);
            int levelDiff = newLevel - level;
            String output = "";
            if (levelDiff > 5 || levelDiff < -5)
            {
                output = "**\t" + creatureList[i] + " - \t";
            }
            else 
            {
                output = "\t" + creatureList[i] + " - \t";
            }
            output += "newLevel(newArmor) = " + newLevel + "; \t";
            output += "newLevel(oldArmor) = " + newLevelOldArmor + "; \t";
            output += "oldLevel = " + level + "; \t";
            output += "newStatLevel = " + newStatLevel + "; \t";
            output += "oldStatLevel = " + (level + statLevel) + "; \t";
            output += "newDmgLevel = " + newDmgLevel + "; \t";
            output += "oldDmgLevel = " + (level + dmgLevel) + "; \t";
            output += "newToHit = " + newToHitLevel + "; \t";
            output += "oldToHit = " + oldToHitLevel + "; \t";
            output += "newArmor = " + newArmorLevel + "; \t";
            output += "oldArmor = " + oldArmorLevel + "; \t";
            LOG("creature_balance", output);
        }
    }
}
