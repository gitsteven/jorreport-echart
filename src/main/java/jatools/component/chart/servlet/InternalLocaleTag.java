/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.Locale;
/*    */ import javax.servlet.jsp.JspException;
/*    */ import javax.servlet.jsp.tagext.TagSupport;
/*    */ 
/*    */ public class InternalLocaleTag extends TagSupport
/*    */ {
/*    */   private String localeString;
/*    */   public Locale locale;
/*    */ 
/*    */   public void setLocaleString(String s)
/*    */   {
/*    */     try
/*    */     {
/* 23 */       String s1 = s.substring(0, s.indexOf("_"));
/* 24 */       String s2 = s.substring(s1.length() + 1);
/* 25 */       this.locale = new Locale(s1, s2);
/*    */     }
/*    */     catch (StringIndexOutOfBoundsException e) {
/* 28 */       System.out.println("Invalid locale:" + s);
/* 29 */       this.locale = Locale.getDefault();
/*    */     }
/*    */   }
/*    */ 
/*    */   public int doStartTag() throws JspException {
/*    */     try {
/* 35 */       ChartTag chartTag = (ChartTag)
/* 36 */         findAncestorWithClass(this, Class.forName("javachart.servlet.ChartTag"));
/*    */ 
/* 38 */       chartTag.setLocale(this.locale);
/*    */     } catch (ClassNotFoundException e) {
/* 40 */       throw new JspException("Couldn't find an outer chart tag.");
/*    */     }
/* 42 */     return 0;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.InternalLocaleTag
 * JD-Core Version:    0.6.2
 */