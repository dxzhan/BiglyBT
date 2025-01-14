/*
 * Copyright (C) 2013 Azureus Software, Inc. All Rights Reserved.
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

package com.biglybt.ui.swt.columns.archivedls;

import com.biglybt.core.util.DisplayFormatters;
import com.biglybt.pif.download.DownloadStub;
import com.biglybt.pif.ui.tables.TableCell;
import com.biglybt.pif.ui.tables.TableCellRefreshListener;
import com.biglybt.pif.ui.tables.TableColumn;
import com.biglybt.pif.ui.tables.TableColumnExtraInfoListener;
import com.biglybt.pif.ui.tables.TableColumnInfo;
import com.biglybt.ui.swt.Utils;
import com.biglybt.ui.swt.views.table.TableCellSWT;

public class ColumnArchiveDLSize
	implements TableCellRefreshListener, TableColumnExtraInfoListener {
	public static String COLUMN_ID = "size";

	public ColumnArchiveDLSize(
		TableColumn column) {
		column.setWidth(70);
		column.setAlignment(TableColumn.ALIGN_TRAIL);

		column.addListeners(this);
	}

	@Override
	public void
	fillTableColumnInfo(
		TableColumnInfo info) {
		info.addCategories(new String[]{
			TableColumn.CAT_ESSENTIAL,
		});

		info.setProficiency(TableColumnInfo.PROFICIENCY_BEGINNER);
	}

	@Override
	public void
	refresh(
		TableCell cell) {
		DownloadStub dl = (DownloadStub) cell.getDataSource();

		long size = 0;

		if (dl != null) {

			size = dl.getTorrentSize();
		}


		if (!cell.setSortValue(size) && cell.isValid()) {

			//return;
		}

		if (!cell.isShown()) {

			return;
		}

		cell.setText(DisplayFormatters.formatByteCountToKiBEtc(size));

		if (Utils.getUserMode() > 0 && (cell instanceof TableCellSWT)) {
			if (size >= 0x40000000l) {
				((TableCellSWT) cell).setTextAlpha(200 | 0x100);
			} else if (size < 0x100000) {
				((TableCellSWT) cell).setTextAlpha(180);
			} else {
				((TableCellSWT) cell).setTextAlpha(255);
			}
		}
	}
}
