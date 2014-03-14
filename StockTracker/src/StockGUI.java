import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;


public class StockGUI extends JFrame {
	private String[] symbolNameHeader = {"Symbol Name"};
	private int[] clickedRows;
	
//	private Object[][] symbolName = {{"GOOGL"},{ "APPL"}, };
	JPanel stockListPanel1;
	JPanel strategyPanel2;
	JPanel stockDisplayPanel3;
	JTable stockTable;
	JTable symbolTable; 
	JScrollPane displayScrollPane;
	JScrollPane stockScrollPane;
	JButton stockButton = new JButton("Add Stocks");
	Object[][] addedList;
	Object[] getColumns;
	DefaultTableModel model;
	
	StockGUI(Object[][] getStockList, String[] getColumnLabels){		
		addedList = getStockList;
		getColumns = getColumnLabels;
		init(addedList, getColumns);
	}
	
	public void init( final Object[][] addedList, final Object[] getColumns){
		stockListPanel1 = new JPanel();
		strategyPanel2 = new JPanel();
		stockDisplayPanel3 = new JPanel();		    
		
		//stockTable = new JTable(addedList, getColumns);
		stockTable = new JTable();
		model = (DefaultTableModel) stockTable.getModel();
		for(int i = 0; i < getColumns.length; i++){
			model.addColumn(getColumns[i]);	
		}
		
		symbolTable = new JTable(addedList, symbolNameHeader) ;
		displayScrollPane = new JScrollPane(stockTable);		
		stockScrollPane = new JScrollPane(symbolTable);
		
		stockListPanel1.add(stockScrollPane);
		stockDisplayPanel3.add(displayScrollPane);
		
		stockListPanel1.setBorder(BorderFactory.createTitledBorder(null, "Stock List Panel", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));		
		strategyPanel2.setBorder(BorderFactory.createTitledBorder(null, "Strategy Panel", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
		stockDisplayPanel3.setBorder(BorderFactory.createTitledBorder(null, "Stock Display Panel", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
		
		displayScrollPane.setPreferredSize(new Dimension(980,160));
		
		stockButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				clickedRows = symbolTable.getSelectedRows();
				
				for(int i = 0; i < clickedRows.length; i++){
					System.out.print("Selected rows are " + clickedRows[i] + " ");
					model.addRow(addedList[clickedRows[i]]);
				}
				System.out.println("");
			}
		});
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)		
				.addGap(50)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)				
				.addComponent(stockDisplayPanel3, GroupLayout.PREFERRED_SIZE, 1000, GroupLayout.PREFERRED_SIZE)	
				.addGroup(layout.createSequentialGroup()
					.addComponent(stockScrollPane, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
					.addComponent(stockButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addGap(200)
						.addComponent(strategyPanel2, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE))));
		
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)	
			.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(stockScrollPane, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)	
				.addComponent(stockButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
				.addComponent(strategyPanel2, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
			
				.addComponent(stockDisplayPanel3, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)));
		
		setResizable(false);
		pack();
		
	}
	
	public void refreshTable(Object[][] stockList){
		for(int i = 0; i < clickedRows.length; i++){
			for(int j = 0; j < ExcelTest.totalcols; j++){
				stockTable.setValueAt(stockList[i][j], i, j);
			}
		}	
	}

}
