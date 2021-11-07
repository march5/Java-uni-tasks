package uj.java.w3;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface JsonMapper {

    String toJson(Map<String, ?> map);

    static JsonMapper defaultInstance() {
        return new JsonMapper() {

            @Override
            public String toJson(Map<String, ?> map) {
                String re = "";

                re += transformVal(map);
                return re;
            }

            public String transformKey(String key){ //when key is a string
                return "\"" + key + "\":";
            }

            public String transformVal(Object value){

                if(value instanceof String) return transformValue((String) value);
                else if(value instanceof List) return  transformValue((List) value);
                else if(value instanceof Map) return  transformValue((Map<String, ?>) value);
                else if(value instanceof Integer) return  transformValue((int) value);
                else if(value instanceof Double) return  transformValue((double) value);
                else if(value instanceof Boolean) return  transformValue((boolean) value);
                else if(value instanceof Long) return transformValue((long) value);
                else if(value == null) return "{}";

                return "No proper transformVal type";
            }

            public String transformValue(double value){
                return Double.toString(value);
            }

            public String transformValue(boolean value){
                return Boolean.toString(value);
            }

            public String transformValue(int value){
                return Integer.toString(value);
            }

            public String transformValue(long value){
                return Long.toString(value);
            }

            public String transformValue(String value){
                value = ((String) value).replaceAll("\"", "\\\\\"");
                return "\"" + value + "\"";
            }

            public String transformValue(List value){

                String re = new String();
                re += " [";

                for(int i = 0; i < value.size(); i++){
                    re += transformVal(value.get(i));
                    if(i < value.size() - 1) re += ", ";
                }

                re += "]\n";
                return re;
            }

            public String transformValue(Map<String, ?> map){
                String re = new String();

                if(map == null) return "{}";

                if(map.isEmpty()) return "{}";

                Iterator<? extends Map.Entry<String, ?>> mapIterator = map.entrySet().iterator();


                Map.Entry<String, ?> entry;
                re += "\n{\n";

                while(mapIterator.hasNext()){
                    entry = mapIterator.next();

                    re += transformKey(entry.getKey());
                    re += transformVal(entry.getValue());

                    if(mapIterator.hasNext())re += ",";
                }

                re += "}\n";

                return re;
            }
        };
    }

}
