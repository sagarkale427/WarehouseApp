package in.proj.warehouse.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;

@Component
public class WhUserTypeUtil {
	
	
	@SuppressWarnings("unchecked")
	public void generatePieChart(String path,List<Object[]> data) {
		//1.Prepare DataSet
		
		@SuppressWarnings("rawtypes")
		DefaultPieDataset dataSet=new DefaultPieDataset();
		
		for(Object[] ob:data) {
			//key(String)-val(Double)
			dataSet.setValue(ob[0].toString(), Double.valueOf(ob[1].toString()));
		}
		
		//2.create JFree Chart object
		//Input=>title, dataset
		JFreeChart chart=ChartFactory.createPieChart("SHiPMENT TYPE MODE", dataSet);
		
		//3.save as image
		try {
		ChartUtils.saveChartAsJPEG(new File(path+"/shipMentModeA.jpg"),  //file location +name  
				chart,    //JFreeChart object
				300,      //width
				300);     //height
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void generateBarChart(String path,List<Object[]> data) {
		//1.Prepare DataSet
		
		DefaultCategoryDataset dataSet=new DefaultCategoryDataset();
		
		for(Object[] ob:data) {
			//val(Double)-key(String)
			dataSet.setValue( Double.valueOf(ob[1].toString()),ob[0].toString(),
					"" //display label
					);
		}
		
		//2.create JFree Chart object
		//Input=>title, dataset
		JFreeChart chart=ChartFactory.createBarChart("SHiPMENT TYPE MODE","MODES","COUNT", dataSet);
		
		//3.save as image
		try {
		ChartUtils.saveChartAsJPEG(new File(path+"/shipMentModeB.jpg"),  //file location +name  
				chart,    //JFreeChart object
				400,      //width
				350);     //height
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
