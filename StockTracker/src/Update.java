import java.util.Timer;
import java.util.TimerTask;

public class Update {

	public static void main(String[] args) {
		final ExcelData excel = new ExcelData();
	    final StockGUI gui = new StockGUI(excel.getStockList(), excel.getColumnNames());
	    gui.setVisible(true);
		
		int period = 30 * 1000; // repeat every 30 sec

	    Timer timer = new Timer();
	    timer.scheduleAtFixedRate(new TimerTask()
	      {
	        public void run()
	        {
	        	excel.update();
	        	excel.sortArray();
	        	Object[][] stocklist = excel.getStockList();
	            gui.refreshTable(stocklist);
	          
	            System.out.println("Beep boop");
	        }
	      }, 0, period);
	    
	}

}
