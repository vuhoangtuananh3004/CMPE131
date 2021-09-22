package PdfConverter;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class PdfConverter {

    public PdfConverter() {
    }

    public void convertFile(ArrayList<ArrayList<String>> dataList) throws FileNotFoundException, DocumentException {
//       Set filePath
        String filePath = "src/main/resources/data.pdf";
//      Set Page Size
        Rectangle myPageSize = new Rectangle(3300, 3000);
        Document document = new Document();
        document.setPageSize(myPageSize);
//      Create File
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
//      Set Font
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13);
        Font fontHeader = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, Font.BOLD);

//      Create table with column = comlumn in the List.
        PdfPTable table = new PdfPTable(dataList.get(0).size());
//      Declare cell
        PdfPCell cell;
//      Set Column width.
        float widths[] = new float[dataList.get(0).size()];
        for (int i = 0; i < dataList.get(0).size(); i++) {
            widths[i] = dataList.get(1).get(1).length();
        }
        table.setWidths(widths);
//      Set HeaderRow --> every page the header will repeat
        table.setHeaderRows(1);

//      Write data on file.
        for (int i = 0; i < dataList.size(); i++) {
            for (int j = 0; j < dataList.get(0).size(); j++) {
                cell = new PdfPCell();
                cell.setFixedHeight(80);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                Paragraph p;
                if (j >= dataList.get(i).size()) {
                    p = new Paragraph("");
                } else {
                    p = new Paragraph(dataList.get(i).get(j));
                }

                if (i == 0) {
                    p.setFont(fontHeader);
                } else {
                    p.setFont(font);
                }
                p.setAlignment(Element.ALIGN_CENTER);
                cell.addElement(p);
                table.addCell(cell);
            }
        }
        document.open();
        document.add(table);
        document.close();
    }
}
