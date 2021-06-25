package ca.utoronto.utm.paint;

import javax.swing.*;  
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

class PaintPanel extends JPanel {
	private static final long serialVersionUID = 3277442988868869424L;
	private ArrayList<PaintCommand> commands = new ArrayList<PaintCommand>();
	private PaintSaveFileParser paintSaveFileParser = new PaintSaveFileParser();
	
	public PaintPanel(){
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(300,300));
	}
	
	public void setCommands(ArrayList<PaintCommand> commands){
		this.commands=commands;
	}
	public void reset(){
		this.commands.clear();
		this.repaint();
	}
	
	public void addCommand(PaintCommand command){
		this.commands.add(command);
	}
	public void save(PrintWriter writer){
		//writer.write("Hello");
		writer.write("PaintSaveFileVersion1.0"+"\n");
		for(int i =0; i<this.commands.size();i++) {
			writer.write(this.commands.get(i).info());
			
		}
		writer.write("End Paint Save File");
		writer.close();
	}
	public void openFile(FileReader fileReader) {
			
			BufferedReader in = new BufferedReader(fileReader);
			paintSaveFileParser.parse(in);
			this.reset();
			this.setCommands(paintSaveFileParser.getCommands());
//			Scanner s = new Scanner(in);
//			while (s.hasNextLine()) {
//				String line = s.nextLine();
//					System.out.println(line);
//			}
//			s.close();
		} 
	public void paintComponent(Graphics g) {
        super.paintComponent(g); //paint background
        Graphics2D g2d = (Graphics2D) g;		
		for(PaintCommand c: this.commands){
			c.execute(g2d);
		}
		g2d.dispose();
	}
}
