import CsvConverter.CsvConverter;
import JsonConverter.JsonConverter;
import PdfConverter.PdfConverter;
import XmlConverter.XmlConverter;
import com.itextpdf.text.DocumentException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class main {
    public static void displayMenu(){
        String result = "";
        String temp = "Convert file to";
        result += "Please select options below:  \n";
        result += "\t 1 " + temp +  " PDF\n" +
                  "\t 2 " + temp +  " JSON\n" +
                  "\t 3 " + temp +  " XML\n" +
                  "\t 4 " + temp +  " CSV\n" +
                  "\t 0  Quit";
        System.out.println(result);
    }

    public static ArrayList<ArrayList<String>> readFile(String filePath) throws FileNotFoundException {
        ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();

        ArrayList<String> getHeader = new ArrayList<String>();
        ArrayList<String> tempArr;

        File f = new File(filePath);
        Scanner sc = new Scanner(f);
        while (sc.hasNextLine()) {
            String temp = sc.nextLine();
            String[] tempData = temp.split("\t+");

            if (getHeader == null) {
                getHeader.addAll(Arrays.asList(tempData));
                dataList.add(getHeader);
            } else {
                tempArr = new ArrayList<>();
                tempArr.addAll(Arrays.asList(tempData));
                dataList.add(tempArr);
            }
        }

        return dataList;
    }


    public static void main(String[] args) throws IOException {
        String filePath = "src/main/resources/data.tvb";
        ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();

        dataList = readFile(filePath);

        Scanner input = new Scanner(System.in);
        Integer getInput = -1;
        while (getInput != 0){
            displayMenu();
            String temp;
            boolean isInteger;

           do {
               System.out.print("Your option is: ");
               temp = input.next();

               try {
                   getInput = Integer.parseInt(temp);
                   isInteger = false;
               } catch (NumberFormatException e) {
                   System.out.println("Please input the number of option only");
                   isInteger = true;
               }
           }while (isInteger);

            switch (getInput){
                case 1:
                    System.out.println("Convert to PDF in progress");
                    PdfConverter pdfConvert = new PdfConverter();
                    try {
                        pdfConvert.convertFile(dataList);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.println("Convert to JSON in progress");
                    JsonConverter jsonConverter = new JsonConverter();
                    jsonConverter.convertFile(dataList);
                    break;
                case 3:
                    System.out.println("Convert to XML in progress");
                    XmlConverter xmlConverter = new XmlConverter();
                    try {
                        xmlConverter.convertFile(dataList);
                    } catch (XMLStreamException |TransformerException e ) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    System.out.println("Convert to CSV in progress");
                    CsvConverter csvConverter = new CsvConverter();
                    csvConverter.converFile(dataList);
                    break;
                case 0:
                    System.out.println("Quit");
                    break;
            }
        }
    }
}

