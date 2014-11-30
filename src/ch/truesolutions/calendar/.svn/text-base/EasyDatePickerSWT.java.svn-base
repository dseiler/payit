package ch.truesolutions.calendar;

import java.util.Date;
import java.util.StringTokenizer;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author Daniel Seiler
 */
public class EasyDatePickerSWT extends Dialog implements MouseListener {

	private final static int LEFT_GAP             = 5;
	private final static int RIGHT_GAP            = 5;
	private final static int BOTTOM_GAP           = 5;
	private final static int TOP_GAP              = 20;
	private final static int TOP_GAP_DAYS         = 4;
        
	private final static byte BG                  = 0;
	private final static byte WEEKEND_BG          = 1;
	private final static byte NEIGHBOR_MONTH_DATE = 2;
	private final static byte HIGHLIGHT           = 3;
	private final static byte UNSELECTABLE        = 4;
	private final static byte MAX_COLORS          = 5;

	//private final static int ARROW_STYLE 		 = 0;
	//private final static int DROPDOWN_STYLE 		 = 1;

	private int iCellWidth;
	private int iCellHeight;
	private int iCalendarWidth;
	private int iCalendarHeight;
	private byte highlightedCell = -1;
    
	//private Choice months = new Choice();
	//private Choice years = new Choice();
    
	//JComboBox    months,years;
	//Button       today;
//	private Button btnToday;
//	private Button btnOk;
//	private Button btnClose;
    
	private Color[] calendarColors= new Color[MAX_COLORS];
    
	//private Canvas mainPanel;
	private Canvas calPanel;
	private Canvas northPanel;
	private Canvas southPanel;
    
	//private Label monthYearLabel;
    
	private boolean is3D = false;
    
	private Text dest = null;
    
	//private Font calFont = new Font("Default",Font.PLAIN,10);// Lucida Console,Comic Sans MS,Miriam Fixed
        
	// for the arrow style
	private int selectedYear;
	private int selectedMonth;    
    
	//private int displayMode = ARROW_STYLE; // if we have a drop down list or arrows

	private CalendarCell[] calendarCells;
	private EasyDatePicker datePicker;
	private Shell shell;
	
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell();
		shell.setLayout(new RowLayout());
		shell.setText("SWT Application");
		shell.open();
		shell.pack();
		new EasyDatePickerSWT(shell, null).show();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public EasyDatePickerSWT(Shell parent, Text textDest) {
		super(parent);
		init(textDest);
	}
    
	
	protected Control createContents(Composite parent) {		
		shell = getShell();
		shell.setText("Easy Date Picker SWT");
		shell.setVisible(false);
		//shell.setSize(100,150);
		calendarColors[BG] = shell.getDisplay().getSystemColor (SWT.COLOR_WIDGET_BACKGROUND);
		calendarColors[WEEKEND_BG] = shell.getDisplay().getSystemColor (SWT.COLOR_DARK_GRAY);
		calendarColors[NEIGHBOR_MONTH_DATE] = shell.getDisplay().getSystemColor (SWT.COLOR_GRAY);
		calendarColors[UNSELECTABLE] = shell.getDisplay().getSystemColor (SWT.COLOR_WHITE);
		calendarColors[HIGHLIGHT] = shell.getDisplay().getSystemColor (SWT.COLOR_RED);

		parent.setLayout(new FormLayout());
		
//		mainPanel = new Canvas(parent,SWT.NONE);
//		mainPanel.setBackground(getShell().getDisplay().getSystemColor (SWT.COLOR_RED));
//		FormData mainFormData = new FormData();
		//mainFormData.height = 150;
		//mainFormData.width = 100;
//		mainPanel.setLayoutData(mainFormData);
		
		calPanel = new Canvas(parent,SWT.NONE);
		//calPanel.setBackground(getShell().getDisplay().getSystemColor (SWT.COLOR_BLUE));
		FormData calPanelFormData = new FormData();
		calPanelFormData.top = new FormAttachment(0,25);
		calPanelFormData.left = new FormAttachment(0,0);
		calPanelFormData.right = new FormAttachment(100,0);
		calPanelFormData.bottom = new FormAttachment(100,-25);
		calPanelFormData.height = 150;
		calPanelFormData.width = 150;
		calPanel.setLayoutData(calPanelFormData);
		Font calPanelFont = calPanel.getFont();
		FontData[] fontData = calPanelFont.getFontData();
		for (int i = 0; i < fontData.length; i++) {
			fontData[i].setHeight(10);
		}
		Font newMainCanvasFont = new Font(shell.getDisplay(), fontData);
		calPanel.setFont(newMainCanvasFont);

		//mainCanvas.setBackground(getShell().getDisplay().getSystemColor (SWT.COLOR_YELLOW));
		calPanel.addPaintListener (new PaintListener () {
			public void paintControl (PaintEvent e) {
				paint(e.gc);
			}
		});
		
		southPanel = new Canvas(parent,SWT.NULL);
		southPanel.setLayout(new FormLayout());
		FormData southPanelFormData = new FormData();
		southPanelFormData.top = new FormAttachment(100,-30);
		southPanelFormData.left = new FormAttachment(0,0);
		southPanelFormData.right = new FormAttachment(100,0);
		southPanelFormData.bottom = new FormAttachment(100,0);
		southPanel.setLayoutData(southPanelFormData);

		northPanel = new Canvas(parent,SWT.NULL);
		//northPanel.setLayout(new FormLayout());
		FormData northPanelFormData = new FormData();
		northPanelFormData.top = new FormAttachment(0,0);
		northPanelFormData.left = new FormAttachment(0,0);
		northPanelFormData.right = new FormAttachment(100,0);
		northPanelFormData.bottom = new FormAttachment(0,20);
		northPanel.setLayoutData(northPanelFormData);
		
		RowLayout rowLayout = new RowLayout();
		rowLayout.wrap = false;
		rowLayout.pack = false;
		rowLayout.justify = true;
		rowLayout.type = SWT.VERTICAL;
		rowLayout.marginLeft = 5;
		rowLayout.marginTop = 5;
		rowLayout.marginRight = 5;
		rowLayout.marginBottom = 5;
		rowLayout.spacing = 0;
		
		northPanel.setLayout(new FillLayout());

		Button btnDblArrowWest = new Button(northPanel,SWT.PUSH);
		btnDblArrowWest.setLayoutData(new RowData(20, 20));
		btnDblArrowWest.setText ("<<");
		btnDblArrowWest.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e){
				System.out.println("<< pressed");
				selectedYear--;
				refreshMonthYearDisplay();
				calendarCells = datePicker.getCalendarCellsForMonth(selectedYear,selectedMonth);
				calPanel.redraw();
			}
		});
		
		Button btnArrowWest = new Button(northPanel,SWT.PUSH);
		btnArrowWest.setLayoutData(new RowData(20, 20));
		btnArrowWest.setText ("<");
		btnArrowWest.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e){
				selectedMonth--;
				if(selectedMonth == -1){
					selectedMonth = 11;
					selectedYear--;
				}
				refreshMonthYearDisplay();
				calendarCells = datePicker.getCalendarCellsForMonth(selectedYear,selectedMonth);
				calPanel.redraw();
			}
		});

		Button btnArrowEast = new Button(northPanel,SWT.PUSH);
		btnArrowEast.setLayoutData(new RowData(20, 20));
		btnArrowEast.setText (">");
		btnArrowEast.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e){
				selectedMonth++;
				if(selectedMonth == 12){
					selectedMonth = 0;
					selectedYear++;
				}
				refreshMonthYearDisplay();
				calendarCells = datePicker.getCalendarCellsForMonth(selectedYear,selectedMonth);
				calPanel.redraw();

			}
		});
		
		Button btnDblArrowEast = new Button(northPanel,SWT.PUSH);
		btnDblArrowEast.setLayoutData(new RowData(20, 20));
		btnDblArrowEast.setText (">>");
		btnDblArrowEast.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e){
				selectedYear++;
				refreshMonthYearDisplay();
				calendarCells = datePicker.getCalendarCellsForMonth(selectedYear,selectedMonth);
				calPanel.redraw();
			}
		});
				
		calPanel.addMouseListener(this);
		
		shell.pack();
		return shell;
	}
	
	private void init(Text textDest) {
		datePicker = new EasyDatePicker();

		// initialize this calendar instance
		this.dest = textDest;
        
		// set the preselected date to the date in the date text field
		if(dest != null){
			datePicker.setPreselectedDate(dest.getText());
		}
		create();
	}
     /*   
        
		northPanel.setLayout(new BorderLayout());
		southPanel.setLayout(new FlowLayout());
		calPanel.setPreferredSize(new Dimension(100,100));
		northPanel.setPreferredSize(new Dimension(100,20));
		southPanel.setPreferredSize(new Dimension(100,30));

		// initialize the colors	
		calendarColors[BG] = new Color(Color.lightGray.getRGB());
		calendarColors[WEEKEND_BG] = new Color(Color.white.darker().getRGB());
		calendarColors[NEIGHBOR_MONTH_DATE] = new Color(Color.gray.getRGB());
		calendarColors[HIGHLIGHT] = new Color(Color.red.getRGB());
		calendarColors[UNSELECTABLE] = new Color(Color.white.getRGB());
		
		selectedYear = datePicker.getCurrentDateField(EasyDatePicker.YEAR);
		selectedMonth = datePicker.getCurrentDateField(EasyDatePicker.MONTH);
        
		calendarCells = datePicker.getCalendarCellsForMonth(selectedYear,selectedMonth);
		//months = new JComboBox();
				
				months.addMouseListener(new MouseAdapter() {
						public void mouseEntered(MouseEvent e) {
										System.out.println("pressed");
						}
				});
		//months.setPreferredSize(new Dimension(90,20));
		//years = new JComboBox();
		//years.setPreferredSize(new Dimension(60,20));
		//today = new Button( "!" );
		//today.setPreferredSize(new Dimension(20,20));
		//years.setSelectedItem( String.valueOf( calToday.get( CALYEAR ) ) );
		//months.setSelectedIndex( calToday.get( CALMONTH ) );
        
		// for the arrow style
		JPanel arrowPanelWest = new JPanel(new GridLayout(1,2));
		JButton btnDblArrowWest = new JButton("<<");
		btnDblArrowWest.setFont(calFont);
		btnDblArrowWest.setPreferredSize(new Dimension(20,16));
		btnDblArrowWest.setMargin(new Insets(0,0,0,0));
		btnDblArrowWest.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				selectedYear--;
				refreshMonthYearDisplay();
				calendarCells = datePicker.getCalendarCellsForMonth(selectedYear,selectedMonth);
				repaint();
			}
		});
        
		JButton btnArrowWest = new JButton("<");
		btnArrowWest.setFont(calFont);
		btnArrowWest.setPreferredSize(new Dimension(20,16));
		btnArrowWest.setMargin(new Insets(0,0,0,0));
		btnArrowWest.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				selectedMonth--;
				if(selectedMonth == -1){
					selectedMonth = 11;
					selectedYear--;
				}
				refreshMonthYearDisplay();
				calendarCells = datePicker.getCalendarCellsForMonth(selectedYear,selectedMonth);
				repaint();
			}
		});
		JPanel arrowPanelEast = new JPanel(new GridLayout(1,2));
		JButton btnArrowEast = new JButton(">");
		btnArrowEast.setFont(calFont);
		btnArrowEast.setPreferredSize(new Dimension(20,16));
		btnArrowEast.setMargin(new Insets(0,0,0,0));
		btnArrowEast.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				selectedMonth++;
				if(selectedMonth == 12){
					selectedMonth = 0;
					selectedYear++;
				}
				refreshMonthYearDisplay();
				calendarCells = datePicker.getCalendarCellsForMonth(selectedYear,selectedMonth);
				repaint();
			}
		});
		JButton btnDblArrowEast = new JButton(">>");
		btnDblArrowEast.setFont(calFont);
		btnDblArrowEast.setPreferredSize(new Dimension(20,16));
		btnDblArrowEast.setMargin(new Insets(0,0,0,0));
		btnDblArrowEast.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				selectedYear++;
				refreshMonthYearDisplay();
				calendarCells = datePicker.getCalendarCellsForMonth(selectedYear,selectedMonth);
				repaint();
			}
		});
        
		monthYearLabel.setFont(calFont);
		monthYearLabel.setPreferredSize(new Dimension(50,15));
		monthYearLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
		// add the month names
		String[] monthNames = datePicker.getMonthNames();
		for(int i=0;i<monthNames.length;i++){
			months.addItem(monthNames[i]);
		}
		// add the years
		years.addItem("2003");
		years.addItem("2004");
		years.addItem("2005");
		years.addItem("2006");

		refreshMonthYearDisplay();
        
		arrowPanelWest.add(btnDblArrowWest);
		arrowPanelWest.add(btnArrowWest);
		arrowPanelEast.add(btnArrowEast);
		arrowPanelEast.add(btnDblArrowEast);
        		
		if(displayMode == DROPDOWN_STYLE)
		{
			northPanel.add( months,BorderLayout.CENTER );
			northPanel.add( years,BorderLayout.EAST );
		}
		else if(displayMode == ARROW_STYLE)
		{
			northPanel.add(arrowPanelWest,BorderLayout.WEST);
			northPanel.add(monthYearLabel,BorderLayout.CENTER);
			northPanel.add(arrowPanelEast,BorderLayout.EAST);
		}
        
		btnOk = new JButton("ok");
		btnOk.setPreferredSize(new Dimension(50,20));
		btnOk.setFont(calFont);
        
		btnToday = new JButton("today");
		btnToday.setPreferredSize(new Dimension(60,20));
		btnToday.setFont(calFont);
        
		btnClose = new JButton("close");
		btnClose.setPreferredSize(new Dimension(60,20));
		btnClose.setFont(calFont);
        
		southPanel.add(btnOk);
		southPanel.add(btnToday);
		southPanel.add(btnClose);
                
		calPanel.addMouseListener( this );
		years.addItemListener( this );
		months.addItemListener( this );
		//today.addActionListener( this );
		btnOk.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnDateFromHighlitedCell();
			}
		});
		btnToday.addActionListener(this);
		btnClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeCalendar();
			}
		});
        
		// Adding the panels to the frame
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(calPanel,BorderLayout.CENTER);
		getContentPane().add(northPanel,BorderLayout.NORTH);
		getContentPane().add(southPanel,BorderLayout.SOUTH);

	}*/
	
	private void refreshMonthYearDisplay()
	{
		//monthYearLabel.setText((datePicker.getMonthNames())[selectedMonth]+" "+String.valueOf(selectedYear));
        
		//years.select(String.valueOf(selectedYear));
		//months.select(selectedMonth);
	}
    
	private void returnDateFromHighlitedCell() {
		if(highlightedCell != -1) {
			Date d = calendarCells[highlightedCell].date;
            
			if(calendarCells[highlightedCell].selectable) {
				if(dest != null) {
					dest.setText(datePicker.formatDate(d));
				} else {
					System.out.println(datePicker.formatDate(d));
				}
				closeCalendar();
			} else {
				System.out.println("Date is not selectable...");
			}
		}
	}

	public void paint(GC gr) {
        
		Point d = calPanel.getSize( );
		int i = 0;
		int iOffset = 4;
        
		iCellWidth = ( d.x - LEFT_GAP - RIGHT_GAP ) / 7;
		iCellHeight = ( d.y - TOP_GAP - BOTTOM_GAP ) / 6;
		iCalendarWidth = iCellWidth * 7;
		iCalendarHeight = iCellHeight * 6;
        
		// Draw the surface and 3D effect
		if(is3D){
			gr.setBackground(getShell().getDisplay().getSystemColor (SWT.COLOR_WHITE));
		}
		else {
			gr.setBackground( calendarColors[BG] ); // switching off the 3D Effect DSe
		}
		
//		gr.drawLine( 0, 0, d.x, 0 );
//		gr.drawLine( 0, 1, d.x, 1 );
//		gr.drawLine( 0, 2, d.x, 2 );
//		gr.drawLine( 0, 0, 0, d.y );
//		gr.drawLine( 1, 0, 1, d.y );
//		gr.drawLine( 2, 0, 2, d.y );
        
//		if(is3D){
//			g.setColor( calendarColors[BG].darker( ) );
//		}
//		gr.drawLine( 2, d.y - 3, d.x, d.y - 3 );
//		gr.drawLine( 1, d.y - 2, d.x, d.y - 2 );
//		gr.drawLine( 0, d.y - 1, d.x, d.y - 1 );
//		gr.drawLine( d.x - 1, 0, d.x - 1, d.y );
//		gr.drawLine( d.x - 2, 1, d.x - 2, d.y );
//		gr.drawLine( d.x - 3, 2, d.x - 3, d.y );

		if(is3D){
			gr.setBackground( calendarColors[BG] );
		}
		gr.fillRectangle( 3, 3, d.x - 6, d.y - 6 );
        
		// Draw the weekend shade
		gr.setBackground( calendarColors[WEEKEND_BG] );
		// Saturday
		int sat = 6;
		sat -= datePicker.getWeekBeginDay();
		if(sat == 0) sat = 6;
		gr.fillRectangle( LEFT_GAP + iCellWidth * sat, TOP_GAP, iCellWidth, iCalendarHeight );
		// Sunday
		int sun = 0;
		sun -= datePicker.getWeekBeginDay();
		if(sun == -1) sun = 6;
		gr.fillRectangle( LEFT_GAP + iCellWidth * sun, TOP_GAP, iCellWidth, iCalendarHeight );
        
		// Draw the week names now
		gr.setForeground(getShell().getDisplay().getSystemColor (SWT.COLOR_WHITE));
		gr.setBackground(calendarColors[BG]);
		StringTokenizer dayNamesTokenizer = new StringTokenizer( datePicker.getDayNames(), " " );
		i = 0;
		while( dayNamesTokenizer.hasMoreTokens( ) ) {
			String dayName = dayNamesTokenizer.nextToken( );
			//iOffset = fm.stringWidth( dayName ) / 2;
//			gr.drawString(  dayName, LEFT_GAP + i * iCellWidth +
//			iCellWidth / 2 - iOffset, TOP_GAP_DAYS - 1 );
			gr.drawString(  dayName, LEFT_GAP + i * iCellWidth +
			iCellWidth / 2 - iOffset, TOP_GAP_DAYS );
			i++;
		}
        
		// Draw the grid now
		gr.setForeground(getShell().getDisplay().getSystemColor (SWT.COLOR_WHITE));
		for( i=1; i<7; i++ ) {
			gr.drawLine( LEFT_GAP, TOP_GAP + i * iCellHeight - 1, LEFT_GAP + iCalendarWidth - 1,
			TOP_GAP + i * iCellHeight - 1 );
		}
		for( i=1; i<8; i++ ) {
			gr.drawLine( LEFT_GAP + i * iCellWidth - 1, TOP_GAP, LEFT_GAP + i * iCellWidth - 1,
			TOP_GAP + iCalendarHeight - 1 );
		}
		gr.setForeground( calendarColors[BG]);
		for( i=0; i<6; i++ ) {
			gr.drawLine( LEFT_GAP, TOP_GAP + i * iCellHeight, LEFT_GAP + iCalendarWidth - 1,
			TOP_GAP + i * iCellHeight );
		}
		for( i=0; i<7; i++ ) {
			gr.drawLine( LEFT_GAP + i * iCellWidth, TOP_GAP, LEFT_GAP + i * iCellWidth,
			TOP_GAP + iCalendarHeight - 1 );
		}
        
		FontData[] fontData = gr.getFont().getFontData();
		for (int fi = 0; fi < fontData.length; fi++) {
			fontData[fi].setHeight((int)(iCellHeight * 0.4));
			fontData[fi].setStyle(SWT.BOLD);
		}
		Font newCalPanelFont = new Font(shell.getDisplay(), fontData);
		gr.setFont(newCalPanelFont);
	
		DrawDates( gr );
        
		if( highlightedCell == -1 ) {
			Date preselectedDate = datePicker.getPreselectedDate();
			highlightedCell = (byte)datePicker.getCellNumberForDate(preselectedDate);
		}
		//if( Cell[cbHighlightedCell].bSelectable ) //DSe enable the red caret without being selectable
		highlightCell( highlightedCell, calendarColors[HIGHLIGHT], gr );
        
		//northPanel.repaint();
		//southPanel.repaint();
	}
    
	private void DrawDates( GC gr ) {
		//printDates();
		int i, iHorizOffset, iVertOffset;
		String sBuffer;
        
		FontMetrics fm = gr.getFontMetrics( );
		iVertOffset = fm.getHeight( ) / 2 - fm.getDescent( );
		for( i=0; i<42; i++ ) {
			sBuffer = String.valueOf(datePicker.getDateField( calendarCells[i].date, EasyDatePicker.DAY ) );
			//iHorizOffset = fm.stringWidth( sBuffer ) / 2;
			iHorizOffset = fm.getAverageCharWidth()*sBuffer.length()/ 2;
            
            if(calendarCells[i].weekend){
				gr.setBackground(calendarColors[WEEKEND_BG]);
            } else {
				gr.setBackground(calendarColors[BG]);
            }
			
			if( !calendarCells[i].selectable ){
				gr.setForeground( calendarColors[UNSELECTABLE] );
			}
			else if(datePicker.getDateField( calendarCells[i].date, EasyDatePicker.MONTH ) != selectedMonth )
				gr.setForeground( calendarColors[NEIGHBOR_MONTH_DATE]);
			else
				gr.setForeground(getShell().getDisplay().getSystemColor (SWT.COLOR_BLACK));
            
			gr.drawString(  sBuffer,
			LEFT_GAP + ( i%7 ) * iCellWidth + iCellWidth / 2 - iHorizOffset,
			TOP_GAP + ( i/7 ) * iCellHeight + iVertOffset );
		}
	}
	
	

	private void highlightCell( int iCellNumber, Color cHighlight , GC g) {
		int	iRow = iCellNumber / 7,
		iColumn = iCellNumber % 7,
		x = iColumn * iCellWidth + 2 + LEFT_GAP,
		y = iRow * iCellHeight + 2 + TOP_GAP;
                
		g.setForeground( cHighlight );
		g.drawLine( x, y, x + iCellWidth - 4, y );
		g.drawLine( x + iCellWidth - 4, y, x + iCellWidth - 4, y + iCellHeight - 4 );
		g.drawLine( x + iCellWidth - 4, y + iCellHeight - 4,  x, y + iCellHeight - 4 );
		g.drawLine( x, y + iCellHeight - 4, x, y );
        
		g.dispose( );
	}
	
	
	private byte getCellNumber( int x, int y ) {
		int	iColumn = ( ( x - LEFT_GAP ) / iCellWidth ),
		iRow = ( ( y - TOP_GAP ) / iCellHeight );
        
		return (byte) (( iRow * 7 ) + iColumn);
	}
    
	private void printDates(){
		String sBuffer;
		int i;
		for( i=0; i<42; i++ ) {
			sBuffer = String.valueOf(datePicker.getDateField( calendarCells[i].date, EasyDatePicker.DAY ) );
			System.out.print(sBuffer+" ");
			if((i+1) % 7 == 0) System.out.println("");
		}
    	
	}

	// try to set the default date to the date in the date text field
	private void setDateToDestFieldDate() {
		Date d = datePicker.getPreselectedDate();

		selectedYear = datePicker.getDateField(d, EasyDatePicker.YEAR);
		selectedMonth = datePicker.getDateField(d, EasyDatePicker.MONTH);

		refreshMonthYearDisplay();

		//defaultCalendar = calDefault;
		highlightedCell = -1;
		calendarCells =
			datePicker.getCalendarCellsForMonth(selectedYear, selectedMonth);
	}
	
	private void closeCalendar() {
		try {
			this.close();
		} catch (Exception ex) {System.out.println(ex);}
	}

	// overide the show method
	public void show() {
		setDateToDestFieldDate();
		if(shell == null){
			this.open();
		} else {
			shell.setVisible(true);
		}
		shell.setFocus();
	}

	/**
	 * 
	 */
	private void handleMouseClick(MouseEvent e, int clickCount) {
		Canvas c = (Canvas)e.getSource();
		int x = e.x;
		int y = e.y;
		int iClickedCell = getCellNumber(x, y);
		Date dBuffer = calendarCells[iClickedCell].date;
		//long lTotalDaysUntildBuffer = GetTotalDaysUntil( dBuffer );

		if (calendarCells[iClickedCell].selectable && clickCount == 2) {
			if (dest != null) {
				dest.setText(datePicker.formatDate(dBuffer));
			} else {
				System.out.println(datePicker.formatDate(dBuffer));
			}
		}
		if (x > LEFT_GAP
			&& x < LEFT_GAP + iCalendarWidth
			&& y > TOP_GAP
			&& y < TOP_GAP + iCalendarHeight
			
			) {
			if (selectedMonth
				== datePicker.getDateField(dBuffer, EasyDatePicker.MONTH)) {
				// a different date is selected from the displayed month
//				highlightCell(
//					highlightedCell,
//					calendarCells[highlightedCell].weekend
//						? calendarColors[WEEKEND_BG]
//						: calendarColors[BG],gc);
				highlightedCell = getCellNumber(x, y);
				//highlightCell(highlightedCell, calendarColors[HIGHLIGHT],gc);
			}
			else { // a date from the neighboring month is selected
				selectedYear = datePicker.getDateField(dBuffer, EasyDatePicker.YEAR);
				selectedMonth = datePicker.getDateField(dBuffer, EasyDatePicker.MONTH);
				refreshMonthYearDisplay();
				calendarCells = datePicker.getCalendarCellsForMonth(selectedYear,selectedMonth);
				highlightedCell = (byte)datePicker.getCellNumberForDate(dBuffer);
				//paint(gc);
			}
			c.redraw();
			
		}
		if (calendarCells[iClickedCell].selectable && clickCount == 2) {
			closeCalendar();
		}
	}

	/**
	 * set the selected date to today
	 */
	/*
	public void actionPerformed(ActionEvent arg0) {
		selectedYear = datePicker.getCurrentDateField(EasyDatePicker.YEAR);
		selectedMonth = datePicker.getCurrentDateField(EasyDatePicker.MONTH);
        
		refreshMonthYearDisplay();
        
		//defaultCalendar = calToday;
		highlightedCell = -1;
		calendarCells = datePicker.getCalendarCellsForMonth(selectedYear,selectedMonth);
		repaint();
	}
	*/

	/**
	 * @param year
	 * @param month
	 * @return
	 */
	public CalendarCell[] getCalendarCellsForMonth(int year, int month) {
		return datePicker.getCalendarCellsForMonth(year, month);
	}

	/**
	 * @return
	 */
	public String getDayNames() {
		return datePicker.getDayNames();
	}

	/**
	 * @return
	 */
	public String getNextSelectableDateAsString() {
		return datePicker.getNextSelectableDateAsString();
	}

	/**
	 * @return
	 */
	public int getWeekBeginDay() {
		return datePicker.getWeekBeginDay();
	}

	/**
	 * @return
	 */
	public boolean isWeekendSelectable() {
		return datePicker.isWeekendSelectable();
	}

	/**
	 * @param string
	 */
	public void setDateFormat(String string) {
		datePicker.setDateFormat(string);
	}

	/**
	 * @param dayNames
	 */
	public void setDayNames(String dayNames) {
		datePicker.setDayNames(dayNames);
	}

	/**
	 * @param weekBeginDay
	 */
	public void setWeekBeginDay(int weekBeginDay) {
		datePicker.setWeekBeginDay(weekBeginDay);
	}

	/**
	 * @param b
	 */
	public void setWeekendSelectable(boolean b) {
		datePicker.setWeekendSelectable(b);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseDoubleClick(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseDoubleClick(MouseEvent event) {
		handleMouseClick(event,2);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseDown(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseDown(MouseEvent event) {
		handleMouseClick(event,1);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseUp(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
