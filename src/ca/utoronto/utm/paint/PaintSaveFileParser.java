package ca.utoronto.utm.paint;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Parse a file in Version 1.0 PaintSaveFile format. An instance of this class
 * understands the paint save file format, storing information about
 * its effort to parse a file. After a successful parse, an instance
 * will have an ArrayList of PaintCommand suitable for rendering.
 * If there is an error in the parse, the instance stores information
 * about the error. For more on the format of Version 1.0 of the paint 
 * save file format, see the associated documentation.
 * 
 * @author 
 *
 */
public class PaintSaveFileParser {
	private int lineNumber = 0; // the current line being parsed
	private String errorMessage =""; // error encountered during parse
	private ArrayList<PaintCommand> commands = new ArrayList<PaintCommand>(); // created as a result of the parse
	
	
	/**
	 * Below are Patterns used in parsing 
	 */
	private Pattern pFileStart=Pattern.compile("^PaintSaveFileVersion1.0$");
	private Pattern pFileEnd=Pattern.compile("^EndPaintSaveFile$");

	private Pattern pCircleStart=Pattern.compile("^Circle$");
	private Pattern pCircleEnd=Pattern.compile("^EndCircle$");
	// ADD MORE!!
	private Pattern pRectangleStart=Pattern.compile("^Rectangle$");
	private Pattern pRectangleEnd=Pattern.compile("^EndRectangle$");
	
	private Pattern pSquiggleStart = Pattern.compile("^Squiggle$");
	private Pattern pSquiggleEnd=Pattern.compile("^EndSquiggle$");
	
	private Pattern pColor=Pattern.compile("^color:(1?[0-9]?[0-9]|2[0-4][0-9]|25[0-5]),(1?[0-9]?[0-9]|2[0-4][0-9]|25[0-5]),(1?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])$");
	private Pattern pFilled=Pattern.compile("^filled:((true)|(false))$");
	
	private Pattern pCenter=Pattern.compile("^center:\\((\\d+),(\\d+)\\)$");
	
	private Pattern pRadius=Pattern.compile("^radius:([0-9]+)$");
	
	private Pattern pSide1=Pattern.compile("^p1:\\(([-]?\\d+),([-]?\\d+)\\)$");
	private Pattern pSide2=Pattern.compile("^p2:\\(([-]?\\d+),([-]?\\d+)\\)$");
	
	private Pattern pPointStart=Pattern.compile("^points$");
	private Pattern pPointEnd=Pattern.compile("^endpoints$");
	private Pattern pPoint=Pattern.compile("^point:\\(([-]?\\d+),([-]?\\d+)\\)$");
	private Pattern pEndSquiggle=Pattern.compile("^EndSquiggle$");
	/**
	 * Store an appropriate error message in this, including 
	 * lineNumber where the error occurred.
	 * @param mesg
	 */
	private void error(String mesg){
		this.errorMessage = "Error in line "+lineNumber+" "+mesg;
	}
	/**
	 * 
	 * @return the PaintCommands resulting from the parse
	 */
	public ArrayList<PaintCommand> getCommands(){
		return this.commands;
	}
	/**
	 * 
	 * @return the error message resulting from an unsuccessful parse
	 */
	public String getErrorMessage(){
		return this.errorMessage;
	}
	
	/**
	 * Parse the inputStream as a Paint Save File Format file.
	 * The result of the parse is stored as an ArrayList of Paint command.
	 * If the parse was not successful, this.errorMessage is appropriately
	 * set, with a useful error message.
	 * 
	 * @param inputStream the open file to parse
	 * @return whether the complete file was successfully parsed
	 */
	public boolean parse(BufferedReader inputStream) {
		this.commands = new ArrayList<PaintCommand>();
		this.errorMessage="";
		
		// During the parse, we will be building one of the 
		// following shapes. As we parse the file, we modify 
		// the appropriate shape.
		
		Circle circle = null; 
		Rectangle rectangle = null;
		Squiggle squiggle = null;
		String color="";
		ArrayList<Point> points = new ArrayList();
		boolean filled = true;
		String center="";
		int p1 = 0,p2=0,p3=0,p4=0;
		int centerX=0;
		int centerY=0;
		int radius=0;
		int red=0;
		int blue=0;
		int green=0;
		boolean endFile = false;
		try {	
			int state=0; Matcher m; String l; Matcher a;
			
			this.lineNumber=0;
			while ((l = inputStream.readLine()) != null) {
				l =l.replaceAll("\\s+", "").replaceAll("\\t+","").replaceAll("\\n+","");
				if(l.isEmpty()) {
					continue;
				}
				endFile=false;
				this.lineNumber++;
				
				System.out.println(lineNumber+" "+l+" "+state);
				switch(state){
				
					case 0:
						m=pFileStart.matcher(l);
						if(m.matches()){
							state=1;
							break;
						}
						error("Error in line "+lineNumber);
						return false;
						
						
					case 1: // Looking for the start of a new object or end of the save file
						m=pFileEnd.matcher(l);
						if(m.matches()) { //Identifier for file ending
							endFile=true;
							state = 18;
							break;
						}
						m=pCircleStart.matcher(l);
						if(m.matches()){
							// ADD CODE!!!
							state=2;
						
							break;
						}
						m=pRectangleStart.matcher(l);
						if(m.matches()) {
							state=7;
							break;
						}
						m=pSquiggleStart.matcher(l);
						if(m.matches()) {
							state=12;
							break;
						}
						else {
							error("Error in line "+lineNumber);
							this.commands.clear();
							return false;
						}
						// ADD CODE
				
					case 2: //circle case
						
						m=pColor.matcher(l);
						
						if(m.matches()) {
							red = Integer.parseInt(m.group(1));
							green = Integer.parseInt(m.group(2));
							blue = Integer.parseInt(m.group(3));
							state=3;
							break;
						}
						else {
							error("Error in line "+lineNumber);
							this.commands.clear();
							return false;
						}
					case 3:
						
						m=pFilled.matcher(l);
						//System.out.println(m);
						if(m.matches()) {
							//System.out.println(m.group(1));
							String b = m.group(1);
							
							if (m.group(1).equals("true")) {
								filled =true;
								state = 4;
								break;
							}
							else{ 
									filled = false;
									state = 4;
									break;
								}
							}
						else {
							error("Error in line "+lineNumber);
							this.commands.clear();
							return false;
						}
					case 4:
							//System.out.println(filled);
						m=pCenter.matcher(l);
						if(m.matches()) {
							centerX = Integer.parseInt(m.group(1));
							centerY = Integer.parseInt(m.group(2));
							state =5;
							break;
						}
						else {
							error("Error in line "+lineNumber);
							this.commands.clear();
							return false;
						}
					case 5:
						
						m=pRadius.matcher(l);
						if(m.matches()) {
							radius = Integer.parseInt(m.group(1));
							state = 6;
							break;
						} else {
							error("Error in line "+lineNumber);
							this.commands.clear();
							return false;
						}
					case 6:
						
						m=pCircleEnd.matcher(l); //Once we find circle end we can create the circle
						if(m.matches()) {
							Point cent = new Point(centerX,centerY);
							Circle c = new Circle(cent,radius);
							Color colour = new Color(red,green,blue);
							c.setColor(colour);
							c.setFill(filled);
							CircleCommand circleCommand = new CircleCommand(c);
							this.commands.add(circleCommand);
							//Circle c = Circle(Integer.parseInt(center),Integer.parseInt(radius));
							state=1;
							break;
						} else {
							error("Error in line "+lineNumber);
							this.commands.clear();
							return false;
						}
						
					case 7: //rectangle
						m=pColor.matcher(l);
						if(m.matches()) {
							color =l;
							red = Integer.parseInt(m.group(1));
							green = Integer.parseInt(m.group(2));
							blue = Integer.parseInt(m.group(3));
							state=8;
							break;
						}
						else {
							error("Error in line "+lineNumber);
							this.commands.clear();
							return false;
						}
					case 8:
						m=pFilled.matcher(l);
						//System.out.println(m);
						if(m.matches()) {
							if (m.group(1).equals("true")) {
								filled =true;
								state = 9;
								break;
							}
							else{ 
									filled = false;
									state = 9;
									break;
								}
							}
						else {
							this.commands.clear();
							error("Error in line "+lineNumber);
							return false;
						}
					case 9:
						m=pSide1.matcher(l);
						if(m.matches()) {
							p1 = Integer.parseInt(m.group(1));
							p2 = Integer.parseInt(m.group(2));
							state=10;
							break;
						} else {
							error("Error in line "+lineNumber);
							this.commands.clear();
							return false;
						}
					case 10:
						m=pSide2.matcher(l);
						if(m.matches()) {
							p3 = Integer.parseInt(m.group(1));
							p4 = Integer.parseInt(m.group(2));
							state = 11;
							break;
						} else {
							error("Error in line "+lineNumber);
							this.commands.clear();
							return false;
						}
					case 11:
						m=pRectangleEnd.matcher(l); //Once we find the rectangle end match we can create rectangle
						if(m.matches()) {
							Point ps1 = new Point(p1,p2);
							Point ps2 = new Point(p3,p4);
							Rectangle r = new Rectangle(ps1,ps2);
							Color colour = new Color(red,green,blue);
							r.setColor(colour);
							r.setFill(filled);
							RectangleCommand rectangleCommand = new RectangleCommand(r);
							this.commands.add(rectangleCommand);
							state=1;
							break;
						} else {
							error("Error in line "+lineNumber);
							this.commands.clear();
							return false;
						}
					// ...
					case 12:
						m=pColor.matcher(l);
						if(m.matches()) {
							color =l;
							red = Integer.parseInt(m.group(1));
							green = Integer.parseInt(m.group(2));
							blue = Integer.parseInt(m.group(3));
							state=13;
							break;
						}
						else {
							error("Error in line "+lineNumber);
							this.commands.clear();
							return false;
						}
					case 13:
						m=pFilled.matcher(l);
						//System.out.println(m);
						if(m.matches()) {
							//System.out.println(m.group(1));
							String b = m.group(1);
							if (b == "true") {
								filled =true;
								state = 14;
								break;
							}
							else{ 
									filled = false;
									state = 14;
									break;
								}
							}
						else {
							error("Error in line "+lineNumber);
							this.commands.clear();
							return false;
						}
					case 14:
						m=pPointStart.matcher(l);
						if(m.matches()) {
							state=15;
							break;
						}
						else {
							error("Error in line "+lineNumber);
							this.commands.clear();
							return false;
						}
					case 15:
						m=pPoint.matcher(l);
						if(m.matches()) {
							
							p1 = Integer.parseInt(m.group(1));
							p2 = Integer.parseInt(m.group(2));
							Point p = new Point(p1,p2);
							points.add(p);
							state=15;
							break;
						}
						m=pPointEnd.matcher(l);
						if(m.matches()) {
						
							state=16;
							break;
						}
						else {
							error("Error in line "+lineNumber);
							this.commands.clear();
							return false;
						}
					case 16:
						m=pSquiggleEnd.matcher(l);
						if(m.matches()) {
							Squiggle squiggles = new Squiggle();
							for (int i=0;i<points.size();i++) {
								squiggles.add(points.get(i));
								
							}
							Color colour = new Color(red,green,blue);
							squiggles.setColor(colour);
							squiggles.setFill(filled);
							SquiggleCommand squiggleCommand = new SquiggleCommand(squiggles);
							this.commands.add(squiggleCommand);
							state=1;
							//System.out.println("empty");
							break;
						}
						else {
							error("Error in line "+lineNumber);
							this.commands.clear();
							return false;
						}
					case 18:
						{
							error("Error in line "+lineNumber);
							this.commands.clear();
							return false;
						}
				}
			}
		}  catch (Exception e){
			
		}
		if (endFile!=true) {
			this.commands.clear();
		}
		return endFile;
	}
}
