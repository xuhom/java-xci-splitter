package org.insanedevelopment.nx.xci.cutter.backend.ui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class XciSplitterSwtGui {

	protected Shell shlXciSplitter;
	private Text inputFile;
	private SwtGuiModel model = new SwtGuiModel();
	private Text cartSize;
	private Text dataSize;
	private Text targetFile;
	private Text xciSize;
	private Label lblNewLabel_1;
	private Label lblNewLabel_2;
	private Text isSplit;
	private Text parts;
	private Button btnNewButton;
	private Button btnNewButton_1;
	private Menu menu_1;
	private MenuItem mntmExit;
	private MenuItem mntmBatchProcessing;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			XciSplitterSwtGui window = new XciSplitterSwtGui();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlXciSplitter.open();
		shlXciSplitter.layout();
		while (!shlXciSplitter.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlXciSplitter = new Shell();
		shlXciSplitter.setSize(625, 401);
		shlXciSplitter.setText("Xci Splitter");

		Button btnSelectSource = new Button(shlXciSplitter, SWT.NONE);
		btnSelectSource.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fileDialog = new FileDialog(shlXciSplitter, SWT.OPEN);
				fileDialog.setFilterExtensions(new String[] { "*.xci;*.xc0", "*.xci", "*.xc0" });
				String file = fileDialog.open();
				if (file != null) {
					inputFile.setText(file);
					model.setSourceFile(file);
					updateInformationFields();
				}
			}

		});
		btnSelectSource.setBounds(10, 48, 75, 25);
		btnSelectSource.setText("Source");

		inputFile = new Text(shlXciSplitter, SWT.BORDER);
		inputFile.setBounds(91, 50, 506, 21);

		cartSize = new Text(shlXciSplitter, SWT.BORDER);
		cartSize.setEditable(false);
		cartSize.setBounds(71, 155, 76, 21);

		Label lblCartSize = new Label(shlXciSplitter, SWT.NONE);
		lblCartSize.setBounds(10, 158, 55, 15);
		lblCartSize.setText("Cart Size");

		Label lblDataSize = new Label(shlXciSplitter, SWT.NONE);
		lblDataSize.setBounds(10, 185, 55, 15);
		lblDataSize.setText("Data Size");

		dataSize = new Text(shlXciSplitter, SWT.BORDER);
		dataSize.setEditable(false);
		dataSize.setBounds(71, 182, 76, 21);

		Button btnTarget = new Button(shlXciSplitter, SWT.NONE);
		btnTarget.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(shlXciSplitter, SWT.SAVE);
				fileDialog.setFilterExtensions(new String[] { "*.xc0;*.xci", "*.xci", "*.xc0" });
				fileDialog.setFileName(model.getTargetFileNameProposal());
				String file = fileDialog.open();
				if (file != null) {
					targetFile.setText(file);
					model.setTargetFile(file);
				}
			}
		});
		btnTarget.setBounds(10, 75, 75, 25);
		btnTarget.setText("Target");

		targetFile = new Text(shlXciSplitter, SWT.BORDER);
		targetFile.setBounds(91, 77, 506, 21);

		xciSize = new Text(shlXciSplitter, SWT.BORDER);
		xciSize.setEditable(false);
		xciSize.setBounds(71, 209, 76, 21);

		Label lblNewLabel = new Label(shlXciSplitter, SWT.NONE);
		lblNewLabel.setBounds(10, 212, 55, 15);
		lblNewLabel.setText("XCI Size");

		lblNewLabel_1 = new Label(shlXciSplitter, SWT.NONE);
		lblNewLabel_1.setBounds(167, 158, 39, 15);
		lblNewLabel_1.setText("is Split?");

		lblNewLabel_2 = new Label(shlXciSplitter, SWT.NONE);
		lblNewLabel_2.setBounds(167, 185, 39, 15);
		lblNewLabel_2.setText("Parts");

		isSplit = new Text(shlXciSplitter, SWT.BORDER);
		isSplit.setEditable(false);
		isSplit.setBounds(216, 155, 76, 21);

		parts = new Text(shlXciSplitter, SWT.BORDER);
		parts.setEditable(false);
		parts.setBounds(216, 185, 76, 21);

		btnNewButton = new Button(shlXciSplitter, SWT.NONE);
		btnNewButton.setEnabled(false);
		btnNewButton.setBounds(329, 151, 76, 79);
		btnNewButton.setText("Trim");

		btnNewButton_1 = new Button(shlXciSplitter, SWT.NONE);
		btnNewButton_1.setBounds(411, 151, 75, 79);
		btnNewButton_1.setText("Split && Trim");

		Menu mainMenu = new Menu(shlXciSplitter, SWT.BAR);
		shlXciSplitter.setMenuBar(mainMenu);

		MenuItem mntmFile = new MenuItem(mainMenu, SWT.CASCADE);
		mntmFile.setText("File");

		menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		mntmBatchProcessing = new MenuItem(menu_1, SWT.NONE);
		mntmBatchProcessing.setEnabled(false);
		mntmBatchProcessing.setText("Batch processing...");

		mntmExit = new MenuItem(menu_1, SWT.NONE);
		mntmExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlXciSplitter.close();
			}
		});
		mntmExit.setText("Exit");

		Button btnNewButton_2 = new Button(shlXciSplitter, SWT.NONE);
		btnNewButton_2.setBounds(492, 151, 75, 79);
		btnNewButton_2.setText("Merge");

		ProgressBar progressBar = new ProgressBar(shlXciSplitter, SWT.NONE);
		progressBar.setBounds(10, 287, 587, 17);
	}

	private void updateInformationFields() {
		cartSize.setText(model.getCartSizeString());
		dataSize.setText(model.getDataSizeString());
		xciSize.setText(model.getFullFileSizeString());
		isSplit.setText(model.getIsSplitString());
		parts.setText(model.getAmountOfPartsString());
	}
}