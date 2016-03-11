package model;


public class LineModel implements ILineModel {
        
        private double x1;
        private double y1;
        private double x2;
        private double y2;
        private double width;
        private String color;
        private double[] style;
        
        public LineModel(double x1, double y1, double x2, double y2, double width, String color, double[] style) {
                this.x1 = x1;
                this.x2 = x2;
                this.y1 = y1;
                this.y2 = y2;
                this.color = color;
                this.width = width;
                this.style = style;
        }
        
        public double getX1() {
                return x1;
        }
        
        public double getX2() {
                return x2;
        }
        
        public double getY1() {
                return y1;
        }
        
        public double getY2() {
                return y2;
        }
        
        public double getWidth() {
                return width;
        }
        
        public String getColor() {
                return color;
        }
        
        public double[] getStyle() {
                return style;
        }
        
        public ILineModel copyLineModel() {
            return new LineModel(this.x1, this.y1, this.x2, this.y2, this.width, this.color, this.style);
        }
        
}