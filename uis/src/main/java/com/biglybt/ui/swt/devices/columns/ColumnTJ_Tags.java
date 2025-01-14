/*
 * Created on Feb 26, 2009
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

package com.biglybt.ui.swt.devices.columns;

import com.biglybt.core.devices.TranscodeFile;
import com.biglybt.pif.ui.tables.TableCell;
import com.biglybt.pif.ui.tables.TableCellRefreshListener;
import com.biglybt.pif.ui.tables.TableColumn;
import com.biglybt.pif.ui.tables.TableColumnExtraInfoListener;
import com.biglybt.pif.ui.tables.TableColumnInfo;

/**
 * @author TuxPaper
 * @created Feb 26, 2009
 */
public class ColumnTJ_Tags
	implements TableCellRefreshListener, TableColumnExtraInfoListener {
	public static final String COLUMN_ID = "tags";

	public ColumnTJ_Tags(TableColumn column) {
		column.initialize(TableColumn.ALIGN_LEAD, TableColumn.POSITION_INVISIBLE, 80);
		column.addListeners(this);
		column.setRefreshInterval(TableColumn.INTERVAL_LIVE);
		column.setType(TableColumn.TYPE_TEXT_ONLY);
	}

	@Override
	public void fillTableColumnInfo(TableColumnInfo info) {
		info.addCategories(new String[]{
			TableColumn.CAT_ESSENTIAL,
		});
		info.setProficiency(TableColumnInfo.PROFICIENCY_BEGINNER);
	}

	// @see com.biglybt.pif.ui.tables.TableCellRefreshListener#refresh(com.biglybt.pif.ui.tables.TableCell)
	@Override
	public void refresh(TableCell cell) {
		TranscodeFile tf = (TranscodeFile) cell.getDataSource();
		if (tf == null) {
			return;
		}

		String[] tags = tf.getTags(true);

		String str = "";

		for (String tag : tags) {

			str += (str.length() == 0 ? "" : ",") + tag;

		}

		cell.setText(str);
	}
}
