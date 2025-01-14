/*
 * Copyright (C) Azureus Software, Inc, All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 */

package com.biglybt.ui.swt.views.table;

import com.biglybt.ui.common.table.TableCellCore;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 * SWT specific functions for Table Cells
 *
 * @author TuxPaper
 * @created Jan 22, 2007
 */
public interface TableCellSWT extends TableCellCore {

	/**
	 * Change the cell's foreground color.
	 * <p>
	 * NOTE: favor (R, G, B)
	 *
	 * @param color SWT Color object.
	 * @return True - Color changed. <br>
	 * False - Color was already set.
	 */
	boolean setForeground(Color color);

	/**
	 * @return
	 */
	Image getIcon();

	/**
	 * Set the cell's icon
	 *
	 * @param img Cell's new icon
	 * @return true if the icon will be used
	 */
	public boolean setIcon(Image img);

	/**
	 * Paint the cell (for graphics)
	 *
	 * @param gc GC object to be used for painting
	 */
	public void doPaint(GC gc);

	public Point getSize();

	public Rectangle getBounds();

	public Rectangle getBoundsOnDisplay();

	public boolean setGraphic(Image img);

	public Image getGraphicSWT();

	/**
	 * @return
	 */
	public Image getBackgroundImage();

	/**
	 * @return
	 */
	Color getForegroundSWT();

	/**
	 * @return
	 */
	TableRowSWT getTableRowSWT();

	/**
	 * @return
	 * @since 3.0.3.4
	 */
	Color getBackgroundSWT();

	/**
	 * @return
	 * @since 3.1.1.1
	 */
	int getTextAlpha();

	/**
	 * @param textOpacity
	 * @since 3.1.1.1
	 */
	void setTextAlpha(int textOpacity);

	void setMouseOver(boolean b);
}
