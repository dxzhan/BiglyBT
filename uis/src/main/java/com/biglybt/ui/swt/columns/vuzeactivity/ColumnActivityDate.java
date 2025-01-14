/*
 * Created on Sep 25, 2008
 *
 * Copyright (C) Azureus Software, Inc, All Rights Reserved.
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

package com.biglybt.ui.swt.columns.vuzeactivity;

import com.biglybt.activities.ActivitiesEntry;
import com.biglybt.pif.ui.tables.TableCell;
import com.biglybt.pif.ui.tables.TableCellAddedListener;
import com.biglybt.ui.swt.views.table.TableCellSWT;
import com.biglybt.ui.swt.views.table.utils.TableColumnCreator;
import com.biglybt.ui.swt.views.tableitems.ColumnDateSizer;

/**
 * @author TuxPaper
 * @created Sep 25, 2008
 */
public class ColumnActivityDate
	extends ColumnDateSizer
	implements TableCellAddedListener {
	public static final String COLUMN_ID = "activityDate";

	/**
	 * @param name
	 * @param tableID
	 */
	public ColumnActivityDate(String tableID) {
		super(null, COLUMN_ID, TableColumnCreator.DATE_COLUMN_WIDTH, tableID);

		setMultiline(false);
	}

	// @see com.biglybt.pif.ui.tables.TableCellAddedListener#cellAdded(com.biglybt.pif.ui.tables.TableCell)
	@Override
	public void cellAdded(TableCell cell) {
		if (cell instanceof TableCellSWT) {
			((TableCellSWT) cell).setTextAlpha(120);
		}
	}

	// @see com.biglybt.ui.swt.views.tableitems.ColumnDateSizer#refresh(com.biglybt.pif.ui.tables.TableCell, long)
	@Override
	public void refresh(TableCell cell, long timestamp) {
		ActivitiesEntry entry = (ActivitiesEntry) cell.getDataSource();
		timestamp = entry.getTimestamp();

		super.refresh(cell, timestamp);
	}
}
