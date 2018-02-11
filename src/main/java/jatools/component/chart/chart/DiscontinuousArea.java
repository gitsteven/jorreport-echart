/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ import java.awt.Graphics;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class DiscontinuousArea extends Area
/*    */ {
/* 20 */   private ArrayList saveBuffer = new ArrayList();
/*    */ 
/*    */   public DiscontinuousArea(Dataset[] d, AxisInterface xAx, AxisInterface yAx, Plotarea subplot)
/*    */   {
/* 26 */     super(d, xAx, yAx, subplot);
/*    */   }
/*    */ 
/*    */   public void draw(Graphics g)
/*    */   {
/* 34 */     for (int i = 0; i < this.datasets.length; i++) {
/* 35 */       if (this.datasets[i] != null) {
/* 36 */         Dataset d = this.datasets[i];
/* 37 */         for (int j = 0; j < d.getData().size(); j++)
/* 38 */           if (d.getDataElementAt(j).label == "D")
/* 39 */             setDiscontinuity(i, j);
/*    */       }
/*    */     }
/* 42 */     super.draw(g);
/* 43 */     resetValuesFromSaveBuffer();
/*    */   }
/*    */   private void resetValuesFromSaveBuffer() {
/* 46 */     for (int i = 0; i < this.saveBuffer.size(); i++) {
/* 47 */       int setNumber = ((Integer)this.saveBuffer.get(i)).intValue();
/* 48 */       i++;
/* 49 */       int elementNumber = ((Integer)this.saveBuffer.get(i)).intValue();
/* 50 */       i++;
/* 51 */       this.datasets[setNumber].getDataElementAt(elementNumber).y = ((Double)this.saveBuffer.get(i)).doubleValue();
/*    */     }
/* 53 */     this.saveBuffer.clear();
/*    */   }
/*    */   private void setDiscontinuity(int setNumber, int elementNumber) {
/* 56 */     this.saveBuffer.add(new Integer(setNumber));
/* 57 */     this.saveBuffer.add(new Integer(elementNumber));
/* 58 */     this.saveBuffer.add(new Double(this.datasets[setNumber].getDataElementAt(elementNumber).y));
/*    */     try {
/* 60 */       double startVal = this.datasets[setNumber].getDataElementAt(elementNumber - 1).y;
/* 61 */       double endVal = this.datasets[setNumber].getDataElementAt(elementNumber + 1).y;
/* 62 */       this.datasets[setNumber].getDataElementAt(elementNumber).y = ((endVal + startVal) / 2.0D);
/*    */     }
/*    */     catch (Exception localException)
/*    */     {
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.DiscontinuousArea
 * JD-Core Version:    0.6.2
 */