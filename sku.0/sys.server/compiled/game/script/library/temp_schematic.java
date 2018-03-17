package script.library;

import script.obj_id;
import script.prose_package;
import script.string_id;

public class temp_schematic extends script.base_script
{
    public temp_schematic()
    {
    }
    public static final int DEFAULT_USE_COUNT = 1;
    public static final String VAR_TEMP_SCHEMATIC_BASE = "temp_schematic";
    public static final String VAR_TEMP_SCHEMATIC_BIO_LINK = "bioLinkSchematic.schematic";
    public static final String STF = "temp_schematic";
    public static final string_id PROSE_TEMP_GRANT = new string_id(STF, "prose_temp_grant");
    public static final string_id PROSE_USES_LEFT = new string_id(STF, "prose_uses_left");
    public static final string_id PROSE_TEMP_REVOKE = new string_id(STF, "prose_temp_revoke");
    public static final string_id PROSE_USES_DECREMENTED = new string_id(STF, "prose_uses_decremented");
    public static final string_id PROSE_SCHEMATIC_EXPIRED = new string_id(STF, "prose_schematic_expired");
    public static final String LIMITED_USE_DATA_TABLE = "datatables/crafting/limited_use_schematics.iff";
    public static final String COL_SCHEMATIC_NAME = "schematic_name";
    public static final String COL_SCHEMATIC_USES = "schematic_uses";
    public static boolean grant(obj_id player, String schematic, int useCount) throws InterruptedException
    {
        return grant(player, obj_id.NULL_ID, schematic, useCount);
    }
    public static boolean grant(obj_id player, obj_id bioLink, String schematic, int useCount) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player) || schematic == null || schematic.equals(""))
        {
            return false;
        }
        return grant(player, bioLink, getObjectTemplateCrc(schematic), useCount);
    }
    public static boolean grant(obj_id player, int schematic, int useCount) throws InterruptedException
    {
        return grant(player, obj_id.NULL_ID, schematic, useCount);
    }
    public static boolean grant(obj_id player, obj_id bioLink, int schematic, int useCount) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player) || schematic == 0)
        {
            return false;
        }
        String ovpath = VAR_TEMP_SCHEMATIC_BASE + "." + schematic;
        if (hasSchematic(player, schematic) && !hasObjVar(player, ovpath))
        {
            return false;
        }
        boolean litmus = true;
        if (!hasSchematic(player, schematic))
        {
            litmus = grantSchematic(player, schematic);
        }
        if (litmus)
        {
            int total = useCount + getIntObjVar(player, ovpath);
            setObjVar(player, ovpath, total);
            string_id product_name = getProductNameFromSchematic(schematic);
            sendSystemMessageProse(player, prose.getPackage(PROSE_TEMP_GRANT, product_name, useCount));
            sendSystemMessageProse(player, prose.getPackage(PROSE_USES_LEFT, product_name, total));
            if (isIdValid(bioLink))
            {
                setObjVar(player, VAR_TEMP_SCHEMATIC_BIO_LINK + "." + schematic, bioLink);
            }
            return true;
        }
        return false;
    }
    public static boolean grant(obj_id player, String schematic) throws InterruptedException
    {
        return grant(player, schematic, DEFAULT_USE_COUNT);
    }
    public static boolean grant(obj_id player, int schematic) throws InterruptedException
    {
        return grant(player, schematic, DEFAULT_USE_COUNT);
    }
    public static boolean revoke(obj_id player, String schematic) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player) || schematic == null || schematic.equals(""))
        {
            return false;
        }
        return revoke(player, getObjectTemplateCrc(schematic));
    }
    public static boolean revoke(obj_id player, int schematic) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player) || schematic == 0)
        {
            return false;
        }
        String ovpath = VAR_TEMP_SCHEMATIC_BASE + "." + schematic;
        if (!hasObjVar(player, ovpath))
        {
            return false;
        }
        if (revokeSchematic(player, schematic))
        {
            removeObjVar(player, ovpath);
            string_id product_name = getProductNameFromSchematic(schematic);
            prose_package ppTempRevoke = prose.getPackage(PROSE_TEMP_REVOKE, product_name);
            sendSystemMessageProse(player, ppTempRevoke);
            return true;
        }
        return false;
    }
    public static boolean decrement(obj_id player, String schematic) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player) || schematic == null || schematic.equals(""))
        {
            return false;
        }
        return decrement(player, getObjectTemplateCrc(schematic));
    }
    public static boolean decrement(obj_id player, int schematic) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player) || schematic == 0)
        {
            return false;
        }
        String ovpath = VAR_TEMP_SCHEMATIC_BASE + "." + schematic;
        int cnt = getIntObjVar(player, ovpath);
        boolean isLimitedUseSchematic = false;
        int limitedUse = dataTableSearchColumnForInt(schematic, COL_SCHEMATIC_NAME, LIMITED_USE_DATA_TABLE);
        if (limitedUse >= 0)
        {
            isLimitedUseSchematic = true;
        }
        if (cnt == 0 && !isLimitedUseSchematic)
        {
            return false;
        }
        string_id product_name = getProductNameFromSchematic(schematic);
        cnt--;
        setObjVar(player, ovpath, cnt);
        if (cnt <= 0)
        {
            prose_package ppExpire = prose.getPackage(PROSE_SCHEMATIC_EXPIRED, product_name);
            sendSystemMessageProse(player, ppExpire);
            if (hasObjVar(player, VAR_TEMP_SCHEMATIC_BIO_LINK + "." + schematic))
            {
                removeObjVar(player, VAR_TEMP_SCHEMATIC_BIO_LINK + "." + schematic);
            }
            return revoke(player, schematic);
        }
        else 
        {
            prose_package ppExpire = prose.getPackage(PROSE_USES_DECREMENTED, product_name);
            sendSystemMessageProse(player, ppExpire);
            prose_package ppUsesLeft = prose.getPackage(PROSE_USES_LEFT, product_name, cnt);
            sendSystemMessageProse(player, ppUsesLeft);
        }
        return true;
    }
    public static boolean decrement(obj_id player, obj_id manufacturingSchematic) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(manufacturingSchematic))
        {
            return false;
        }
        return decrement(player, getDraftSchematicCrc(manufacturingSchematic));
    }
    public static obj_id getBioLinkSchematicId(obj_id player, int schematic) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player) || !exists(player) || schematic == 0)
        {
            return obj_id.NULL_ID;
        }
        if (hasObjVar(player, VAR_TEMP_SCHEMATIC_BIO_LINK + "." + schematic))
        {
            return getObjIdObjVar(player, VAR_TEMP_SCHEMATIC_BIO_LINK + "." + schematic);
        }
        return obj_id.NULL_ID;
    }
}
