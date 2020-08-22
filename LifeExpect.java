package LifeExpectency;
import java.util.*;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.*;
public class LifeExpect extends PApplet{
	UnfoldingMap map;
	private Map<String,Float> lifeExpMap;
	private List<Feature> countries;
	private List<Marker> countryMarkers;
	public void setup() {
		//setting up the applet
		size(800,600,OPENGL);
		//creating a map from google
		map = new UnfoldingMap(this,50,50,700,500,new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
		lifeExpMap = ParseFeed.loadLifeExpectancyFromCSV(this,"LifeExpectancyWorldBank.csv");
		//
		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		//
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		//
		map.addMarkers(countryMarkers);
		//
		shadeCountries();
		//This provide the user to manupilate the map like zooming, pinching, locating etc
		
	}
	//this method is runs in a loop
	public void draw() {
		map.draw();
	}
	private void shadeCountries() {
		for(Marker marker : countryMarkers) {
			String countryId = marker.getId();
			if(lifeExpMap.containsKey(countryId)) {
				float lifeExp = this.lifeExpMap.get(countryId);
				int colorLevel = (int) map(lifeExp,40,90,20,255);
				marker.setColor(color(255-colorLevel,100,colorLevel));
			}else {
				marker.setColor(color(150,150,150));
			}
		}
	}

}
