package CsvConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CsvConverter {

    public CsvConverter() {
    }

    public void converFile(ArrayList<ArrayList<String>> dataList) throws IOException {
//       Set filePath
        String filePath = "src/main/resources/data.csv";

        File file = new File(filePath);

        FileWriter myWriter = new FileWriter(file);

        String result = "";
        for (int i = 0; i < dataList.size(); i++) {
            for (int j = 0; j < dataList.get(0).size(); j++) {
                if (j < dataList.get(i).size()){
                    result += dataList.get(i).get(j);
                    if (j < dataList.get(i).size() - 1){
                        result += ",";
                    }
                }else{
                    result += ",";
                }
            }
            result += "\n";
        }
        myWriter.write(result);
        myWriter.close();
    }
}
