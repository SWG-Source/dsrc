package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class list extends script.base_script
{
    public list()
    {
    }
    public static obj_var[] sortToArray(obj_var_list ovl) throws InterruptedException
    {
        obj_var[] listArray = listToArray(ovl);
        int left = 0;
        int right = listArray.length - 1;
        obj_var[] sortedArray = quickSort(left, right, listArray);
        return sortedArray;
    }
    public static obj_var[] quickSort(int left, int right, obj_var[] listArray) throws InterruptedException
    {
        if (right - left <= 0)
        {
            return listArray;
        }
        else 
        {
            Object pivot = ((listArray[right])).getData();
            int partition = 0;
            boolean validInstance = false;
            if (pivot instanceof Integer)
            {
                partition = partitionIntArray(left, right, (((Integer)pivot)).intValue(), listArray);
                validInstance = true;
            }
            else if (pivot instanceof Float)
            {
                partition = partitionFloatArray(left, right, (((Float)pivot)).floatValue(), listArray);
                validInstance = true;
            }
            if (validInstance)
            {
                quickSort(left, partition - 1, listArray);
                quickSort(partition + 1, right, listArray);
            }
        }
        return listArray;
    }
    public static int partitionIntArray(int left, int right, int pivot, obj_var[] listArray) throws InterruptedException
    {
        int leftPtr = left - 1;
        int rightPtr = right;
        while (true)
        {
            while (((listArray[++leftPtr])).getIntData() < pivot);
            while (rightPtr > 0 && ((listArray[--rightPtr])).getIntData() > pivot);
            if (leftPtr >= rightPtr)
            {
                break;
            }
            else 
            {
                swap(leftPtr, rightPtr, listArray);
            }
        }
        swap(leftPtr, right, listArray);
        return leftPtr;
    }
    public static int partitionFloatArray(int left, int right, float pivot, obj_var[] listArray) throws InterruptedException
    {
        int leftPtr = left - 1;
        int rightPtr = right;
        while (true)
        {
            while (((listArray[++leftPtr])).getFloatData() < pivot);
            while (rightPtr > 0 && ((listArray[--rightPtr])).getFloatData() > pivot);
            if (leftPtr >= rightPtr)
            {
                break;
            }
            else 
            {
                swap(leftPtr, rightPtr, listArray);
            }
        }
        swap(leftPtr, right, listArray);
        return leftPtr;
    }
    public static void swap(int dex1, int dex2, obj_var[] listArray) throws InterruptedException
    {
        obj_var temp = listArray[dex1];
        listArray[dex1] = listArray[dex2];
        listArray[dex2] = temp;
    }
    public static obj_var[] listToArray(obj_var_list ovl) throws InterruptedException
    {
        int cnt = ovl.getNumItems();
        obj_var[] array = new obj_var[cnt];
        for (int i = 0; i < cnt; ++i)
        {
            array[i] = ovl.getObjVar(i);
        }
        return array;
    }
    public static obj_var_list[] listToListArray(obj_var_list ovl) throws InterruptedException
    {
        int cnt = ovl.getNumItems();
        obj_var_list[] array = new obj_var_list[cnt];
        for (int i = 0; i < cnt; ++i)
        {
            obj_var ov = ovl.getObjVar(i);
            if (!(ov instanceof obj_var_list))
            {
                return null;
            }
            array[i] = (obj_var_list)ov;
        }
        return array;
    }
}
