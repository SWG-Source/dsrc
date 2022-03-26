package script.systems.buff;

import script.*;
import script.library.*;

import java.util.ArrayList;

public class buff_builder_effect_map extends script.base_script
{
    public static final String DATATABLE_BUFF_BUILDER_EFFECT_MAP = "datatables/buff/buff_builder_effect_map.iff";

    public buff_builder_effect_map()
    {
    }

  
    public static String[] getEffectList(String affectName) throws InterruptedException
    {
        //get the effect from the list

        ArrayList<String> effectList = new ArrayList<String>();


        int rowIndex = dataTableSearchColumnForString(affectName, "BUFF_BUILDER_AFFECTS", DATATABLE_BUFF_BUILDER_EFFECT_MAP);
    
        if (rowIndex >= 0)
        {
            int rowCount = dataTableGetNumRows(DATATABLE_BUFF_BUILDER_EFFECT_MAP);
            dictionary row;
            String affect;

            for (int index = 0; index < rowCount; index++)
            {
                row = dataTableGetRow(DATATABLE_BUFF_BUILDER_EFFECT_MAP, index);
                affect = row.getString("BUFF_BUILDER_AFFECTS");

                if (affect.equals(affectName))
                {
                    effectList.add(row.getString("AFFECT"));
                }
            }
        }

        return effectList.toArray(new String[effectList.size()]);
    }

    public static String[] getEffects() throws InterruptedException
    {
        return dataTableGetStringColumn(DATATABLE_BUFF_BUILDER_EFFECT_MAP, "AFFECT");
    }    




}
