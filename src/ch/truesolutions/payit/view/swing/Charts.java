/**
 * Chart2D, a java library for drawing two dimensional charts.
 * Copyright (C) 2001 Jason J. Simas
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * The author of this library may be contacted at:
 * E-mail:  jjsimas@users.sourceforge.net
 * Street Address:  J J Simas, 887 Tico Road, Ojai, CA 93023-3555 USA
 */
package ch.truesolutions.payit.view.swing;

import net.sourceforge.chart2d.*;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import ch.truesolutions.payit.model.Config;
import ch.truesolutions.payit.model.DAO;

import java.awt.Color;
import java.util.*;


/**
 * A Chart2D demo demonstrating the LBChart2D object.
 * Container Class: JFrame<br>
 * Program Types:  Applet or Application<br>
 */
public class Charts extends JDialog {


  //private JFrame frame = null;
  private static boolean isApplet = false;
  //private Chart2D chart = null;
  private List al = null;
  private int numbData = 0;
  private final String[] monthNames = 	   {Config.getInstance().getText("month.jan"),
												Config.getInstance().getText("month.feb"),
												Config.getInstance().getText("month.mar"),
												Config.getInstance().getText("month.apr"),
												Config.getInstance().getText("month.may"),
												Config.getInstance().getText("month.jun"),
												Config.getInstance().getText("month.jul"),
												Config.getInstance().getText("month.aug"),
												Config.getInstance().getText("month.sep"),
												Config.getInstance().getText("month.oct"),
												Config.getInstance().getText("month.nov"),
												Config.getInstance().getText("month.dec")};


  /**
   * For running as an application.
   * Calls init() and start().
   * @param args An unused parameter.
   */
  public static void main (String[] args) {

    isApplet = false;
    JFrame dummyFrame = new JFrame();
    new Charts(dummyFrame);
    //exit on frame close event
  }
  
  public Charts(JFrame parent){
  	super(parent);
  	init();
    start();
  }


  /**
   * Configure the chart and frame, and open the frame.
   */
  public void init() {

    // do the query
    al = DAO.getInstance().executeQuery("SELECT month(execdate) as monat,year(execdate) as jahr,sum(amount) as summe,avg(amount) as durchschnitt,max(amount) as maximum,min(amount) as minimum ,count(*) as anzahl FROM payments GROUP BY  month(execdate),year(execdate) ORDER BY year(execdate)");
    Object[] alArray = al.toArray();
    Comparator alComparator = new Comparator(){
    	public int compare(Object o1,Object o2){
    		// compare month and year of the array lists
    		ArrayList al1,al2 = null;
    		int month1 = -1;
    		int month2 = -1;
    		int year1 = -1;
    		int year2 = -1;
    		if(o1 != null && o1 instanceof ArrayList){
    			al1 = (ArrayList)o1;
    			try{
    				month1 = ((Integer)(al1.get(0))).intValue();
    				year1 = ((Integer)(al1.get(1))).intValue();
    			} catch (Exception e){}
    		}
    		if(o2 != null && o2 instanceof ArrayList){
    			al2 = (ArrayList)o2;
    			try{
    				month2 = ((Integer)(al2.get(0))).intValue();
    				year2 = ((Integer)(al2.get(1))).intValue();
    			} catch (Exception e){}
    		}
    		if(year1 < year2){
    			return -1;
    		} 
    		if(year1 > year2){
    			return 1;
    		}
    		if(year1 == year2){
    			if(month1 < month2){
    				return -1;
    			}
    			if(month1 > month2){
    				return 1;
    			}
    		}
    		return 0;
    	}
    };
    		
    	
    Arrays.sort(alArray,alComparator);
    al = new ArrayList(Arrays.asList(alArray));
    numbData = al != null ? al.size() : 0;

    JTabbedPane panes = new JTabbedPane();

    panes.addTab (Config.getInstance().getText("chart.sum.tab"), getChart2DPaymentSum());
    panes.addTab (Config.getInstance().getText("chart.number.tab"), getChart2DNumbOfPayments());
        
    boolean dynamicSizeCalc = false;
    if (dynamicSizeCalc) {
      int maxWidth = 0;
      int maxHeight = 0;
      /*
      chart.pack();
      Dimension size = chart.getSize();
      maxWidth = maxWidth > size.width ? maxWidth : size.width;
      maxHeight = maxHeight > size.height ? maxHeight : size.height;
      */
      for (int i = 0; i < panes.getTabCount(); ++i) {
        Chart2D chart2D = (Chart2D)panes.getComponentAt (i);
        chart2D.pack();
        Dimension size = chart2D.getSize();
        maxWidth = maxWidth > size.width ? maxWidth : size.width;
        maxHeight = maxHeight > size.height ? maxHeight : size.height;
      }
      
      Dimension maxSize = new Dimension (maxWidth, maxHeight);
      System.out.println (maxSize);
      
      for (int i = 0; i < panes.getTabCount(); ++i) {
        Chart2D chart2D = (Chart2D)panes.getComponentAt (i);
        chart2D.setSize (maxSize);
        chart2D.setPreferredSize (maxSize);
      }
      //chart.setSize (maxSize);
      //chart.setPreferredSize (maxSize);
      
      System.out.println (panes.getPreferredSize());
    }
    else {
      Dimension maxSize = new Dimension (561, 214);
      for (int i = 0; i < panes.getTabCount(); ++i) {
        Chart2D chart2D = (Chart2D)panes.getComponentAt (i);
        chart2D.setSize (maxSize);
        chart2D.setPreferredSize (maxSize);
      }
      panes.setPreferredSize (new Dimension (566 + 5, 280 + 5)); //+ 5 slop
    }

    //frame = new JFrame();
    this.getContentPane().add (panes);
    this.setTitle (Config.getInstance().getText("chart.title"));
    this.addWindowListener (
      new WindowAdapter() {
        public void windowClosing (WindowEvent e) {
          destroy();
    } } );
    this.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setLocation (
      (screenSize.width - this.getSize().width) / 2,
      (screenSize.height - this.getSize().height) / 2);
  }


  /**
   * Shows the JFrame GUI.
   */
  public void start() {
    this.setVisible(true);
  }


  /**
   * Ends the application or applet.
   */
  public void destroy() {

    this.setVisible(false);
  }

  /**
   * Builds the demo chart.
   * @return The demo chart.
   */
  private Chart2D getChart2DPaymentSum() {
    
    //<-- Begin Chart2D configuration -->

    //Configure object properties
    Object2DProperties object2DProps = new Object2DProperties();
    object2DProps.setObjectTitleText (Config.getInstance().getText("chart.sum.title"));

    //Configure chart properties
    Chart2DProperties chart2DProps = new Chart2DProperties();
    chart2DProps.setChartDataLabelsPrecision (-2);

    //Configure legend properties
    LegendProperties legendProps = new LegendProperties();
    String[] legendLabels = {"Mov. Avg.", "Raw Data"};
    legendProps.setLegendLabelsTexts (legendLabels);

    //Configure graph chart properties
    GraphChart2DProperties graphChart2DProps = new GraphChart2DProperties();
    String[] labelsAxisLabels = new String[numbData];
    for(int k=0; k<numbData;k++){
    	Integer year = new Integer(0);
    	Integer month = new Integer(0);
    	Object om = ((ArrayList)(al.get(k))).get(0);
    	Object oy = ((ArrayList)(al.get(k))).get(1);
    	if(om != null && om instanceof Integer){
    		month = (Integer)om;
    	}
    	if(oy != null && oy instanceof Integer){
    		year = (Integer)oy;
    	}
    	labelsAxisLabels[k] = monthNames[month.intValue()]+"\n"+year.toString();
    }

    graphChart2DProps.setLabelsAxisLabelsTexts (labelsAxisLabels);
    //graphChart2DProps.setLabelsAxisTitleText ("Age Ranges");
    graphChart2DProps.setNumbersAxisTitleText ("CHF");
    graphChart2DProps.setChartDatasetCustomizeGreatestValue (true);
    graphChart2DProps.setChartDatasetCustomGreatestValue (1f);
    graphChart2DProps.setChartDatasetCustomizeLeastValue (true);
    graphChart2DProps.setChartDatasetCustomLeastValue (0f);

    //Configure graph properties
    GraphProperties graphProps = new GraphProperties();
    graphProps.setGraphComponentsAlphaComposite (graphProps.ALPHA_COMPOSITE_MILD);

    //Configure dataset
    Dataset dataset = new Dataset (1, numbData, 1);
    for(int i=0;i<numbData;i++){
    	Double sum = new Double(0); 
    	Object o = ((ArrayList)(al.get(i))).get(2);
    	if(o != null && o instanceof Double){
    		sum = (Double)o;
    	}
    	dataset.set(0,i,0,(int)(sum.doubleValue()));
    }
    
    //Configure graph component colors
    MultiColorsProperties multiColorsProps = new MultiColorsProperties();

    //Configure graph properties (trend)
    GraphProperties graphPropsTrend = new GraphProperties();
    graphPropsTrend.setGraphBarsExistence (false);
    graphPropsTrend.setGraphLinesExistence (true);

    //Configure dataset (trend)
    Dataset datasetTrend = new Dataset();
    datasetTrend.addMovingAverage (dataset, 3);

    //Configure graph component colors (trend)
    MultiColorsProperties multiColorsPropsTrend = new MultiColorsProperties();
    multiColorsPropsTrend.setColorsCustomize (true);
    multiColorsPropsTrend.setColorsCustom (new Color[] {new Color (193, 183, 0)});

    //Configure chart
    LBChart2D chart2D = new LBChart2D();
    chart2D.setObject2DProperties (object2DProps);
    chart2D.setChart2DProperties (chart2DProps);
    chart2D.setLegendProperties (legendProps);
    chart2D.setGraphChart2DProperties (graphChart2DProps);
    chart2D.addGraphProperties (graphPropsTrend);
    chart2D.addDataset (datasetTrend);
    chart2D.addMultiColorsProperties (multiColorsPropsTrend);
    chart2D.addGraphProperties (graphProps);
    chart2D.addDataset (dataset);
    chart2D.addMultiColorsProperties (multiColorsProps);

    //Optional validation:  Prints debug messages if invalid only.
    if (!chart2D.validate (false)) chart2D.validate (true);

    //<-- End Chart2D configuration -->

    return chart2D;
  }
  
  /**
   * Builds the demo chart.
   * @return The demo chart.
   */
  private Chart2D getChart2DNumbOfPayments() {

    //<-- Begin Chart2D configuration -->

    //Configure object properties
    Object2DProperties object2DProps = new Object2DProperties();
    object2DProps.setObjectTitleText (Config.getInstance().getText("chart.number.title"));

    //Configure chart properties
    Chart2DProperties chart2DProps = new Chart2DProperties();
    chart2DProps.setChartDataLabelsPrecision (-0);

    //Configure legend properties
    LegendProperties legendProps = new LegendProperties();
    String[] legendLabels = {"Mov. Avg.", "Raw Data"};
    legendProps.setLegendLabelsTexts (legendLabels);

    //Configure graph chart properties
    GraphChart2DProperties graphChart2DProps = new GraphChart2DProperties();
    String[] labelsAxisLabels = new String[numbData];
    for(int k=0; k<numbData;k++){
    	Integer year = new Integer(0);
    	Integer month = new Integer(0);
    	Object om = ((ArrayList)(al.get(k))).get(0);
    	Object oy = ((ArrayList)(al.get(k))).get(1);
    	if(om != null && om instanceof Integer){
    		month = (Integer)om;
    	}
    	if(oy != null && oy instanceof Integer){
    		year = (Integer)oy;
    	}
    	labelsAxisLabels[k] = monthNames[month.intValue()]+"\n"+year.toString();
    }

    graphChart2DProps.setLabelsAxisLabelsTexts (labelsAxisLabels);
    //graphChart2DProps.setLabelsAxisTitleText ("Age Ranges");
    graphChart2DProps.setNumbersAxisTitleText (Config.getInstance().getText("chart.number.tab"));
    graphChart2DProps.setChartDatasetCustomizeGreatestValue (true);
    graphChart2DProps.setChartDatasetCustomGreatestValue (1f);
    graphChart2DProps.setChartDatasetCustomizeLeastValue (true);
    graphChart2DProps.setChartDatasetCustomLeastValue (0f);

    //Configure graph properties
    GraphProperties graphProps = new GraphProperties();
    graphProps.setGraphComponentsAlphaComposite (graphProps.ALPHA_COMPOSITE_MILD);

    //Configure dataset
    Dataset dataset = new Dataset (1, numbData, 1);
    for(int i=0;i<numbData;i++){
    	Integer sum = new Integer(0); 
    	Object o = ((ArrayList)(al.get(i))).get(6);
    	if(o != null && o instanceof Integer){
    		sum = (Integer)o;
    	}
    	dataset.set(0,i,0,(int)(sum.intValue()));
    }
    
    //Configure graph component colors
    MultiColorsProperties multiColorsProps = new MultiColorsProperties();

    //Configure graph properties (trend)
    GraphProperties graphPropsTrend = new GraphProperties();
    graphPropsTrend.setGraphBarsExistence (false);
    graphPropsTrend.setGraphLinesExistence (true);

    //Configure dataset (trend)
    Dataset datasetTrend = new Dataset();
    datasetTrend.addMovingAverage (dataset, 3);

    //Configure graph component colors (trend)
    MultiColorsProperties multiColorsPropsTrend = new MultiColorsProperties();
    multiColorsPropsTrend.setColorsCustomize (true);
    multiColorsPropsTrend.setColorsCustom (new Color[] {new Color (193, 183, 0)});

    //Configure chart
    LBChart2D chart2D = new LBChart2D();
    chart2D.setObject2DProperties (object2DProps);
    chart2D.setChart2DProperties (chart2DProps);
    chart2D.setLegendProperties (legendProps);
    chart2D.setGraphChart2DProperties (graphChart2DProps);
    chart2D.addGraphProperties (graphPropsTrend);
    chart2D.addDataset (datasetTrend);
    chart2D.addMultiColorsProperties (multiColorsPropsTrend);
    chart2D.addGraphProperties (graphProps);
    chart2D.addDataset (dataset);
    chart2D.addMultiColorsProperties (multiColorsProps);

    //Optional validation:  Prints debug messages if invalid only.
    if (!chart2D.validate (false)) chart2D.validate (true);

    //<-- End Chart2D configuration -->

    return chart2D;
  }
}