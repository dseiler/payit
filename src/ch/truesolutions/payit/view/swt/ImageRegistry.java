/*
 * Created on 19.04.2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package ch.truesolutions.payit.view.swt;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * @author Daniel
 */
public class ImageRegistry {

	private static ImageRegistry instance;
	private org.eclipse.jface.resource.ImageRegistry registry;
	
	public static ImageRegistry getInstance(){
		if(instance == null){
			instance = new ImageRegistry();
		}
		return instance;
	}
	
	public Image getImage(String key){
		return registry.get(key);
	}
	
	public ImageDescriptor getImageDescriptor(String key){
		return registry.getDescriptor(key);
	}
	/**
	 * 
	 */
	private ImageRegistry() {
		super();
		registry = new org.eclipse.jface.resource.ImageRegistry();
		registry.put("esred_image",
			ImageDescriptor.createFromURL(newURL("file:images/esRed_image.gif")));
		registry.put("esblue_image",
			ImageDescriptor.createFromURL(newURL("file:images/esBlue_image.gif")));
		registry.put("esorange_image",
			ImageDescriptor.createFromURL(newURL("file:images/esOrange_image.gif")));
		registry.put("esbank_image",
			ImageDescriptor.createFromURL(newURL("file:images/esBank_image.gif")));
		registry.put("ipi_image",
			ImageDescriptor.createFromURL(newURL("file:images/ipi_image.gif")));
		// Toolbar Icons
		registry.put("esred_icon",
			ImageDescriptor.createFromURL(newURL("file:images/esRed_icon.gif")));
		registry.put("esblue_icon",
			ImageDescriptor.createFromURL(newURL("file:images/esBlue_icon.gif")));
		registry.put("esorange_icon",
			ImageDescriptor.createFromURL(newURL("file:images/esOrange_icon.gif")));
		registry.put("esbank_icon",
			ImageDescriptor.createFromURL(newURL("file:images/esBank_icon.gif")));
		registry.put("ipi_icon",
			ImageDescriptor.createFromURL(newURL("file:images/ipi_icon.gif")));
		// Table Icons
		registry.put("esred_icon_table",
			ImageDescriptor.createFromURL(newURL("file:images/esRed_icon_table.gif")));
		registry.put("esblue_icon_table",
			ImageDescriptor.createFromURL(newURL("file:images/esBlue_icon_table.gif")));
		registry.put("esorange_icon_table",
			ImageDescriptor.createFromURL(newURL("file:images/esOrange_icon_table.gif")));
		registry.put("esbank_icon_table",
			ImageDescriptor.createFromURL(newURL("file:images/esBank_icon_table.gif")));
		registry.put("ipi_icon_table",
			ImageDescriptor.createFromURL(newURL("file:images/ipi_icon_table.gif")));
	}

	private URL newURL(String url_name)
	{
	  try
	  {
		return new URL(url_name);
	  }
	  catch (MalformedURLException e)
	  {
		throw new RuntimeException("Malformed URL " + url_name + ", exception: "+e);
	  }
	}
}
