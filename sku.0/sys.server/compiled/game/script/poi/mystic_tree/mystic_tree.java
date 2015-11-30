package script.poi.mystic_tree;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.groundquests;

public class mystic_tree extends script.base_script
{
    public mystic_tree()
    {
    }
    public static final string_id HEAL_TREE = new string_id("ep3/mystic_tree", "heal_tree");
    public static final string_id TRANSFORM = new string_id("ep3/mystic_tree", "transform");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, HEAL_TREE);
        menu_info_data menuInfoData = mi.getMenuItemById(menu);
        if (menuInfoData != null)
        {
            menuInfoData.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id hasMedicine = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/quest/ep3_forest_cure.iff");
            String template = getTemplateName(self);
            if (template.equals("object/tangible/item/ep3/poi_kash_mystic_tree_sickly.iff"))
            {
                if (isIdValid(hasMedicine))
                {
                    sendSystemMessage(player, TRANSFORM);
                    location selfLoc = getLocation(self);
                    playClientEffectLoc(player, "clienteffect/healing_tree.cef", selfLoc, 0.f);
                    destroyObject(hasMedicine);
                    messageTo(self, "spawnHealthyTree", null, 1.5f, true);
                    utils.setScriptVar(self, "playerId", player);
                }
                else 
                {
                    sendSystemMessage(player, new string_id("ep3/mystic_tree", "cannot_heal"));
                }
            }
            else 
            {
                sendSystemMessage(player, new string_id("ep3/mystic_tree", "not_sickly"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int respawnSickTree(obj_id self, dictionary params) throws InterruptedException
    {
        location selfLoc = getLocation(self);
        obj_id newTree = createObject("object/tangible/item/ep3/poi_kash_mystic_tree_sickly.iff", selfLoc);
        setYaw(newTree, 140);
        attachScript(newTree, "poi.mystic_tree.mystic_tree");
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int spawnHealthyTree(obj_id self, dictionary params) throws InterruptedException
    {
        location selfLoc = getLocation(self);
        obj_id newTree = createObject("object/tangible/item/ep3/poi_kash_mystic_tree_healthy.iff", selfLoc);
        setYaw(newTree, 140);
        attachScript(newTree, "poi.mystic_tree.mystic_tree");
        obj_id player = utils.getObjIdScriptVar(self, "playerId");
        groundquests.sendSignal(player, "curetree");
        messageTo(newTree, "respawnSickTree", null, 60, true);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
