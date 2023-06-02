package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excelreader {
	static FileInputStream fis=null;
	public FileInputStream getFileInputStream() {
		String filepath=System.getProperty("user.dir")+"//src//test//java//data//Userreg.xlsx";
		File srcfile=new File(filepath);
		try {
			fis=new FileInputStream(srcfile);
		} catch (FileNotFoundException e) {
			System.out.println("Test Data file is not found");
			System.exit(0);
		}
		return fis;
	}
	public Object [][] getExcelData() throws IOException{
		fis=getFileInputStream();	
		XSSFWorkbook wb =new XSSFWorkbook(fis);
		XSSFSheet sheet=wb.getSheetAt(0);
		int TotalNumberOfRows=	(sheet.getLastRowNum()+1);
		int TotalNumberOfcol=4;
		String[][] arrayExcelData=new String[TotalNumberOfRows][TotalNumberOfcol];
		for (int i = 0; i < TotalNumberOfRows; i++) {
			for (int j = 0; j < TotalNumberOfcol; j++) {
				XSSFRow row=sheet.getRow(i);
				arrayExcelData[i][j]=row.getCell(j).toString();
			}

		}
		wb.close();
		return arrayExcelData;
	}
}
