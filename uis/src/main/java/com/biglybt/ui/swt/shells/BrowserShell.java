/*
 * Created on Oct 13, 2008
 * Created by Paul Gardner
 *
 * Copyright (C) Azureus Software, Inc, All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */


package com.biglybt.ui.swt.shells;

import com.biglybt.core.internat.MessageText;

public class
BrowserShell {
	public BrowserShell(
		String title_resource,
		String url,
		int width,
		int height) {
		MessageBoxShell boxShell =
			new MessageBoxShell(
				MessageText.getString(title_resource),
				"",
				new String[]{
					//MessageText.getString("Button.ok")
				},
				0);

		boxShell.setUrl(url);

		boxShell.setBrowserFollowLinks(true);

		boxShell.setSquish(true);

		boxShell.setSize(width, height);

		boxShell.open(null);
	}
}
