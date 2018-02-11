/*    */ package jatools.component.chart.beans.data;
/*    */ 
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import java.io.Serializable;
/*    */ import java.util.EventObject;
/*    */ 
/*    */ public class DataEvent extends EventObject
/*    */   implements Serializable
/*    */ {
/* 27 */   public Dataset[] datasetArray = new Dataset[20];
/*    */   public static final int Y_DATA_MASK = 1;
/*    */   public static final int X_DATA_MASK = 2;
/*    */   public static final int Z_DATA_MASK = 4;
/*    */   public static final int AUX_DATA_MASK = 8;
/*    */   public static final int LABELS_MASK = 16;
/*    */   public static final int SET_NAMES_MASK = 32;
/* 58 */   public int modifiers = 3;
/*    */ 
/*    */   public DataEvent(Object source) {
/* 61 */     super(source);
/*    */   }
/*    */   public DataEvent(Object source, int modifiers) {
/* 64 */     super(source);
/* 65 */     this.modifiers = modifiers;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.beans.data.DataEvent
 * JD-Core Version:    0.6.2
 */