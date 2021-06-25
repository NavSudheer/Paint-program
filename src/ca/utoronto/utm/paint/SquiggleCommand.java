package ca.utoronto.utm.paint;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SquiggleCommand implements PaintCommand {
	private Squiggle squiggle;
	public SquiggleCommand(Squiggle squiggle){
		this.squiggle = squiggle;
	}
	public void execute(Graphics2D g2d){
		ArrayList<Point> points = this.squiggle.getPoints();
		g2d.setColor(squiggle.getColor());
		for(int i=0;i<points.size()-1;i++){
			Point p1 = points.get(i);
			Point p2 = points.get(i+1);
			g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
	}
	@Override
	public String info() {
		// TODO Auto-generated method stub
		String s ="Squiggle"+"\n"; 
		s+=this.squiggle.toString();
		s+="\tpoints"+"\n";
		for (int i=0;i<this.squiggle.getPoints().size()-1;i++) {
			s+="\t"+"\t"+"point:("+this.squiggle.getPoints().get(i).x+","+this.squiggle.getPoints().get(i).y+")"+"\n";
		}
		s+="end points"+"\n";
		s+="EndSquiggle"+"\n";
		return s;
	}
}
