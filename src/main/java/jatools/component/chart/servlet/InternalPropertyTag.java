/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import javax.servlet.jsp.JspException;
/*    */ import javax.servlet.jsp.tagext.TagSupport;
/*    */ 
/*    */ public class InternalPropertyTag extends TagSupport
/*    */ {
/*    */   private String name;
/*    */   private String value;
/*    */ 
/*    */   public void setName(String s)
/*    */   {
/* 27 */     this.name = s;
/*    */   }
/*    */ 
/*    */   public void setValue(String s)
/*    */   {
/* 35 */     this.value = s;
/*    */   }
/*    */ 
/*    */   public int doStartTag() throws JspException {
/*    */     try {
/* 40 */       ChartTag chartTag = (ChartTag)
/* 41 */         findAncestorWithClass(this, Class.forName("javachart.servlet.ChartTag"));
/*    */ 
/* 43 */       chartTag.addInternalProperty(this.name, this.value);
/*    */     } catch (ClassNotFoundException e) {
/* 45 */       throw new JspException("Couldn't find an outer chart tag.");
/*    */     }
/* 47 */     return 0;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.InternalPropertyTag
 * JD-Core Version:    0.6.2
 */