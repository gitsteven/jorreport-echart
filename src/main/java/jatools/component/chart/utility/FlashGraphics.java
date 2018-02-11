/*      */ package jatools.component.chart.utility;
/*      */ 
/*      */ import com.anotherbigidea.flash.movie.Actions;
/*      */ import com.anotherbigidea.flash.movie.Button;
/*      */ import com.anotherbigidea.flash.movie.FontDefinition;
/*      */ import com.anotherbigidea.flash.movie.FontLoader;
/*      */ import com.anotherbigidea.flash.movie.Frame;
/*      */ import com.anotherbigidea.flash.movie.Instance;
/*      */ import com.anotherbigidea.flash.movie.Movie;
/*      */ import com.anotherbigidea.flash.movie.Text;
/*      */ import com.anotherbigidea.flash.movie.Transform;
/*      */ import com.anotherbigidea.flash.structs.AlphaTransform;
/*      */ import jatools.component.chart.Tip;
/*      */ import jatools.component.chart.chart.ChartInterface;
/*      */ import jatools.component.chart.chart.Dataset;
/*      */ import jatools.component.chart.chart.GanttChart;
/*      */ import jatools.component.chart.chart.Globals;
/*      */ import jatools.component.chart.chart.Highlighter;
/*      */ import jatools.component.chart.chart.HorizBarChart;
/*      */ import jatools.component.chart.chart.PieChart;
/*      */ import jatools.component.chart.chart.StackBarChart;
/*      */ import jatools.component.chart.servlet.Bean;
/*      */ import java.awt.Composite;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.GradientPaint;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.Image;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.RenderingHints.Key;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.font.FontRenderContext;
/*      */ import java.awt.font.GlyphVector;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.PathIterator;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Point2D.Float;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.BufferedImageOp;
/*      */ import java.awt.image.ImageObserver;
/*      */ import java.awt.image.RenderedImage;
/*      */ import java.awt.image.renderable.RenderableImage;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.text.AttributedCharacterIterator;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import org.krysalis.barcode4j.output.java2d.DrawGlyphVectorDisabled;
/*      */ 
/*      */ public class FlashGraphics extends Graphics2D
/*      */   implements Cloneable, DrawGlyphVectorDisabled
/*      */ {
/*   67 */   static Hashtable fontDefs = null;
/*   68 */   static Properties fontMap = null;
/*      */   static final int dwellPadding = 4;
/*      */   static final int TIP_ABOVE = 0;
/*      */   static final int TIP_RIGHT = 1;
/*      */   static final int TIP_CENTERED = 2;
/*      */   Graphics2D baseGraphics;
/*      */   Movie movie;
/*      */   Frame baseFrame;
/*      */   com.anotherbigidea.flash.movie.Font font;
/*      */   Point2D translate;
/*      */   Point2D scale2;
/*      */   double rotation;
/*      */   double rotationX;
/*      */   double rotationY;
/*      */   Paint paint;
/*      */   Bean bean;
/*      */   boolean useToolTips;
/*      */   int linkLayer;
/*      */   boolean linkMapDone;
/*      */   com.anotherbigidea.flash.movie.Font toolTipFont;
/*      */   com.anotherbigidea.flash.structs.Color toolTipColor;
/*      */   protected int toolTipAlignment;
/*      */ 
/*      */   public FlashGraphics(Graphics2D g, int width, int height)
/*      */   {
/*   99 */     this.movie = null;
/*  100 */     this.baseFrame = null;
/*  101 */     this.translate = new Point2D.Float(0.0F, 0.0F);
/*  102 */     this.rotation = 0.0D;
/*  103 */     this.rotationX = (-1.0D / 0.0D);
/*  104 */     this.rotationY = (-1.0D / 0.0D);
/*  105 */     this.paint = null;
/*  106 */     this.bean = null;
/*  107 */     this.useToolTips = true;
/*  108 */     this.linkLayer = 1;
/*  109 */     this.linkMapDone = false;
/*  110 */     this.toolTipFont = null;
/*  111 */     this.toolTipColor = new com.anotherbigidea.flash.structs.Color(0, 0, 0);
/*  112 */     this.toolTipAlignment = 0;
/*  113 */     this.baseGraphics = g;
/*  114 */     this.movie = new Movie();
/*  115 */     this.movie.setWidth(width);
/*  116 */     this.movie.setHeight(height);
/*  117 */     this.baseFrame = this.movie.appendFrame();
/*  118 */     this.baseFrame.stop();
/*      */   }
/*      */ 
/*      */   public void draw(java.awt.Shape shape)
/*      */   {
/*  127 */     double multiplier = 10.0D;
/*      */ 
/*  129 */     if (this.scale2 != null) {
/*  130 */       multiplier *= this.scale2.getX();
/*      */     }
/*      */ 
/*  133 */     PathIterator pi = shape.getPathIterator(null);
/*  134 */     double[] coords = new double[2];
/*  135 */     pi.currentSegment(coords);
/*      */ 
/*  137 */     com.anotherbigidea.flash.movie.Shape s = new com.anotherbigidea.flash.movie.Shape();
/*  138 */     java.awt.Color c = this.baseGraphics.getColor();
/*  139 */     s.defineLineStyle(1.0D * multiplier, new com.anotherbigidea.flash.structs.Color(c.getRed(), c.getGreen(), c.getBlue()));
/*  140 */     s.setLineStyle(1);
/*  141 */     s.move((int)(coords[0] * multiplier), (int)(coords[1] * multiplier));
/*      */ 
/*  143 */     int startX = (int)(coords[0] * multiplier);
/*  144 */     int startY = (int)(coords[1] * multiplier);
/*  145 */     pi.next();
/*      */ 
/*  147 */     for (; !pi.isDone(); pi.next()) {
/*  148 */       pi.currentSegment(coords);
/*  149 */       s.line((int)(coords[0] * multiplier), (int)(coords[1] * multiplier));
/*      */     }
/*      */ 
/*  152 */     s.line(startX, startY);
/*      */ 
/*  154 */     Instance instance = this.baseFrame.placeSymbol(s, 0, 0);
/*  155 */     Transform t = new Transform(0.0D, 0.1D, 0.1D, this.translate.getX(), this.translate.getY());
/*  156 */     this.baseFrame.alter(instance, t, null);
/*      */   }
/*      */ 
/*      */   public void draw2(java.awt.Shape shape) {
/*  160 */     double multiplier = 10.0D;
/*      */ 
/*  162 */     if (this.scale2 != null) {
/*  163 */       multiplier *= this.scale2.getX();
/*      */     }
/*      */ 
/*  166 */     PathIterator pi = shape.getPathIterator(null);
/*  167 */     double[] coords = new double[2];
/*  168 */     pi.currentSegment(coords);
/*      */ 
/*  170 */     com.anotherbigidea.flash.movie.Shape s = new com.anotherbigidea.flash.movie.Shape();
/*  171 */     java.awt.Color c = this.baseGraphics.getColor();
/*  172 */     s.defineLineStyle(0.1D * multiplier, new com.anotherbigidea.flash.structs.Color(c.getRed(), c.getGreen(), c.getBlue()));
/*  173 */     s.setLineStyle(1);
/*  174 */     s.move((int)(coords[0] * multiplier), (int)(coords[1] * multiplier));
/*      */ 
/*  176 */     int startX = (int)(coords[0] * multiplier);
/*  177 */     int startY = (int)(coords[1] * multiplier);
/*  178 */     pi.next();
/*      */ 
/*  180 */     for (; !pi.isDone(); pi.next()) {
/*  181 */       pi.currentSegment(coords);
/*  182 */       s.line((int)(coords[0] * multiplier), (int)(coords[1] * multiplier));
/*      */     }
/*      */ 
/*  187 */     Instance instance = this.baseFrame.placeSymbol(s, 0, 0);
/*  188 */     Transform t = new Transform(0.0D, 0.1D, 0.1D, this.translate.getX(), this.translate.getY());
/*  189 */     this.baseFrame.alter(instance, t, null);
/*      */   }
/*      */ 
/*      */   public boolean drawImage(Image arg0, AffineTransform arg1, ImageObserver arg2)
/*      */   {
/*  203 */     return false;
/*      */   }
/*      */ 
/*      */   public void drawImage(BufferedImage bufferedimage, BufferedImageOp bufferedimageop, int i, int j)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void drawRenderedImage(RenderedImage renderedimage, AffineTransform affinetransform)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void drawRenderableImage(RenderableImage renderableimage, AffineTransform affinetransform)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void drawString(String s, int x, int y)
/*      */   {
/*  243 */     drawString(s, x, y);
/*      */   }
/*      */ 
/*      */   public void drawString(String s, float x, float y)
/*      */   {
/*  254 */     if (s == "") {
/*  255 */       return;
/*      */     }
/*      */ 
/*  258 */     if (this.font == null) {
/*  259 */       this.font = SystemFontDefinition.getDialogFlashFont();
/*      */     }
/*      */ 
/*  262 */     if ((this.font.getDefinition() instanceof SystemFontDefinition)) {
/*  263 */       SystemFontDefinition sf = (SystemFontDefinition)this.font.getDefinition();
/*      */ 
/*  265 */       if (!sf.canDisplayAll(s)) {
/*  266 */         this.font = SystemFontDefinition.getDefaultFlashFont();
/*      */       }
/*      */     }
/*      */ 
/*  270 */     java.awt.Color c = this.baseGraphics.getColor();
/*  271 */     int size = this.baseGraphics.getFont().getSize();
/*  272 */     Text text = new Text(null);
/*      */ 
/*  274 */     boolean rotate = false;
/*      */ 
/*  276 */     if (Math.abs(this.rotation) > 0.01D) {
/*  277 */       rotate = true;
/*      */     }
/*      */     try
/*      */     {
/*  281 */       if (rotate)
/*  282 */         text.row(this.font.chars(s, size), new com.anotherbigidea.flash.structs.Color(c.getRed(), c.getGreen(), c.getBlue()), 
/*  283 */           0.0D, 0.0D, true, true);
/*      */       else {
/*  285 */         text.row(this.font.chars(s, size), new com.anotherbigidea.flash.structs.Color(c.getRed(), c.getGreen(), c.getBlue()), x, 
/*  286 */           y, true, true);
/*      */       }
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*  292 */     Instance instance = this.baseFrame.placeSymbol(text, 0, 0);
/*      */ 
/*  294 */     if (rotate) {
/*  295 */       Transform t = new Transform(this.rotation, 
/*  296 */         this.rotationX - this.rotationY + y + this.translate.getX(), 
/*  297 */         this.rotationY + this.rotationX - x + this.translate.getY());
/*  298 */       this.baseFrame.alter(instance, t, null);
/*      */     }
/*      */ 
/*  301 */     if (this.scale2 != null)
/*  302 */       this.baseFrame.alter(instance, 
/*  303 */         new Transform(0.0D, this.scale2.getX(), this.scale2.getX(), this.translate.getX(), this.translate.getY()), 
/*  304 */         null);
/*      */   }
/*      */ 
/*      */   public void drawString(AttributedCharacterIterator attributedcharacteriterator, int i, int j)
/*      */   {
/*  316 */     System.out.println("draw String in float space");
/*      */   }
/*      */ 
/*      */   public void drawString(AttributedCharacterIterator attributedcharacteriterator, float f, float f1)
/*      */   {
/*  328 */     System.out.println("draw String in float space");
/*      */   }
/*      */ 
/*      */   public void drawGlyphVector(GlyphVector glyphvector, float x, float y)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void fill(java.awt.Shape shape)
/*      */   {
/*  352 */     double multiplier = 5.0D;
/*      */ 
/*  354 */     if (this.scale2 != null) {
/*  355 */       multiplier = 5.0D * this.scale2.getX();
/*      */     }
/*      */ 
/*  358 */     PathIterator pi = shape.getPathIterator(null);
/*  359 */     double[] coords = new double[2];
/*  360 */     pi.currentSegment(coords);
/*      */ 
/*  362 */     com.anotherbigidea.flash.movie.Shape s = new com.anotherbigidea.flash.movie.Shape();
/*  363 */     java.awt.Color c = this.baseGraphics.getColor();
/*  364 */     s.defineFillStyle(new com.anotherbigidea.flash.structs.Color(c.getRed(), c.getGreen(), c.getBlue()));
/*  365 */     s.setRightFillStyle(1);
/*  366 */     s.move((int)(coords[0] * multiplier), (int)(coords[1] * multiplier));
/*      */ 
/*  368 */     int startX = (int)(coords[0] * multiplier);
/*  369 */     int startY = (int)(coords[1] * multiplier);
/*  370 */     pi.next();
/*      */ 
/*  372 */     for (; !pi.isDone(); pi.next()) {
/*  373 */       pi.currentSegment(coords);
/*  374 */       s.line((int)(coords[0] * multiplier), (int)(coords[1] * multiplier));
/*      */     }
/*      */ 
/*  377 */     s.line(startX, startY);
/*      */ 
/*  379 */     Instance instance = this.baseFrame.placeSymbol(s, 0, 0);
/*  380 */     Transform t = new Transform(0.0D, 0.2D, 0.2D, this.translate.getX(), this.translate.getY());
/*      */ 
/*  382 */     this.baseFrame.alter(instance, t, null);
/*      */   }
/*      */ 
/*      */   public boolean hit(Rectangle arg0, java.awt.Shape arg1, boolean arg2)
/*      */   {
/*  400 */     return false;
/*      */   }
/*      */ 
/*      */   public GraphicsConfiguration getDeviceConfiguration()
/*      */   {
/*  409 */     return this.baseGraphics.getDeviceConfiguration();
/*      */   }
/*      */ 
/*      */   public void setComposite(Composite composite)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void setPaint(Paint paint)
/*      */   {
/*  426 */     this.paint = paint;
/*      */   }
/*      */ 
/*      */   public void setStroke(Stroke stroke)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void setRenderingHint(RenderingHints.Key key, Object obj)
/*      */   {
/*      */   }
/*      */ 
/*      */   public Object getRenderingHint(RenderingHints.Key arg0)
/*      */   {
/*  454 */     return this.baseGraphics.getRenderingHint(arg0);
/*      */   }
/*      */ 
/*      */   public void setRenderingHints(Map map)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void addRenderingHints(Map map)
/*      */   {
/*      */   }
/*      */ 
/*      */   public RenderingHints getRenderingHints()
/*      */   {
/*  479 */     return this.baseGraphics.getRenderingHints();
/*      */   }
/*      */ 
/*      */   public void translate(int x, int y)
/*      */   {
/*  489 */     translate(x, y);
/*      */   }
/*      */ 
/*      */   public void translate(double x, double y)
/*      */   {
/*  499 */     this.translate.setLocation(this.translate.getX() + x, this.translate.getY() + y);
/*      */   }
/*      */ 
/*      */   public void rotate(double arg0)
/*      */   {
/*  508 */     System.out.println("rotate double");
/*      */   }
/*      */ 
/*      */   public void rotate(double rot, double x, double y)
/*      */   {
/*  519 */     this.rotation += rot;
/*  520 */     this.rotationX = x;
/*  521 */     this.rotationY = y;
/*      */   }
/*      */ 
/*      */   public void scale(double d, double d1)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void shear(double d, double d1)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void transform(AffineTransform arg0)
/*      */   {
/*  548 */     System.out.println("transform to");
/*      */   }
/*      */ 
/*      */   public void setTransform(AffineTransform affinetransform)
/*      */   {
/*      */   }
/*      */ 
/*      */   public AffineTransform getTransform()
/*      */   {
/*  565 */     return this.baseGraphics.getTransform();
/*      */   }
/*      */ 
/*      */   public Paint getPaint()
/*      */   {
/*  574 */     return this.baseGraphics.getPaint();
/*      */   }
/*      */ 
/*      */   public Composite getComposite()
/*      */   {
/*  583 */     return this.baseGraphics.getComposite();
/*      */   }
/*      */ 
/*      */   public void setBackground(java.awt.Color color)
/*      */   {
/*      */   }
/*      */ 
/*      */   public java.awt.Color getBackground()
/*      */   {
/*  600 */     return this.baseGraphics.getBackground();
/*      */   }
/*      */ 
/*      */   public Stroke getStroke()
/*      */   {
/*  609 */     return this.baseGraphics.getStroke();
/*      */   }
/*      */ 
/*      */   public void clip(java.awt.Shape shape)
/*      */   {
/*      */   }
/*      */ 
/*      */   public FontRenderContext getFontRenderContext()
/*      */   {
/*  626 */     return this.baseGraphics.getFontRenderContext();
/*      */   }
/*      */ 
/*      */   public Graphics create()
/*      */   {
/*      */     try
/*      */     {
/*  636 */       return (FlashGraphics)clone();
/*      */     }
/*      */     catch (CloneNotSupportedException e)
/*      */     {
/*  641 */       e.printStackTrace();
/*      */ 
/*  644 */       System.out.println("failed to create");
/*      */     }
/*  646 */     return null;
/*      */   }
/*      */ 
/*      */   public java.awt.Color getColor()
/*      */   {
/*  655 */     return this.baseGraphics.getColor();
/*      */   }
/*      */ 
/*      */   public void setColor(java.awt.Color arg0)
/*      */   {
/*  664 */     this.paint = null;
/*  665 */     this.baseGraphics.setColor(arg0);
/*      */   }
/*      */ 
/*      */   public void setPaintMode()
/*      */   {
/*      */   }
/*      */ 
/*      */   public void setXORMode(java.awt.Color color)
/*      */   {
/*      */   }
/*      */ 
/*      */   public java.awt.Font getFont()
/*      */   {
/*  688 */     return this.baseGraphics.getFont();
/*      */   }
/*      */ 
/*      */   public void setFont(java.awt.Font f)
/*      */   {
/*  697 */     if (fontMap == null) {
/*  698 */       loadFontMap();
/*      */     }
/*      */ 
/*  701 */     String fontName = f.getName();
/*  702 */     String flashFontName = fontMap.getProperty(fontName);
/*      */     FontDefinition fd;
/*      */     FontDefinition fd;
/*  705 */     if (flashFontName == null) {
/*  706 */       this.baseGraphics.setFont(new java.awt.Font("Verdana", f.getStyle(), f.getSize()));
/*      */ 
/*  708 */       fd = new SystemFontDefinition(f);
/*      */     } else {
/*  710 */       this.baseGraphics.setFont(f);
/*  711 */       fd = getFontDefinition(flashFontName);
/*      */     }
/*      */ 
/*  714 */     this.font = new com.anotherbigidea.flash.movie.Font(fd);
/*      */   }
/*      */ 
/*      */   public FontMetrics getFontMetrics(java.awt.Font f)
/*      */   {
/*  725 */     return this.baseGraphics.getFontMetrics(f);
/*      */   }
/*      */ 
/*      */   public Rectangle getClipBounds()
/*      */   {
/*  734 */     return this.baseGraphics.getClipBounds();
/*      */   }
/*      */ 
/*      */   public void clipRect(int arg0, int arg1, int arg2, int arg3)
/*      */   {
/*  746 */     System.out.println("clipRect");
/*      */   }
/*      */ 
/*      */   public void setClip(int arg0, int arg1, int arg2, int arg3)
/*      */   {
/*  758 */     System.out.println("setClip");
/*      */   }
/*      */ 
/*      */   public java.awt.Shape getClip()
/*      */   {
/*  767 */     return this.baseGraphics.getClip();
/*      */   }
/*      */ 
/*      */   public void setClip(java.awt.Shape shape)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void copyArea(int i, int j, int k, int l, int i1, int j1)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void drawLine(int x0, int y0, int x1, int y1)
/*      */   {
/*  800 */     com.anotherbigidea.flash.movie.Shape s = new com.anotherbigidea.flash.movie.Shape();
/*  801 */     java.awt.Color c = this.baseGraphics.getColor();
/*  802 */     s.defineLineStyle(1.0D, new com.anotherbigidea.flash.structs.Color(c.getRed(), c.getGreen(), c.getBlue()));
/*  803 */     s.setLineStyle(1);
/*  804 */     s.move(x0, y0);
/*  805 */     s.line(x1, y1);
/*      */ 
/*  807 */     Instance instance = this.baseFrame.placeSymbol(s, 0, 0);
/*      */ 
/*  809 */     if ((Math.abs(this.translate.getX()) > 0.0D) || (Math.abs(this.translate.getY()) > 0.0D)) {
/*  810 */       Transform t = new Transform(0.0D, this.translate.getX(), this.translate.getY());
/*  811 */       this.baseFrame.alter(instance, t, null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void fillRect(int x, int y, int szX, int szY)
/*      */   {
/*  829 */     com.anotherbigidea.flash.movie.Shape s = new com.anotherbigidea.flash.movie.Shape();
/*      */ 
/*  832 */     if ((this.paint instanceof GradientPaint)) {
/*  833 */       buildGradient(s);
/*      */ 
/*  835 */       int multiplier = 5;
/*  836 */       s.setRightFillStyle(1);
/*  837 */       s.move(x * multiplier, y * multiplier);
/*  838 */       szX *= multiplier;
/*  839 */       szY *= multiplier;
/*  840 */       s.line(x * multiplier + szX, y * multiplier);
/*  841 */       s.line(x * multiplier + szX, y * multiplier + szY);
/*  842 */       s.line(x * multiplier, y * multiplier + szY);
/*  843 */       s.line(x * multiplier, y * multiplier);
/*  844 */       Instance instance = this.baseFrame.placeSymbol(s, 0, 0);
/*      */ 
/*  846 */       Transform t = new Transform(0.0D, 1.0D / multiplier, 
/*  847 */         1.0D / multiplier, this.translate.getX(), this.translate.getY());
/*  848 */       this.baseFrame.alter(instance, t, null);
/*      */ 
/*  854 */       return;
/*      */     }
/*      */ 
/*  857 */     java.awt.Color c = this.baseGraphics.getColor();
/*  858 */     s.defineFillStyle(new com.anotherbigidea.flash.structs.Color(c.getRed(), c.getGreen(), c.getBlue()));
/*  859 */     s.setRightFillStyle(1);
/*  860 */     s.move(x, y);
/*  861 */     s.line(x + szX, y);
/*  862 */     s.line(x + szX, y + szY);
/*  863 */     s.line(x, y + szY);
/*  864 */     s.line(x, y);
/*  865 */     Instance instance = this.baseFrame.placeSymbol(s, 0, 0);
/*      */ 
/*  867 */     if ((Math.abs(this.translate.getX()) > 0.0D) || (Math.abs(this.translate.getY()) > 0.0D)) {
/*  868 */       Transform t = new Transform(0.0D, this.translate.getX(), this.translate.getY());
/*  869 */       this.baseFrame.alter(instance, t, null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void drawRect(int x, int y, int szX, int szY)
/*      */   {
/*  888 */     com.anotherbigidea.flash.movie.Shape s = new com.anotherbigidea.flash.movie.Shape();
/*  889 */     java.awt.Color c = this.baseGraphics.getColor();
/*  890 */     s.defineLineStyle(1.0D, new com.anotherbigidea.flash.structs.Color(c.getRed(), c.getGreen(), c.getBlue()));
/*  891 */     s.setLineStyle(1);
/*  892 */     s.move(x, y);
/*  893 */     s.line(x + szX, y);
/*  894 */     s.line(x + szX, y + szY);
/*  895 */     s.line(x, y + szY);
/*  896 */     s.line(x, y);
/*      */ 
/*  898 */     Instance instance = this.baseFrame.placeSymbol(s, 0, 0);
/*      */ 
/*  900 */     if ((Math.abs(this.translate.getX()) > 0.0D) || (Math.abs(this.translate.getY()) > 0.0D)) {
/*  901 */       Transform t = new Transform(0.0D, this.translate.getX(), this.translate.getY());
/*  902 */       this.baseFrame.alter(instance, t, null);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void buildGradient(com.anotherbigidea.flash.movie.Shape s)
/*      */   {
/*  912 */     GradientPaint gp = (GradientPaint)this.paint;
/*  913 */     Point2D p1 = gp.getPoint1();
/*  914 */     Point2D p2 = gp.getPoint2();
/*  915 */     boolean isHorizontal = true;
/*      */     double rot;
/*      */     double height;
/*      */     double displayRatio;
/*      */     double rot;
/*  920 */     if (Math.abs(p1.getX() - p2.getX()) > 1.0D) {
/*  921 */       double height = Math.abs(p1.getX() - p2.getX());
/*  922 */       double displayRatio = height / this.movie.getWidth();
/*  923 */       rot = 0.0D;
/*      */     } else {
/*  925 */       height = Math.abs(p1.getY() - p2.getY());
/*  926 */       displayRatio = height / this.movie.getHeight();
/*  927 */       rot = 1.570796326794897D;
/*  928 */       isHorizontal = false;
/*      */     }
/*      */ 
/*  931 */     int numberOfTransitions = 1;
/*      */ 
/*  933 */     if (gp.isCyclic()) {
/*  934 */       numberOfTransitions = 2;
/*      */     }
/*      */ 
/*  937 */     com.anotherbigidea.flash.structs.Color[] colors = new com.anotherbigidea.flash.structs.Color[numberOfTransitions + 1];
/*  938 */     int[] ratios = new int[numberOfTransitions + 1];
/*  939 */     int startR = gp.getColor1().getRed();
/*  940 */     int startG = gp.getColor1().getGreen();
/*  941 */     int startB = gp.getColor1().getBlue();
/*  942 */     int endR = gp.getColor2().getRed();
/*  943 */     int endG = gp.getColor2().getGreen();
/*  944 */     int endB = gp.getColor2().getBlue();
/*  945 */     colors[0] = new com.anotherbigidea.flash.structs.Color(startR, startG, startB);
/*  946 */     colors[1] = new com.anotherbigidea.flash.structs.Color(endR, endG, endB);
/*      */ 
/*  948 */     if (isHorizontal) {
/*  949 */       if (gp.isCyclic()) {
/*  950 */         ratios[0] = 60;
/*  951 */         ratios[1] = 170;
/*  952 */         ratios[2] = 255;
/*  953 */         colors[2] = new com.anotherbigidea.flash.structs.Color(startR, startG, startB);
/*      */       } else {
/*  955 */         ratios[1] = 255;
/*      */       }
/*      */     } else {
/*  958 */       int center = 132;
/*  959 */       int offset = Math.min((int)height, 255 - center);
/*      */ 
/*  961 */       if (gp.isCyclic()) {
/*  962 */         ratios[0] = (center - offset);
/*  963 */         ratios[1] = center;
/*  964 */         ratios[2] = (center + offset);
/*  965 */         colors[2] = new com.anotherbigidea.flash.structs.Color(startR, startG, startB);
/*      */       } else {
/*  967 */         ratios[0] = (center - offset);
/*  968 */         ratios[1] = (center + offset);
/*      */       }
/*      */     }
/*      */ 
/*  972 */     if (isHorizontal) {
/*  973 */       Transform t = new Transform(rot, displayRatio * 5.0D, displayRatio * 5.0D, 
/*  974 */         height / 2.0D * 5.0D, height / 2.0D * 5.0D);
/*  975 */       s.defineFillStyle(colors, ratios, t, false);
/*      */     } else {
/*  977 */       Transform t = new Transform(rot, displayRatio, displayRatio, height / 2.0D * 5.0D, 
/*  978 */         height / 2.0D * 5.0D);
/*  979 */       s.defineFillStyle(colors, ratios, t, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void clearRect(int i, int j, int k, int l)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void drawRoundRect(int i, int j, int k, int l, int i1, int j1)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void fillRoundRect(int i, int j, int k, int l, int i1, int j1)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void drawOval(int i, int j, int k, int l)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void fillOval(int i, int j, int k, int l)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void drawArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5)
/*      */   {
/* 1053 */     System.out.println("drawArc");
/*      */   }
/*      */ 
/*      */   public void fillArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5)
/*      */   {
/* 1067 */     System.out.println("fillArc");
/*      */   }
/*      */ 
/*      */   public void drawPolyline(int[] x, int[] y, int n)
/*      */   {
/* 1078 */     if (n == 0) {
/* 1079 */       return;
/*      */     }
/*      */ 
/* 1082 */     com.anotherbigidea.flash.movie.Shape s = new com.anotherbigidea.flash.movie.Shape();
/* 1083 */     java.awt.Color c = this.baseGraphics.getColor();
/* 1084 */     s.defineLineStyle(1.0D, new com.anotherbigidea.flash.structs.Color(c.getRed(), c.getGreen(), c.getBlue()));
/* 1085 */     s.setLineStyle(1);
/* 1086 */     s.move(x[0], y[0]);
/*      */ 
/* 1088 */     for (int i = 1; i < n; i++) {
/* 1089 */       s.line(x[i], y[i]);
/*      */     }
/* 1091 */     Instance instance = this.baseFrame.placeSymbol(s, 0, 0);
/*      */ 
/* 1093 */     if ((Math.abs(this.translate.getX()) > 0.0D) || (Math.abs(this.translate.getY()) > 0.0D)) {
/* 1094 */       Transform t = new Transform(0.0D, this.translate.getX(), this.translate.getY());
/* 1095 */       this.baseFrame.alter(instance, t, null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void drawPolygon(int[] x, int[] y, int n)
/*      */   {
/* 1112 */     if (n == 0) {
/* 1113 */       return;
/*      */     }
/*      */ 
/* 1116 */     com.anotherbigidea.flash.movie.Shape s = new com.anotherbigidea.flash.movie.Shape();
/* 1117 */     java.awt.Color c = this.baseGraphics.getColor();
/* 1118 */     s.defineLineStyle(1.0D, new com.anotherbigidea.flash.structs.Color(c.getRed(), c.getGreen(), c.getBlue()));
/* 1119 */     s.setLineStyle(1);
/* 1120 */     s.move(x[0], y[0]);
/*      */ 
/* 1122 */     for (int i = 1; i < n; i++) {
/* 1123 */       s.line(x[i], y[i]);
/*      */     }
/* 1125 */     Instance instance = this.baseFrame.placeSymbol(s, 0, 0);
/*      */ 
/* 1127 */     if ((Math.abs(this.translate.getX()) > 0.0D) || (Math.abs(this.translate.getY()) > 0.0D)) {
/* 1128 */       Transform t = new Transform(0.0D, this.translate.getX(), this.translate.getY());
/* 1129 */       this.baseFrame.alter(instance, t, null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void fillPolygon(int[] x, int[] y, int n)
/*      */   {
/* 1146 */     com.anotherbigidea.flash.movie.Shape s = new com.anotherbigidea.flash.movie.Shape();
/*      */ 
/* 1148 */     if ((this.paint instanceof GradientPaint)) {
/* 1149 */       buildGradient(s);
/* 1150 */       s.setRightFillStyle(1);
/*      */ 
/* 1152 */       double multiplier = 5.0D;
/* 1153 */       s.move(x[0] * multiplier, y[0] * multiplier);
/*      */ 
/* 1155 */       for (int i = 1; i < n; i++) {
/* 1156 */         s.line(x[i] * multiplier, y[i] * multiplier);
/*      */       }
/* 1158 */       s.line(x[0] * multiplier, y[0] * multiplier);
/*      */ 
/* 1160 */       Instance instance = this.baseFrame.placeSymbol(s, 0, 0);
/* 1161 */       Transform t = new Transform(0.0D, 1.0D / multiplier, 1.0D / multiplier, 
/* 1162 */         this.translate.getX(), this.translate.getY());
/* 1163 */       this.baseFrame.alter(instance, t, null);
/*      */ 
/* 1169 */       return;
/*      */     }
/*      */ 
/* 1172 */     java.awt.Color c = this.baseGraphics.getColor();
/* 1173 */     s.defineFillStyle(new com.anotherbigidea.flash.structs.Color(c.getRed(), c.getGreen(), c.getBlue()));
/* 1174 */     s.setRightFillStyle(1);
/* 1175 */     s.move(x[0], y[0]);
/*      */ 
/* 1177 */     for (int i = 1; i < n; i++) {
/* 1178 */       s.line(x[i], y[i]);
/*      */     }
/* 1180 */     s.line(x[0], y[0]);
/*      */ 
/* 1182 */     Instance instance = this.baseFrame.placeSymbol(s, 0, 0);
/*      */ 
/* 1184 */     if ((Math.abs(this.translate.getX()) > 0.0D) || (Math.abs(this.translate.getY()) > 0.0D)) {
/* 1185 */       Transform t = new Transform(0.0D, this.translate.getX(), this.translate.getY());
/* 1186 */       this.baseFrame.alter(instance, t, null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean drawImage(Image arg0, int arg1, int arg2, ImageObserver arg3)
/*      */   {
/* 1207 */     System.out.println("drawImage img int int imageobserver");
/*      */ 
/* 1209 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean drawImage(Image arg0, int arg1, int arg2, int i, int j, ImageObserver imageobserver)
/*      */   {
/* 1226 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean drawImage(Image arg0, int arg1, int arg2, java.awt.Color color, ImageObserver imageobserver)
/*      */   {
/* 1242 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean drawImage(Image arg0, int arg1, int arg2, int i, int j, java.awt.Color color, ImageObserver imageobserver)
/*      */   {
/* 1260 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean drawImage(Image arg0, int arg1, int arg2, int i, int j, int k, int l, int i1, int j1, ImageObserver imageobserver)
/*      */   {
/* 1281 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean drawImage(Image arg0, int arg1, int arg2, int i, int j, int k, int l, int i1, int j1, java.awt.Color color, ImageObserver imageobserver)
/*      */   {
/* 1303 */     return false;
/*      */   }
/*      */ 
/*      */   public void dispose()
/*      */   {
/* 1310 */     this.baseGraphics.dispose();
/*      */   }
/*      */ 
/*      */   public void write(String s)
/*      */     throws IOException
/*      */   {
/* 1321 */     this.movie.write(s);
/*      */   }
/*      */ 
/*      */   public void write(OutputStream out)
/*      */     throws IOException
/*      */   {
/* 1332 */     this.movie.write(out);
/*      */   }
/*      */ 
/*      */   FontDefinition getFontDefinition(String name) {
/* 1336 */     if (fontDefs == null) {
/* 1337 */       loadFontMap();
/*      */     }
/*      */ 
/* 1340 */     FontDefinition fd = (FontDefinition)fontDefs.get(name);
/*      */ 
/* 1342 */     if (fd == null) {
/*      */       try {
/* 1344 */         fd = FontLoader.loadFont(getClass().getResourceAsStream("/flashfonts/" + name));
/*      */       } catch (IOException e) {
/* 1346 */         System.out.println("problem loading font glyphs at " + name);
/*      */       }
/*      */ 
/* 1349 */       fontDefs.put(name, fd);
/*      */     }
/*      */ 
/* 1352 */     return fd;
/*      */   }
/*      */ 
/*      */   void loadFontMap() {
/*      */     try {
/* 1357 */       fontMap = new Properties();
/* 1358 */       fontMap.load(getClass().getResourceAsStream("/flashfonts/font.properties"));
/*      */     } catch (Exception e) {
/* 1360 */       System.out.println("unable to get Flash font properties");
/*      */     }
/*      */ 
/* 1363 */     if (fontDefs == null)
/* 1364 */       fontDefs = new Hashtable();
/*      */   }
/*      */ 
/*      */   protected void getLinkMap(Bean bean)
/*      */   {
/* 1369 */     this.bean = bean;
/*      */ 
/* 1371 */     if (!bean.useToolTips) {
/* 1372 */       return;
/*      */     }
/*      */ 
/* 1375 */     ChartInterface chart = bean.chart;
/*      */ 
/* 1377 */     if (chart == null) {
/* 1378 */       return;
/*      */     }
/*      */ 
/* 1381 */     if ((chart instanceof PieChart))
/* 1382 */       this.toolTipAlignment = 2;
/* 1383 */     else if ((chart instanceof GanttChart))
/* 1384 */       this.toolTipAlignment = 2;
/* 1385 */     else if ((chart instanceof HorizBarChart))
/* 1386 */       this.toolTipAlignment = 1;
/* 1387 */     else if ((chart instanceof StackBarChart)) {
/* 1388 */       this.toolTipAlignment = 0;
/*      */     }
/*      */ 
/* 1391 */     generateLinkMap(chart);
/*      */   }
/*      */ 
/*      */   public void generateLinkMap(ChartInterface chart)
/*      */   {
/* 1400 */     if (this.linkMapDone) {
/* 1401 */       System.out.println("already generated a link map!");
/*      */     }
/*      */ 
/* 1404 */     Highlighter linker = new Highlighter(chart.getGlobals());
/* 1405 */     int h = chart.getGlobals().getMaxY();
/*      */ 
/* 1408 */     Dataset[] datasets = chart.getDatasets();
/*      */ 
/* 1410 */     setFont(new java.awt.Font("宋体", 0, 10));
/*      */ 
/* 1412 */     for (int i = 0; i < datasets.length; i++) {
/* 1413 */       Dataset d = datasets[i];
/*      */ 
/* 1415 */       if ((d != null) && (d.hasTip())) {
/* 1416 */         Tip[] tips = d.getTips();
/*      */ 
/* 1418 */         for (int j = 0; j < d.getData().size(); j++)
/*      */           try {
/* 1420 */             Tip tip = tips[j];
/*      */ 
/* 1422 */             if (tip != null) {
/* 1423 */               Point[][] coords = linker.getHighlightPointSet(d.getData().get(j));
/*      */ 
/* 1425 */               if (coords != null)
/* 1426 */                 for (int l = 0; l < coords.length; l++)
/* 1427 */                   addToolTipText(h, coords[l], tip.tip, tip.url, tip.target);
/*      */               else
/* 1429 */                 System.out.println("coords is null!");
/*      */             }
/*      */           }
/*      */           catch (Exception ignored) {
/* 1433 */             System.out.println("problem with link map:" + ignored.getMessage() + 
/* 1434 */               " at dataset " + i);
/*      */           }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void addToolTipText(int chartHeight, Point[] coords, String tip, String url, String target)
/*      */   {
/* 1442 */     Button b = new Button(true);
/* 1443 */     Transform t = new Transform();
/* 1444 */     com.anotherbigidea.flash.movie.Shape s = new com.anotherbigidea.flash.movie.Shape();
/* 1445 */     s.defineFillStyle(new com.anotherbigidea.flash.structs.Color(0, 0, 128));
/* 1446 */     s.setRightFillStyle(1);
/* 1447 */     s.move(coords[0].x, chartHeight - coords[0].y);
/*      */ 
/* 1449 */     for (int i = 1; i < coords.length; i++) {
/* 1450 */       s.line(coords[i].x, chartHeight - coords[i].y);
/*      */     }
/* 1452 */     s.line(coords[0].x, chartHeight - coords[0].y);
/*      */ 
/* 1454 */     AlphaTransform at = new AlphaTransform();
/* 1455 */     at.setMultAlpha(0.2D);
/* 1456 */     b.addLayer(s, t, at, this.linkLayer, true, false, false, true);
/* 1457 */     this.linkLayer += 1;
/*      */ 
/* 1459 */     Point tipSize = getTipSize(tip);
/* 1460 */     Point tipCenter = getTipCenter(coords);
/*      */ 
/* 1462 */     switch (this.toolTipAlignment) {
/*      */     case 0:
/* 1464 */       tipCenter.y += tipSize.y;
/*      */ 
/* 1466 */       break;
/*      */     case 1:
/* 1469 */       tipCenter.x += tipSize.x - 16;
/*      */     }
/*      */ 
/* 1474 */     if (tipCenter.x + 
/* 1474 */       tipSize.x / 2 > this.movie.getWidth() - 1) {
/* 1475 */       tipCenter.x = (this.movie.getWidth() - tipSize.x / 2 - 4 - 5);
/*      */     }
/*      */ 
/* 1478 */     if (tipCenter.y - tipSize.y / 2 < 1) {
/* 1479 */       tipCenter.y = (tipSize.y / 2 + 4 + 5);
/*      */     }
/*      */ 
/* 1482 */     if (tipCenter.x - tipSize.x / 2 < 1) {
/* 1483 */       tipCenter.x = (tipSize.x / 2 + 4 + 5);
/*      */     }
/*      */ 
/* 1486 */     if (tipCenter.y + tipSize.y / 2 > chartHeight - 1) {
/* 1487 */       tipCenter.y = (chartHeight - tipSize.y / 2 - 4 - 5);
/*      */     }
/*      */ 
/* 1490 */     at = new AlphaTransform();
/* 1491 */     s = new com.anotherbigidea.flash.movie.Shape();
/* 1492 */     s.defineFillStyle(new com.anotherbigidea.flash.structs.Color(255, 255, 188));
/* 1493 */     s.setRightFillStyle(1);
/* 1494 */     s.defineLineStyle(1.0D, new com.anotherbigidea.flash.structs.Color(0, 0, 0));
/* 1495 */     s.setLineStyle(1);
/* 1496 */     s.move(tipCenter.x - tipSize.x / 2 - 4, chartHeight - (
/* 1497 */       tipCenter.y - tipSize.y / 2 - 4));
/* 1498 */     s.line(tipCenter.x + tipSize.x / 2 + 4, chartHeight - (
/* 1499 */       tipCenter.y - tipSize.y / 2 - 4));
/* 1500 */     s.line(tipCenter.x + tipSize.x / 2 + 4, chartHeight - (
/* 1501 */       tipCenter.y + tipSize.y / 2 + 4));
/* 1502 */     s.line(tipCenter.x - tipSize.x / 2 - 4, chartHeight - (
/* 1503 */       tipCenter.y + tipSize.y / 2 + 4));
/* 1504 */     s.line(tipCenter.x - tipSize.x / 2 - 4, chartHeight - (
/* 1505 */       tipCenter.y - tipSize.y / 2 - 4));
/* 1506 */     b.addLayer(s, t, at, this.linkLayer, false, false, false, true);
/* 1507 */     this.linkLayer += 1;
/*      */ 
/* 1509 */     if (this.font == null) {
/* 1510 */       this.font = SystemFontDefinition.getDialogFlashFont();
/*      */     }
/*      */ 
/* 1513 */     if ((this.font.getDefinition() instanceof SystemFontDefinition)) {
/* 1514 */       SystemFontDefinition sf = (SystemFontDefinition)this.font.getDefinition();
/*      */ 
/* 1516 */       if (!sf.canDisplayAll(tip)) {
/* 1517 */         this.font = SystemFontDefinition.getDefaultFlashFont();
/*      */       }
/*      */     }
/*      */ 
/* 1521 */     Text text = new Text(null);
/*      */     try
/*      */     {
/* 1524 */       FontMetrics fm = this.baseGraphics.getFontMetrics();
/* 1525 */       int yStart = chartHeight - (tipCenter.y + tipSize.y / 2 - fm.getMaxAscent());
/* 1526 */       int xStart = tipCenter.x - tipSize.x / 2;
/* 1527 */       b.addLayer(text, t, at, this.linkLayer, false, false, false, true);
/*      */ 
/* 1529 */       String[] tips = tip.split("\n");
/*      */ 
/* 1531 */       int y = yStart;
/*      */ 
/* 1533 */       for (int i = 0; i < tips.length; i++) {
/* 1534 */         text.row(this.font.chars(tips[i], this.baseGraphics.getFont().getSize()), this.toolTipColor, 
/* 1535 */           xStart, y, true, true);
/*      */ 
/* 1538 */         y += 2 + fm.getMaxAscent();
/*      */       }
/*      */ 
/* 1544 */       Actions a = b.addActions(8, 3);
/*      */ 
/* 1546 */       if (url == null) {
/* 1547 */         url = "";
/*      */       }
/*      */ 
/* 1550 */       if (target == null) {
/* 1551 */         target = "_blank";
/*      */       }
/*      */ 
/* 1554 */       a.getURL(url, target);
/* 1555 */       a.end();
/*      */     } catch (Exception missingGlyph) {
/* 1557 */       System.out.println("Can't find a glyph");
/*      */     }
/*      */ 
/* 1560 */     this.baseFrame.placeSymbol(b, 0, 0);
/*      */   }
/*      */ 
/*      */   private Point getTipSize(String tip) {
/* 1564 */     FontMetrics fm = this.baseGraphics.getFontMetrics();
/* 1565 */     int yVal = 0;
/* 1566 */     int xVal = 0;
/*      */ 
/* 1568 */     yVal += 4;
/*      */ 
/* 1570 */     String[] tips = tip.split("\n");
/*      */ 
/* 1572 */     for (int i = 0; i < tips.length; i++) {
/* 1573 */       xVal = Math.max(xVal, fm.stringWidth(tips[i]));
/* 1574 */       yVal += fm.getMaxAscent();
/*      */ 
/* 1576 */       if (i > 0) {
/* 1577 */         yVal += 2;
/*      */       }
/*      */     }
/*      */ 
/* 1581 */     return new Point(xVal, yVal);
/*      */   }
/*      */ 
/*      */   private Point getTipCenter(Point[] points) {
/* 1585 */     int maxX = 0;
/* 1586 */     int maxY = 0;
/* 1587 */     int minX = 2147483647;
/* 1588 */     int minY = 2147483647;
/*      */ 
/* 1590 */     for (int i = 0; i < points.length; i++) {
/* 1591 */       maxX = Math.max(maxX, points[i].x);
/* 1592 */       maxY = Math.max(maxY, points[i].y);
/* 1593 */       minX = Math.min(minX, points[i].x);
/* 1594 */       minY = Math.min(minY, points[i].y);
/*      */     }
/*      */ 
/* 1597 */     int centerX = minX + (maxX - minX) / 2;
/* 1598 */     int centerY = minY + (maxY - minY) / 2;
/*      */ 
/* 1600 */     switch (this.toolTipAlignment) {
/*      */     case 2:
/* 1602 */       return new Point(centerX, centerY);
/*      */     case 0:
/* 1605 */       return new Point(centerX, maxY);
/*      */     case 1:
/* 1608 */       return new Point(maxX, centerY);
/*      */     }
/*      */ 
/* 1611 */     return new Point(centerX, centerY);
/*      */   }
/*      */ 
/*      */   public void setScale(Point2D scale)
/*      */   {
/* 1620 */     this.scale2 = scale;
/*      */   }
/*      */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.utility.FlashGraphics
 * JD-Core Version:    0.6.2
 */