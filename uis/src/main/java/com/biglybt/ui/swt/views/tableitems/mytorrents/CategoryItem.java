/*
 * File    : CategoryItem.java
 * Created : 01 feb. 2004
 * By      : TuxPaper
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details ( see the LICENSE file ).
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.biglybt.ui.swt.views.tableitems.mytorrents;

import com.biglybt.core.category.Category;
import com.biglybt.core.download.DownloadManager;
import com.biglybt.pif.download.Download;
import com.biglybt.pif.ui.tables.TableCell;
import com.biglybt.pif.ui.tables.TableCellRefreshListener;
import com.biglybt.pif.ui.tables.TableColumnInfo;
import com.biglybt.ui.swt.views.table.CoreTableColumnSWT;

/**
 * Display Category torrent belongs to.
 *
 * @author TuxPaper
 */
public class CategoryItem
	extends CoreTableColumnSWT
	implements TableCellRefreshListener {
	public static final Class DATASOURCE_TYPE = Download.class;

	public static final String COLUMN_ID = "category";

	/**
	 * Default Constructor
	 */
	public CategoryItem(String sTableID) {
		super(DATASOURCE_TYPE, COLUMN_ID, ALIGN_LEAD, 70, sTableID);
		setRefreshInterval(INTERVAL_LIVE);
	}

	@Override
	public void fillTableColumnInfo(TableColumnInfo info) {
		info.addCategories(new String[]{CAT_CONTENT});
	}

	@Override
	public void refresh(TableCell cell) {
		String sCategory = null;
		DownloadManager dm = (DownloadManager) cell.getDataSource();
		if (dm != null) {
			Category cat = dm.getDownloadState().getCategory();
			if (cat != null)
				sCategory = cat.getName();
		}
		cell.setText((sCategory == null) ? "" : sCategory);
	}
}
