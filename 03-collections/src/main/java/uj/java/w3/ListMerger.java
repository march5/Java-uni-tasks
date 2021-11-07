package uj.java.w3;

import java.util.ArrayList;
import java.util.List;

public class ListMerger {
    public static List<Object> mergeLists(List<?> l1, List<?> l2) {

        List<Object> list = new ArrayList<Object>();

        if( l1 != null && l2 != null){
            int k = 0, l = 0;

            while(k < l1.size() && l < l2.size()){
                list.add(l1.get(k++));
                list.add(l2.get(l++));
            }

            while(k < l1.size()){
                list.add(l1.get(k++));
            }

            while(l < l2.size()){
                list.add(l2.get(l++));
            }
        }

        if(l1 == null && l2 != null){
            for(int i = 0; i < l2.size(); i ++) list.add(l2.get(i));
        }

        if(l1 != null && l2 == null){
            for(int i = 0; i < l2.size(); i ++) list.add(l1.get(i));
        }

        final List<Object> list2 = List.of(list.toArray());
        return list2;
    }

}
