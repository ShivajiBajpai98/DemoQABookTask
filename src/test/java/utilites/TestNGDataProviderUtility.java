package utilites;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

public class TestNGDataProviderUtility
{
	@Test
	@DataProvider(name="BookData")
	public Object[][] getData()
	{


		String path="src/test/java/resources/BookData.xlsx";
		ExcelReaders xlsxUtility= new ExcelReaders(path);



		int totalrows=xlsxUtility.getRowCount("Book");
		int totalcols=xlsxUtility.getCellCount("Book",1);



		Object formData[][]=new Object[totalrows][totalcols];
		for (int i=1;i<=totalrows;i++)
		{
			for (int j=0; j<totalcols;j++)
			{
				formData[i-1][j]=xlsxUtility.getCellData("Book",i,j);
				//System.out.print(formData[i-1][j]+ " ");

			}
		}

		return formData;
	}

}