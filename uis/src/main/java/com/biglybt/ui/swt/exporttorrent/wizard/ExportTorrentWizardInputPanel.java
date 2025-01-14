/*
 * File    : ExportTorrentWizardFilePanel.java
 * Created : 13-Oct-2003
 * By      : stuff
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details ( see the LICENSE file ).
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.biglybt.ui.swt.exporttorrent.wizard;

/**
 * @author parg
 */

import com.biglybt.common.constant.Constants;
import com.biglybt.core.internat.MessageText;
import com.biglybt.ui.swt.Messages;
import com.biglybt.ui.swt.wizard.AbstractWizardPanel;
import com.biglybt.ui.swt.wizard.IWizardPanel;
import com.biglybt.ui.swt.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import java.io.File;

public class
ExportTorrentWizardInputPanel
	extends AbstractWizardPanel {
	protected boolean file_valid = false;

	public ExportTorrentWizardInputPanel(
		Wizard wizard,
		IWizardPanel previous) {
		super(wizard, previous);
	}


	@Override
	public void
	show() {
		wizard.setTitle(MessageText.getString("exportTorrentWizard.torrentfile.title"));

		Composite rootPanel = wizard.getPanel();
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		rootPanel.setLayout(layout);

		Composite panel = new Composite(rootPanel, SWT.NULL);
		GridData gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER | GridData.FILL_HORIZONTAL);
		panel.setLayoutData(gridData);
		layout = new GridLayout();
		layout.numColumns = 3;
		panel.setLayout(layout);

		Label label = new Label(panel, SWT.WRAP);
		gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.widthHint = 380;
		label.setLayoutData(gridData);
		Messages.setLanguageText(label, "exportTorrentWizard.torrentfile.message");

		label = new Label(panel, SWT.NULL);
		Messages.setLanguageText(label, "exportTorrentWizard.torrentfile.path");

		final Text textPath = new Text(panel, SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		textPath.setLayoutData(gridData);
		textPath.setText("");

		Button browse = new Button(panel, SWT.PUSH);
		Messages.setLanguageText(browse, "exportTorrentWizard.torrentfile.browse");
		browse.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {

				FileDialog fd = new FileDialog(wizard.getWizardWindow());

				fd.setFileName(textPath.getText());

				fd.setFilterExtensions(new String[]{"*.torrent", "*.tor", Constants.FILE_WILDCARD});

				String path = fd.open();

				if (path != null) {

					textPath.setText(path);
				}
			}
		});

		textPath.addListener(SWT.Modify, new Listener() {

			@Override
			public void handleEvent(Event event) {

				String path = textPath.getText();

				((ExportTorrentWizard) wizard).setTorrentFile(path);

				file_valid = false;

				try {

					File f = new File(path);

					if (f.exists()) {

						if (f.isFile()) {

							file_valid = true;

							wizard.setErrorMessage("");
						} else {

							wizard.setErrorMessage(MessageText.getString("exportTorrentWizard.torrentfile.invalidPath"));
						}
					}

				} catch (Exception e) {
					wizard.setErrorMessage(MessageText.getString("exportTorrentWizard.torrentfile.invalidPath"));
				}

				wizard.setNextEnabled(file_valid);
			}
		});

		textPath.setText(((ExportTorrentWizard) wizard).getTorrentFile());

		textPath.setFocus();
	}

	@Override
	public IWizardPanel
	getNextPanel() {
		return (new ExportTorrentWizardOutputPanel(wizard, this));
	}

	@Override
	public boolean
	isNextEnabled() {
		return (file_valid);
	}
}
