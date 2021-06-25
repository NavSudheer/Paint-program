package ca.utoronto.utm.paint;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.ArrayList;

public class RectangleCommand implements PaintCommand {
	private Rectangle rectangle;
	public RectangleCommand(Rectangle rectangle){
		this.rectangle = rectangle;
	}
	public void execute(Graphics2D g2d){
		g2d.setColor(rectangle.getColor());
		Point topLeft = this.rectangle.getTopLeft();
		Point dimensions = this.rectangle.getDimensions();
		if(rectangle.isFill()){
			g2d.fillRect(topLeft.x, topLeft.y, dimensions.x, dimensions.y);
		} else {
			g2d.drawRect(topLeft.x, topLeft.y, dimensions.x, dimensions.y);
		}
	}
	@Override
	public String info() {
		// TODO Auto-generated method stub
		String s ="Rectangle"+"\n"; 
		s+=this.rectangle.toString();
		s+="\tp1:"+"("+this.rectangle.getTopLeft().x+","+ this.rectangle.getTopLeft().y+")\n";
		s+="\tp2:"+"("+this.rectangle.getBottomRight().x+","+ this.rectangle.getBottomRight().y+")\n";
		s+="EndRectangle"+"\n";
		
		return s;
	}
}
