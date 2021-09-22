package JsonConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JsonConverter {
    public JsonConverter() {
    }
    public static String toPrettyFormat(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }


    public void convertFile(ArrayList<ArrayList<String>> dataList) throws IOException {
        String result ="{\"players\":[ \n";
        for (int i = 1; i < dataList.size(); i++){
            JSONObject jsonObject = new JSONObject();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            for (int j = 0; j < dataList.get(0).size(); j++){
                if (j < dataList.get(i).size()){
                    jsonObject.put(dataList.get(0).get(j), dataList.get(i).get(j));
                }else {
                    jsonObject.put(dataList.get(0).get(j), null);
                }
            }
            String convert = gson.toJson(jsonObject);
            result+=  convert;
            if (i < dataList.size() - 1){
                result +=",";

            }
        }

        result +="\n]\n}";
        String filePath = "src/main/resources/writeFile/data.json";


        FileWriter file = new FileWriter(filePath);
        file.write(result);
        file.close();

    }


}



