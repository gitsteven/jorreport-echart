/*     */ package jatools.component.chart.applet;
/*     */ 
/*     */ import jatools.component.chart.chart.Gc;
/*     */ import jatools.core.view.FontUtil;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class ChartUtil
/*     */ {
/*     */   public static Color getColor(String s)
/*     */   {
/*  32 */     if (s.equalsIgnoreCase("black")) {
/*  33 */       return Color.black;
/*     */     }
/*     */ 
/*  36 */     if (s.equalsIgnoreCase("white")) {
/*  37 */       return Color.white;
/*     */     }
/*     */ 
/*  40 */     if (s.equalsIgnoreCase("lightGray")) {
/*  41 */       return Color.lightGray;
/*     */     }
/*     */ 
/*  44 */     if (s.equalsIgnoreCase("gray")) {
/*  45 */       return Color.gray;
/*     */     }
/*     */ 
/*  48 */     if (s.equalsIgnoreCase("darkGray")) {
/*  49 */       return Color.darkGray;
/*     */     }
/*     */ 
/*  52 */     if (s.equalsIgnoreCase("red")) {
/*  53 */       return Color.red;
/*     */     }
/*     */ 
/*  56 */     if (s.equalsIgnoreCase("pink")) {
/*  57 */       return Color.pink;
/*     */     }
/*     */ 
/*  60 */     if (s.equalsIgnoreCase("orange")) {
/*  61 */       return Color.orange;
/*     */     }
/*     */ 
/*  64 */     if (s.equalsIgnoreCase("yellow")) {
/*  65 */       return Color.yellow;
/*     */     }
/*     */ 
/*  68 */     if (s.equalsIgnoreCase("green")) {
/*  69 */       return Color.green;
/*     */     }
/*     */ 
/*  72 */     if (s.equalsIgnoreCase("magenta")) {
/*  73 */       return Color.magenta;
/*     */     }
/*     */ 
/*  76 */     if (s.equalsIgnoreCase("cyan")) {
/*  77 */       return Color.cyan;
/*     */     }
/*     */ 
/*  80 */     if (s.equalsIgnoreCase("blue")) {
/*  81 */       return Color.blue;
/*     */     }
/*     */ 
/*  84 */     if (s.equalsIgnoreCase("transparent")) {
/*  85 */       return Gc.TRANSPARENT;
/*     */     }
/*     */     try
/*     */     {
/*  89 */       if (s.startsWith("0x")) {
/*  90 */         s = s.substring(2);
/*     */       }
/*     */ 
/*  93 */       return new Color(Integer.parseInt(s, 16));
/*     */     } catch (NumberFormatException e) {
/*  95 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  98 */     return Color.black;
/*     */   }
/*     */ 
/*     */   public static Font getFont(String prop)
/*     */   {
/* 106 */     if (prop == null) {
/* 107 */       return Gc.defaultFont;
/*     */     }
/* 109 */     String[] propArr = prop.split(",");
/*     */ 
/* 111 */     return FontUtil.getFont(new Font(propArr[0], Integer.parseInt(propArr[2]), 
/* 112 */       Integer.parseInt(propArr[1])));
/*     */   }
/*     */ 
/*     */   public static String toString(Font font)
/*     */   {
/* 121 */     if (font == null) {
/* 122 */       return null;
/*     */     }
/* 124 */     return font.getName() + "," + font.getSize() + "," + font.getStyle();
/*     */   }
/*     */ 
/*     */   public static String toString(Color color)
/*     */   {
/* 133 */     if (color == null) {
/* 134 */       return "transparent";
/*     */     }
/*     */ 
/* 137 */     return "0x" + Integer.toHexString(color.getRGB() & 0xFFFFFF);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 146 */     System.out.println(toString(getColor("0x90ffab")));
/* 147 */     System.out.println(Integer.parseInt("FFF9999", 16));
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.applet.ChartUtil
 * JD-Core Version:    0.6.2
 */