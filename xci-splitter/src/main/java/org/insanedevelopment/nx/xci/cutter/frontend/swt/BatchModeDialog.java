package org.insanedevelopment.nx.xci.cutter.frontend.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.insanedevelopment.nx.xci.cutter.backend.batch.BatchHelper;
import org.insanedevelopment.nx.xci.cutter.backend.batch.BatchProgressUpdater;
import org.insanedevelopment.nx.xci.cutter.frontend.GuiModelBatchMode;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Label;

public class BatchModeDialog {

	protected Shell shlBatchOperations;
	private List listBatchItems;
	private Button btnAddFile;

	private GuiModelBatchMode model = new GuiModelBatchMode();
	private Label lblSingleFileStatus;
	private ProgressBar progressBarSingleFile;
	private Button btnSplitTrim;
	private Button btnMerge;
	private Button btnTrim;
	private Button checkDeleteFilesAfter;
	private Label lblFileStatus;
	private ProgressBar progressBarFiles;
	private Label lblFileName;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BatchModeDialog window = new BatchModeDialog();
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
		shlBatchOperations.open();
		shlBatchOperations.layout();
		while (!shlBatchOperations.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
//		shlBatchOperations = new Shell(SWT.SHELL_TRIM & (~SWT.RESIZE));
		shlBatchOperations = new Shell(SWT.SHELL_TRIM);

		shlBatchOperations.setSize(582, 665);
		shlBatchOperations.setText("Batch operations");
		shlBatchOperations.setLayout(null);

		listBatchItems = new List(shlBatchOperations, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		listBatchItems.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == 127) {
					int[] indices = listBatchItems.getSelectionIndices();
					listBatchItems.remove(indices);
				}
			}
		});
		listBatchItems.setBounds(10, 10, 556, 445);

		Button btnSelectFolder = new Button(shlBatchOperations, SWT.NONE);
		btnSelectFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(shlBatchOperations);
				String baseDir = dialog.open();
				java.util.List<String> files = BatchHelper.getAllXciImageFilesRecursively(baseDir);
				for (String file : files) {
					listBatchItems.add(file);
				}
			}
		});
		btnSelectFolder.setBounds(10, 461, 75, 25);
		btnSelectFolder.setText("Add Folder");

		btnAddFile = new Button(shlBatchOperations, SWT.NONE);
		btnAddFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(shlBatchOperations, SWT.OPEN);
				fileDialog.setFilterExtensions(new String[] { "*.xci;*.xc0", "*.xci", "*.xc0" });
				String file = fileDialog.open();
				if (file != null) {
					listBatchItems.add(file);
				}
			}
		});
		btnAddFile.setBounds(10, 492, 75, 25);
		btnAddFile.setText("Add File");

		progressBarSingleFile = new ProgressBar(shlBatchOperations, SWT.NONE);
		progressBarSingleFile.setBounds(10, 609, 556, 17);

		lblSingleFileStatus = new Label(shlBatchOperations, SWT.NONE);
		lblSingleFileStatus.setBounds(10, 588, 556, 15);

		progressBarFiles = new ProgressBar(shlBatchOperations, SWT.NONE);
		progressBarFiles.setBounds(10, 565, 556, 17);

		lblFileName = new Label(shlBatchOperations, SWT.NONE);
		lblFileName.setBounds(10, 544, 556, 15);

		btnTrim = new Button(shlBatchOperations, SWT.NONE);
		btnTrim.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				model.setFiles(listBatchItems.getItems());
				model.trim(createBatchProgressUpdater(), createProgressBarUpdater(), checkDeleteFilesAfter.getSelection());
			}
		});
		btnTrim.setBounds(91, 461, 75, 56);
		btnTrim.setText("Trim");

		btnSplitTrim = new Button(shlBatchOperations, SWT.NONE);
		btnSplitTrim.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				model.setFiles(listBatchItems.getItems());
				model.splitAndTrim(createBatchProgressUpdater(), createProgressBarUpdater(), checkDeleteFilesAfter.getSelection());
			}
		});
		btnSplitTrim.setText("Split &&Trim");
		btnSplitTrim.setBounds(172, 461, 75, 56);

		btnMerge = new Button(shlBatchOperations, SWT.NONE);
		btnMerge.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				model.setFiles(listBatchItems.getItems());
				model.merge(createBatchProgressUpdater(), createProgressBarUpdater(), checkDeleteFilesAfter.getSelection());
			}
		});
		btnMerge.setText("Merge");
		btnMerge.setBounds(253, 461, 75, 56);

		checkDeleteFilesAfter = new Button(shlBatchOperations, SWT.CHECK);
		checkDeleteFilesAfter.setBounds(344, 481, 85, 16);
		checkDeleteFilesAfter.setText("delete files");

		lblFileStatus = new Label(shlBatchOperations, SWT.NONE);
		lblFileStatus.setBounds(10, 523, 556, 15);

	}

	private ProgressBarUpdater createProgressBarUpdater() {
		return new ProgressBarUpdater(lblSingleFileStatus, progressBarSingleFile, btnSplitTrim, btnMerge, btnTrim);
	}

	private BatchProgressUpdater createBatchProgressUpdater() {
      return new BatchProgressBarUpdater(lblFileStatus, lblFileName, progressBarFiles);
	}
}
