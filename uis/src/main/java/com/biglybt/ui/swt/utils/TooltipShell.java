/* *
 * Copyright (C) Bigly Software, Inc, All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package com.biglybt.ui.swt.utils;

import com.biglybt.ui.swt.Utils;
import com.biglybt.ui.swt.mainwindow.Colors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;


public class TooltipShell
	implements Listener {
	private final Composite composite;
	private final TooltipProvider ttp;
	Shell toolTipShell = null;
	Shell mainShell = null;
	CLabel toolTipLabel = null;


	/**
	 * Initialize
	 */
	public TooltipShell(TooltipProvider ttp, Composite composite) {
		this.ttp = ttp;
		this.composite = composite;
		mainShell = composite.getShell();

		composite.addListener(SWT.Dispose, this);
		composite.addListener(SWT.KeyDown, this);
		composite.addListener(SWT.MouseMove, this);
		composite.addListener(SWT.MouseHover, this);
		composite.addListener(SWT.MouseExit, this);
		mainShell.addListener(SWT.Deactivate, this);
	}

	@Override
	public void handleEvent(Event event) {
		switch (event.type) {
			case SWT.MouseHover: {
				if (toolTipShell != null && !toolTipShell.isDisposed())
					toolTipShell.dispose();

				if (!Utils.getTTEnabled()) {
					return;
				}

				String tt = ttp.getTooltip(new Point(event.x, event.y));

				if (tt == null) {

					return;
				}

				Display d = composite.getDisplay();
				if (d == null)
					return;

				// We don't get mouse down notifications on trim or borders..
				toolTipShell = new Shell(composite.getShell(), SWT.ON_TOP);
				FillLayout f = new FillLayout();
				try {
					f.marginWidth = 3;
					f.marginHeight = 1;
				} catch (NoSuchFieldError e) {
					/* Ignore for Pre 3.0 SWT.. */
				}
				toolTipShell.setLayout(f);
				toolTipShell.setBackground(Colors.getSystemColor(d, SWT.COLOR_INFO_BACKGROUND));

				Point size = new Point(0, 0);

				toolTipLabel = new CLabel(toolTipShell, SWT.WRAP);
				toolTipLabel.setForeground(Colors.getSystemColor(d, SWT.COLOR_INFO_FOREGROUND));
				toolTipLabel.setBackground(Colors.getSystemColor(d, SWT.COLOR_INFO_BACKGROUND));

				toolTipLabel.setText(tt.replaceAll("&", "&&"));
				// compute size on label instead of shell because label
				// calculates wrap, while shell doesn't
				size = toolTipLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				if (size.x > 600) {
					size = toolTipLabel.computeSize(600, SWT.DEFAULT, true);
				}

				size.x += toolTipShell.getBorderWidth() * 2 + 2;
				size.y += toolTipShell.getBorderWidth() * 2;
				try {
					size.x += toolTipShell.getBorderWidth() * 2 + (f.marginWidth * 2);
					size.y += toolTipShell.getBorderWidth() * 2 + (f.marginHeight * 2);
				} catch (NoSuchFieldError e) {
					/* Ignore for Pre 3.0 SWT.. */
				}
				Point pt = composite.toDisplay(event.x, event.y);
				Rectangle displayRect;
				try {
					displayRect = composite.getMonitor().getClientArea();
				} catch (NoSuchMethodError e) {
					displayRect = composite.getDisplay().getClientArea();
				}
				if (pt.x + size.x > displayRect.x + displayRect.width) {
					pt.x = displayRect.x + displayRect.width - size.x;
				}

				if (pt.y + size.y > displayRect.y + displayRect.height) {
					pt.y -= size.y + 2;
				} else {
					pt.y += 21;
				}

				if (pt.y < displayRect.y)
					pt.y = displayRect.y;

				toolTipShell.setBounds(pt.x, pt.y, size.x, size.y);
				toolTipShell.setVisible(true);

				break;
			}

			case SWT.Dispose:
				if (mainShell != null && !mainShell.isDisposed())
					mainShell.removeListener(SWT.Deactivate, this);

				// fall through

			default:
				if (toolTipShell != null) {
					toolTipShell.dispose();
					toolTipShell = null;
					toolTipLabel = null;
				}
				break;
		}
	}

	public interface
	TooltipProvider {
		public String
		getTooltip(
			Point location);
	}
}
