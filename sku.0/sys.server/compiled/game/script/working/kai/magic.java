package script.working.kai;

import script.attribute;
import script.combat_engine.hit_result;
import script.deltadictionary;
import script.dictionary;
import script.library.*;
import script.obj_id;

import java.util.StringTokenizer;

public class magic extends script.base_script
{
    public magic()
    {
    }
    public void printTokens(StringTokenizer st) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "tokens -> " + st.countTokens() + " nextToken ->" + st.nextToken());
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (speaker != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(speaker, "kai"))
        {
            return SCRIPT_CONTINUE;
        }
        if (text.startsWith("where"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            sendSystemMessageTestingOnly(self, getName(target) + " is at " + (getLocation(target)).toString());
        }
        if (text.startsWith("enemycheck"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            sendSystemMessageTestingOnly(self, getName(target) + " isEnemy=" + pvpIsEnemy(self, target) + ", canAttack=" + pvpCanAttack(self, target));
        }
        if (text.startsWith("testclass"))
        {
            boolean rslt = utils.testItemClassRequirements(self, getLookAtTarget(self), false, "trap.");
            sendSystemMessageTestingOnly(self, "Pass Class requirements: " + rslt);
        }
        if (text.startsWith("getowner"))
        {
            obj_id owner = getOwner(getLookAtTarget(self));
            sendSystemMessageTestingOnly(self, "Owner for " + getName(getLookAtTarget(self)) + " is " + getName(owner));
        }
        if (text.startsWith("getpl"))
        {
            StringTokenizer st = new StringTokenizer(text);
            printTokens(st);
            String lvl = "";
            int level = 1;
            if (st.hasMoreTokens())
            {
                level = utils.stringToInt(st.nextToken());
            }
            sendSystemMessageTestingOnly(self, "Level " + level + " gives power of " + stealth.getPowerLevel(level, false));
        }
        obj_id inv = utils.getInventoryContainer(self);
        if (text.startsWith("getsv"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            deltadictionary dd = target.getScriptVars();
            sendSystemMessageTestingOnly(self, dd.toString());
        }
        if (text.startsWith("hurt"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            hit_result hit = new hit_result();
            hit.damage = 1000;
            hit.hitLocation = 0;
            doDamage(target, self, hit);
        }
        if (text.startsWith("dodot"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                target = self;
            }
            sendSystemMessageTestingOnly(self, "APPLY DOT=" + dot.applyBleedingEffect(self, target, "bleed777", 0, 5000, 15));
        }
        if (text.startsWith("mycontainer"))
        {
            sendSystemMessageTestingOnly(self, "MyContainer=" + getTopMostContainer(self));
        }
        if (text.startsWith("spy1"))
        {
            buff.applyBuff(self, "invis_sp_buff_invis_1");
            return SCRIPT_CONTINUE;
        }
        if (text.startsWith("smug"))
        {
            sendSystemMessageTestingOnly(self, "Applied=" + buff.applyBuff(self, "invis_sm_buff_invis_1"));
            return SCRIPT_CONTINUE;
        }
        if (text.startsWith("act"))
        {
            sendSystemMessageTestingOnly(self, "action Up!");
            setMaxAttrib(self, ACTION, 10000);
            setAttrib(self, ACTION, 10000);
            return SCRIPT_CONTINUE;
        }
        if (text.startsWith("steal"))
        {
            if (stealth.canSteal(self, getLookAtTarget(self)))
            {
                stealth.steal(self, getLookAtTarget(self));
            }
        }
        if (text.startsWith("gettab"))
        {
            String bleh = stealth.getStealableTemplateFromTable(self);
            sendSystemMessageTestingOnly(self, bleh);
        }
        if (text.startsWith("dtype"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "No target.");
                return SCRIPT_CONTINUE;
            }
            weapons.setWeaponData(target);
        }
        if (text.startsWith("cleaninvis"))
        {
            utils.removeScriptVar(self, stealth.INVIS_UPKEEP_MSG_DISPATCHED);
        }
        if (text.startsWith("loc"))
        {
            sendSystemMessageTestingOnly(self, "Location is" + (getLocation(getLookAtTarget(self))).toString());
        }
        if (text.startsWith("regen"))
        {
            sendSystemMessageTestingOnly(self, "Regen multiplier is 0");
        }
        if (text.startsWith("getname"))
        {
            sendSystemMessageTestingOnly(self, "Name is: '" + getName(getLookAtTarget(self)) + "'");
        }
        if (text.startsWith("getcount"))
        {
            sendSystemMessageTestingOnly(self, "Count is: '" + getCount(getLookAtTarget(self)) + "'");
        }
        if (text.startsWith("getcommands"))
        {
            String[] commands = getCommandListingForPlayer(getLookAtTarget(self));
            sendSystemMessageTestingOnly(self, "Commands:");
            for (String command : commands) {
                sendSystemMessageTestingOnly(self, "'" + command + "'");
            }
        }
        if ((toLower(text)).startsWith("incapcount"))
        {
            int num = 0;
            if (hasObjVar(getLookAtTarget(self), "combat.intIncapacitationCount"))
            {
                num = getIntObjVar(self, "combat.intIncapacitationCount");
            }
            sendSystemMessageTestingOnly(self, "# of timers incap'd = " + num);
        }
        if ((toLower(text)).startsWith("damage"))
        {
            StringTokenizer st = new StringTokenizer(text);
            if (st.countTokens() != 3)
            {
                return SCRIPT_CONTINUE;
            }
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                return SCRIPT_CONTINUE;
            }
            String command = st.nextToken();
            String attribStr = st.nextToken();
            String amountStr = st.nextToken();
            attribute[] attrib = getAttribs(target);
            int amount = utils.stringToInt(amountStr);
            switch (attribStr) {
                case "health":
                    attrib[HEALTH] = new attribute(attrib[HEALTH].getType(), (attrib[HEALTH].getValue() - amount));
                    break;
                case "action":
                    attrib[ACTION] = new attribute(attrib[ACTION].getType(), (attrib[ACTION].getValue() - amount));
                    break;
                case "mind":
                    attrib[MIND] = new attribute(attrib[MIND].getType(), (attrib[MIND].getValue() - amount));
                    break;
                case "all":
                    for (int i = 0; i < attrib.length; i += NUM_ATTRIBUTES_PER_GROUP) {
                        attrib[i] = new attribute(attrib[i].getType(), (attrib[i].getValue() - amount));
                    }
                    break;
                default:
                    return SCRIPT_CONTINUE;
            }
            for (int j = 0; j < attrib.length; j += NUM_ATTRIBUTES_PER_GROUP)
            {
                if (attrib[j].getValue() < 1)
                {
                    attrib[j] = new attribute(attrib[j].getType(), 1);
                }
            }
            if (setAttribs(target, attrib))
            {
                sendSystemMessageTestingOnly(self, "...target successully damaged...");
            }
            return SCRIPT_CONTINUE;
        }
        if (text.startsWith("getniche"))
        {
            sendSystemMessageTestingOnly(self, "Niche for " + getLookAtTarget(self) + " = " + ai_lib.aiGetNiche(getLookAtTarget(self)));
        }
        if (text.startsWith("whichgroup"))
        {
            sendSystemMessageTestingOnly(self, "Group Object for " + getLookAtTarget(self) + " = " + getGroupObject(getLookAtTarget(self)));
        }
        if (text.startsWith("mobtest"))
        {
            sendSystemMessageTestingOnly(self, "isMob(" + getLookAtTarget(self) + ") = " + isMob(getLookAtTarget(self)));
        }
        if (text.startsWith("listdp"))
        {
            obj_id dp = utils.getPlayerDatapad(self);
            obj_id[] things = getContents(dp);
            for (int i = 0; i < things.length; i++)
            {
                sendSystemMessageTestingOnly(self, "Item[" + i + "] = " + things[i] + ", " + getTemplateName(things[i]) + ")");
                obj_id[] thingcontents = getContents(things[i]);
                for (int x = 0; x < thingcontents.length; x++)
                {
                    sendSystemMessageTestingOnly(self, "--Item[" + x + "] = " + thingcontents[x] + ", " + getTemplateName(thingcontents[x]) + ")");
                }
            }
        }
        if (text.equals("packHouse"))
        {
            player_structure.packBuilding(self, getLookAtTarget(self));
            sendSystemMessageTestingOnly(self, "Did i crash");
        }
        if (text.startsWith("cleandp"))
        {
            obj_id dp = utils.getPlayerDatapad(self);
            obj_id[] things = getContents(dp);
            for (obj_id thing : things) {
                destroyObject(thing);
            }
            sendSystemMessageTestingOnly(self, "DP cleaned.");
        }
        if (text.startsWith("sockets"))
        {
            setSkillModSockets(utils.stringToObjId("10590100"), 4);
        }
        if (text.startsWith("wcreate"))
        {
            StringTokenizer st = new StringTokenizer(text);
            printTokens(st);
            String name = "";
            if (st.hasMoreTokens())
            {
                name = st.nextToken();
            }
            float num = 1.0f;
            if (st.hasMoreTokens())
            {
                num = utils.stringToFloat(st.nextToken());
            }
            LOG("weapons", "Creating weapon " + name + " at " + num + " capacity.");
            weapons.createWeapon(name, speaker, weapons.VIA_SCHEMATIC, num);
            return SCRIPT_CONTINUE;
        }
        if ((toLower(text)).equals("grant all weapon schematics"))
        {
            LOG("weapons", "Granting schematics");
            weapons.grantOrDenyAllWeaponSchematics("all", getLookAtTarget(self), weapons.VIA_SCHEMATIC, true);
            return SCRIPT_CONTINUE;
        }
        if ((toLower(text)).equals("revoke all weapon schematics"))
        {
            LOG("weapons", "Revoking schematics");
            weapons.grantOrDenyAllWeaponSchematics("all", getLookAtTarget(self), weapons.VIA_SCHEMATIC, false);
            return SCRIPT_CONTINUE;
        }
        if ((toLower(text)).equals("lootdata"))
        {
            LOG("loot", "Fetching loot data for object ");
            obj_id item = getLookAtTarget(self);
            if (!isIdValid(item))
            {
                LOG("loot", "No valid lookat target to get loot data for..." + item);
                return SCRIPT_CONTINUE;
            }
            dictionary weaponCraftingData = loot.getComponentData(item);
            if (weaponCraftingData == null)
            {
                LOG("loot", "Couldn't find data for ..." + item);
                return SCRIPT_CONTINUE;
            }
            LOG("loot", "Object loot data for " + item + " as follows:");
            LOG("loot", "---------------------------------------------");
            LOG("loot", weaponCraftingData.toString());
            return SCRIPT_CONTINUE;
        }
        if ((toLower(text)).startsWith("makelootweapon"))
        {
            LOG("loot", "Creating loot weapon for object ");
            obj_id item = getLookAtTarget(self);
            if (!isIdValid(item))
            {
                LOG("loot", "No valid lookat target to transform into a loot item..." + item);
                return SCRIPT_CONTINUE;
            }
            StringTokenizer st = new StringTokenizer(text);
            printTokens(st);
            String level = "1";
            if (st.hasMoreTokens())
            {
                level = st.nextToken();
            }
            int lev = utils.stringToInt(level);
            if (lev < 1 || lev > 350)
            {
                LOG("loot", "Invalid level.  Use 1 - 350");
                return SCRIPT_CONTINUE;
            }
            if (loot.randomizeWeapon(item, lev))
            {
                LOG("loot", "Weapon " + item + " has been re-rolled as a level " + lev + " loot item.");
            }
            else 
            {
                LOG("loot", "Weapon could not be initialized as a loot drop.");
            }
        }
        if ((toLower(text)).startsWith("makelootarmor"))
        {
            LOG("loot", "Creating loot armor for object ");
            obj_id item = getLookAtTarget(self);
            if (!isIdValid(item))
            {
                LOG("loot", "No valid lookat target to transform into a loot item..." + item);
                return SCRIPT_CONTINUE;
            }
            StringTokenizer st = new StringTokenizer(text);
            printTokens(st);
            String level = "1";
            if (st.hasMoreTokens())
            {
                level = st.nextToken();
            }
            int lev = utils.stringToInt(level);
            if (lev < 1 || lev > 350)
            {
                LOG("loot", "Invalid level.  Use 1 - 350");
                return SCRIPT_CONTINUE;
            }
            if (loot.randomizeArmor(item, lev))
            {
                LOG("loot", "Armor " + item + " has been re-rolled as a level " + lev + " loot item.");
            }
            else 
            {
                LOG("loot", "Armor could not be initialized as a loot drop.");
            }
        }
        if ((toLower(text)).startsWith("makelootcomponent"))
        {
            LOG("loot", "Creating loot component for object ");
            obj_id item = getLookAtTarget(self);
            if (!isIdValid(item))
            {
                LOG("loot", "No valid lookat target to transform into a loot item..." + item);
                return SCRIPT_CONTINUE;
            }
            StringTokenizer st = new StringTokenizer(text);
            printTokens(st);
            String level = "1";
            if (st.hasMoreTokens())
            {
                level = st.nextToken();
            }
            int lev = utils.stringToInt(level);
            if (lev < 1 || lev > 350)
            {
                LOG("loot", "Invalid level.  Use 1 - 350");
                return SCRIPT_CONTINUE;
            }
            if (loot.randomizeComponent(item, lev, utils.getInventoryContainer(self)))
            {
                LOG("loot", "Component " + item + " has been re-rolled as a level " + lev + " loot item.");
            }
            else 
            {
                LOG("loot", "Component could not be initialized as a loot drop.");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
