package script.beta;

import script.*;
import script.library.*;

import java.util.Arrays;
import java.util.Vector;

public class debugger extends script.base_script
{
    public debugger()
    {
    }
    public static final String DEBUG_EMOTE_TARGET = "debug.emote_target";
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        boolean foundTrigger = false;
        obj_id target = getLookAtTarget(self);
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            target = self;
        }
        java.util.StringTokenizer st = new java.util.StringTokenizer(text);
        String arg = st.nextToken();
        if (arg.equals(""))
        {
        }
        else if (arg.equals("addJediSlot"))
        {
            addJediSlot(target);
            foundTrigger = true;
        }
        else if (arg.equals("insure"))
        {
            if (st.hasMoreTokens())
            {
                target = utils.stringToObjId(st.nextToken());
                if (isIdValid(target))
                {
                    if (setInsured(target, true))
                    {
                        sendSystemMessageTestingOnly(self, target + " insured: " + isInsured(target));
                    }
                }
                foundTrigger = true;
            }
        }
        else if (arg.equals("uninsure"))
        {
            if (st.hasMoreTokens())
            {
                target = utils.stringToObjId(st.nextToken());
                if (isIdValid(target))
                {
                    if (setInsured(target, false))
                    {
                        sendSystemMessageTestingOnly(self, target + " uninsured: " + !isInsured(target));
                    }
                }
                foundTrigger = true;
            }
        }
        else if (arg.equals("autoinsure"))
        {
            if (st.hasMoreTokens())
            {
                target = utils.stringToObjId(st.nextToken());
                if (isIdValid(target))
                {
                    if (setAutoInsured(target))
                    {
                        sendSystemMessageTestingOnly(self, target + " autoinsured: " + isAutoInsured(target));
                    }
                }
                foundTrigger = true;
            }
        }
        else if (arg.equals("setUninsurable"))
        {
            if (st.hasMoreTokens())
            {
                target = utils.stringToObjId(st.nextToken());
                if (isIdValid(target))
                {
                    if (setUninsurable(target, true))
                    {
                        sendSystemMessageTestingOnly(self, target + " uninsurable: " + isUninsurable(target));
                    }
                }
                foundTrigger = true;
            }
        }
        else if (arg.equals("clearUninsurable"))
        {
            if (st.hasMoreTokens())
            {
                target = utils.stringToObjId(st.nextToken());
                if (isIdValid(target))
                {
                    if (setUninsurable(target, false))
                    {
                        sendSystemMessageTestingOnly(self, target + " is insurable: " + !isUninsurable(target));
                    }
                }
                foundTrigger = true;
            }
        }
        else if (arg.equals("getName"))
        {
            if (st.hasMoreTokens())
            {
                obj_id tmp = utils.stringToObjId(st.nextToken());
                if (isIdValid(tmp))
                {
                    target = tmp;
                }
            }
            sendSystemMessageTestingOnly(self, target + " name: " + getName(target));
            foundTrigger = true;
        }
        else if (arg.equals("setFactionStanding"))
        {
            if (st.hasMoreTokens() && st.countTokens() == 2)
            {
                String factionName = st.nextToken();
                int value = utils.stringToInt(st.nextToken());
                if (value < 1)
                {
                    sendSystemMessageTestingOnly(self, "[DEBUGGER] syntax: setFactionStanding <faction> <value>");
                }
                else 
                {
                    factions.setFactionStanding(target, factionName, value);
                    sendSystemMessageTestingOnly(self, "[DEBUGGER] attempting to setfaction: faction = " + factionName + " val = " + value);
                }
            }
            foundTrigger = true;
        }
        else if (arg.equals("getGameTime"))
        {
            sendSystemMessageTestingOnly(self, "Current game time = " + getGameTime());
            foundTrigger = true;
        }
        else if (arg.equals("pvpCanAttack"))
        {
            sendSystemMessageTestingOnly(self, "pvpCanAttack(self, " + target + ") = " + pvpCanAttack(self, target));
            foundTrigger = true;
        }
        else if (arg.equals("pvpCanBeAttacked"))
        {
            sendSystemMessageTestingOnly(self, "pvpCanBeAttacked(" + target + ", self) = " + pvpCanAttack(target, self));
            foundTrigger = true;
        }
        else if (arg.equals("clearLots"))
        {
            sendSystemMessageTestingOnly(self, "Clearing your lots...");
            int current_lots = getAccountNumLots(getPlayerObject(self));
            adjustLotCount(getPlayerObject(self), -current_lots);
            foundTrigger = true;
        }
        else if (arg.equals("getLots"))
        {
            int current_lots = getAccountNumLots(getPlayerObject(self));
            sendSystemMessageTestingOnly(self, "current account lots = " + current_lots);
            foundTrigger = true;
        }
        else if (arg.equals("getWeaponType"))
        {
            if (st.hasMoreTokens())
            {
                target = utils.stringToObjId(st.nextToken());
            }
            if (isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "target weapon type = " + getWeaponType(target));
            }
            foundTrigger = true;
        }
        else if (arg.equals("fireworkShow"))
        {
            obj_id inv = utils.getInventoryContainer(self);
            if (isIdValid(inv))
            {
                obj_id show = createObject("object/tangible/firework/show.iff", inv, "");
                if (isIdValid(show))
                {
                    String[] fx_opt = dataTableGetStringColumnNoDefaults(firework.TBL_FX, "name");
                    String[] fxs = new String[firework.SHOW_EVENT_MAX];
                    float[] delays = new float[firework.SHOW_EVENT_MAX];
                    for (int i = 0; i < fxs.length; i++)
                    {
                        fxs[i] = fx_opt[rand(1, fx_opt.length - 1)];
                        if (i == 0)
                        {
                            delays[i] = 10.0f;
                        }
                        else 
                        {
                            delays[i] = 0.1f;
                        }
                    }
                    if (fxs != null && fxs.length > 0 && delays != null && delays.length > 0)
                    {
                        setObjVar(show, firework.VAR_SHOW_FX, fxs);
                        setObjVar(show, firework.VAR_SHOW_DELAY, delays);
                        setCount(show, firework.SHOW_EVENT_MAX);
                        sendSystemMessageTestingOnly(self, "Fireworks show created in your inventory!");
                    }
                }
                else 
                {
                    debugSpeakMsg(self, "fireworkShow: unable to create valid object in your inventory!");
                }
            }
            foundTrigger = true;
        }
        else if (arg.equals("getPalVars"))
        {
            Vector entries = new Vector();
            entries.setSize(0);
            dictionary d = hue.getPalcolorData(target);
            if (d != null && !d.isEmpty())
            {
                java.util.Enumeration keys = d.keys();
                while (keys.hasMoreElements())
                {
                    String var = (String)keys.nextElement();
                    int idx = d.getInt(var);
                    entries = utils.addElement(entries, var + " -> " + idx);
                }
            }
            if (entries == null || entries.size() == 0)
            {
                sendSystemMessageTestingOnly(self, "No palcolor data for target: " + target);
            }
            else 
            {
                sui.listbox(self, self, "Palvar data for " + target, sui.OK_ONLY, target.toString(), entries, "noHandler");
            }
            foundTrigger = true;
        }
        else if (arg.equals("getOwner"))
        {
            sendSystemMessageTestingOnly(self, "Target(" + target + ") owner = " + getOwner(target));
            foundTrigger = true;
        }
        else if (arg.equals("setOwner"))
        {
            sendSystemMessageTestingOnly(self, "Attempting to set owner... of Target(" + target + ") to owner = " + self);
            if (setOwner(target, self))
            {
                sendSystemMessageTestingOnly(self, " Set Owner succeeded");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, " Set Owner failed");
            }
            sendSystemMessageTestingOnly(self, "Target(" + target + ") owner = " + getOwner(target));
            foundTrigger = true;
        }
        else if (arg.equals("getContainerType"))
        {
            if (st.hasMoreTokens())
            {
                obj_id tmpTarget = utils.stringToObjId(st.nextToken());
                if (isIdValid(tmpTarget))
                {
                    target = tmpTarget;
                }
            }
            sendSystemMessageTestingOnly(self, "Target(" + target + ") container type = " + getContainerType(target));
            foundTrigger = true;
        }
        else if (arg.equals("playAnim"))
        {
            if (st.hasMoreTokens())
            {
                String anim = st.nextToken();
                sendSystemMessageTestingOnly(self, "Target(" + target + ") attempting to play anim: " + anim);
                ai_lib.doAction(target, anim);
            }
            foundTrigger = true;
        }
        else if (arg.equals("spawnSignedStructures"))
        {
            sendSystemMessageTestingOnly(self, "attempting to spawn all signed structures...");
            String[] tpf = dataTableGetStringColumnNoDefaults("datatables/structure/player_structure_sign.iff", "STRUCTURE_APPEARANCE");
            if (tpf != null && tpf.length > 0)
            {
                location there = getLocation(self);
                there.x = there.x - 60.0f;
                dictionary d = new dictionary();
                d.put("template", tpf);
                d.put("loc", there);
                d.put("cnt", 0);
                messageTo(self, "handleSignedStructureCreation", d, 3, false);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "unable to locate structure templates");
            }
            foundTrigger = true;
        }
        else if (arg.equals("setMagicCondition"))
        {
            sendSystemMessageTestingOnly(self, "Target(" + target + ") attempting to set condition magic");
            setCondition(target, CONDITION_MAGIC_ITEM);
            foundTrigger = true;
        }
        else if (arg.equals("clearMagicCondition"))
        {
            sendSystemMessageTestingOnly(self, "Target(" + target + ") attempting to set condition magic");
            clearCondition(target, CONDITION_MAGIC_ITEM);
            foundTrigger = true;
        }
        else if (arg.equals("getCount"))
        {
            if (st.hasMoreTokens())
            {
                target = utils.stringToObjId(st.nextToken());
                if (isIdValid(target))
                {
                    int cnt = getCount(target);
                    sendSystemMessageTestingOnly(self, "Target(" + target + ") get count = " + cnt);
                }
            }
            foundTrigger = true;
        }
        else if (arg.equals("setCount"))
        {
            if (st.hasMoreTokens() && st.countTokens() == 2)
            {
                target = utils.stringToObjId(st.nextToken());
                int cnt = utils.stringToInt(st.nextToken());
                if (isIdValid(target) && cnt > 0)
                {
                    setCount(target, cnt);
                    sendSystemMessageTestingOnly(self, "Target(" + target + ") set count = " + cnt);
                }
            }
            foundTrigger = true;
        }
        else if (arg.equals("incrementCount"))
        {
            if (st.hasMoreTokens() && st.countTokens() == 2)
            {
                target = utils.stringToObjId(st.nextToken());
                int cnt = utils.stringToInt(st.nextToken());
                if (isIdValid(target) && cnt > 0)
                {
                    incrementCount(target, cnt);
                    sendSystemMessageTestingOnly(self, "Target(" + target + ") increment count = " + cnt + " total cnt = " + getCount(target));
                }
            }
            foundTrigger = true;
        }
        else if (arg.equals("baitFishingPole"))
        {
            obj_id held = getObjectInSlot(self, "hold_r");
            if (!isIdValid(held))
            {
                sendSystemMessageTestingOnly(self, "Invalid obj_id reference in slot HELD_R");
            }
            else 
            {
                if (!minigame.isFishingPole(held))
                {
                    sendSystemMessageTestingOnly(self, "HELD_R object is not a fishing pole");
                }
                else 
                {
                    obj_id bait = createObject("object/tangible/fishing/bait/bait_grub.iff", held, "");
                    if (isIdValid(bait))
                    {
                        sendSystemMessageTestingOnly(self, "Bait(" + bait + ") created in HELD_R(" + held + ")");
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "Unable to create bait(" + bait + ") in HELD_R(" + held + ")");
                    }
                }
            }
            foundTrigger = true;
        }
        else if (arg.equals("debugCatchFish"))
        {
            if (minigame.isFishing(self))
            {
                sendSystemMessageTestingOnly(self, "Setting caught fish scriptvars...");
                utils.setScriptVar(self, minigame.SCRIPTVAR_STATUS, minigame.FS_CAUGHT);
            }
            foundTrigger = true;
        }
        else if (arg.equals("debugCatchLoot"))
        {
            if (minigame.isFishing(self))
            {
                sendSystemMessageTestingOnly(self, "Setting caught fishing loot scriptvars...");
                utils.setScriptVar(self, minigame.SCRIPTVAR_STATUS, minigame.FS_LOOT);
            }
            foundTrigger = true;
        }
        else if (arg.equals("makeFish"))
        {
            obj_id fish = minigame.spawnFishingFish(self, getLocation(self));
            if (isIdValid(fish))
            {
                sendSystemMessageTestingOnly(self, "creating fish: obj_id = " + fish);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "unable to make fish!");
            }
            foundTrigger = true;
        }
        else if (arg.equals("makeFirework"))
        {
            obj_id inv = utils.getInventoryContainer(self);
            obj_id fw = createObject("object/tangible/firework/default.iff", inv, "");
            if (isIdValid(fw))
            {
                sendSystemMessageTestingOnly(self, "creating firework: obj_id = " + fw);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "unable to make firework!");
            }
            foundTrigger = true;
        }
        else if (arg.equals("testGetHeadingToLocation"))
        {
            if (target != self)
            {
                location here = getLocation(self);
                location there = getLocation(target);
                float range = getDistance(here, there);
                float heading = utils.getHeadingToLocation(here, there);
                sendSystemMessageTestingOnly(self, "range = " + range + " heading = " + heading);
                sendSystemMessageTestingOnly(self, "If it was successful, an explosion should have lit under your feet!");
                location testLoc = utils.rotatePointXZ(there, range, heading);
                createObject("object/static/particle/particle_sm_explosion.iff", testLoc);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Self is an invalid target for this test trigger");
            }
            foundTrigger = true;
        }
        else if (arg.equals("openObject"))
        {
            if (isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "Attempting to open target container...");
                utils.requestContainerOpen(self, target);
            }
            foundTrigger = true;
        }
        else if (arg.equals("openInventory"))
        {
            obj_id inv = utils.getInventoryContainer(target);
            if (isIdValid(inv))
            {
                sendSystemMessageTestingOnly(self, "Attempting to open target's inventory...");
                utils.requestContainerOpen(self, inv);
            }
            foundTrigger = true;
        }
        else if (arg.equals("grenade"))
        {
            obj_id inv = utils.getInventoryContainer(self);
            obj_id grenade = createObject("object/weapon/ranged/grenade/grenade_fragmentation_light.iff", inv, "");
            if (isIdValid(grenade))
            {
                sendSystemMessageTestingOnly(self, "Grenade(" + grenade + ") created in your inventory");
            }
            foundTrigger = true;
        }
        else if (arg.equals("grenadeMine"))
        {
            obj_id inv = utils.getInventoryContainer(target);
            obj_id grenade = createObject("object/weapon/ranged/grenade/grenade_fragmentation_light.iff", inv, "");
            if (isIdValid(grenade))
            {
                sendSystemMessageTestingOnly(self, "Grenade(" + grenade + ") created AS A MINE in target inventory");
                setObjVar(grenade, "isMine", 1);
            }
            foundTrigger = true;
        }
        else if (arg.equals("fillMinefield"))
        {
            obj_id grenade = createObject("object/weapon/mine/wp_mine_drx55.iff", target, "");
            if (isIdValid(grenade))
            {
                sendSystemMessageTestingOnly(self, "DRX55 MINE(" + grenade + ") created AS A MINE in minefield");
                setObjVar(grenade, "isMine", 1);
            }
            foundTrigger = true;
        }
        else if (arg.equals("quickDeed"))
        {
            obj_id inv = utils.getInventoryContainer(self);
            obj_id deed = createObject("object/tangible/deed/player_house_deed/tatooine_house_small_deed.iff", inv, "");
            if (isIdValid(deed))
            {
                setObjVar(deed, "player_structure.deed.buildtime", 15);
                sendSystemMessageTestingOnly(self, "deed set in inventory...");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "unable to create deed...");
            }
            foundTrigger = true;
        }
        else if (arg.equals("sign"))
        {
            if (st.countTokens() == 1)
            {
                target = utils.stringToObjId(st.nextToken());
                if (isIdValid(target))
                {
                    sendSystemMessageTestingOnly(self, "attempting to createStructureSign on " + target);
                    obj_id sign = player_structure.createStructureSign(target);
                    if (isIdValid(sign))
                    {
                        sendSystemMessageTestingOnly(self, "sign(" + sign + ") created...");
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "unable to create structure sign...");
                    }
                }
                foundTrigger = true;
            }
        }
        else if (arg.equals("rings"))
        {
            obj_id inv = utils.getInventoryContainer(self);
            if (isIdValid(inv))
            {
                for (int i = 1; i < 5; i++)
                {
                    createObject("object/tangible/wearables/ring/ring_s0" + i + ".iff", inv, "");
                }
                sendSystemMessageTestingOnly(self, "Rings created in your inventory...");
            }
            foundTrigger = true;
        }
        else if (arg.equals("maxhp"))
        {
            int max = getMaxHitpoints(target);
            setHitpoints(target, max);
            sendSystemMessageTestingOnly(self, "Target(" + target + ") set to max hp!");
            foundTrigger = true;
        }
        else if (arg.equals("makeSocketed"))
        {
            int cnt = 0;
            if (st.countTokens() == 2)
            {
                target = utils.stringToObjId(st.nextToken());
            }
            if (st.countTokens() == 1)
            {
                cnt = utils.stringToInt(st.nextToken());
            }
            if (isIdValid(target) && cnt > 0)
            {
                int got = getGameObjectType(target);
                if (isGameObjectTypeOf(got, GOT_clothing) || isGameObjectTypeOf(got, GOT_misc_container_wearable) || isGameObjectTypeOf(got, GOT_armor))
                {
                    setSkillModSockets(target, cnt);
                    sendSystemMessageTestingOnly(self, "makeSocketed: setting socket count -> " + cnt);
                }
            }
            foundTrigger = true;
        }
        else if (arg.equals("makeGem"))
        {
            obj_id inv = utils.getInventoryContainer(self);
            if (isIdValid(inv))
            {
                int lvl = rand(10, 60);
                obj_id gem = magic_item.makeGem(inv, lvl);
                if (isIdValid(gem))
                {
                    sendSystemMessageTestingOnly(self, "Gem succuessfully created: obj_id = " + gem + " lvl = " + lvl);
                }
            }
            foundTrigger = true;
        }
        else if (arg.equals("makeLoot"))
        {
            if (st.countTokens() == 1)
            {
                String itemArg = st.nextToken();
                int lvl = rand(1, 60);
                obj_id inv = null;
                if (isMob(target))
                {
                    inv = utils.getInventoryContainer(self);
                }
                else if (getContainerType(target) > 0)
                {
                    inv = target;
                }
                if (isIdValid(inv))
                {
                }
                foundTrigger = true;
            }
        }
        else if (arg.equals("testRandomLoot"))
        {
            int[] opt = 
            {
                1,
                3,
                4,
                5,
                6
            };
            int idx = rand(0, opt.length - 1);
            String template = "object/tangible/wearables/backpack/backpack_s0" + opt[idx] + ".iff";
            target = createObject(template, utils.getInventoryContainer(self), "");
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "unable to create template: " + template);
            }
            else 
            {
                setName(target, "lootTest:" + getGameTime());
                String[] lootTypes = dataTableGetStringColumnNoDefaults("datatables/mob/creatures.iff", "miscType");
                if ((lootTypes != null) && (lootTypes.length > 0))
                {
                    int n = 0;
                    for (int i = 0; i < 50; i++)
                    {
                        if (n >= 50)
                        {
                            break;
                        }
                        int lvl = rand(1, 60);
                        String itemArg = "datatables/loot/generic.iff:clothing";
                    }
                    sendSystemMessageTestingOnly(self, "created " + n + " loot items in target = " + target);
                }
            }
            foundTrigger = true;
        }
        else if (arg.equals("makeJunk"))
        {
            String itemArg = "datatables/loot/generic.iff:misc";
            if (st.countTokens() == 1)
            {
                itemArg = "datatables/loot/generic.iff:" + st.nextToken();
            }
            int lvl = rand(1, 60);
            obj_id inv = utils.getInventoryContainer(self);
            if (isIdValid(inv))
            {
            }
            foundTrigger = true;
        }
        else if (arg.equals("createObject"))
        {
            if (st.countTokens() == 1)
            {
                String template = st.nextToken();
                obj_id item = createObject(template, getLocation(self));
                if (isIdValid(item))
                {
                    sendSystemMessageTestingOnly(self, "createObject: object created! id = " + item);
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "createObject: unable to create object!!");
                }
                foundTrigger = true;
            }
        }
        else if (arg.equals("convertToMagicItem"))
        {
            if (st.hasMoreTokens())
            {
                String sTarget = st.nextToken();
                target = utils.stringToObjId(sTarget);
            }
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "convertToMagicItem: invalid target!");
            }
            else 
            {
                int lvl = rand(5, 250);
                if (magic_item.convertToMagicItem(target, lvl))
                {
                    sendSystemMessageTestingOnly(self, "Target(" + target + "): converted to magic item where lvl = " + lvl);
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "Target(" + target + "): convert failed @ lvl = " + lvl);
                }
            }
            foundTrigger = true;
        }
        else if (arg.equals("magicBackpack"))
        {
            int lvl = rand(5, 250);
            int[] opt = 
            {
                1,
                3,
                4,
                5,
                6
            };
            int idx = rand(0, opt.length - 1);
            String template = "object/tangible/wearables/backpack/backpack_s0" + opt[idx] + ".iff";
            target = createObject(template, utils.getInventoryContainer(self), "");
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "unable to create template: " + template);
            }
            else 
            {
                if (magic_item.convertToMagicItem(target, lvl))
                {
                    sendSystemMessageTestingOnly(self, "Target(" + target + "): converted to magic item where lvl = " + lvl);
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "Target(" + target + "): convert failed @ lvl = " + lvl);
                }
            }
            foundTrigger = true;
        }
        else if (arg.equals("getInventoryContainer"))
        {
            obj_id inv = utils.getInventoryContainer(target);
            if (isIdValid(inv))
            {
                sendSystemMessageTestingOnly(self, "Target = " + target + " Inventory = " + inv);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Unable to locate an inventory for target = " + target);
            }
            foundTrigger = true;
        }
        else if (arg.equals("getCityRegion"))
        {
            location here = getLocation(self);
            region[] r = getRegionsWithGeographicalAtPoint(here, regions.GEO_CITY);
            if ((r == null) || (r.length == 0))
            {
                sendSystemMessageTestingOnly(self, "No city regions located here!");
            }
            if (r.length > 1)
            {
                sendSystemMessageTestingOnly(self, "Multiple city regions located here!");
                for (int i = 0; i < r.length; i++)
                {
                    sendSystemMessageTestingOnly(self, "region[" + i + "] = " + r[i].getName());
                }
            }
            region city = r[0];
            sendSystemMessageTestingOnly(self, "Primary city region (region[0]) = " + city.getName());
            foundTrigger = true;
        }
        else if (arg.equals("addFaction"))
        {
            if (st.hasMoreTokens() && st.countTokens() == 2)
            {
                String faction = st.nextToken();
                String sVal = st.nextToken();
                int val = utils.stringToInt(sVal);
                if (val != -1)
                {
                    if (factions.addFactionStanding(target, faction, val))
                    {
                        sendSystemMessageTestingOnly(self, "Granted " + val + "pts of " + faction + " faction to target: " + target);
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "Unable to grant faction pts to target. target = " + target);
                        sendSystemMessageTestingOnly(self, "faction = " + faction + " val = " + val);
                    }
                }
            }
            foundTrigger = true;
        }
        else if (arg.equals("getStanding"))
        {
            if (st.hasMoreTokens() && st.countTokens() == 1)
            {
                String faction = st.nextToken();
                float val = factions.getFactionStanding(self, faction);
                sendSystemMessageTestingOnly(self, faction + " faction standing for " + target + " = " + val);
            }
            foundTrigger = true;
        }
        else if (arg.equals("clearAttribMods"))
        {
            removeAllAttribModifiers(target);
            sendSystemMessageTestingOnly(self, "Attrib mods cleared for target = " + target);
            foundTrigger = true;
        }
        else if (arg.equals("getAttrib"))
        {
            if (st.hasMoreTokens())
            {
                int attrib = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "target (" + target + ") attrib (" + attrib + ") value = " + getAttrib(target, attrib));
                sendSystemMessageTestingOnly(self, "max = " + getMaxAttrib(target, attrib) + " wounded max = " + getWoundedMaxAttrib(target, attrib));
                sendSystemMessageTestingOnly(self, "unmod max = " + getUnmodifiedMaxAttrib(target, attrib));
            }
            foundTrigger = true;
        }
        else if (arg.equals("getName"))
        {
            if (st.hasMoreTokens())
            {
                target = utils.stringToObjId(st.nextToken());
                if (!isIdValid(target))
                {
                    target = self;
                }
            }
            sendSystemMessageTestingOnly(self, "(" + target + ") target name = " + getName(target));
            foundTrigger = true;
        }
        else if (arg.equals("setName"))
        {
            if (st.hasMoreTokens())
            {
                int numTokens = st.countTokens();
                String name = null;
                switch (numTokens)
                {
                    case 2:
                    target = utils.stringToObjId(st.nextToken());
                    break;
                }
                name = st.nextToken();
                if (isIdValid(target) && (name != null) && (!name.equals("")))
                {
                    sendSystemMessageTestingOnly(self, "renaming (" + target + ")" + getName(target) + " -> " + name);
                    setName(target, name);
                }
            }
            foundTrigger = true;
        }
        else if (arg.equals("getItemInSlot"))
        {
            if (st.hasMoreTokens())
            {
                String slot = st.nextToken();
                obj_id item = getObjectInSlot(target, slot);
                if ((item == null) || (item == obj_id.NULL_ID))
                {
                    sendSystemMessageTestingOnly(self, "(" + target + ") no object in slot(" + slot + ")");
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "(" + target + ") object in slot(" + slot + ") is (" + item + ")" + getName(item));
                }
                foundTrigger = true;
            }
        }
        else if (arg.startsWith("getCombatDiff"))
        {
            int diff = getLevel(target);
            sendSystemMessageTestingOnly(self, "Target (" + target + ") combat difficulty = " + diff);
            foundTrigger = true;
        }
        else if (arg.equals("getGOT"))
        {
            if (st.hasMoreTokens())
            {
                String sItem = st.nextToken();
                obj_id item = utils.stringToObjId(sItem);
                if (isIdValid(item))
                {
                    int gotType = getGameObjectType(item);
                    sendSystemMessageTestingOnly(self, "(" + item + ")" + getName(item) + " is of object type " + gotType);
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "unable to parse valid obj_id");
                }
                foundTrigger = true;
            }
        }
        else if (arg.equals("getObjectInSlot"))
        {
            if (st.hasMoreTokens())
            {
                String sTarget = "";
                String slotName = "";
                switch (st.countTokens())
                {
                    case 1:
                    slotName = st.nextToken();
                    break;
                    case 2:
                    sTarget = st.nextToken();
                    slotName = st.nextToken();
                    target = utils.stringToObjId(sTarget);
                    break;
                }
                if (isIdValid(target))
                {
                    obj_id itemInSlot = getObjectInSlot(target, slotName);
                    if (isIdValid(itemInSlot))
                    {
                        sendSystemMessageTestingOnly(self, "(" + target + ") object in slot " + slotName + " = (" + itemInSlot + ") " + getName(itemInSlot));
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "no valid item in slot " + slotName);
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "unable to parse valid obj_id");
                }
            }
            foundTrigger = true;
        }
        else if (arg.equals("powerup"))
        {
            if (st.hasMoreTokens())
            {
                int cnt = st.countTokens();
                if (cnt == 2)
                {
                    String sItem = st.nextToken();
                    String sPup = st.nextToken();
                    obj_id item = utils.stringToObjId(sItem);
                    obj_id pup = utils.stringToObjId(sPup);
                    if ((!isIdValid(item)) || (!isIdValid(pup)))
                    {
                        sendSystemMessageTestingOnly(self, "unable to parse valid obj_ids");
                    }
                    else 
                    {
                    }
                }
                foundTrigger = true;
            }
        }
        else if (arg.equals("setScriptVar"))
        {
            if (st.hasMoreTokens())
            {
                int cnt = st.countTokens();
                if (cnt == 2)
                {
                    String path = st.nextToken();
                    String val = st.nextToken();
                    if (utils.setScriptVar(target, path, val))
                    {
                        sendSystemMessageTestingOnly(self, "scriptvar " + path + " set to " + val + "!");
                    }
                }
                foundTrigger = true;
            }
        }
        else if (arg.equals("hasScriptVar"))
        {
            if (st.hasMoreTokens())
            {
                int cnt = st.countTokens();
                String path = st.nextToken();
                if (utils.hasScriptVar(target, path))
                {
                    sendSystemMessageTestingOnly(self, "hasScriptVar = TRUE");
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "hasScriptVar = FALSE");
                }
                foundTrigger = true;
            }
        }
        else if (arg.equals("removeScriptVar"))
        {
            if (st.hasMoreTokens())
            {
                int cnt = st.countTokens();
                String path = st.nextToken();
                utils.removeScriptVar(target, path);
                sendSystemMessageTestingOnly(self, "scriptvar " + path + " removed!");
                foundTrigger = true;
            }
        }
        else if (arg.equals("getScriptVar"))
        {
            if (st.hasMoreTokens())
            {
                int cnt = st.countTokens();
                String path = st.nextToken();
                String val = utils.getStringScriptVar(target, path);
                if (val == null)
                {
                    sendSystemMessageTestingOnly(self, "target does not have scriptvar " + path);
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "scriptvar(" + path + ") = " + val);
                }
                foundTrigger = true;
            }
        }
        else if (arg.equals("showScriptVars"))
        {
            debugConsoleMsg(self, "*****************************");
            if (st.hasMoreTokens())
            {
                String soid = st.nextToken();
                obj_id tmp = utils.stringToObjId(soid);
                debugSpeakMsg(self, "attempting to parse oid from " + soid);
                if (isIdValid(tmp))
                {
                    target = tmp;
                }
                else 
                {
                    debugSpeakMsg(self, "UNABLE to parse oid from " + soid);
                }
            }
            debugConsoleMsg(self, "showScriptVars: target = " + target);
            deltadictionary delta = target.getScriptVars();
            if (delta == null)
            {
                debugConsoleMsg(self, "NONE!");
            }
            else 
            {
                debugConsoleMsg(self, "dd = " + delta.toString());
            }
            sendSystemMessageTestingOnly(self, "Check console for showScriptVars output");
            foundTrigger = true;
        }
        else if (arg.equals("clearScriptVars"))
        {
            deltadictionary delta = target.getScriptVars();
            java.util.Enumeration keys = delta.keys();
            while (keys.hasMoreElements())
            {
                delta.remove(keys.nextElement());
            }
            foundTrigger = true;
        }
        else if (arg.equals("empty"))
        {
            if (st.hasMoreTokens())
            {
                obj_id container = utils.getInventoryContainer(target);
                String param = st.nextToken();
                if (param.startsWith("inv"))
                {
                    container = utils.getInventoryContainer(self);
                }
                else if (param.equals("datapad"))
                {
                    container = utils.getPlayerDatapad(self);
                }
                else 
                {
                    return SCRIPT_CONTINUE;
                }
                obj_id[] items = getContents(container);
                if ((items == null) || (items.length == 0))
                {
                }
                else 
                {
                    for (obj_id item : items) {
                        destroyObject(item);
                    }
                }
                foundTrigger = true;
            }
        }
        else if (arg.equals("pay"))
        {
            if (st.hasMoreTokens())
            {
                String param = st.nextToken();
                if (st.hasMoreTokens())
                {
                    String sAmt = st.nextToken();
                    int amt = utils.stringToInt(sAmt);
                    if (amt < 1)
                    {
                        sendSystemMessageTestingOnly(self, "[debugger] SYNTAX: pay <acct name>|<obj_id> <amt>");
                        return SCRIPT_CONTINUE;
                    }
                    dictionary d = new dictionary();
                    obj_id payTarget = utils.stringToObjId(param);
                    if ((payTarget == null) || (payTarget == obj_id.NULL_ID))
                    {
                        money.pay(self, param, amt, "foo", d, true);
                    }
                    else 
                    {
                        money.pay(self, payTarget, amt, "foo", d, true);
                    }
                }
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "[debugger] SYNTAX: pay <acct name>|<obj_id> <amt>");
                return SCRIPT_CONTINUE;
            }
            foundTrigger = true;
        }
        else if (arg.equals("lightsaber"))
        {
            createObject("object/weapon/melee/sword/sword_lightsaber_luke.iff", utils.getInventoryContainer(target), "");
            sendSystemMessageTestingOnly(target, "check inv for new lightsaber");
            foundTrigger = true;
        }
        else if (arg.equals("lightsaber2h"))
        {
            createObject("object/weapon/melee/sword/sword_lightsaber_luke_2h_test.iff", utils.getInventoryContainer(target), "");
            sendSystemMessageTestingOnly(target, "check inv for new lightsaber");
            foundTrigger = true;
        }
        else if (arg.equals("spawn"))
        {
            if (st.hasMoreTokens())
            {
                String type = st.nextToken();
                create.object(type, getLocation(self));
                foundTrigger = true;
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "[debugger] SYNTAX: spawn <mob type>");
                return SCRIPT_OVERRIDE;
            }
        }
        else if (arg.equals("dataItem"))
        {
            target = self;
            String tpf = "data_item.iff";
            if (st.hasMoreTokens())
            {
                String param = st.nextToken();
                if (param.equals("rebel"))
                {
                    tpf = "data_rebel.iff";
                }
                else if (param.equals("imperial"))
                {
                    tpf = "data_imperial.iff";
                }
                else 
                {
                }
            }
            obj_id di = createObject("object/intangible/data_item/" + tpf, utils.getPlayerDatapad(target), "");
            sendSystemMessageTestingOnly(target, "check datapad for new data item(" + di + ") " + tpf);
            foundTrigger = true;
        }
        else if (arg.equals("makePet"))
        {
            if (!isMob(target))
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                pet_lib.makePet(target, self);
            }
            foundTrigger = true;
        }
        else if (arg.equals("message"))
        {
            if (st.hasMoreTokens())
            {
                String handler = st.nextToken();
                sendSystemMessageTestingOnly(self, "messaging " + target + " with handler " + handler);
                messageTo(target, handler, null, 0, false);
            }
            foundTrigger = true;
        }
        else if (arg.equals("setYaw"))
        {
            if (st.hasMoreTokens())
            {
                if (st.countTokens() == 2)
                {
                    target = utils.stringToObjId(st.nextToken());
                }
                if ((target == null) || (target == obj_id.NULL_ID) || (target == self))
                {
                    sendSystemMessageTestingOnly(self, "syntax: setYaw <target id> <yaw>");
                }
                else 
                {
                    String sYaw = st.nextToken();
                    float yaw = utils.stringToFloat(sYaw);
                    if (yaw == Float.NEGATIVE_INFINITY)
                    {
                        yaw = 0;
                    }
                    sendSystemMessageTestingOnly(self, "(" + target + ")" + getName(target) + " rotating to " + yaw);
                    setYaw(target, yaw);
                }
            }
            foundTrigger = true;
        }
        else if (arg.equals("getYaw"))
        {
            if (st.hasMoreTokens())
            {
                target = utils.stringToObjId(st.nextToken());
            }
            if ((target == null) || (target == obj_id.NULL_ID) || (target == self))
            {
                sendSystemMessageTestingOnly(self, "syntax: getYaw (<target id>)");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "(" + target + ")" + getName(target) + " yaw = " + getYaw(target));
            }
            foundTrigger = true;
        }
        else if (arg.equals("spin"))
        {
            float yaw = rand(-180, 180);
            sendSystemMessageTestingOnly(self, "(" + target + ")" + getName(target) + " rotating to " + yaw);
            setYaw(target, yaw);
            foundTrigger = true;
        }
        else if (arg.equals("pathTo"))
        {
            if (isMob(target) && !isPlayer(target))
            {
                location myLoc = getLocation(self);
                sendSystemMessageTestingOnly(self, "pathing (" + target + ")" + getName(target) + " to " + myLoc.toString());
                pathTo(target, myLoc);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "pathTo trigger may only be used on AI-driven mobs");
            }
            foundTrigger = true;
        }
        else if (arg.equals("pathFrom"))
        {
            if (isMob(target) && !isPlayer(target))
            {
                location myLoc = getLocation(self);
                sendSystemMessageTestingOnly(self, "pathing (" + target + ")" + getName(target) + " away from self: " + myLoc.toString());
                ai_lib.pathAwayFrom(target, self);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "pathFrom trigger may only be used on AI-driven mobs");
            }
            foundTrigger = true;
        }
        else if (arg.equals("getPosture"))
        {
            debugSpeakMsg(self, "(" + target + ") " + getName(target) + "'s posture = " + getPosture(target));
            foundTrigger = true;
        }
        else if (arg.equals("setPosture"))
        {
            if (st.hasMoreTokens())
            {
                if (st.countTokens() == 2)
                {
                    obj_id tmp = utils.stringToObjId(st.nextToken());
                    if (isIdValid(tmp))
                    {
                        target = tmp;
                    }
                }
                int posture = utils.stringToInt(st.nextToken());
                if (posture < 0)
                {
                    debugSpeakMsg(self, "int param must be >= 0");
                }
                else 
                {
                    setPosture(target, posture);
                }
            }
            else 
            {
                debugSpeakMsg(self, "syntax: setPosture <posture(int)>");
            }
            debugSpeakMsg(self, "(" + target + ") " + getName(target) + "'s posture = " + getPosture(target));
            foundTrigger = true;
        }
        else if (arg.equals("fullHeal"))
        {
            healShockWound(target, getShockWound(target));
            setAttrib(target, HEALTH, getUnmodifiedMaxAttrib(target, HEALTH));
            foundTrigger = true;
        }
        else if (arg.equals("resetStomachs"))
        {
            player_stomach.resetStomachs(target);
            debugSpeakMsg(target, "stomaches reset!");
            foundTrigger = true;
        }
        else if (arg.equals("incapacitateMob"))
        {
            debug.incapacitateMob(target);
            foundTrigger = true;
        }
        else if (arg.equals("deathBlow") || (arg.equals("coup de grace")))
        {
            pclib.coupDeGrace(target, self);
            foundTrigger = true;
        }
        else if (arg.equals("killCreature"))
        {
            debug.killCreature(target);
            foundTrigger = true;
        }
        else if (arg.equals("forceSuicide"))
        {
            if (target != self)
            {
                debug.forceSuicide(target);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "forceSuicide requires a non-self target!");
            }
            foundTrigger = true;
        }
        else if (arg.equals("suicide"))
        {
            debug.suicide(self);
            foundTrigger = true;
        }
        else if (arg.equals("KILL"))
        {
            if (isMob(target))
            {
                kill(target);
            }
            foundTrigger = true;
        }
        else if (arg.equals("nuke"))
        {
            if (!isPlayer(target))
            {
                destroyObject(target);
            }
            foundTrigger = true;
        }
        else if (arg.equals("woundMob"))
        {
            foundTrigger = true;
        }
        else if (arg.equals("assignSkillTemplate"))
        {
            String skill_name = st.nextToken();
            skill.assignSkillTemplate(target, skill_name);
            foundTrigger = true;
        }
        else if (arg.equals("clearSkills"))
        {
            String[] skill_names = getSkillListingForPlayer(target);
            if ((skill_names == null) || (skill_names.length == 0))
            {
            }
            else 
            {
                for (String skill_name : skill_names) {
                    revokeSkill(target, skill_name);
                }
            }
            foundTrigger = true;
        }
        else if (arg.equals("getAllRequiredSkills"))
        {
            String skill_name = st.nextToken();
            String[] skills = skill.getAllRequiredSkills(skill_name);
            if ((skills == null) || (skills.length == 0))
            {
                sendSystemMessageTestingOnly(self, "error getting required skills for : " + skill_name);
                foundTrigger = true;
            }
            else 
            {
                debugConsoleMsg(self, "SKILLS REQUIRED FOR: " + skill_name);
                debugConsoleMsg(self, "****************************************");
                for (String s : skills) {
                    debugConsoleMsg(self, " * " + s);
                }
            }
            sendSystemMessageTestingOnly(self, "Check console for output...");
            foundTrigger = true;
        }
        else if (arg.equals("grantSkill"))
        {
            String skill_name = st.nextToken();
            if (grantSkill(self, skill_name))
            {
                sendSystemMessageTestingOnly(self, "grantSkill::skill granted: " + skill_name);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "grantSkill::unable to grant skill: " + skill_name);
                return SCRIPT_OVERRIDE;
            }
            foundTrigger = true;
        }
        else if (arg.equals("revokeSkill"))
        {
            String skill_name = st.nextToken();
            revokeSkill(self, skill_name);
            sendSystemMessageTestingOnly(self, "revokeSkill::skill revoked: " + skill_name);
            foundTrigger = true;
        }
        else if (arg.equals("grantSkillToPlayer"))
        {
            String skill_name = st.nextToken();
            if (skill.grantSkillToPlayer(self, skill_name))
            {
                sendSystemMessageTestingOnly(self, "grantSkillToPlayer::skill granted: " + skill_name);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "grantSkillToPlayer::unable to grant skill: " + skill_name);
                return SCRIPT_OVERRIDE;
            }
            foundTrigger = true;
        }
        else if (arg.equals("purchaseSkill"))
        {
            String skill_name = st.nextToken();
            dictionary xpReqs = getSkillPrerequisiteExperience(skill_name);
            java.util.Enumeration e = xpReqs.keys();
            while (e.hasMoreElements())
            {
                String xpType = (String)(e.nextElement());
                int xpCost = xpReqs.getInt(xpType);
                grantExperiencePoints(self, xpType, xpCost);
            }
            skill.purchaseSkill(self, skill_name);
            foundTrigger = true;
        }
        else if (arg.equals("grantAllSkills"))
        {
            String[] skillList = dataTableGetStringColumn(skill.TBL_SKILL, "NAME");
            if (skillList != null && skillList.length > 0)
            {
                dictionary d = new dictionary();
                d.put("skills", skillList);
                d.put("idx", 0);
                messageTo(self, "handleGrantAllSkills", d, 3.0f, false);
            }
            foundTrigger = true;
        }
        else if (arg.equals("grantBadge"))
        {
            String badgeName = st.nextToken();
            badge.grantBadge(target, badgeName);
            foundTrigger = true;
        }
        else if (arg.equals("revokeBadge"))
        {
            String badgeName = st.nextToken();
            badge.revokeBadge(target, badgeName, true);
            foundTrigger = true;
        }
        else if (arg.equals("getContents"))
        {
            if (st.hasMoreTokens())
            {
                obj_id tmp = utils.stringToObjId(st.nextToken());
                if (isIdValid(tmp))
                {
                    target = tmp;
                }
            }
            obj_id[] contents = utils.getContents(target, true);
            if ((contents == null) || (contents.length == 0))
            {
                sendSystemMessageTestingOnly(self, "Unable to retrieve contents of object #" + target);
            }
            else 
            {
                String[] names = utils.makeNameList(contents);
                String[] src = new String[contents.length];
                for (int i = 0; i < contents.length; i++)
                {
                    src[i] = "(" + contents[i] + ")" + names[i];
                }
                sui.listbox(self, "Inventory Contents for (" + target + ")" + getName(target), src);
            }
            foundTrigger = true;
        }
        else if (arg.equals("getInventoryContents"))
        {
            obj_id inv = utils.getInventoryContainer(target);
            if ((inv == null) || (inv == obj_id.NULL_ID))
            {
                foundTrigger = true;
            }
            else 
            {
                obj_id[] contents = utils.getContents(inv, true);
                String[] names = utils.makeNameList(contents);
                String[] src = new String[contents.length];
                for (int i = 0; i < contents.length; i++)
                {
                    src[i] = "(" + contents[i] + ")" + names[i];
                }
                sui.listbox(self, "Inventory Contents for (" + target + ")" + getName(target), src);
            }
            foundTrigger = true;
        }
        else if (arg.equals("follow"))
        {
            if (isMob(target))
            {
                ai_lib.aiFollow(target, self);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Follow commands are only usable on non-pc mobs!");
            }
            foundTrigger = true;
        }
        else if (arg.equals("stopFollow"))
        {
            if (isMob(target))
            {
                ai_lib.aiStopFollowing(target);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Follow commands are only usable on non-pc mobs!");
            }
            foundTrigger = true;
        }
        else if (arg.equals("setBehavior"))
        {
            if (st.hasMoreTokens())
            {
                int behavior = -1;
                String sBehavior = st.nextToken();
                switch (sBehavior) {
                    case "sentinel":
                        behavior = ai_lib.BEHAVIOR_SENTINEL;
                        break;
                    case "wander":
                        behavior = ai_lib.BEHAVIOR_WANDER;
                        break;
                    case "loiter":
                        behavior = ai_lib.BEHAVIOR_LOITER;
                        break;
                    default:
                        behavior = utils.stringToInt(sBehavior);
                        break;
                }
                if (behavior > -1)
                {
                    if (isMob(target) && !isPlayer(target))
                    {
                        ai_lib.setDefaultCalmBehavior(target, behavior);
                        sendSystemMessageTestingOnly(self, getName(target) + "'s behavior set to " + behavior);
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "set behavior commands are only usable on non-pc mobs!");
                    }
                    foundTrigger = true;
                }
            }
            sendSystemMessageTestingOnly(self, "SYNTAX: setBehavior <int behavior>");
            foundTrigger = true;
        }
        else if (arg.equals("mood"))
        {
            if (st.hasMoreTokens())
            {
                String mood = st.nextToken();
                if (isMob(target))
                {
                    if (!isPlayer(target))
                    {
                        ai_lib.setMood(target, mood);
                        sendSystemMessageTestingOnly(self, "Attempting to set " + getName(target) + "'s mood to " + mood);
                    }
                    else 
                    {
                        if (queueCommand(target, (1000440469), null, mood, COMMAND_PRIORITY_DEFAULT))
                        {
                            sendSystemMessageTestingOnly(self, getName(target) + "'s mood set to " + mood);
                        }
                        else 
                        {
                            sendSystemMessageTestingOnly(self, "Unable to set " + getName(target) + "'s mood to " + mood);
                        }
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "moods commands are only valid for creature objects!");
                }
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "SYNTAX: mood <mood name>");
            }
            foundTrigger = true;
        }
        else if (arg.equals("speak"))
        {
            if (!isMob(target))
            {
                sendSystemMessageTestingOnly(self, "speak is only usable on mobs!");
                foundTrigger = true;
            }
            String chat = "none";
            String mood = "none";
            String msg = "";
            while (st.hasMoreTokens())
            {
                String tok = st.nextToken();
                if (tok.startsWith("+"))
                {
                    chat = tok.substring(1);
                }
                else if (tok.startsWith("-"))
                {
                    mood = tok.substring(1);
                }
                else 
                {
                    msg += tok + " ";
                }
            }
            if (msg.length() == 0)
            {
                msg = "BLAH";
            }
            else if (msg.length() > 1)
            {
                msg = msg.substring(0, msg.length() - 1);
            }
            msg = chat + " " + mood + " 0" + msg;
            queueCommand(target, (-296481545), null, msg, COMMAND_PRIORITY_DEFAULT);
            foundTrigger = true;
        }
        else if (arg.equals("emote"))
        {
            if (st.hasMoreTokens())
            {
                String social = st.nextToken();
                if (social.equals("target"))
                {
                    if (st.hasMoreTokens())
                    {
                        String sTarget = st.nextToken();
                        obj_id tId = utils.stringToObjId(sTarget);
                        if ((tId != null) && (tId != obj_id.NULL_ID))
                        {
                            setObjVar(self, DEBUG_EMOTE_TARGET, tId);
                            foundTrigger = true;
                        }
                    }
                    removeObjVar(self, DEBUG_EMOTE_TARGET);
                    foundTrigger = true;
                }
                String param = "";
                if (st.hasMoreTokens())
                {
                    param = st.nextToken();
                }
                if (isMob(target))
                {
                    switch (param) {
                        case "self":
                            queueCommand(target, (1780871594), target, social, COMMAND_PRIORITY_DEFAULT);
                            break;
                        case "me":
                            queueCommand(target, (1780871594), self, social, COMMAND_PRIORITY_DEFAULT);
                            break;
                        case "other":
                            obj_id tId = getObjIdObjVar(self, DEBUG_EMOTE_TARGET);
                            if ((tId != null) && (tId != obj_id.NULL_ID) && (exists(tId)) && (isInWorld(tId)) && (tId.isLoaded())) {
                                queueCommand(target, (1780871594), tId, social, COMMAND_PRIORITY_DEFAULT);
                            } else {
                                sendSystemMessageTestingOnly(self, "invalid DEBUG_EMOTE_TARGET! set it using 'emote target <oid>'");
                            }
                            break;
                        default:
                            queueCommand(target, (1780871594), null, social, COMMAND_PRIORITY_DEFAULT);
                            break;
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "emote commands are only valid for creature objects!");
                }
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "SYNTAX: emote <mood name>");
            }
            foundTrigger = true;
        }
        else if (arg.equals("xp"))
        {
            if (!isPlayer(target))
            {
                sendSystemMessageTestingOnly(self, "debugger::xp: target must be a player!");
                foundTrigger = true;
            }
            if (st.hasMoreTokens())
            {
                String xp_type = st.nextToken();
                int xp_amt = getExperiencePoints(target, xp_type);
                if (st.hasMoreTokens())
                {
                    String sAmt = st.nextToken();
                    int amt = 0;
                    if (sAmt.startsWith("+") || sAmt.startsWith("-"))
                    {
                        amt = utils.stringToInt(sAmt.substring(1));
                        if (amt == -1)
                        {
                            sendSystemMessageTestingOnly(self, "debugger::xp: invalid xp delta parameter...");
                            foundTrigger = true;
                        }
                        if (sAmt.startsWith("-"))
                        {
                            amt = -amt;
                        }
                    }
                    else 
                    {
                        amt = utils.stringToInt(sAmt);
                        if (amt == -1)
                        {
                            sendSystemMessageTestingOnly(self, "debugger::xp: invalid xp amount...");
                            foundTrigger = true;
                        }
                        amt = amt - xp_amt;
                    }
                    if (amt != 0)
                    {
                        xp.grant(target, xp_type, amt);
                    }
                }
                else 
                {
                    String msg = "(" + target + ") " + getName(target) + " has " + xp_amt + "pts of " + xp_type + " experience";
                    sendSystemMessageTestingOnly(self, msg);
                }
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "SYNTAX: xp <type> (+|-)<amt>");
            }
            foundTrigger = true;
        }
        else if (arg.equals("repelValue"))
        {
            int val = camping.getCampRepelValue(target);
            sendSystemMessageTestingOnly(self, "camp repel value = " + val);
            foundTrigger = true;
        }
        else if (arg.equals("fillInv"))
        {
            obj_id inv = utils.getInventoryContainer(target);
            if ((inv == null) || (inv == obj_id.NULL_ID))
            {
            }
            else 
            {
                while (getVolumeFree(inv) > 0)
                {
                    createObject("object/tangible/test/inventory_filler.iff", inv, "");
                }
            }
            foundTrigger = true;
        }
        else if (arg.equals("hasTriggerVolume"))
        {
            if (st.hasMoreTokens())
            {
                String vol_name = st.nextToken();
                if (hasTriggerVolume(target, vol_name))
                {
                    sendSystemMessageTestingOnly(self, "(" + target + ") " + getName(target) + " has a trigger volume named " + vol_name);
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "(" + target + ") " + getName(target) + " does NOT have a trigger volume named " + vol_name);
                }
            }
            foundTrigger = true;
        }
        else if (arg.equals("getDistance2D"))
        {
            if (st.hasMoreTokens())
            {
                location here = getLocation(target);
                int cnt = st.countTokens();
                obj_id other = utils.stringToObjId(st.nextToken());
                if ((other == null) || (other == obj_id.NULL_ID))
                {
                }
                else 
                {
                    location there = getLocation(other);
                    float dist = 0.0f;
                    switch (cnt)
                    {
                        case 1:
                        dist = utils.getDistance2D(here, there);
                        sendSystemMessageTestingOnly(self, "distance between " + target + " and " + other + " = " + dist);
                        break;
                        case 2:
                        target = utils.stringToObjId(st.nextToken());
                        if ((target == null) || (target == obj_id.NULL_ID))
                        {
                        }
                        else 
                        {
                            here = getLocation(target);
                            dist = utils.getDistance2D(here, there);
                            sendSystemMessageTestingOnly(self, "distance between " + target + " and " + other + " = " + dist);
                        }
                    }
                }
            }
            else 
            {
                float range = utils.getDistance2D(getLocation(self), getLocation(target));
                sendSystemMessageTestingOnly(self, "distance between " + target + " and self = " + range);
            }
            foundTrigger = true;
        }
        else if (arg.equals("getLocation"))
        {
            if (st.hasMoreTokens())
            {
                obj_id tmp = utils.stringToObjId(st.nextToken());
                if (isIdValid(tmp))
                {
                    target = tmp;
                }
            }
            location here = getLocation(target);
            sendSystemMessageTestingOnly(self, "target = " + target + " loc = " + here.toString());
            foundTrigger = true;
        }
        else if (arg.equals("color") || arg.equals("hue"))
        {
            boolean ss = false;
            if (st.hasMoreTokens())
            {
                int col = utils.stringToInt(st.nextToken());
                if (col > -1)
                {
                    hueClothes(target, col);
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "[beta.debugger] hue passed invalid color -> randomizing");
                    hue.hueObject(target);
                }
            }
            else 
            {
                ss = true;
            }
            if (ss)
            {
                sendSystemMessageTestingOnly(self, "[beta.debugger] syntax: hue <int index>");
            }
            foundTrigger = true;
        }
        
        {
        }
        if (foundTrigger)
        {
            sendSystemMessageTestingOnly(self, "[beta.debugger] " + text);
            return SCRIPT_OVERRIDE;
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
    }
    public void hueClothes(obj_id newClothes, int col) throws InterruptedException
    {
        custom_var[] allVars = getAllCustomVars(newClothes);
        for (custom_var cv : allVars) {
            ranged_int_custom_var ri = (ranged_int_custom_var) cv;
            if (cv != null) {
                if (cv.isPalColor()) {
                    ri.setValue(col);
                }
            }
        }
        return;
    }
    public int handleSignedStructureCreation(obj_id self, dictionary params) throws InterruptedException
    {
        String[] tmp = params.getStringArray("template");
        Vector tpf = new Vector(Arrays.asList(tmp));
        String template = (String)(tpf.elementAt(0));
        location there = params.getLocation("loc");
        int cnt = params.getInt("cnt");
        obj_id it = createObject(template, there);
        if (!isIdValid(it))
        {
            sendSystemMessageTestingOnly(self, "unable create structure from template: " + template);
        }
        else 
        {
            obj_id sign = player_structure.createStructureSign(it);
            if (!isIdValid(sign))
            {
                sendSystemMessageTestingOnly(self, "unable create structure sign for template: " + template);
            }
        }
        tpf = utils.removeElementAt(tpf, 0);
        if (tpf != null && tpf.size() == 0)
        {
            params.put("template", tpf);
            if (cnt / 5 > 0)
            {
                there.x = there.x + 300.0f;
                there.z = there.z + 60.0f;
            }
            else 
            {
                there.x = there.x - 60.0f;
                params.put("loc", there);
            }
            cnt++;
            params.put("cnt", cnt);
            messageTo(self, "handleSignedStructureCreation", params, 3, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleGrantAllSkills(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "handleGrantAllSkills entered...");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        String[] skillList = params.getStringArray("skills");
        int idx = params.getInt("idx");
        for (int i = idx; i < 5; i++)
        {
            if (i < skillList.length)
            {
                sendSystemMessageTestingOnly(self, "attempting to grant: " + skillList[i]);
                grantSkill(self, skillList[i]);
            }
            else 
            {
                return SCRIPT_CONTINUE;
            }
        }
        params.put("idx", idx + 5);
        messageTo(self, "handleGrantAllSkills", params, 3.0f, false);
        return SCRIPT_CONTINUE;
    }
}
