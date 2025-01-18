package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
	   String path="/Users/user/eclipse-workspace/OpencartV121/testData/Opencart_LoginData.xlsx";
	   //taking excel from the testdata
	   
	   XLUtility xlutil = new XLUtility(path); //creating an object for XLUtility
	   
	   int totalrows=xlutil.getRowCount("Sheet1");
	   int totalcols=xlutil.getCellCount("Sheet1", 1);
	   String logindata[][]=new String[totalrows][totalcols]; //created for two dimension array which can store
	   
	   for(int i=1;i<=totalrows;i++) //1 //read the data from xl storing in two dimensional array
	   {
		   for(int j=0;j<totalcols;j++) //0 i is rows j is col
		   {
			   logindata[i-1][j] = xlutil.getCellData("Sheet1", i, j); //1,0
		   }
		   
		   
	   }
	   return logindata; //returning two dimension array
	}
	
	//DataProvider 2
	
	//DataProvider 3
	
	//DataProvider 4

}
