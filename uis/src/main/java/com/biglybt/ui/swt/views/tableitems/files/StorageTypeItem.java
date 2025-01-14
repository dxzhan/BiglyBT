/*
 * Copyright (C) Azureus Software, Inc, All Rights Reserved.
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

package com.biglybt.ui.swt.views.tableitems.files;

import com.biglybt.core.disk.DiskManagerFileInfo;
import com.biglybt.core.internat.MessageText;
import com.biglybt.pif.ui.tables.TableCell;
import com.biglybt.pif.ui.tables.TableCellRefreshListener;
import com.biglybt.pif.ui.tables.TableColumnInfo;
import com.biglybt.pif.ui.tables.TableManager;
import com.biglybt.ui.swt.views.table.CoreTableColumnSWT;


/**
 * @author Parg
 */
public class StorageTypeItem
	extends CoreTableColumnSWT
	implements TableCellRefreshListener {
	/**
	 * Default Constructor
	 */
	public StorageTypeItem() {
		super("storagetype", ALIGN_LEAD, POSITION_INVISIBLE, 70, TableManager.TABLE_TORRENT_FILES);
		setRefreshInterval(INTERVAL_LIVE);
	}

	@Override
	public void fillTableColumnInfo(TableColumnInfo info) {
		info.addCategories(new String[]{
			CAT_CONTENT,
		});
		info.setProficiency(TableColumnInfo.PROFICIENCY_INTERMEDIATE);
	}

	@Override
	public void refresh(TableCell cell) {
		DiskManagerFileInfo fileInfo = (DiskManagerFileInfo) cell.getDataSource();
		String tmp;
		if (fileInfo == null) {
			tmp = "";
		} else {
			int st = fileInfo.getStorageType();
			if (st < 0) {
				tmp = "";
			} else if (st == DiskManagerFileInfo.ST_LINEAR) {
				tmp = MessageText.getString("FileItem.storage.linear");
			} else if (st == DiskManagerFileInfo.ST_COMPACT) {
				tmp = MessageText.getString("FileItem.storage.compact");
			} else {
				tmp = MessageText.getString("FileItem.storage.reorder");
			}
		}
		cell.setText(tmp);
	}
}
