/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.DataProvider;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import javax.servlet.jsp.JspException;
/*    */ import javax.servlet.jsp.tagext.TagSupport;
/*    */ 
/*    */ public class RandomDataProvider extends TagSupport
/*    */   implements DataProvider
/*    */ {
/* 24 */   protected int numDatasets = 3;
/* 25 */   protected int numObservations = 5;
/*    */ 
/* 28 */   ArrayList datasets = new ArrayList();
/* 29 */   String uniqueIdentifier = null;
/*    */ 
/* 90 */   String[] chars = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", 
/* 91 */     "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
/*    */ 
/*    */   public void setNumDatasets(int i)
/*    */   {
/* 36 */     this.numDatasets = i;
/*    */   }
/*    */ 
/*    */   public void setNumObservations(int i)
/*    */   {
/* 44 */     this.numObservations = i;
/*    */   }
/*    */ 
/*    */   public int doStartTag() throws JspException {
/*    */     try {
/* 49 */       ChartTag chartTag = (ChartTag)
/* 50 */         findAncestorWithClass(this, Class.forName("javachart.servlet.ChartTag"));
/*    */ 
/* 52 */       createData();
/* 53 */       chartTag.setDataProvider(this);
/*    */     } catch (ClassNotFoundException e) {
/* 55 */       throw new JspException("Couldn't find an outer chart tag to provide data to.");
/*    */     }
/* 57 */     return 1;
/*    */   }
/*    */ 
/*    */   protected void createData()
/*    */   {
/* 62 */     this.datasets.clear();
/* 63 */     for (int i = 0; i < this.numDatasets; i++) {
/* 64 */       Dataset d = new Dataset();
/* 65 */       d.setName("Series " + i);
/* 66 */       for (int j = 0; j < this.numObservations; j++) {
/* 67 */         d.addPoint(j, Math.random(), this.chars[(j % this.chars.length)]);
/*    */       }
/* 69 */       this.datasets.add(d);
/*    */     }
/*    */   }
/*    */ 
/*    */   public Iterator getDatasets()
/*    */   {
/* 79 */     return this.datasets.iterator();
/*    */   }
/*    */ 
/*    */   public String getUniqueIdentifier()
/*    */   {
/* 87 */     return "time:" + System.currentTimeMillis();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.RandomDataProvider
 * JD-Core Version:    0.6.2
 */